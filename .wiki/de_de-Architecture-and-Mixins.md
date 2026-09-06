# 🧩 Architektur & Mixin-Subsystem

| Parameter | Spezifikation |
| :--- | :--- |
| **Mixin-Konfiguration** | `src/main/resources/collapsible-game-rules.mixins.json` |
| **Refmap-Kennung** | `collapsible-game-rules-refmap.json` |
| **Kompatibilitätslevel**| `JAVA_25` |
| **Default Require** | `1` |
| **Wurzelpaket** | `net.instantgratification.collapsiblegamerules` |
| **Mixin-Paket** | `net.instantgratification.collapsiblegamerules.mixin` |
| **Gesamtzahl Mixins** | `4` Client-Mixins (`3` Injektoren + `1` Interface-Accessor) |

---

## 📖 Paketstruktur des Systems

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

## 🔍 Detailanalyse der Mixins

### 1. `AbstractGameRulesScreenRuleListMixin`
* **Zielklasse**: `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList.class`
* **Vererbung**: Erbt von `ContainerObjectSelectionList<AbstractGameRulesScreen.RuleEntry>`
* **Rolle**: Hauptcontroller der UI. Fängt das Füllen der Liste ab, zählt Kind-Regeln je Kategorie, filtert sichtbare Einträge anhand des Einklappzustands und rendert interaktive Header.

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
* **Zielklasse**: `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.CategoryRuleEntry.class`
* **Rolle**: Bietet Zugriff auf das private Bytecode-Feld `Component label` der Vanilla-Einträge.

```java
@Mixin(AbstractGameRulesScreen.CategoryRuleEntry.class)
public interface CategoryRuleEntryAccessor {
    @Accessor("label")
    Component collapsible_game_rules$getLabel();
}
```

---

### 3. `ScreenMixin`
* **Zielklasse**: `net.minecraft.client.gui.screens.Screen.class`
* **Rolle**: Reagiert auf das Schließen des Menüs. Beim Beenden von `AbstractGameRulesScreen` werden Änderungen über `GameRuleStateConfig.saveIfDirty()` auf Festplatte geschrieben.

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
* **Zielklasse**: `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.IntegerRuleEntry.class`
* **Rolle**: Abstrakte Brücke zu Vanilla-Zahlenfeldern zur Einbettung von Schiebereglern.

```java
@Mixin(AbstractGameRulesScreen.IntegerRuleEntry.class)
public abstract class IntegerRuleEntryMixin extends AbstractGameRulesScreen.RuleEntry {
    public IntegerRuleEntryMixin() {
        super(null);
    }
}
```

---

## 📜 Mixin-Konfigurationsdatei (`collapsible-game-rules.mixins.json`)

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

## 🔗 Weiterführende Dokumentation

* [[🏠 Übersicht & Startseite|de_de-Home]]
* [[🗂️ Einklappbare Kategorien|de_de-Collapsible-Categories]]
* [[🧠 Zustandsspeicherung & JSON-Konfiguration|de_de-State-Persistence-and-Config]]
* [[📚 DasikLibrary API-Integration|de_de-API-and-Library-Integration]]
