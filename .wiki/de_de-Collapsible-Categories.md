# 🗂️ Einklappbare Kategorien

| Parameter | Spezifikation |
| :--- | :--- |
| **Systemkomponente** | `CollapsibleCategoryRuleEntry` (Innere Klasse) |
| **Zugehöriger Mixin** | `AbstractGameRulesScreenRuleListMixin` |
| **Zielklasse** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList` |
| **Statussymbole** | Ausgeklappt: `▼ ` \| Eingeklappt: `▶ ` |
| **Regelzähler-Badge** | ` (N rules)` (`ChatFormatting.GRAY`) |
| **Hervorhebung bei Hover** | `0x22FFFFFF` (25% halbtransparenter weißer Kasten) |
| **Trennlinie unten** | `0x44AAAAAA` (Dezente Linie) |
| **Textfarbe** | Bei Hover: `0xFFFFFFAA` \| Normal: `0xFFFFFFFF` |
| **Klick-Sound** | `net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK` (Lautstärke: `1.0F`) |
| **Barrierefreiheit** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 Übersicht

In Minecraft Vanilla werden die Kategorienamen als statische Textlabels dargestellt (`CategoryRuleEntry`), unter denen alle Regeln in einer langen Liste stehen. Bei vielen Mods wird das Menü unübersichtlich.

**Collapsible Categories** ersetzt statische Labels durch interaktive Widgets (`CollapsibleCategoryRuleEntry`), die nach Belieben ein- und ausgeklappt werden können.

---

## 🎨 Visuelles Layout & Hierarchie

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ [ Expand All ]                                             [ Collapse All ] │ ◄── GlobalActionsRuleEntry (Index 0)
├─────────────────────────────────────────────────────────────────────────────┤
│ ▼ ⚔️ Mobs (14 rules)                                                        │ ◄── CollapsibleCategoryRuleEntry (Expanded)
│ ─────────────────────────────────────────────────────────────────────────── │
│   mobGriefing                                                     [ ON ]    │ ◄── Child RuleEntry
│   doMobSpawning                                                   [ ON ]    │ ◄── Child RuleEntry
│   doMobLoot                                                       [ ON ]    │ ◄── Child RuleEntry
├─────────────────────────────────────────────────────────────────────────────┤
│ ▶ 👤 Player (8 rules)                                                       │ ◄── CollapsibleCategoryRuleEntry (Collapsed)
│ ─────────────────────────────────────────────────────────────────────────── │
│ ▼ 🌧️ Updates (6 rules)                                                      │ ◄── CollapsibleCategoryRuleEntry (Expanded)
│ ─────────────────────────────────────────────────────────────────────────── │
│   doFireTick                                                      [ ON ]    │ ◄── Child RuleEntry
│   randomTickSpeed                                                 [ 3  ]    │ ◄── Child RuleEntry
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## ⚙️ Technische Mechaniken

### 1. Zählalgorithmus für Kind-Regeln
In `updateVisibleEntries()` wird ermittelt, wie viele Regeln bis zur nächsten Kategorie vorhanden sind:

```java
int childCount = 0;
for (int j = i + 1; j < this.collapsible_game_rules$allEntries.size(); j++) {
    if (this.collapsible_game_rules$allEntries.get(j) instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
        break;
    }
    childCount++;
}
```

### 2. Rendering-Pipeline (`extractContent`)
Wird über die moderne `GuiGraphicsExtractor`-Schnittstelle ausgeführt:
1. **Hover-Kasten**: Zeichnet bei Mausberührung ein halbtransparentes Rechteck `0x22FFFFFF` von `[getX() - 2, getY()]` bis `[getX() + getWidth() + 2, getY() + 24]`.
2. **Pfeil und Text**: Präfix (`▼ ` oder `▶ `), Kategoriename und graues Zähler-Badge (` (N rules)`).
3. **Zentrierung**: Horizontal bei `getContentXMiddle()` zentriert, mit vertikalem Versatz `getContentY() + 5`.
4. **Trennlinie**: Linie `0x44AAAAAA` bei `getY() + 23` trennt Kategorien optisch ab.

### 3. Mausklick-Behandlung
In `mouseClicked(MouseButtonEvent event, boolean doubleClick)`:
* **Linksklick (`event.button() == 0`)** oder **Rechtsklick (`event.button() == 1`)**:
  1. Führt `toggleAction.run()` aus.
  2. Speichert den neuen Zustand in `GameRuleStateConfig`.
  3. Spielt den Klick-Sound: `SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)`.
  4. Ruft `updateVisibleEntries()` auf, um sichtbare Einträge anzupassen.
  5. Aktualisiert die Listenmaße mit `updateSizeAndPosition(...)`.

### 4. Barrierefreiheit & Sprachausgabe
Implementiert `NarratableEntry`:
* **Priorität**: `NarrationPriority.HOVERED`
* **Ausgabe**: Überträgt den Kategorienamen als `NarratedElementType.TITLE` für Screenreader.

---

## 🔗 Weiterführende Dokumentation

* [[🏠 Übersicht & Startseite|de_de-Home]]
* [[🌎 Globale Aktionen & Massenumschalter|de_de-Global-Actions-and-Bulk-Toggles]]
* [[⌨️ Tastaturnavigation & Barrierefreiheit|de_de-Keyboard-Navigation]]
* [[🧩 Architektur & Mixin-Subsystem|de_de-Architecture-and-Mixins]]
