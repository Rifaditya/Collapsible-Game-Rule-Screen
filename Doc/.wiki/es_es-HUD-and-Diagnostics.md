# 🖥️ HUD, Diagnósticos y Renderizado de UI

| Parámetro | Especificación |
| :--- | :--- |
| **Motor Gráfico** | `net.minecraft.client.gui.GuiGraphicsExtractor` |
| **Contexto de Pantalla** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen` |
| **Color de Texto al Enfocar** | `0xFFFFFFAA` (Amarillo suave) |
| **Color de Texto Normal** | `0xFFFFFFFF` (Blanco nítido) |
| **Caja de Fondo al Enfocar** | `0x22FFFFFF` (Blanco translúcido) |
| **Separador de Categoría** | `0x44AAAAAA` (Línea gris delimitadora) |
| **Fondo de Botón Activo** | `0x4400FF00` (Verde esmeralda) |
| **Fondo de Botón Inactivo** | `0x44FF0000` (Rojo rubí) |
| **Narración de Pantalla** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 Visión General

Minecraft 26.2 reestructuró el pipeline gráfico del cliente, trasladando las operaciones de dibujo a la arquitectura moderna `GuiGraphicsExtractor`.

Collapsible Game Rules está diseñado de forma nativa para este motor, aplicando rellenos vectoriales directos, centrado métrico tipográfico y lectura accesible sin caída de fotogramas.

---

## 🎨 Paleta de Colores de la Interfaz

| Componente | Código Hex ARGB | Descripción Visual | Uso |
| :--- | :--- | :--- | :--- |
| **Fondo al Pasar Cursor** | `0x22FFFFFF` | Recuadro blanco con 13% de opacidad. | Encabezado de categorías y botones globales. |
| **Línea Separadora** | `0x44AAAAAA` | Línea gris clara de 1px con 27% de opacidad. | Borde inferior de categorías y acciones globales. |
| **Texto con Cursor Encima** | `0xFFFFFFAA` | Tono amarillo claro destacado. | Nombres de categorías y botones enfocados. |
| **Texto Estándar** | `0xFFFFFFFF` | Blanco puro al 100%. | Nombres de categorías e insignias. |
| **Insignia de Conteo** | `ChatFormatting.GRAY` | Gris Vanilla (` (N rules)`). | Sufijo añadido tras el nombre de la categoría. |
| **Fondo Activado** | `0x4400FF00` | Verde esmeralda con 27% de opacidad. | Fondo para estado `✔ ON` en `BooleanToggleWidget`. |
| **Fondo Desactivado** | `0x44FF0000` | Rojo rubí con 27% de opacidad. | Fondo para estado `✖ OFF` en `BooleanToggleWidget`. |

---

## 💻 Implementación de Renderizado

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

## 🔊 Sonido y Respuestas Táctiles

* **Evento**: `SoundEvents.UI_BUTTON_CLICK`
* **Volumen**: `1.0F`
* **Tono**: `1.0F`
* **Momentos de Disparo**:
  - Clic izquierdo o derecho en el encabezado de una categoría.
  - Teclas Espacio / Enter sobre una categoría.
  - Flecha izquierda (plegar) o Flecha derecha (desplegar).
  - Clic en los botones «Expand All» o «Collapse All».

---

## 🔗 Documentación Relacionada

* [[🏠 Resumen y Portal Principal|es_es-Home]]
* [[🗂️ Categorías Desplegables|es_es-Collapsible-Categories]]
* [[⌨️ Navegación por Teclado y Accesibilidad|es_es-Keyboard-Navigation]]
* [[🧩 Arquitectura y Subsistema Mixin|es_es-Architecture-and-Mixins]]
