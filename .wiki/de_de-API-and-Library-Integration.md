# 📚 DasikLibrary API-Integration

| Parameter | Spezifikation |
| :--- | :--- |
| **Zentrale Abhängigkeit** | `net.dasik.social:dasik-library` |
| **Versionsbeschränkung** | `dasik-library: >=1.7.0` (Build: `1.7.4`) |
| **Isolations-Hilfsklasse** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **Abgefragte API-Methode** | `DynamicGameRuleManager.getGeneratedTranslations()` |
| **Laufzeitprüfung** | Strikte Prüfung in `CollapsibleGameRulesFabric.onInitialize()` |

---

## 📖 Übersicht

Collapsible Game Rules arbeitet nahtlos mit der **DasikLibrary** zusammen, um dynamische Kategorieübersetzungen und Metadaten bereitzustellen.

Um die Stabilität der JVM sicherzustellen, sind alle direkten Klassenreferenzen in der Hilfsklasse `DasikMetadataHelper` gekapselt.

---

## 🔒 ClassLoading-Isolationsarchitektur

Im modernen Java führt der direkte Aufruf fremder Bibliotheken in Mixins zum sofortigen Laden durch die JVM. Fehlt die Bibliothek, stürzt das Spiel mit einem fatalen `NoClassDefFoundError` ab.

Durch Kapselung der `DynamicGameRuleManager`-Aufrufe in `DasikMetadataHelper` lädt die JVM die Klassen erst, wenn die Mod geladen ist:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      CLASSLOADING ISOLATION PATTERN                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   AbstractGameRulesScreenRuleListMixin                                      │
│        │                                                                    │
│        ▼ (Checks FabricLoader.isModLoaded("dasik-library"))                 │
│   ┌─────────────────────────────────────────────────────────────────────┐   │
│   │ if (FabricLoader.getInstance().isModLoaded("dasik-library")) {      │   │
│   │     categoryKey = DasikMetadataHelper.getCategoryTranslation(...);  │   │
│   │ }                                                                   │   │
│   └─────────────────────────────────────────────────────────────────────┘   │
│        │                                                                    │
│        ▼ (Only loads DasikMetadataHelper when confirmed safe)               │
│   DasikMetadataHelper ──> net.dasik.social.api.gamerule.DynamicGameRuleManager│
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 💻 Implementierung der Metadatenabfrage

```java
public final class DasikMetadataHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger("collapsible-game-rules");

    private DasikMetadataHelper() {}

    /**
     * Retrieves the localized name for a category from DasikLibrary metadata.
     * Enforced Hard Dependency: This method makes direct calls to DasikLibrary.
     */
    public static String getCategoryTranslation(String categoryLabel) {
        Map<String, String> translations =
                DynamicGameRuleManager.getGeneratedTranslations();

        return translations.getOrDefault(
                "gamerule.category." + categoryLabel.toLowerCase(Locale.ROOT),
                categoryLabel
        );
    }
}
```

---

## 🛡️ Strikte Überprüfung der Abhängigkeit

```java
public class CollapsibleGameRulesFabric implements ModInitializer {
    public static final String MOD_ID = "collapsible-game-rules";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
        LOGGER.info("Initializing Collapsible Game Rules [Core Align 2.1]");

        // Hard Dependency Enforcement
        if (!FabricLoader.getInstance().isModLoaded("dasik-library")) {
            throw new RuntimeException("Collapsible Game Rules requires DasikLibrary to function. Please install it.");
        }
    }
}
```

---

## 🔗 Weiterführende Dokumentation

* [[🏠 Übersicht & Startseite|de_de-Home]]
* [[🧭 Versionskompatibilitätsmatrix|de_de-Version-Compatibility]]
* [[✨ Kategorie-Verschönerung & Formatierung|de_de-Category-Prettification-and-Naming]]
* [[🧩 Architektur & Mixin-Subsystem|de_de-Architecture-and-Mixins]]
