# 🗂️ Categorías Desplegables

| Parámetro | Especificación |
| :--- | :--- |
| **Componente del Sistema** | `CollapsibleCategoryRuleEntry` (Clase interna) |
| **Mixin Contenedor** | `AbstractGameRulesScreenRuleListMixin` |
| **Clase Objetivo** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList` |
| **Iconos de Estado** | Desplegado: `▼ ` \| Plegado: `▶ ` |
| **Insignia de Conteo** | ` (N rules)` (`ChatFormatting.GRAY`) |
| **Color al Pasar el Cursor** | `0x22FFFFFF` (25% Blanco semitransparente) |
| **Línea Separadora Inferior** | `0x44AAAAAA` (Delimitador sutil) |
| **Color del Texto** | Con cursor encima: `0xFFFFFFAA` \| Normal: `0xFFFFFFFF` |
| **Sonido de Clic** | `net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK` (Volumen: `1.0F`) |
| **Tipo de Narración** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 Visión General

En Minecraft Vanilla, la pantalla de reglas de juego muestra los nombres de categorías como texto estático (`CategoryRuleEntry`), listando todas las reglas en un panel interminable. Al añadir mods con decenas de parámetros, la pantalla resulta difícil de examinar.

**Collapsible Categories** sustituye estas etiquetas por widgets interactivos `CollapsibleCategoryRuleEntry` que pueden expandirse o contraerse a voluntad.

---

## 🎨 Diseño Visual y Jerarquía

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

## ⚙️ Mecánicas Técnicas

### 1. Algoritmo de Conteo de Reglas
En `updateVisibleEntries()`, se calcula el número de reglas pertenecientes a cada categoría antes del siguiente encabezado:

```java
int childCount = 0;
for (int j = i + 1; j < this.collapsible_game_rules$allEntries.size(); j++) {
    if (this.collapsible_game_rules$allEntries.get(j) instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
        break;
    }
    childCount++;
}
```

### 2. Pipeline de Renderizado (`extractContent`)
Utiliza la interfaz moderna `GuiGraphicsExtractor`:
1. **Recuadro al pasar el cursor**: Se dibuja un fondo `0x22FFFFFF` entre `[getX() - 2, getY()]` y `[getX() + getWidth() + 2, getY() + 24]`.
2. **Flecha y Texto**: Prefijo (`▼ ` o `▶ `), etiqueta de categoría e insignia de conteo gris (` (N rules)`).
3. **Alineación Centrada**: Dibujado horizontalmente en `getContentXMiddle()` con desplazamiento vertical `getContentY() + 5`.
4. **Separador Inferior**: Línea delimitadora `0x44AAAAAA` en `getY() + 23`.

### 3. Gestión de Clics del Ratón
En `mouseClicked(MouseButtonEvent event, boolean doubleClick)`:
* **Clic Izquierdo (`event.button() == 0`)** o **Clic Derecho (`event.button() == 1`)**:
  1. Ejecuta `toggleAction.run()`.
  2. Actualiza el estado booleano en `GameRuleStateConfig`.
  3. Reproduce el sonido de clic: `SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)`.
  4. Llama a `updateVisibleEntries()` para actualizar los elementos visibles.
  5. Recalcula las dimensiones con `updateSizeAndPosition(...)`.

### 4. Accesibilidad y Narración de Pantalla
Implementa `NarratableEntry`:
* **Prioridad**: `NarrationPriority.HOVERED`
* **Salida**: La etiqueta se envía como `NarratedElementType.TITLE` para lectores de pantalla.

---

## 🔗 Documentación Relacionada

* [[🏠 Resumen y Portal Principal|es_es-Home]]
* [[🌎 Acciones Globales y Alternancia Masiva|es_es-Global-Actions-and-Bulk-Toggles]]
* [[⌨️ Navegación por Teclado y Accesibilidad|es_es-Keyboard-Navigation]]
* [[🧩 Arquitectura y Subsistema Mixin|es_es-Architecture-and-Mixins]]
