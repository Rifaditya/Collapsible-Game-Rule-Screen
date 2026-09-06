# ⌨️ Navegação por Teclado & Acessibilidade

| Parâmetro | Especificação |
| :--- | :--- |
| **Classe do Componente** | `CollapsibleCategoryRuleEntry` |
| **Interface de Acessibilidade**| `net.minecraft.client.gui.narration.NarratableEntry` |
| **Prioridade de Narração** | `NarrationPriority.HOVERED` |
| **Elemento Narrado** | `NarratedElementType.TITLE` |
| **Teclas de Alternância** | `GLFW_KEY_SPACE`, `GLFW_KEY_ENTER`, `GLFW_KEY_KP_ENTER` |
| **Tecla de Recolhimento** | `GLFW_KEY_LEFT` (Apenas quando expandido) |
| **Tecla de Expansão** | `GLFW_KEY_RIGHT` (Apenas quando recolhido) |
| **Retorno Sonoro** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 Visão Geral

O Collapsible Game Rules oferece suporte completo à navegação por teclado e leitura de tela, garantindo que jogadores possam configurar o mundo usando teclado, controles ou leitores de tela sem necessidade do mouse.

---

## ⌨️ Tabela de Atalhos de Teclado

Quando um cabeçalho está em foco no `RuleList`, o método `keyPressed(KeyEvent event)` gerencia as seguintes teclas:

| Tecla | Constante GLFW | Ação Executada | Condição | Retorno Sonoro |
| :--- | :--- | :--- | :--- | :--- |
| **Espaço** | `GLFW_KEY_SPACE` | Alternar Estado | Sempre | `UI_BUTTON_CLICK` |
| **Enter** | `GLFW_KEY_ENTER` | Alternar Estado | Sempre | `UI_BUTTON_CLICK` |
| **Enter Numérico** | `GLFW_KEY_KP_ENTER` | Alternar Estado | Sempre | `UI_BUTTON_CLICK` |
| **Seta Esquerda (←)** | `GLFW_KEY_LEFT` | **Recolher Categoria** | Apenas se `expanded == true` | `UI_BUTTON_CLICK` |
| **Seta Direita (→)** | `GLFW_KEY_RIGHT` | **Expandir Categoria** | Apenas se `expanded == false` | `UI_BUTTON_CLICK` |

---

## ⚙️ Implementação Técnica

### 1. Processamento das Setas
Segue o padrão clássico de árvores hierárquicas de sistemas operacionais:

```java
@Override
public boolean keyPressed(KeyEvent event) {
    int keyCode = event.key();
    if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER || keyCode == GLFW.GLFW_KEY_SPACE) {
        this.toggleAction.run();
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        return true;
    } else if (keyCode == GLFW.GLFW_KEY_LEFT && this.expanded) {
        this.toggleAction.run();
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        return true;
    } else if (keyCode == GLFW.GLFW_KEY_RIGHT && !this.expanded) {
        this.toggleAction.run();
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        return true;
    }
    return super.keyPressed(event);
}
```

### 2. Leitor de Tela (`updateNarration`)

```java
@Override
public NarrationPriority narrationPriority() {
    return NarrationPriority.HOVERED;
}

@Override
public void updateNarration(NarrationElementOutput output) {
    output.add(NarratedElementType.TITLE, this.label);
}
```

---

## 🔗 Documentação Relacionada

* [[🏠 Visão Geral & Página Inicial|pt_br-Home]]
* [[🗂️ Categorias Dobráveis|pt_br-Collapsible-Categories]]
* [[🖥️ HUD, Diagnósticos & Renderização de Interface|pt_br-HUD-and-Diagnostics]]
* [[🧩 Arquitetura & Subsistema Mixin|pt_br-Architecture-and-Mixins]]
