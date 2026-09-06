# 🌎 Ações Globais & Alternância em Massa

| Parâmetro | Especificação |
| :--- | :--- |
| **Classe do Componente** | `net.instantgratification.collapsiblegamerules.ui.GlobalActionsRuleEntry` |
| **Posição na Lista** | Índice `0` (Sempre fixado no topo de `RuleList`) |
| **Botão Esquerdo** | `[ Expand All ]` (`gui.collapsible-game-rules.expand_all`) |
| **Botão Direito** | `[ Collapse All ]` (`gui.collapsible-game-rules.collapse_all`) |
| **Centro do Botão Esquerdo** | `this.getX() + this.getWidth() / 4` |
| **Centro do Botão Direito** | `this.getX() + 3 * this.getWidth() / 4` |
| **Destaque ao Passar o Mouse**| `0x22FFFFFF` (Aplicado sobre a metade ativa) |
| **Separador Inferior** | `0x44AAAAAA` |
| **Som de Clique** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 Visão Geral

Ao lidar com centenas de regras, expandir ou recolher cada pasta manualmente se torna demorado.

A **Barra de Ações Globais** fica fixada no **Índice 0**, permitindo expandir ou recolher todas as categorias de uma só vez com um simples clique.

---

## 🎨 Layout Dividido e Interação

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ◄──────────────── Left Half ───────────────►◄────────────── Right Half ───► │
│               [ Expand All ]                               [ Collapse All ] │
│ ─────────────────────────────────────────────────────────────────────────── │
└─────────────────────────────────────────────────────────────────────────────┘
```

* **Zona Esquerda (`mouseX < getX() + getWidth() / 2`)**: Executa `expandAll`.
* **Zona Direita (`mouseX >= getX() + getWidth() / 2`)**: Executa `collapseAll`.
* **Foco do Cursor**: Destaca a metade sobreposta com preenchimento `0x22FFFFFF` e texto em `0xFFFFFFAA`.

---

## ⚙️ Mecânicas Técnicas

### 1. Injeção Fixa no Índice 0
Em `AbstractGameRulesScreenRuleListMixin`:

```java
if (!this.collapsible_game_rules$allEntries.isEmpty()) {
    this.addEntry(new GlobalActionsRuleEntry(
        () -> {
            List<String> allKeys = this.collapsible_game_rules$allEntries.stream()
                .filter(e -> e instanceof AbstractGameRulesScreen.CategoryRuleEntry)
                .map(e -> {
                    Component lbl = ((CategoryRuleEntryAccessor) e).collapsible_game_rules$getLabel();
                    if (lbl.getContents() instanceof TranslatableContents translatable) {
                        return translatable.getKey();
                    }
                    return lbl.getString();
                })
                .toList();
            GameRuleStateConfig.expandAll(allKeys);
            this.collapsible_game_rules$updateVisibleEntries();
        },
        () -> {
            GameRuleStateConfig.collapseAll();
            this.collapsible_game_rules$updateVisibleEntries();
        }
    ));
}
```

### 2. Identificação das Categorias
1. Filtra as entradas do tipo `CategoryRuleEntry`.
2. Acessa o rótulo via `CategoryRuleEntryAccessor`.
3. Se contiver `TranslatableContents`, obtém a chave de tradução (`gamerule.category.spawning`).
4. Caso contrário, utiliza a string direta.
5. Agrupa em lista imutável Java 25 (`.toList()`) e aciona `GameRuleStateConfig.expandAll(allKeys)`.

### 3. Gerenciamento do Clique
Em `GlobalActionsRuleEntry.mouseClicked(MouseButtonEvent event, boolean doubleClick)`:
```java
if (event.button() == 0 || event.button() == 1) {
    double mouseX = event.x();
    if (mouseX < this.getX() + this.getWidth() / 2.0) {
        this.expandAll.run();
    } else {
        this.collapseAll.run();
    }
    Minecraft.getInstance().getSoundManager().play(
        SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)
    );
    return true;
}
```

---

## 🌐 Chaves de Localização

```json
{
  "gui.collapsible-game-rules.expand_all": "Expand All",
  "gui.collapsible-game-rules.collapse_all": "Collapse All"
}
```

---

## 🔗 Documentação Relacionada

* [[🏠 Visão Geral & Página Inicial|pt_br-Home]]
* [[🗂️ Categorias Dobráveis|pt_br-Collapsible-Categories]]
* [[🧠 Persistência de Estado & Configuração JSON|pt_br-State-Persistence-and-Config]]
* [[🧩 Arquitetura & Subsistema Mixin|pt_br-Architecture-and-Mixins]]
