# 🌎 Acciones Globales y Alternancia Masiva

| Parámetro | Especificación |
| :--- | :--- |
| **Clase del Componente** | `net.instantgratification.collapsiblegamerules.ui.GlobalActionsRuleEntry` |
| **Posición en Lista** | Índice `0` (Fijado en la parte superior de `RuleList`) |
| **Botón Izquierdo** | `[ Expand All ]` (`gui.collapsible-game-rules.expand_all`) |
| **Botón Derecho** | `[ Collapse All ]` (`gui.collapsible-game-rules.collapse_all`) |
| **Centro del Botón Izquierdo** | `this.getX() + this.getWidth() / 4` |
| **Centro del Botón Derecho** | `this.getX() + 3 * this.getWidth() / 4` |
| **Color al Pasar el Cursor** | `0x22FFFFFF` (Aplicado sobre la mitad enfocada) |
| **Línea Separadora Inferior** | `0x44AAAAAA` |
| **Sonido de Clic** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 Visión General

En modpacks extensos, expandir o contraer cada categoría una a una resulta tedioso.

**Acciones Globales** introduce una barra superior anclada en el **Índice 0**, permitiendo expandir o contraer todas las categorías del juego con un solo clic.

---

## 🎨 Diseño Visual y Botón Dividido

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ◄──────────────── Left Half ───────────────►◄────────────── Right Half ───► │
│               [ Expand All ]                               [ Collapse All ] │
│ ─────────────────────────────────────────────────────────────────────────── │
└─────────────────────────────────────────────────────────────────────────────┘
```

* **Zona Izquierda (`mouseX < getX() + getWidth() / 2`)**: Activa `expandAll`.
* **Zona Derecha (`mouseX >= getX() + getWidth() / 2`)**: Activa `collapseAll`.
* **Cursor encima**: Resalta únicamente la mitad enfocada con tinte `0x22FFFFFF` y texto `0xFFFFFFAA`.

---

## ⚙️ Mecánicas Técnicas

### 1. Inserción Anclada en el Índice 0
En `AbstractGameRulesScreenRuleListMixin`, la cabecera se inyecta antes de las categorías:

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

### 2. Extracción de Identificadores de Categoría
1. Filtra las entradas de tipo `CategoryRuleEntry`.
2. Obtiene el texto de la etiqueta mediante `CategoryRuleEntryAccessor`.
3. Si contiene `TranslatableContents`, extrae la clave (`gamerule.category.spawning`).
4. Si no está traducida, utiliza el texto literal.
5. Genera una lista inmutable de Java 25 (`.toList()`) y la envía a `GameRuleStateConfig.expandAll(allKeys)`.

### 3. Distribución de Clics
En `GlobalActionsRuleEntry.mouseClicked(MouseButtonEvent event, boolean doubleClick)`:
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

## 🌐 Claves de Localización

```json
{
  "gui.collapsible-game-rules.expand_all": "Expand All",
  "gui.collapsible-game-rules.collapse_all": "Collapse All"
}
```

---

## 🔗 Documentación Relacionada

* [[🏠 Resumen y Portal Principal|es_es-Home]]
* [[🗂️ Categorías Desplegables|es_es-Collapsible-Categories]]
* [[🧠 Persistencia de Estado y Configuración JSON|es_es-State-Persistence-and-Config]]
* [[🧩 Arquitectura y Subsistema Mixin|es_es-Architecture-and-Mixins]]
