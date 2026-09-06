# ⌨️ Tastaturnavigation & Barrierefreiheit

| Parameter | Spezifikation |
| :--- | :--- |
| **Komponentenklasse** | `CollapsibleCategoryRuleEntry` |
| **Barrierefreiheits-Schnittstelle** | `net.minecraft.client.gui.narration.NarratableEntry` |
| **Sprachausgabe-Priorität** | `NarrationPriority.HOVERED` |
| **Elementtyp der Sprachausgabe**| `NarratedElementType.TITLE` |
| **Umschalttasten** | `GLFW_KEY_SPACE`, `GLFW_KEY_ENTER`, `GLFW_KEY_KP_ENTER` |
| **Taste zum Einklappen** | `GLFW_KEY_LEFT` (Nur wenn Kategorie ausgeklappt ist) |
| **Taste zum Ausklappen** | `GLFW_KEY_RIGHT` (Nur wenn Kategorie eingeklappt ist) |
| **Akustisches Feedback** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 Übersicht

Collapsible Game Rules unterstützt vollständige Tastatursteuerung und Screenreader-Ausgabe, damit Menüs komplett ohne Maus bedient werden können.

---

## ⌨️ Tastenbelegung

Wenn ein Kategorie-Header in `RuleList` fokussiert ist, verarbeitet `keyPressed(KeyEvent event)` folgende Tasten:

| Taste | GLFW-Konstante | Aktion | Bedingung | Sound |
| :--- | :--- | :--- | :--- | :--- |
| **Leertaste** | `GLFW_KEY_SPACE` | Zustand umschalten | Immer | `UI_BUTTON_CLICK` |
| **Eingabetaste (Enter)** | `GLFW_KEY_ENTER` | Zustand umschalten | Immer | `UI_BUTTON_CLICK` |
| **Ziffernblock-Enter** | `GLFW_KEY_KP_ENTER` | Zustand umschalten | Immer | `UI_BUTTON_CLICK` |
| **Pfeiltaste links (←)** | `GLFW_KEY_LEFT` | **Kategorie einklappen**| Nur wenn `expanded == true` | `UI_BUTTON_CLICK` |
| **Pfeiltaste rechts (→)**| `GLFW_KEY_RIGHT` | **Kategorie ausklappen**| Nur wenn `expanded == false` | `UI_BUTTON_CLICK` |

---

## ⚙️ Technische Implementierung

### 1. Auswertung der Pfeiltasten
Entspricht Standard-Hierarchiebäumen moderner Betriebssysteme:

```java
@Override
public boolean keyPressed(KeyEvent event) {
    int keyCode = event.key();
    if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER || keyCode == GLFW.GLFW_KEY_SPACE) {
        this.toggleAction.run();
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        return true;
    } else if (keyCode == GLFW.GLFW_KEY_LEFT && this.expanded) {
        this.toggleAction.run();
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        return true;
    } else if (keyCode == GLFW.GLFW_KEY_RIGHT && !this.expanded) {
        this.toggleAction.run();
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        return true;
    }
    return super.keyPressed(event);
}
```

### 2. Barrierefreie Sprachausgabe (`updateNarration`)

```java
@Override
public NarrationPriority narrationPriority() {
    return NarrationPriority.HOVERED;
}

@Override
public void updateNarration(NarrationElementOutput output) {
    output.add(NarratedElementType.TITLE, this.label);
}
```

---

## 🔗 Weiterführende Dokumentation

* [[🏠 Übersicht & Startseite|de_de-Home]]
* [[🗂️ Einklappbare Kategorien|de_de-Collapsible-Categories]]
* [[🖥️ HUD, Diagnose & UI-Rendering|de_de-HUD-and-Diagnostics]]
* [[🧩 Architektur & Mixin-Subsystem|de_de-Architecture-and-Mixins]]
