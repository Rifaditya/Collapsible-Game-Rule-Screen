# 🔍 Integração de Busca Inteligente

| Parámetro | Especificação |
| :--- | :--- |
| **Método Interceptado** | `populateChildren(Ljava/lang/String;)V` |
| **Ponto de Injeção** | `@At("TAIL")` |
| **Classe Mixin** | `AbstractGameRulesScreenRuleListMixin` |
| **Normalização do Texto** | `filter.toLowerCase(java.util.Locale.ROOT)` |
| **Flag de Pesquisa Ativa** | `isSearching = !currentFilter.isEmpty()` |
| **Regra de Desdobramento** | `isExpanded = isSearching || GameRuleStateConfig.isExpanded(key)` |
| **Método de Atualização** | `collapsible_game_rules$updateVisibleEntries()` |

---

## 📖 Visão Geral

No menu padrão, o campo de busca filtra regras pelo nome ou descrição. Em uma interface dobrável, uma filtragem direta esconderia os resultados dentro de categorias fechadas.

A **Busca Inteligente** resolve isso monitorando o que é digitado: havendo termos na busca, qualquer categoria que possua regras correspondentes é expandida dinamicamente, permitindo visualizar os resultados sem cliques adicionais.

---

## ⚙️ Fluxo da Busca Inteligente

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          SMART SEARCH PIPELINE                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Player types query into Search Bar (e.g. "fire")                          │
│        │                                                                    │
│        ▼                                                                    │
│   Vanilla AbstractGameRulesScreen.RuleList.populateChildren("fire")         │
│   (Filters the internal list to matching rules & their category headers)    │
│        │                                                                    │
│        ▼ (@Inject at TAIL)                                                  │
│   AbstractGameRulesScreenRuleListMixin.collapsible_game_rules$onPopulate... │
│        ├─ Stores normalized query: filter.toLowerCase(Locale.ROOT)          │
│        ├─ Captures filtered list: allEntries = new ArrayList<>(children())  │
│        └─ Calls updateVisibleEntries()                                      │
│             │                                                               │
│             ▼                                                               │
│        isSearching = !currentFilter.isEmpty() (Evaluates to TRUE)           │
│             │                                                               │
│             ▼                                                               │
│        Every present category header is forced isExpanded = TRUE            │
│        All matched child rules render immediately!                          │
│                                                                             │
│   Player clears Search Bar ("")                                             │
│        │                                                                    │
│        ▼                                                                    │
│   isSearching = FALSE ──> Reverts to persistent GameRuleStateConfig states! │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 💻 Detalhes Técnicos de Implementação

### 1. Ponto de Injeção `@Inject`
O mixin intercepta o final do método `populateChildren`:

```java
@Inject(method = "populateChildren(Ljava/lang/String;)V", at = @At("TAIL"))
private void collapsible_game_rules$onPopulateChildren(String filter, CallbackInfo ci) {
    this.collapsible_game_rules$currentFilter = (filter != null) ? filter.toLowerCase(java.util.Locale.ROOT) : "";
    // Save the currently generated list of all entries
    this.collapsible_game_rules$allEntries = new ArrayList<>(this.children());
    this.collapsible_game_rules$updateVisibleEntries();
}
```

### 2. Avaliação Dinâmica do Desdobramento
Em `updateVisibleEntries()`:

```java
boolean isSearching = !this.collapsible_game_rules$currentFilter.isEmpty();
final String finalPersistenceKey = persistenceKey;
boolean isExpanded = isSearching || GameRuleStateConfig.isExpanded(finalPersistenceKey);
```

### 3. Restauração Não Destrutiva de Estado
Como `isSearching` é uma condição temporária que só atua durante a digitação, limpar o campo de busca restabelece imediatamente as preferências salvas no `GameRuleStateConfig` sem modificar o arquivo de configuração no disco.

---

## 🔗 Documentação Relacionada

* [[🏠 Visão Geral & Página Inicial|pt_br-Home]]
* [[🗂️ Categorias Dobráveis|pt_br-Collapsible-Categories]]
* [[🧠 Persistência de Estado & Configuração JSON|pt_br-State-Persistence-and-Config]]
* [[🧩 Arquitetura & Subsistema Mixin|pt_br-Architecture-and-Mixins]]
