# 🧠 Persistência de Estado & Configuração JSON

| Parâmetro | Especificação |
| :--- | :--- |
| **Classe de Configuração** | `net.instantgratification.collapsiblegamerules.GameRuleStateConfig` |
| **Caminho do Arquivo** | `.minecraft/config/collapsible-game-rules-state.json` |
| **Armazenamento em Memória** | `Set<String> expandedCategories = new HashSet<>()` |
| **Motor de Serialização** | `com.google.gson.Gson` (Pretty-Printing) |
| **Flag de Otimização I/O** | `private static boolean isDirty = false` |
| **Hook de Salvamento** | `ScreenMixin` intercepta `Screen.removed()` (`@At("HEAD")`) |
| **Chave de Persistência** | Chave de tradução (`TranslatableContents.getKey()`) ou string literal |

---

## 📖 Visão Geral

O Collapsible Game Rules conta com um sistema de persistência com controle de fluxo. Em vez de redefinir as categorias ao fechar a tela, o mod armazena quais estavam abertas ou fechadas entre sessões.

---

## 📄 Formato de Configuração JSON

O arquivo `.minecraft/config/collapsible-game-rules-state.json` salva um array simples e legível:

```json
[
  "gamerule.category.spawning",
  "gamerule.category.mobs",
  "gamerule.category.updates"
]
```

* **Presente no array**: A categoria está **EXPANDIDA**.
* **Ausente do array**: A categoria está **RECOLHIDA** (padrão).

---

## ⚡ Controle de I/O de Alta Performance

Gravar em disco a cada clique no mouse geraria travamentos desnecessários. Para garantir **zero perda de desempenho**, `GameRuleStateConfig` utiliza a flag `isDirty`:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       THROTTLED PERSISTENCE WORKFLOW                        │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Player clicks Category Header                                             │
│        │                                                                    │
│        ▼                                                                    │
│   GameRuleStateConfig.setExpanded(key, state)                               │
│        ├─ Updates in-memory HashSet<String> in 0.0001 μs                    │
│        └─ Marks: isDirty = true (ZERO DISK I/O)                             │
│                                                                             │
│   Player closes Game Rules Screen (Esc, Done, or Cancel)                    │
│        │                                                                    │
│        ▼                                                                    │
│   ScreenMixin.collapsible_game_rules$onRemoved()                            │
│        │                                                                    │
│        ▼                                                                    │
│   GameRuleStateConfig.saveIfDirty()                                         │
│        ├─ Checks: if (isDirty) Ellipsis                                      │
│        ├─ Writes JSON to disk in background buffer                          │
│        └─ Resets: isDirty = false                                           │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 💻 Métodos da API

### Métodos Públicos de `GameRuleStateConfig`

| Assinatura | Retorno | Descrição |
| :--- | :--- | :--- |
| `load()` | `void` | Carrega o arquivo `collapsible-game-rules-state.json` na inicialização. |
| `save()` | `void` | Grava o conjunto `expandedCategories` no disco usando `Files.newBufferedWriter`. |
| `saveIfDirty()` | `void` | Salva no disco apenas se `isDirty == true`, redefinindo a flag em seguida. |
| `isExpanded(String categoryKey)` | `boolean` | Verifica se a categoria está no conjunto `expandedCategories`. |
| `setExpanded(String categoryKey, boolean expanded)` | `void` | Adiciona ou remove a chave e marca `isDirty = true`. |
| `expandAll(Iterable<String> allKeys)` | `void` | Adiciona todas as chaves em massa e marca `isDirty = true`. |
| `collapseAll()` | `void` | Limpa todas as entradas e marca `isDirty = true`. |

---

## 🔒 Salvamento ao Fechar a Tela (`ScreenMixin`)

```java
@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Inject(method = "removed", at = @At("HEAD"))
    private void collapsible_game_rules$onRemoved(CallbackInfo ci) {
        if ((Object) this instanceof AbstractGameRulesScreen) {
            GameRuleStateConfig.saveIfDirty();
        }
    }
}
```

Isso garante que todas as escolhas do jogador sejam salvas ao sair, seja clicando em **Concluído**, **Cancelar** ou pressionando a tecla **Escape**.

---

## 🔗 Documentação Relacionada

* [[🏠 Visão Geral & Página Inicial|pt_br-Home]]
* [[🌎 Ações Globais & Alternância em Massa|pt_br-Global-Actions-and-Bulk-Toggles]]
* [[🧩 Arquitetura & Subsistema Mixin|pt_br-Architecture-and-Mixins]]
