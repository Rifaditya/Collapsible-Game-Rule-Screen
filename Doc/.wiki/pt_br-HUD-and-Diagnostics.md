# 🖥️ HUD, Diagnósticos & Renderização de Interface

| Parâmetro | Especificação |
| :--- | :--- |
| **Motor Gráfico** | `net.minecraft.client.gui.GuiGraphicsExtractor` |
| **Contexto de Tela** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen` |
| **Cor do Texto ao Focar** | `0xFFFFFFAA` (Destaque amarelo suave) |
| **Cor do Texto Normal** | `0xFFFFFFFF` (Branco nítido) |
| **Caixa de Foco Superior** | `0x22FFFFFF` (Branco translúcido) |
| **Divisória de Categoria** | `0x44AAAAAA` (Borda horizontal) |
| **Fundo de Botão Ativo** | `0x4400FF00` (Verde esmeralda) |
| **Fundo de Botão Inativo** | `0x44FF0000` (Vermelho rubi) |
| **Narração de Acessibilidade** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 Visão Geral

O Minecraft 26.2 modernizou profundamente a estrutura de renderização do cliente, migrando comandos convencionais de desenho para o subsistema `GuiGraphicsExtractor`.

O Collapsible Game Rules aproveita esse motor graficamente moderno para desenho direto de polígonos, centralização baseada na tipografia e suporte a leitores de tela sem perda de quadros por segundo.

---

## 🎨 Paleta de Cores da Interface

| Componente Visual | Código Hex ARGB | Descrição | Onde é Aplicado |
| :--- | :--- | :--- | :--- |
| **Preenchimento de Foco** | `0x22FFFFFF` | Fundo branco com 13% de opacidade. | Destaque de categorias e botões globais. |
| **Borda Divisória** | `0x44AAAAAA` | Linha cinza clara de 1px com 27% de opacidade. | Parte inferior de categorias e ações. |
| **Texto com Cursor** | `0xFFFFFFAA` | Amarelo suave luminoso. | Rótulos de categorias e botões ao passar o mouse. |
| **Texto Padrão** | `0xFFFFFFFF` | Branco 100% puro. | Títulos principais e contagem de regras. |
| **Emblema de Contagem** | `ChatFormatting.GRAY` | Cinza Vanilla (` (N rules)`). | Sufixo adicionado aos títulos de categoria. |
| **Fundo do Modo Ligado** | `0x4400FF00` | Verde esmeralda com 27% de opacidade. | Fundo para o estado `✔ ON` em `BooleanToggleWidget`. |
| **Fundo do Modo Desligado** | `0x44FF0000` | Vermelho rubi com 27% de opacidade. | Fundo para o estado `✖ OFF` em `BooleanToggleWidget`. |

---

## 💻 Implementação do Renderizador

```java
@Override
public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
    // 1. Premium Highlight on hover
    if (hovered) {
        graphics.fill(this.getX() - 2, this.getY(), this.getX() + this.getWidth() + 2, this.getY() + 24, 0x22FFFFFF);
    }

    // 2. Directional arrow, label, and child count badge
    String prefix = this.expanded ? "▼ " : "▶ ";
    Component countBadge = Component.literal(" (" + this.childCount + " rules)").withStyle(ChatFormatting.GRAY);
    Component display = Component.literal(prefix).append(this.label).append(countBadge);

    // 3. Centered text with dynamic hover tint
    graphics.centeredText(Minecraft.getInstance().font, display,
            this.getContentXMiddle(), this.getContentY() + 5, hovered ? 0xFFFFFFAA : 0xFFFFFFFF);
    
    // 4. Subtle separating line at the bottom
    graphics.fill(this.getX() + 10, this.getY() + 23, this.getX() + this.getWidth() - 10, this.getY() + 24, 0x44AAAAAA);
}
```

---

## 🔊 Retorno Sonoro e Acessibilidade

* **Evento**: `SoundEvents.UI_BUTTON_CLICK`
* **Volume**: `1.0F`
* **Pitch**: `1.0F`
* **Disparadores**:
  - Clique esquerdo ou direito sobre um cabeçalho.
  - Teclas Espaço / Enter sobre uma categoria focada.
  - Seta esquerda (recolher) ou Seta direita (expandir).
  - Cliques em «Expand All» ou «Collapse All».

---

## 🔗 Documentação Relacionada

* [[🏠 Visão Geral & Página Inicial|pt_br-Home]]
* [[🗂️ Categorias Dobráveis|pt_br-Collapsible-Categories]]
* [[⌨️ Navegação por Teclado & Acessibilidade|pt_br-Keyboard-Navigation]]
* [[🧩 Arquitetura & Subsistema Mixin|pt_br-Architecture-and-Mixins]]
