# 🧩 Architecture & Sous-système Mixin

| Paramètre | Spécification |
| :--- | :--- |
| **Fichier de Mixins** | `src/main/resources/collapsible-game-rules.mixins.json` |
| **Identifiant Refmap** | `collapsible-game-rules-refmap.json` |
| **Niveau de Compatibilité**| `JAVA_25` |
| **Default Require** | `1` |
| **Package Racine** | `net.instantgratification.collapsiblegamerules` |
| **Package des Mixins** | `net.instantgratification.collapsiblegamerules.mixin` |
| **Nombre de Mixins** | `4` Mixins Client (`3` Injecteurs + `1` Accesseur d'Interface) |

---

## 📖 Arborescence des Packages

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

## 🔍 Analyse Détaillée des Mixins

### 1. `AbstractGameRulesScreenRuleListMixin`
* **Classe Cible** : `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList.class`
* **Hiérarchie** : Élargit `ContainerObjectSelectionList<AbstractGameRulesScreen.RuleEntry>`
* **Rôle** : Cœur de l'interface. Gère la liste, compte les règles, filtre selon l'état et dessine les en-têtes interactifs.

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
* **Classe Cible** : `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.CategoryRuleEntry.class`
* **Rôle** : Offre un accès au champ privé `Component label` des catégories Vanilla.

```java
@Mixin(AbstractGameRulesScreen.CategoryRuleEntry.class)
public interface CategoryRuleEntryAccessor {
    @Accessor("label")
    Component collapsible_game_rules$getLabel();
}
```

---

### 3. `ScreenMixin`
* **Classe Cible** : `net.minecraft.client.gui.screens.Screen.class`
* **Rôle** : Détecte la fermeture des fenêtres. À la sortie de `AbstractGameRulesScreen`, sauvegarde sur disque via `GameRuleStateConfig.saveIfDirty()`.

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
* **Classe Cible** : `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.IntegerRuleEntry.class`
* **Rôle** : Pont abstrait pour connecter les sliders aux entrées de règles entières Vanilla.

```java
@Mixin(AbstractGameRulesScreen.IntegerRuleEntry.class)
public abstract class IntegerRuleEntryMixin extends AbstractGameRulesScreen.RuleEntry {
    public IntegerRuleEntryMixin() {
        super(null);
    }
}
```

---

## 📜 Fichier de Configuration Mixin (`collapsible-game-rules.mixins.json`)

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

## 🔗 Documentation Associée

* [[🏠 Aperçu & Accueil|fr_fr-Home]]
* [[🗂️ Catégories Repliables|fr_fr-Collapsible-Categories]]
* [[🧠 Persistance de l'État & Configuration JSON|fr_fr-State-Persistence-and-Config]]
* [[📚 Intégration de l'API DasikLibrary|fr_fr-API-and-Library-Integration]]
