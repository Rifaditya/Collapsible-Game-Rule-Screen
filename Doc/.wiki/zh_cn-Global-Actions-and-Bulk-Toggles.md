# 🌎 全局操作与批量切换

| 参数 | 规格说明 |
| :--- | :--- |
| **组件类** | `net.instantgratification.collapsiblegamerules.ui.GlobalActionsRuleEntry` |
| **列表位置** | 索引 `0` (永久置顶于 `RuleList` 顶部) |
| **左侧操作按钮** | `[ Expand All ]` (`gui.collapsible-game-rules.expand_all`) |
| **右侧操作按钮** | `[ Collapse All ]` (`gui.collapsible-game-rules.collapse_all`) |
| **左按钮中心位置** | `this.getX() + this.getWidth() / 4` |
| **右按钮中心位置** | `this.getX() + 3 * this.getWidth() / 4` |
| **悬停背景色** | `0x22FFFFFF` (应用于当前悬停的半区) |
| **底部分隔线** | `0x44AAAAAA` |
| **点击音效** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 机制概述

在管理包含数百条游戏规则的模组包时，逐一展开或折叠每个类别显得繁琐。

**Global Actions** 在规则列表的顶部（**索引 0**）固定置入双按钮横条，提供一键全选展开与全选折叠所有类别的便捷操作。

---

## 🎨 视觉排版与分割按钮设计

该顶栏横跨屏幕宽度并划分为两个独立的点击区域：

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ◄──────────────── Left Half ───────────────►◄────────────── Right Half ───► │
│               [ Expand All ]                               [ Collapse All ] │
│ ─────────────────────────────────────────────────────────────────────────── │
└─────────────────────────────────────────────────────────────────────────────┘
```

* **左侧区域 (`mouseX < getX() + getWidth() / 2`)**：触发 `expandAll`。
* **右侧区域 (`mouseX >= getX() + getWidth() / 2`)**：触发 `collapseAll`。
* **悬停状态**：仅高亮光标所在的半区（`0x22FFFFFF`），并将文本颜色变更为 `0xFFFFFFAA`。

---

## ⚙️ 技术机制

### 1. 顶部置顶注入
在 `AbstractGameRulesScreenRuleListMixin` 的 `updateVisibleEntries()` 中，顶栏在任何规则项目之前注入：

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

### 2. 类别标识键提取
在展开所有分类时，Stream 管线会提取所有分类的唯一标识：
1. 过滤出所有 `CategoryRuleEntry` 实例。
2. 通过 `CategoryRuleEntryAccessor` 获取分类标签。
3. 若包含 `TranslatableContents`，提取翻译键（如 `gamerule.category.spawning`）。
4. 未翻译时回退至原始字符串。
5. 收集为不可变 Java 25 列表（`.toList()`）并传入 `GameRuleStateConfig.expandAll(allKeys)`。

### 3. 点击事件分发
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

## 🌐 本地化键位

按钮文本在 `assets/collapsiblegamerules/lang/` 中定义：

```json
{
  "gui.collapsible-game-rules.expand_all": "Expand All",
  "gui.collapsible-game-rules.collapse_all": "Collapse All"
}
```

---

## 🔗 相关文档

* [[🏠 概览与首页|zh_cn-Home]]
* [[🗂️ 可折叠类别|zh_cn-Collapsible-Categories]]
* [[🧠 状态持久化与 JSON 配置|zh_cn-State-Persistence-and-Config]]
* [[🧩 架构与 Mixin 子系统|zh_cn-Architecture-and-Mixins]]
