# 🖥️ HUD, Diagnose & UI-Rendering

| Parameter | Spezifikation |
| :--- | :--- |
| **Grafik-Engine** | `net.minecraft.client.gui.GuiGraphicsExtractor` |
| **Bildschirm-Kontext** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen` |
| **Textfarbe bei Hover** | `0xFFFFFFAA` (Sanftes Gelb) |
| **Standard-Textfarbe** | `0xFFFFFFFF` (Klares Weiß) |
| **Kasten bei Hover** | `0x22FFFFFF` (Dezente weiße Fläche) |
| **Kategorie-Trennlinie** | `0x44AAAAAA` (Horizontale Begrenzung) |
| **Farbe Schalter Aktiv** | `0x4400FF00` (Smaragdgrün) |
| **Farbe Schalter Inaktiv** | `0x44FF0000` (Rubinrot) |
| **Sprachausgabe** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 Übersicht

Minecraft 26.2 hat die Rendering-Pipeline grundlegend modernisiert und GUI-Zeichenoperationen auf das moderne `GuiGraphicsExtractor`-Subsystem umgestellt.

Collapsible Game Rules wurde direkt für diese Engine entwickelt und bietet Vektorfarbfüllungen, zentrierte Schriftmetriken und Screenreader-Ausgabe ohne Performanceverlust.

---

## 🎨 Farbpalette der Benutzeroberfläche

| Komponente | Hex-ARGB-Code | Beschreibung | Einsatzort |
| :--- | :--- | :--- | :--- |
| **Hover-Fläche** | `0x22FFFFFF` | Weiß mit 13% Deckkraft. | Überfahrene Kategorie-Header und Aktionsbuttons. |
| **Trennlinie** | `0x44AAAAAA` | Helles Grau mit 27% Deckkraft (1px). | Untere Kante von Headern und Aktionsleiste. |
| **Text bei Hover** | `0xFFFFFFAA` | Sanft leuchtendes Gelb. | Text überfahrener Buttons und Header. |
| **Standard-Text** | `0xFFFFFFFF` | 100% reines Weiß. | Regulärer Kategorietext und Zähler-Badges. |
| **Zähler-Badge** | `ChatFormatting.GRAY` | Klassisches Vanilla-Grau (` (N rules)`). | An Kategorie-Titel angehängter Suffix. |
| **Schalter Aktiv** | `0x4400FF00` | Smaragdgrün mit 27% Deckkraft. | Hintergrund für `✔ ON` in `BooleanToggleWidget`. |
| **Schalter Inaktiv**| `0x44FF0000` | Rubinrot mit 27% Deckkraft. | Hintergrund für `✖ OFF` in `BooleanToggleWidget`. |

---

## 💻 Rendering-Implementierung

```java
@Override
public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
    // 1. Premium Highlight on hover
    if (hovered) {
        graphics.fill(this.getX() - 2, this.getY(), this.getX() + this.getWidth() + 2, this.getY() + 24, 0x22FFFFFF);
    }

    // 2. Directional arrow, label, and child count badge
    String prefix = this.expanded ? "▼ " : "▶ ";
    Component countBadge = Component.literal(" (" + this.childCount + " rules)").withStyle(ChatFormatting.GRAY);
    Component display = Component.literal(prefix).append(this.label).append(countBadge);

    // 3. Centered text with dynamic hover tint
    graphics.centeredText(Minecraft.getInstance().font, display,
            this.getContentXMiddle(), this.getContentY() + 5, hovered ? 0xFFFFFFAA : 0xFFFFFFFF);
    
    // 4. Subtle separating line at the bottom
    graphics.fill(this.getX() + 10, this.getY() + 23, this.getX() + this.getWidth() - 10, this.getY() + 24, 0x44AAAAAA);
}
```

---

## 🔊 Audio- und Barrierefreiheits-Feedback

* **Sound-Ereignis**: `SoundEvents.UI_BUTTON_CLICK`
* **Lautstärke**: `1.0F`
* **Tonhöhe**: `1.0F`
* **Auslöser**:
  - Mausklick auf Kategorie-Header.
  - Tastendruck auf Leertaste / Eingabe bei fokussierter Kategorie.
  - Pfeiltaste links (einklappen) oder rechts (ausklappen).
  - Klick auf «Expand All» oder «Collapse All».

---

## 🔗 Weiterführende Dokumentation

* [[🏠 Übersicht & Startseite|de_de-Home]]
* [[🗂️ Einklappbare Kategorien|de_de-Collapsible-Categories]]
* [[⌨️ Tastaturnavigation & Barrierefreiheit|de_de-Keyboard-Navigation]]
* [[🧩 Architektur & Mixin-Subsystem|de_de-Architecture-and-Mixins]]
