# 📚 Intégration de l'API DasikLibrary

| Paramètre | Spécification |
| :--- | :--- |
| **Dépendance Centrale** | `net.dasik.social:dasik-library` |
| **Contrainte de Version** | `dasik-library: >=1.7.0` (Build : `1.7.4`) |
| **Classe d'Isolation** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **Méthode d'API Sollicitée** | `DynamicGameRuleManager.getGeneratedTranslations()` |
| **Contrôle au Démarrage** | Exigence stricte dans `CollapsibleGameRulesFabric.onInitialize()` |

---

## 📖 Vue d'Ensemble

Collapsible Game Rules s'intègre directement avec **DasikLibrary** pour assurer la traduction et le formatage automatique des métadonnées de catégories pour les mods récents.

Pour préserver l'intégrité de la JVM, tous les appels à `DasikLibrary` sont isolés dans la classe utilitaire à chargement différé `DasikMetadataHelper`.

---

## 🔒 Architecture d'Isolation du ClassLoading

Dans un environnement Java moderne, importer une classe externe directement dans une classe chargée tôt (comme un Mixin) déclenche immédiatement son chargement par la JVM. Si la bibliothèque est manquante, une erreur fatale `NoClassDefFoundError` se produit.

En confinant `DynamicGameRuleManager` dans `DasikMetadataHelper`, la JVM ne charge la classe que lorsqu'elle est avérée présente :

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

## 💻 Implémentation des Requêtes de Traduction

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

## 🛡️ Vérification Obligatoire au Démarrage

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

## 🔗 Documentation Associée

* [[🏠 Aperçu & Accueil|fr_fr-Home]]
* [[🧭 Matrice de Compatibilité des Versions|fr_fr-Version-Compatibility]]
* [[✨ Embellissement & Formatage des Catégories|fr_fr-Category-Prettification-and-Naming]]
* [[🧩 Architecture & Sous-système Mixin|fr_fr-Architecture-and-Mixins]]
