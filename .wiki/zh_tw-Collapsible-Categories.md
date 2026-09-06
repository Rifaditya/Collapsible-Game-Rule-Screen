# 🗂️ 可折疊類別

| 參數 | 規格說明 |
| :--- | :--- |
| **核心元件** | `CollapsibleCategoryRuleEntry` (內部類別) |
| **所屬 Mixin** | `AbstractGameRulesScreenRuleListMixin` |
| **目標類別** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList` |
| **狀態指示圖示** | 展開: `▼ ` \| 折疊: `▶ ` |
| **數量徽章格式** | ` (N rules)` (顏色: `ChatFormatting.GRAY`) |
| **懸停高亮顏色** | `0x22FFFFFF` (25% 不透明度白色方框) |
| **底部分隔線顏色** | `0x44AAAAAA` (細分界線) |
| **文字顏色** | 懸停: `0xFFFFFFAA` \| 預設: `0xFFFFFFFF` |
| **互動音效** | `net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK` (音量: `1.0F`) |
| **朗讀類型** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 機制概述

在原生 Minecraft 中，遊戲規則畫面將分類標題呈現為靜態、無法互動的文字標籤（`CategoryRuleEntry`），所有子規則依序排列在單一滾動面板中。當模組新增數十項規則時，介面會變得極其冗長且難以瀏覽。

**Collapsible Categories** 將靜態標籤替換為支援展開與收合的互動式 `CollapsibleCategoryRuleEntry` 元件。

---

## 🎨 視覺排版與層次結構

在 `RuleList` 中呈現時，每個分類標題都包含動態視覺指示與狀態徽章：

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

## ⚙️ 技術機制

### 1. 子規則計數演算法
當 `updateVisibleEntries()` 遍歷 `allEntries` 時，會計算當前分類到下一個分類之間的子規則數量：

```java
int childCount = 0;
for (int j = i + 1; j < this.collapsible_game_rules$allEntries.size(); j++) {
    if (this.collapsible_game_rules$allEntries.get(j) instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
        break;
    }
    childCount++;
}
```

### 2. 類別渲染管線 (`extractContent`)
渲染流程透過 Minecraft 現代 `GuiGraphicsExtractor` 介面執行：
1. **懸停方框**：游標懸停在分類條目上時，在 `[getX() - 2, getY()]` 到 `[getX() + getWidth() + 2, getY() + 24]` 繪製半透明矩形（`0x22FFFFFF`）。
2. **箭頭與文字**：展開時加上 `▼ `，折疊時加上 `▶ `，後接分類標籤與灰色規則數徽章（` (N rules)`）。
3. **置中對齊**：文字水平置中繪製於 `getContentXMiddle()`，垂直偏移為 `getContentY() + 5`。
4. **底部分隔線**：在 `getY() + 23` 處繪製一條細分隔線（`0x44AAAAAA`）區隔相鄰類別。

### 3. 滑鼠點擊事件處理
分類標題在 `mouseClicked(MouseButtonEvent event, boolean doubleClick)` 中捕捉點擊：
* **左鍵點擊 (`event.button() == 0`)** 或 **右鍵點擊 (`event.button() == 1`)**：
  1. 觸發類別的 `toggleAction.run()`。
  2. 將新的布林狀態寫入 `GameRuleStateConfig`。
  3. 播放原版點擊音效：`SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)`。
  4. 呼叫 `updateVisibleEntries()` 動態從清單中新增或移除子規則項目。
  5. 呼叫 `updateSizeAndPosition(...)` 重新計算清單長度。

### 4. 無障礙與螢幕朗讀
`CollapsibleCategoryRuleEntry` 實作 `NarratableEntry`：
* **優先級**：`NarrationPriority.HOVERED`
* **朗讀內容**：將類別標籤宣告為 `NarratedElementType.TITLE`，確保螢幕朗讀器在聚焦或懸停時朗讀類別名稱。

---

## 🔗 相關文件

* [[🏠 概覽與首頁|zh_tw-Home]]
* [[🌎 全域操作與批次切換|zh_tw-Global-Actions-and-Bulk-Toggles]]
* [[⌨️ 鍵盤導航與無障礙輔助|zh_tw-Keyboard-Navigation]]
* [[🧩 架構與 Mixin 子系統|zh_tw-Architecture-and-Mixins]]
