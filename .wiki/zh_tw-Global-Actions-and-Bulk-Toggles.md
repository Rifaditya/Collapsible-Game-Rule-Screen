# 🌎 全域操作與批次切換

| 參數 | 規格說明 |
| :--- | :--- |
| **元件類別** | `net.instantgratification.collapsiblegamerules.ui.GlobalActionsRuleEntry` |
| **清單位置** | 索引 `0` (永久置頂於 `RuleList` 頂部) |
| **左側操作按鈕** | `[ Expand All ]` (`gui.collapsible-game-rules.expand_all`) |
| **右側操作按鈕** | `[ Collapse All ]` (`gui.collapsible-game-rules.collapse_all`) |
| **左按鈕中心位置** | `this.getX() + this.getWidth() / 4` |
| **右按鈕中心位置** | `this.getX() + 3 * this.getWidth() / 4` |
| **懸停背景色** | `0x22FFFFFF` (套用於當前懸停的半區) |
| **底部分隔線** | `0x44AAAAAA` |
| **點擊音效** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 機制概述

在管理包含數百條遊戲規則的模組包時，逐一展開或折疊每個類別顯得繁瑣。

**Global Actions** 在規則清單的頂部（**索引 0**）固定置入雙按鈕橫條，提供一鍵全選展開與全選折疊所有類別的便捷操作。

---

## 🎨 視覺排版與分割按鈕設計

該頂欄橫跨螢幕寬度並劃分為兩個獨立的點擊區域：

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ◄──────────────── Left Half ───────────────►◄────────────── Right Half ───► │
│               [ Expand All ]                               [ Collapse All ] │
│ ─────────────────────────────────────────────────────────────────────────── │
└─────────────────────────────────────────────────────────────────────────────┘
```

* **左側區域 (`mouseX < getX() + getWidth() / 2`)**：觸發 `expandAll`。
* **右側區域 (`mouseX >= getX() + getWidth() / 2`)**：觸發 `collapseAll`。
* **懸停狀態**：僅高亮游標所在的半區（`0x22FFFFFF`），並將文字顏色變更為 `0xFFFFFFAA`。

---

## ⚙️ 技術機制

### 1. 頂部置頂注入
在 `AbstractGameRulesScreenRuleListMixin` 的 `updateVisibleEntries()` 中，頂欄在任何規則項目之前注入：

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

### 2. 類別標識鍵提取
在展開所有分類時，Stream 管線會提取所有分類的唯一標識：
1. 過濾出所有 `CategoryRuleEntry` 實例。
2. 透過 `CategoryRuleEntryAccessor` 獲取分類標籤。
3. 若包含 `TranslatableContents`，提取翻譯鍵（如 `gamerule.category.spawning`）。
4. 未翻譯時回退至原始字串。
5. 收集為不可變 Java 25 清單（`.toList()`）並傳入 `GameRuleStateConfig.expandAll(allKeys)`。

### 3. 點擊事件派發
在 `GlobalActionsRuleEntry.mouseClicked(MouseButtonEvent event, boolean doubleClick)` 中：
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

## 🌐 在地化鍵位

按鈕文字在 `assets/collapsiblegamerules/lang/` 中定義：

```json
{
  "gui.collapsible-game-rules.expand_all": "Expand All",
  "gui.collapsible-game-rules.collapse_all": "Collapse All"
}
```

---

## 🔗 相關文件

* [[🏠 概覽與首頁|zh_tw-Home]]
* [[🗂️ 可折疊類別|zh_tw-Collapsible-Categories]]
* [[🧠 狀態持久化與 JSON 設定|zh_tw-State-Persistence-and-Config]]
* [[🧩 架構與 Mixin 子系統|zh_tw-Architecture-and-Mixins]]
