# ✨ Embellissement & Formatage des Catégories

| Paramètre | Spécification |
| :--- | :--- |
| **Classe Utilitaire** | `net.instantgratification.collapsiblegamerules.util.CategoryPrettifier` |
| **Façade de Métadonnées** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **Condition de Formatage** | `!Language.getInstance().has(key)` |
| **Suppression de Préfixe** | Supprime `"gamerule.category."` |
| **Séparateurs Traités** | Point `.` (namespace) et expression `[_-]` (mots) |
| **Source de Données** | `DynamicGameRuleManager.getGeneratedTranslations()` |

---

## 📖 Vue d'Ensemble

Les mods tiers créent parfois des catégories avec des clés techniques (comme `gamerule.category.better-bats.better_bats` ou `gamerule.category.item_clumps`) sans ajouter les traductions associées dans `lang/en_us.json`. En jeu Vanilla, cela produit des textes bruts désagréables à lire.

**Category Prettification** formate dynamiquement ces clés non traduites en titres soignés avec majuscules initiales.

---

## ⚙️ Étapes du Formatage

`CategoryPrettifier.prettifyCategoryKey(String key)` effectue les étapes suivantes :

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       CATEGORY PRETTIFICATION PIPELINE                      │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Input Key: "gamerule.category.better-bats.better_bats"                    │
│        │                                                                    │
│        ▼ [Step 1: Prefix Stripping]                                         │
│   Strip "gamerule.category." ──> "better-bats.better_bats"                  │
│        │                                                                    │
│        ▼ [Step 2: Namespace & Path Separation]                              │
│   Separate namespace "better-bats" and path "better_bats"                   │
│        │                                                                    │
│        ▼ [Step 3: Redundancy Normalization]                                 │
│   Compare normalized strings: "betterbats" == "betterbats"                  │
│   Deduplicate to single segment: "better_bats"                              │
│        │                                                                    │
│        ▼ [Step 4: Delimiter Splitting & Capitalization]                     │
│   Split by "[_-]" ──> ["better", "bats"]                                    │
│   Capitalize words ──> ["Better", "Bats"]                                   │
│        │                                                                    │
│        ▼ [Step 5: String Join]                                              │
│   Output Display Title: "Better Bats"                                       │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📊 Tableau d'Exemples de Transformation

| Clé Brute de Catégorie | Titre Obtenu | Remarques |
| :--- | :--- | :--- |
| `gamerule.category.better-bats.better_bats` | **Better Bats** | Élimine les doublons entre namespace et chemin. |
| `gamerule.category.minecraft.spawning` | **Spawning** | Retire le namespace Vanilla `minecraft`. |
| `gamerule.category.social_mobs.wolf_pack` | **Social Mobs Wolf Pack** | Assemble harmonieusement les termes distincts. |
| `gamerule.category.custom_rules` | **Custom Rules** | Remplace les tirets bas et applique des majuscules. |
| `gamerule.category.instant-gratification.ore-multiplier` | **Instant Gratification Ore Multiplier** | Découpe les tirets et capitalise chaque terme. |

---

## 💻 Implémentation Java

```java
public static String prettifyCategoryKey(String key) {
    if (key == null) {
        return "";
    }
    String name = key;
    if (name.startsWith("gamerule.category.")) {
        name = name.substring("gamerule.category.".length());
    }

    // Split namespace and path if dot is present
    int dotIndex = name.indexOf('.');
    if (dotIndex != -1) {
        String ns = name.substring(0, dotIndex);
        String path = name.substring(dotIndex + 1);
        
        // If the namespace is "minecraft", just drop it
        if (ns.equals("minecraft")) {
            name = path;
        } else {
            // Normalize for comparison
            String normNs = ns.replace("-", "").replace("_", "").toLowerCase(Locale.ROOT);
            String normPath = path.replace("-", "").replace("_", "").toLowerCase(Locale.ROOT);
            if (normPath.contains(normNs) || normNs.contains(normPath)) {
                name = path; // Use the path part since it's more specific or includes namespace
            } else {
                name = ns + " " + path;
            }
        }
    }

    // Split by underscore or dash
    String[] parts = name.split("[_-]");
    List<String> words = new ArrayList<>();
    for (String part : parts) {
        if (part.isEmpty()) {
            continue;
        }
        String capitalized = part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1);
        words.add(capitalized);
    }
    return String.join(" ", words);
}
```

---

## 🔗 Documentation Associée

* [[🏠 Aperçu & Accueil|fr_fr-Home]]
* [[🗂️ Catégories Repliables|fr_fr-Collapsible-Categories]]
* [[📚 Intégration de l'API DasikLibrary|fr_fr-API-and-Library-Integration]]
