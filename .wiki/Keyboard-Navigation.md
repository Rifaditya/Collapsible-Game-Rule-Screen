# ⌨️ Keyboard Navigation & Accessibility

| Parameter | Specification |
| :--- | :--- |
| **Component Class** | `CollapsibleCategoryRuleEntry` |
| **Accessibility Interface** | `net.minecraft.client.gui.narration.NarratableEntry` |
| **Narration Priority** | `NarrationPriority.HOVERED` |
| **Narration Element Type** | `NarratedElementType.TITLE` |
| **Toggle Keys** | `GLFW_KEY_SPACE`, `GLFW_KEY_ENTER`, `GLFW_KEY_KP_ENTER` |
| **Collapse Key** | `GLFW_KEY_LEFT` (Only active when expanded) |
| **Expand Key** | `GLFW_KEY_RIGHT` (Only active when collapsed) |
| **Audio Feedback** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 Overview

Collapsible Game Rules includes full keyboard navigation and screen narration compliance, enabling players to manage complex game rule menus using keyboard inputs, controllers, or assistive screen readers without touching the mouse.

---

## ⌨️ Key Event Mapping Table

When a category header is focused in the `RuleList`, the following keyboard events are processed by `keyPressed(KeyEvent event)`:

| Key Binding | GLFW Constant | Action Triggered | Condition | Audio Feedback |
| :--- | :--- | :--- | :--- | :--- |
| **Space** | `GLFW_KEY_SPACE` | Toggle Expansion | Always | `UI_BUTTON_CLICK` |
| **Enter (Return)** | `GLFW_KEY_ENTER` | Toggle Expansion | Always | `UI_BUTTON_CLICK` |
| **Numpad Enter** | `GLFW_KEY_KP_ENTER` | Toggle Expansion | Always | `UI_BUTTON_CLICK` |
| **Left Arrow (←)** | `GLFW_KEY_LEFT` | **Collapse Category** | Only if `expanded == true` | `UI_BUTTON_CLICK` |
| **Right Arrow (→)** | `GLFW_KEY_RIGHT` | **Expand Category** | Only if `expanded == false` | `UI_BUTTON_CLICK` |

---

## ⚙️ Technical Implementation

### 1. Directional Key Processing
The implementation follows standard OS hierarchical tree-view navigation conventions:

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

### 2. Screen Reader Narration (`updateNarration`)
To ensure full accessibility for visually impaired players, the widget implements Minecraft's narration protocol:

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

## 🔗 Related Documentation

* [[Overview & Home|Home]]
* [[Collapsible Categories|Collapsible-Categories]]
* [[HUD, Diagnostics & UI Rendering|HUD-and-Diagnostics]]
* [[Architecture & Mixin Subsystem|Architecture-and-Mixins]]
