# 🖥️ HUD、診斷與 UI 渲染

| 參數 | 規格說明 |
| :--- | :--- |
| **圖形繪製引擎** | `net.minecraft.client.gui.GuiGraphicsExtractor` |
| **目標畫面環境** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen` |
| **懸停文字著色** | `0xFFFFFFAA` (柔和淡黃高亮) |
| **預設文字著色** | `0xFFFFFFFF` (清晰純白) |
| **標題懸停背景框** | `0x22FFFFFF` (半透明覆蓋矩形) |
| **類別分隔線** | `0x44AAAAAA` (水平分界邊框) |
| **開啟開關底色** | `0x4400FF00` (翡翠綠) |
| **關閉開關底色** | `0x44FF0000` (寶石紅) |
| **無障礙朗讀類型** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 機制概述

Minecraft 26.2 對客戶端渲染管線進行了重大重構，將標準 GUI 繪製操作全面遷移至現代 `GuiGraphicsExtractor` 子系統。

Collapsible Game Rules 完全原生基於該繪圖引擎構建，採用直接向量顏色填滿、字型度量置中計算與無障礙朗讀輸出，實現零影格率開銷。

---

## 🎨 調色盤與視覺診斷

| 視覺元件 | ARGB 十六進制 | 視覺外觀描述 | 應用位置 |
| :--- | :--- | :--- | :--- |
| **懸停填滿** | `0x22FFFFFF` | 13% 不透明度白色矩形覆蓋。 | 類別標題懸停與全域按鈕懸停。 |
| **邊框分隔線** | `0x44AAAAAA` | 27% 不透明度淺灰 1px 分割線。 | 類別標題與全域按鈕的底部邊緣。 |
| **懸停文字** | `0xFFFFFFAA` | 柔和亮黃色文字。 | 滑鼠懸停時的類別文字與按鈕文字。 |
| **標準文字** | `0xFFFFFFFF` | 100% 純白文字。 | 類別標題文字與規則計數徽章。 |
| **計數徽章** | `ChatFormatting.GRAY` | 原版灰色文字 (` (N rules)`)。 | 附加於類別標題後的後綴。 |
| **開關開啟底色** | `0x4400FF00` | 27% 不透明度翡翠綠。 | `BooleanToggleWidget` 的 `✔ ON` 狀態背景。 |
| **開關關閉底色** | `0x44FF0000` | 27% 不透明度寶石紅。 | `BooleanToggleWidget` 的 `✖ OFF` 狀態背景。 |

---

## 💻 渲染實作細節

### 現代 `GuiGraphicsExtractor` 管線
在 `CollapsibleCategoryRuleEntry.extractContent(...)` 內部：

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

## 🔊 音訊與無障礙反饋

玩家與介面互動時，原版點擊音效提供實質反饋：

* **事件**: `SoundEvents.UI_BUTTON_CLICK`
* **音量**: `1.0F`
* **音調**: `1.0F`
* **觸發時機**:
  - 滑鼠左鍵或右鍵點擊類別標題。
  - 鍵盤按下空白鍵 / Enter 鍵切換類別。
  - 鍵盤按下左方向鍵（折疊）或右方向鍵（展開）。
  - 滑鼠點擊 `[ Expand All ]` 或 `[ Collapse All ]` 按鈕。

---

## 🔗 相關文件

* [[🏠 概覽與首頁|zh_tw-Home]]
* [[🗂️ 可折疊類別|zh_tw-Collapsible-Categories]]
* [[⌨️ 鍵盤導航與無障礙輔助|zh_tw-Keyboard-Navigation]]
* [[🧩 架構與 Mixin 子系統|zh_tw-Architecture-and-Mixins]]
