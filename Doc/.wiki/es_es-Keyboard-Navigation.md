# ⌨️ Navegación por Teclado y Accesibilidad

| Parámetro | Especificación |
| :--- | :--- |
| **Clase del Componente** | `CollapsibleCategoryRuleEntry` |
| **Interfaz de Accesibilidad** | `net.minecraft.client.gui.narration.NarratableEntry` |
| **Prioridad de Narración** | `NarrationPriority.HOVERED` |
| **Elemento Narrado** | `NarratedElementType.TITLE` |
| **Teclas de Alternancia** | `GLFW_KEY_SPACE`, `GLFW_KEY_ENTER`, `GLFW_KEY_KP_ENTER` |
| **Tecla de Plegado** | `GLFW_KEY_LEFT` (Sólo cuando está desplegado) |
| **Tecla de Despliegue** | `GLFW_KEY_RIGHT` (Sólo cuando está plegado) |
| **Sonido de Interacción** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 Visión General

Collapsible Game Rules incluye soporte completo para navegación con teclado y narración en pantalla, permitiendo configurar todas las opciones mediante teclado, mando o lectores de pantalla sin depender del ratón.

---

## ⌨️ Asignación de Teclas

Cuando un encabezado de categoría tiene el foco en `RuleList`, el método `keyPressed(KeyEvent event)` responde a los siguientes eventos:

| Tecla | Constante GLFW | Acción | Condición | Sonido |
| :--- | :--- | :--- | :--- | :--- |
| **Espacio** | `GLFW_KEY_SPACE` | Alternar Despliegue | Siempre | `UI_BUTTON_CLICK` |
| **Enter** | `GLFW_KEY_ENTER` | Alternar Despliegue | Siempre | `UI_BUTTON_CLICK` |
| **Enter Numérico** | `GLFW_KEY_KP_ENTER` | Alternar Despliegue | Siempre | `UI_BUTTON_CLICK` |
| **Flecha Izquierda (←)** | `GLFW_KEY_LEFT` | **Plegar Categoría** | Sólo si `expanded == true` | `UI_BUTTON_CLICK` |
| **Flecha Derecha (→)** | `GLFW_KEY_RIGHT` | **Desplegar Categoría** | Sólo si `expanded == false` | `UI_BUTTON_CLICK` |

---

## ⚙️ Implementación Técnica

### 1. Procesamiento de Teclas Direccionales
Sigue los convenios estándar de navegación en árbol de los sistemas operativos:

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

### 2. Narración para Accesibilidad (`updateNarration`)

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

## 🔗 Documentación Relacionada

* [[🏠 Resumen y Portal Principal|es_es-Home]]
* [[🗂️ Categorías Desplegables|es_es-Collapsible-Categories]]
* [[🖥️ HUD, Diagnósticos y Renderizado de UI|es_es-HUD-and-Diagnostics]]
* [[🧩 Arquitectura y Subsistema Mixin|es_es-Architecture-and-Mixins]]
