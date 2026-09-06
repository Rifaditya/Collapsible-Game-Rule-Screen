# ✨ Kategorie-Verschönerung & Formatierung

| Parameter | Spezifikation |
| :--- | :--- |
| **Hilfsklasse** | `net.instantgratification.collapsiblegamerules.util.CategoryPrettifier` |
| **Metadaten-Fassade** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **Fallback-Bedingung** | `!Language.getInstance().has(key)` |
| **Präfix-Entfernung** | Entfernt `"gamerule.category."` |
| **Trennzeichen-Behandlung**| Punkt `.` (Namespace) und Regex `[_-]` (Wörter) |
| **Metadaten-Quelle** | `DynamicGameRuleManager.getGeneratedTranslations()` |

---

## 📖 Übersicht

Drittanbieter-Mods registrieren Kategorien oft mit rohen Schlüsseln (wie `gamerule.category.better-bats.better_bats` oder `gamerule.category.item_clumps`), ohne Übersetzungen in `lang/en_us.json` einzutragen. In Vanilla erscheinen dadurch kryptische Texte im Menü.

**Category Prettification** bereinigt und formatiert unübersetzte Schlüssel zur Laufzeit in saubere, lesbare Überschriften im Title-Case-Format.

---

## ⚙️ Ablauf des Formatierungsalgorithmus

`CategoryPrettifier.prettifyCategoryKey(String key)` führt folgende Schritte aus:

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

## 📊 Transformationsbeispiele

| Roher Spielregel-Schlüssel | Angezeigte Beschriftung | Anmerkungen |
| :--- | :--- | :--- |
| `gamerule.category.better-bats.better_bats` | **Better Bats** | Entfernt redundante Namespace- und Pfadteile. |
| `gamerule.category.minecraft.spawning` | **Spawning** | Lässt den Standard-Namespace `minecraft` weg. |
| `gamerule.category.social_mobs.wolf_pack` | **Social Mobs Wolf Pack** | Verbindet unterschiedliche Segmente sinnvoll. |
| `gamerule.category.custom_rules` | **Custom Rules** | Ersetzt Unterstriche durch Leerzeichen und setzt Großbuchstaben. |
| `gamerule.category.instant-gratification.ore-multiplier` | **Instant Gratification Ore Multiplier** | Trennt Bindestriche und kapitalisiert Wörter. |

---

## 💻 Quellcode-Implementierung

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

## 🔗 Weiterführende Dokumentation

* [[🏠 Übersicht & Startseite|de_de-Home]]
* [[🗂️ Einklappbare Kategorien|de_de-Collapsible-Categories]]
* [[📚 DasikLibrary API-Integration|de_de-API-and-Library-Integration]]
