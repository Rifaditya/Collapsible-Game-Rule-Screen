# 🎛️ Spielregel-Voreinstellungen & Steuerelemente

| Parameter | Spezifikation |
| :--- | :--- |
| **Preset-Engine** | `net.instantgratification.collapsiblegamerules.preset.GameRulePresetEngine` |
| **Preset-Datenstruktur** | `record Preset(String id, Component displayName, Map<String, Object> ruleValues)` |
| **Interaktive Widgets** | `IntegerSliderWidget`, `BooleanToggleWidget` |
| **Slider-Hilfsklasse** | `net.instantgratification.collapsiblegamerules.util.GameRuleSliderHelper` |
| **Hintergrundfarbe Aktiv** | `0x4400FF00` (Smaragdgrüner Hintergrund) |
| **Hintergrundfarbe Inaktiv**| `0x44FF0000` (Rubinroter Hintergrund) |
| **Integrierte Presets** | `builder` ("🏰 Baumeister"), `fast_play` ("⚡ Schnelles Spiel"), `hardcore` ("💀 Hardcore-Realismus") |

---

## 📖 Übersicht

Collapsible Game Rules bietet praktische Voreinstellungen und intuitive Schieberegler, um Konfigurationen mit einem Klick zu laden oder Zahlenwerte flüssig anzupassen.

---

## 🏰 Matrix der integrierten Presets

`GameRulePresetEngine` stellt drei vorgefertigte Gameplay-Profile bereit:

| Preset-ID | Titel | Spielregel | Konfigurierter Wert | Auswirkung im Spiel |
| :--- | :--- | :--- | :--- | :--- |
| `builder` | **🏰 Baumeister** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doMobSpawning`<br>`keepInventory`<br>`mobGriefing`<br>`doFireTick` | `false`<br>`false`<br>`false`<br>`true`<br>`false`<br>`false` | Ideal zum Bauen: friert Sonne/Wetter ein, stoppt Mobs, Creeper-Schäden und Feuer. |
| `fast_play` | **⚡ Schnelles Spiel** | `randomTickSpeed`<br>`playersSleepingPercentage`<br>`keepInventory` | `10`<br>`0`<br>`true` | Schnelles Survival: $3\times$ schnelleres Pflanzenwachstum, Nacht überspringen mit 1 Spieler, Inventar bleibt erhalten. |
| `hardcore` | **💀 Hardcore-Realismus** | `naturalRegeneration`<br>`doInsomnia`<br>`playersSleepingPercentage` | `false`<br>`true`<br>`100` | Extremes Survival: keine passive Lebensregeneration (nur Äpfel/Tränke), Phantome aktiv. |

---

## 🎚️ Schieberegler-Widget (`IntegerSliderWidget`)

Das `IntegerSliderWidget` ersetzt numerische Textfelder durch ziehbare Schieberegler.

### Mathematische Normalisierungsformeln

Berechnung der Slider-Position aus dem Ganzzahlwert $v$:
$$\text{normalized} = \frac{\text{clamp}(v, \text{min}, \text{max}) - \text{min}}{\text{max} - \text{min}}$$

Berechnung des Ganzzahlwertes aus der Slider-Position $p \in [0.0, 1.0]$:
$$\text{calculatedInt} = \text{min} + \text{round}\left(p \times (\text{max} - \text{min})\right)$$

### Grenzen der Vanilla-Regeln (`GameRuleSliderHelper`)

| Regelschlüssel | Minimum ($	ext{min}$) | Maximum ($	ext{max}$) | Vanilla-Standard |
| :--- | :--- | :--- | :--- |
| `randomTickSpeed` | `0` | `100` | `3` |
| `spawnRadius` | `0` | `32` | `10` |
| `playersSleepingPercentage` | `0` | `100` | `100` |
| `maxEntityCramming` | `0` | `100` | `24` |
| `maxCommandChainLength` | `0` | `65536` | `65536` |
| `commandModificationBlockLimit` | `0` | `65536` | `32768` |

---

## 🔘 Boolesches Umschalt-Widget (`BooleanToggleWidget`)

`BooleanToggleWidget` bietet klares visuelles Feedback:

* **Zustand: WAHR (`ON`)**: Zeigt `✔ ON` in Grün auf grünem Hintergrund (`0x4400FF00`).
* **Zustand: FALSCH (`OFF`)**: Zeigt `✖ OFF` in Rot auf rotem Hintergrund (`0x44FF0000`).
* **Mausklick**: Schaltet um und führt den Callback `onToggle.accept(newState)` aus.

```
┌─────────────────────────┐     ┌─────────────────────────┐
│         ✔ ON            │     │         ✖ OFF           │
│   (Green Tint 0x4400FF00)│     │   (Red Tint 0x44FF0000) │
└─────────────────────────┘     └─────────────────────────┘
```

---

## 🔗 Weiterführende Dokumentation

* [[🏠 Übersicht & Startseite|de_de-Home]]
* [[🗂️ Einklappbare Kategorien|de_de-Collapsible-Categories]]
* [[📜 Spielregeln-Referenztabelle|de_de-GameRules-Reference]]
* [[🧩 Architektur & Mixin-Subsystem|de_de-Architecture-and-Mixins]]
