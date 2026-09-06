# ⌨️ 鍵盤導航與無障礙輔助

| 參數 | 規格說明 |
| :--- | :--- |
| **元件類別** | `CollapsibleCategoryRuleEntry` |
| **無障礙介面** | `net.minecraft.client.gui.narration.NarratableEntry` |
| **朗讀優先級** | `NarrationPriority.HOVERED` |
| **朗讀元素類型** | `NarratedElementType.TITLE` |
| **切換快捷鍵** | `GLFW_KEY_SPACE`, `GLFW_KEY_ENTER`, `GLFW_KEY_KP_ENTER` |
| **收合按鍵** | `GLFW_KEY_LEFT` (僅展開時有效) |
| **展開按鍵** | `GLFW_KEY_RIGHT` (僅折疊時有效) |
| **音效回饋** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 機制概述

Collapsible Game Rules 完全遵循鍵盤導航與無障礙朗讀規範，讓玩家能夠完全透過鍵盤、控制器或螢幕朗讀器流暢操作複雜的遊戲規則選單，無需仰賴滑鼠。

---

## ⌨️ 按鍵事件對照表

當分類標題在 `RuleList` 中處於聚焦狀態時，`keyPressed(KeyEvent event)` 會處理以下按鍵操作：

| 按鍵綁定 | GLFW 常數 | 觸發操作 | 生效條件 | 音效回饋 |
| :--- | :--- | :--- | :--- | :--- |
| **Space** | `GLFW_KEY_SPACE` | 切換展開/折疊 | 始終生效 | `UI_BUTTON_CLICK` |
| **Enter** | `GLFW_KEY_ENTER` | 切換展開/折疊 | 始終生效 | `UI_BUTTON_CLICK` |
| **Numpad Enter** | `GLFW_KEY_KP_ENTER` | 切換展開/折疊 | 始終生效 | `UI_BUTTON_CLICK` |
| **Left Arrow (←)** | `GLFW_KEY_LEFT` | **折疊分類** | 僅當 `expanded == true` | `UI_BUTTON_CLICK` |
| **Right Arrow (→)** | `GLFW_KEY_RIGHT` | **展開分類** | 僅當 `expanded == false` | `UI_BUTTON_CLICK` |

---

## ⚙️ 技術實作細節

### 1. 方向鍵邏輯處理
遵循標準作業系統樹狀導航慣例：

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

### 2. 螢幕朗讀朗誦支援 (`updateNarration`)
實作原版無障礙協定：

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

## 🔗 相關文件

* [[🏠 概覽與首頁|zh_tw-Home]]
* [[🗂️ 可折疊類別|zh_tw-Collapsible-Categories]]
* [[🖥️ HUD、診斷與 UI 渲染|zh_tw-HUD-and-Diagnostics]]
* [[🧩 架構與 Mixin 子系統|zh_tw-Architecture-and-Mixins]]
