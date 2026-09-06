# 🗂️ Categorias Dobráveis

| Parâmetro | Especificação |
| :--- | :--- |
| **Componente do Sistema** | `CollapsibleCategoryRuleEntry` (Classe interna) |
| **Mixin Responsável** | `AbstractGameRulesScreenRuleListMixin` |
| **Classe Alvo** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList` |
| **Ícones de Estado** | Expandido: `▼ ` \| Recolhido: `▶ ` |
| **Formato da Contagem** | ` (N rules)` (`ChatFormatting.GRAY`) |
| **Destaque ao Passar o Mouse**| `0x22FFFFFF` (Caixa branca com 25% de opacidade) |
| **Linha Divisória Inferior** | `0x44AAAAAA` (Linha sutil) |
| **Cor do Texto** | Com foco: `0xFFFFFFAA` \| Padrão: `0xFFFFFFFF` |
| **Áudio de Interação** | `net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK` (Volume: `1.0F`) |
| **Tipo de Narração** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 Visão Geral

No Minecraft nativo, a tela de regras apresenta os títulos das categorias como simples rótulos estáticos (`CategoryRuleEntry`), listando todas as regras em uma única lista contínua. Em modpacks grandes, o menu se torna excessivamente extenso.

**Collapsible Categories** substitui esses rótulos estáticos por widgets dinâmicos `CollapsibleCategoryRuleEntry`, que podem ser expandidos e recolhidos conforme a necessidade do jogador.

---

## 🎨 Layout Visual e Estrutura em Árvore

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ [ Expand All ]                                             [ Collapse All ] │ ◄── GlobalActionsRuleEntry (Index 0)
├─────────────────────────────────────────────────────────────────────────────┤
│ ▼ ⚔️ Mobs (14 rules)                                                        │ ◄── CollapsibleCategoryRuleEntry (Expanded)
│ ─────────────────────────────────────────────────────────────────────────── │
│   mobGriefing                                                     [ ON ]    │ ◄── Child RuleEntry
│   doMobSpawning                                                   [ ON ]    │ ◄── Child RuleEntry
│   doMobLoot                                                       [ ON ]    │ ◄── Child RuleEntry
├─────────────────────────────────────────────────────────────────────────────┤
│ ▶ 👤 Player (8 rules)                                                       │ ◄── CollapsibleCategoryRuleEntry (Collapsed)
│ ─────────────────────────────────────────────────────────────────────────── │
│ ▼ 🌧️ Updates (6 rules)                                                      │ ◄── CollapsibleCategoryRuleEntry (Expanded)
│ ─────────────────────────────────────────────────────────────────────────── │
│   doFireTick                                                      [ ON ]    │ ◄── Child RuleEntry
│   randomTickSpeed                                                 [ 3  ]    │ ◄── Child RuleEntry
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## ⚙️ Mecânicas Técnicas

### 1. Algoritmo de Contagem de Regras
Durante a execução de `updateVisibleEntries()`, a lista `allEntries` é percorrida para contar quantas regras pertencem à categoria até o próximo cabeçalho:

```java
int childCount = 0;
for (int j = i + 1; j < this.collapsible_game_rules$allEntries.size(); j++) {
    if (this.collapsible_game_rules$allEntries.get(j) instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
        break;
    }
    childCount++;
}
```

### 2. Pipeline de Renderização (`extractContent`)
Utiliza a interface gráfica `GuiGraphicsExtractor`:
1. **Caixa de Foco**: Ao passar o cursor, desenha-se um retângulo `0x22FFFFFF` entre `[getX() - 2, getY()]` e `[getX() + getWidth() + 2, getY() + 24]`.
2. **Seta e Texto**: Prefixo (`▼ ` ou `▶ `), rótulo da categoria e a contagem cinza (` (N rules)`).
3. **Centralização**: Desenhado horizontalmente em `getContentXMiddle()` com deslocamento vertical `getContentY() + 5`.
4. **Separador Inferior**: Uma linha sutil `0x44AAAAAA` em `getY() + 23` demarca o limite inferior.

### 3. Gerenciamento de Cliques
Em `mouseClicked(MouseButtonEvent event, boolean doubleClick)`:
* **Botão Esquerdo (`event.button() == 0`)** ou **Botão Direito (`event.button() == 1`)**:
  1. Aciona o callback `toggleAction.run()`.
  2. Atualiza o estado booleano em `GameRuleStateConfig`.
  3. Toca o áudio de clique: `SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)`.
  4. Executa `updateVisibleEntries()` para atualizar os itens visíveis.
  5. Recalcula as medidas com `updateSizeAndPosition(...)`.

### 4. Acessibilidade e Leitor de Tela
Implementa a interface `NarratableEntry`:
* **Prioridade**: `NarrationPriority.HOVERED`
* **Saída**: Registra o título como `NarratedElementType.TITLE` para leitores de tela.

---

## 🔗 Documentação Relacionada

* [[🏠 Visão Geral & Página Inicial|pt_br-Home]]
* [[🌎 Ações Globais & Alternância em Massa|pt_br-Global-Actions-and-Bulk-Toggles]]
* [[⌨️ Navegação por Teclado & Acessibilidade|pt_br-Keyboard-Navigation]]
* [[🧩 Arquitetura & Subsistema Mixin|pt_br-Architecture-and-Mixins]]
