# 🧠 Zustandsspeicherung & JSON-Konfiguration

| Parameter | Spezifikation |
| :--- | :--- |
| **Konfigurationsklasse** | `net.instantgratification.collapsiblegamerules.GameRuleStateConfig` |
| **Speicherort** | `.minecraft/config/collapsible-game-rules-state.json` |
| **Speicherstruktur im RAM** | `Set<String> expandedCategories = new HashSet<>()` |
| **Serialisierungs-Engine** | `com.google.gson.Gson` (Pretty-Printing aktiviert) |
| **I/O-Drosselungs-Flag** | `private static boolean isDirty = false` |
| **Speicherpunkt** | `ScreenMixin` fängt `Screen.removed()` ab (`@At("HEAD")`) |
| **Schlüsselstrategie** | Übersetzungsschlüssel (`TranslatableContents.getKey()`) oder Literal |

---

## 📖 Übersicht

Collapsible Game Rules verfügt über eine asynchrone, gedrosselte Zustandsspeicherung. Kategorien bleiben beim Schließen und erneuten Öffnen in dem Zustand, in dem der Spieler sie hinterlassen hat.

---

## 📄 JSON-Konfigurationsformat

Die Datei `.minecraft/config/collapsible-game-rules-state.json` speichert ein klares JSON-Array:

```json
[
  "gamerule.category.spawning",
  "gamerule.category.mobs",
  "gamerule.category.updates"
]
```

* **Im Array vorhanden**: Die Kategorie ist aktuell **AUSGEKLAPPT**.
* **Im Array nicht vorhanden**: Die Kategorie ist aktuell **EINGEKLAPPT** (Standard).

---

## ⚡ Leistungsstarke I/O-Drosselung

Festplattenzugriffe bei jedem Klick würden Mikroruckler verursachen. Um **stotterfreie 60+ FPS** zu garantieren, nutzt `GameRuleStateConfig` das `isDirty`-Flag:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       THROTTLED PERSISTENCE WORKFLOW                        │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Player clicks Category Header                                             │
│        │                                                                    │
│        ▼                                                                    │
│   GameRuleStateConfig.setExpanded(key, state)                               │
│        ├─ Updates in-memory HashSet<String> in 0.0001 μs                    │
│        └─ Marks: isDirty = true (ZERO DISK I/O)                             │
│                                                                             │
│   Player closes Game Rules Screen (Esc, Done, or Cancel)                    │
│        │                                                                    │
│        ▼                                                                    │
│   ScreenMixin.collapsible_game_rules$onRemoved()                            │
│        │                                                                    │
│        ▼                                                                    │
│   GameRuleStateConfig.saveIfDirty()                                         │
│        ├─ Checks: if (isDirty) Ellipsis                                      │
│        ├─ Writes JSON to disk in background buffer                          │
│        └─ Resets: isDirty = false                                           │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 💻 API-Methodenreferenz

### Öffentliche Methoden von `GameRuleStateConfig`

| Methodensignatur | Rückgabetyp | Beschreibung |
| :--- | :--- | :--- |
| `load()` | `void` | Lädt `collapsible-game-rules-state.json` beim Clientstart. |
| `save()` | `void` | Schreibt `expandedCategories` über `Files.newBufferedWriter` auf Festplatte. |
| `saveIfDirty()` | `void` | Schreibt nur bei `isDirty == true` und setzt das Flag zurück. |
| `isExpanded(String categoryKey)` | `boolean` | Prüft, ob ein Schlüssel in `expandedCategories` liegt. |
| `setExpanded(String categoryKey, boolean expanded)` | `void` | Ändert den Eintrag im Set und setzt `isDirty = true`. |
| `expandAll(Iterable<String> allKeys)` | `void` | Fügt alle Schlüssel dem Set hinzu und markiert `isDirty = true`. |
| `collapseAll()` | `void` | Leert das Set und markiert `isDirty = true`. |

---

## 🔒 Bildschirm-Schließung über ScreenMixin

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

Dies gewährleistet sicheres Speichern beim Verlassen über **Fertig**, **Abbrechen** oder die **Escape**-Taste.

---

## 🔗 Weiterführende Dokumentation

* [[🏠 Übersicht & Startseite|de_de-Home]]
* [[🌎 Globale Aktionen & Massenumschalter|de_de-Global-Actions-and-Bulk-Toggles]]
* [[🧩 Architektur & Mixin-Subsystem|de_de-Architecture-and-Mixins]]
