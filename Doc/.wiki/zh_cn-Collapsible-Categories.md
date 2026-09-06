# 🗂️ 可折叠类别

| 参数 | 规格说明 |
| :--- | :--- |
| **核心组件** | `CollapsibleCategoryRuleEntry` (内部类) |
| **所属 Mixin** | `AbstractGameRulesScreenRuleListMixin` |
| **目标类** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList` |
| **状态指示图标** | 展开: `▼ ` \| 折叠: `▶ ` |
| **数量徽章格式** | ` (N rules)` (颜色: `ChatFormatting.GRAY`) |
| **悬停高亮颜色** | `0x22FFFFFF` (25% 不透明度白色方框) |
| **底部分隔线颜色** | `0x44AAAAAA` (细分界线) |
| **文字颜色** | 悬停: `0xFFFFFFAA` \| 默认: `0xFFFFFFFF` |
| **交互音效** | `net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK` (音量: `1.0F`) |
| **朗读类型** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 机制概述

在原生 Minecraft 中，游戏规则屏幕将分类标题呈现为静态、无法交互的文本标签（`CategoryRuleEntry`），所有子规则依次排列在单一滚动面板中。当模组添加数十项规则时，界面会变得极其冗长且难以浏览。

**Collapsible Categories** 将静态标签替换为支持展开与收起的交互式 `CollapsibleCategoryRuleEntry` 组件。

---

## 🎨 视觉排版与层次结构

在 `RuleList` 中呈现时，每个分类标题都包含动态视觉指示与状态徽章：

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

## ⚙️ 技术机制

### 1. 子规则计数算法
当 `updateVisibleEntries()` 遍历 `allEntries` 时，会计算当前分类到下一个分类之间的子规则数量：

```java
int childCount = 0;
for (int j = i + 1; j < this.collapsible_game_rules$allEntries.size(); j++) {
    if (this.collapsible_game_rules$allEntries.get(j) instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
        break;
    }
    childCount++;
}
```

### 2. 类别渲染管线 (`extractContent`)
渲染流程通过 Minecraft 现代 `GuiGraphicsExtractor` 接口执行：
1. **悬停方框**：光标悬停在分类条目上时，在 `[getX() - 2, getY()]` 到 `[getX() + getWidth() + 2, getY() + 24]` 绘制半透明矩形（`0x22FFFFFF`）。
2. **箭头与文字**：展开时加上 `▼ `，折叠时加上 `▶ `，后接分类标签与灰色规则数徽章（` (N rules)`）。
3. **居中对齐**：文本水平居中绘制于 `getContentXMiddle()`，垂直偏移为 `getContentY() + 5`。
4. **底部分隔线**：在 `getY() + 23` 处绘制一条细分隔线（`0x44AAAAAA`）区隔相邻类别。

### 3. 鼠标点击事件处理
分类标题在 `mouseClicked(MouseButtonEvent event, boolean doubleClick)` 中捕获点击：
* **左键点击 (`event.button() == 0`)** 或 **右键点击 (`event.button() == 1`)**：
  1. 触发类别的 `toggleAction.run()`。
  2. 将新的布尔状态写入 `GameRuleStateConfig`。
  3. 播放原版点击音效：`SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)`。
  4. 调用 `updateVisibleEntries()` 动态从列表中添加或移除子规则项目。
  5. 调用 `updateSizeAndPosition(...)` 重新计算列表长度。

### 4. 无障碍与屏幕朗读
`CollapsibleCategoryRuleEntry` 实现 `NarratableEntry`：
* **优先级**：`NarrationPriority.HOVERED`
* **朗读内容**：将类别标签声明为 `NarratedElementType.TITLE`，确保屏幕朗读器在聚焦或悬停时朗读类别名称。

---

## 🔗 相关文档

* [[🏠 概览与首页|zh_cn-Home]]
* [[🌎 全局操作与批量切换|zh_cn-Global-Actions-and-Bulk-Toggles]]
* [[⌨️ 键盘导航与无障碍辅助|zh_cn-Keyboard-Navigation]]
* [[🧩 架构与 Mixin 子系统|zh_cn-Architecture-and-Mixins]]
