# 🌎 Globale Aktionen & Massenumschalter

| Parameter | Spezifikation |
| :--- | :--- |
| **Komponentenklasse** | `net.instantgratification.collapsiblegamerules.ui.GlobalActionsRuleEntry` |
| **Position in Liste** | Index `0` (Immer ganz oben in `RuleList` fixiert) |
| **Linker Button** | `[ Expand All ]` (`gui.collapsible-game-rules.expand_all`) |
| **Rechter Button** | `[ Collapse All ]` (`gui.collapsible-game-rules.collapse_all`) |
| **Mittelpunkt linker Button** | `this.getX() + this.getWidth() / 4` |
| **Mittelpunkt rechter Button**| `this.getX() + 3 * this.getWidth() / 4` |
| **Hover-Farbe** | `0x22FFFFFF` (Hebt die überfahrene Hälfte hervor) |
| **Trennlinie unten** | `0x44AAAAAA` |
| **Klick-Sound** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 Übersicht

In umfangreichen Modpacks mit hunderten Spielregeln ist das manuelle Öffnen oder Schließen einzelner Ordner mühsam.

Die **Globale Aktionsleiste** ist auf **Index 0** verankert und ermöglicht das vollständige Aus- oder Einklappen aller Kategorien mit nur einem Mausklick.

---

## 🎨 Geteiltes Button-Design

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ◄──────────────── Left Half ───────────────►◄────────────── Right Half ───► │
│               [ Expand All ]                               [ Collapse All ] │
│ ─────────────────────────────────────────────────────────────────────────── │
└─────────────────────────────────────────────────────────────────────────────┘
```

* **Linke Zone (`mouseX < getX() + getWidth() / 2`)**: Löst `expandAll` aus.
* **Rechte Zone (`mouseX >= getX() + getWidth() / 2`)**: Löst `collapseAll` aus.
* **Hover-Zustand**: Hebt nur die fokussierte Hälfte mit `0x22FFFFFF` hervor und wechselt die Schriftfarbe zu `0xFFFFFFAA`.

---

## ⚙️ Technische Mechaniken

### 1. Fixierte Injektion auf Index 0
In `AbstractGameRulesScreenRuleListMixin`:

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

### 2. Ermittlung der Kategorie-Schlüssel
1. Filtert Einträge nach `CategoryRuleEntry`.
2. Liest das Label über `CategoryRuleEntryAccessor`.
3. Extrahiert den Übersetzungsschlüssel (`gamerule.category.spawning`).
4. Verwendet bei unübersetzten Schlüsseln den Text.
5. Sammelt Schlüssel in einer unveränderlichen Java 25-Liste (`.toList()`) und übergibt sie an `GameRuleStateConfig.expandAll(allKeys)`.

### 3. Klick-Auswertung
In `GlobalActionsRuleEntry.mouseClicked(MouseButtonEvent event, boolean doubleClick)`:
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

## 🌐 Lokalisierungsschlüssel

```json
{
  "gui.collapsible-game-rules.expand_all": "Expand All",
  "gui.collapsible-game-rules.collapse_all": "Collapse All"
}
```

---

## 🔗 Weiterführende Dokumentation

* [[🏠 Übersicht & Startseite|de_de-Home]]
* [[🗂️ Einklappbare Kategorien|de_de-Collapsible-Categories]]
* [[🧠 Zustandsspeicherung & JSON-Konfiguration|de_de-State-Persistence-and-Config]]
* [[🧩 Architektur & Mixin-Subsystem|de_de-Architecture-and-Mixins]]
