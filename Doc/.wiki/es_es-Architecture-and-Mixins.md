# 🧩 Arquitectura y Subsistema Mixin

| Parámetro | Especificación |
| :--- | :--- |
| **Configuración Mixin** | `src/main/resources/collapsible-game-rules.mixins.json` |
| **Identificador Refmap** | `collapsible-game-rules-refmap.json` |
| **Nivel de Compatibilidad** | `JAVA_25` |
| **Default Require** | `1` |
| **Paquete Raíz** | `net.instantgratification.collapsiblegamerules` |
| **Paquete Mixin** | `net.instantgratification.collapsiblegamerules.mixin` |
| **Total de Mixins** | `4` Mixins de Cliente (`3` Inyectores + `1` Accesor de Interfaz) |

---

## 📖 Arquitectura de Paquetes del Sistema

```
net.instantgratification.collapsiblegamerules
├── CollapsibleGameRulesFabric.java           ──> Main ModInitializer (Guard & Hard Dependency check)
├── CollapsibleGameRulesFabricClient.java     ──> ClientModInitializer (Loads GameRuleStateConfig)
├── GameRuleStateConfig.java                  ──> Config & JSON state persistence (Set<String>)
├── mixin
│   ├── AbstractGameRulesScreenRuleListMixin.java ──> Core engine (List interception, CollapsibleCategoryRuleEntry)
│   ├── CategoryRuleEntryAccessor.java        ──> Interface Accessor for category Component label
│   ├── IntegerRuleEntryMixin.java            ──> Abstract wrapper for integer rule entries
│   └── ScreenMixin.java                      ──> Intercepts Screen.removed() to flush pending state
├── preset
│   └── GameRulePresetEngine.java             ──> Built-in preset definitions (Builder, Fast Play, Hardcore)
├── ui
│   ├── BooleanToggleWidget.java              ──> Interactive emerald green / ruby red toggle switch
│   ├── GlobalActionsRuleEntry.java           ──> Pinned index 0 bulk toggle header ([ Expand ] / [ Collapse ])
│   └── IntegerSliderWidget.java              ──> Draggable numeric slider with math normalization
└── util
    ├── CategoryPrettifier.java               ──> Regex & string parser for unlocalized category keys
    ├── DasikMetadataHelper.java              ──> Lazy-loaded isolation layer querying DasikLibrary
    ├── GameRuleSliderHelper.java             ──> Vanilla integer rule bounds and default values
    └── ModVersionGuard.java                  ──> Zero-dependency runtime Knot ClassLoader integrity check
```

---

## 🔍 Análisis Detallado de Mixins

### 1. `AbstractGameRulesScreenRuleListMixin`
* **Clase Objetivo**: `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList.class`
* **Herencia**: Hereda de `ContainerObjectSelectionList<AbstractGameRulesScreen.RuleEntry>`
* **Función**: Controlador principal de la UI. Intercepta la generación de la lista, calcula el número de reglas por categoría, filtra los elementos visibles según el estado de despliegue y dibuja las cabeceras interactivas.

```java
@Mixin(AbstractGameRulesScreen.RuleList.class)
public abstract class AbstractGameRulesScreenRuleListMixin
        extends ContainerObjectSelectionList<AbstractGameRulesScreen.RuleEntry> {

    @Unique
    private List<AbstractGameRulesScreen.RuleEntry> collapsible_game_rules$allEntries = new ArrayList<>();

    @Unique
    private String collapsible_game_rules$currentFilter = "";

    @Inject(method = "populateChildren(Ljava/lang/String;)V", at = @At("TAIL"))
    private void collapsible_game_rules$onPopulateChildren(String filter, CallbackInfo ci) {
        this.collapsible_game_rules$currentFilter = (filter != null) ? filter.toLowerCase(java.util.Locale.ROOT) : "";
        this.collapsible_game_rules$allEntries = new ArrayList<>(this.children());
        this.collapsible_game_rules$updateVisibleEntries();
    }
}
```

---

### 2. `CategoryRuleEntryAccessor`
* **Clase Objetivo**: `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.CategoryRuleEntry.class`
* **Función**: Proporciona acceso a nivel de bytecode al campo privado `Component label` de Vanilla.

```java
@Mixin(AbstractGameRulesScreen.CategoryRuleEntry.class)
public interface CategoryRuleEntryAccessor {
    @Accessor("label")
    Component collapsible_game_rules$getLabel();
}
```

---

### 3. `ScreenMixin`
* **Clase Objetivo**: `net.minecraft.client.gui.screens.Screen.class`
* **Función**: Detecta el cierre de pantallas. Cuando se cierra `AbstractGameRulesScreen`, descarga las modificaciones pendientes al disco mediante `GameRuleStateConfig.saveIfDirty()`.

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

---

### 4. `IntegerRuleEntryMixin`
* **Clase Objetivo**: `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.IntegerRuleEntry.class`
* **Función**: Ofrece un puente abstracto hacia los campos numéricos de Vanilla para alojar controles deslizantes.

```java
@Mixin(AbstractGameRulesScreen.IntegerRuleEntry.class)
public abstract class IntegerRuleEntryMixin extends AbstractGameRulesScreen.RuleEntry {
    public IntegerRuleEntryMixin() {
        super(null);
    }
}
```

---

## 📜 Archivo de Configuración Mixin (`collapsible-game-rules.mixins.json`)

```json
{
    "required": true,
    "package": "net.instantgratification.collapsiblegamerules.mixin",
    "refmap": "collapsible-game-rules-refmap.json",
    "compatibilityLevel": "JAVA_25",
    "client": [
        "ScreenMixin",
        "AbstractGameRulesScreenRuleListMixin",
        "CategoryRuleEntryAccessor",
        "IntegerRuleEntryMixin"
    ],
    "injectors": {
        "defaultRequire": 1
    }
}
```

---

## 🔗 Documentación Relacionada

* [[🏠 Resumen y Portal Principal|es_es-Home]]
* [[🗂️ Categorías Desplegables|es_es-Collapsible-Categories]]
* [[🧠 Persistencia de Estado y Configuración JSON|es_es-State-Persistence-and-Config]]
* [[📚 Integración con la API de DasikLibrary|es_es-API-and-Library-Integration]]
