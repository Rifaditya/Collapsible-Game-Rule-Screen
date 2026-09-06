# 🖥️ HUD、诊断与 UI 渲染

| 参数 | 规格说明 |
| :--- | :--- |
| **图形绘制引擎** | `net.minecraft.client.gui.GuiGraphicsExtractor` |
| **目标屏幕环境** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen` |
| **悬停文本着色** | `0xFFFFFFAA` (柔和淡黄高亮) |
| **默认文本着色** | `0xFFFFFFFF` (清晰纯白) |
| **标题悬停背景框** | `0x22FFFFFF` (半透明覆盖矩形) |
| **类别分隔线** | `0x44AAAAAA` (水平分界边框) |
| **开启开关底色** | `0x4400FF00` (翡翠绿) |
| **关闭开关底色** | `0x44FF0000` (宝石红) |
| **无障碍朗读类型** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 机制概述

Minecraft 26.2 对客户端渲染管线进行了重大重构，将标准 GUI 绘制操作全面迁移至现代 `GuiGraphicsExtractor` 子系统。

Collapsible Game Rules 完全原生基于该绘图引擎构建，采用直接矢量颜色填充、字体度量居中计算与无障碍朗读输出，实现零帧率开销。

---

## 🎨 调色盘与视觉诊断

| 视觉组件 | ARGB 十六进制 | 视觉外观描述 | 应用位置 |
| :--- | :--- | :--- | :--- |
| **悬停填充** | `0x22FFFFFF` | 13% 不透明度白色矩形覆盖。 | 类别标题悬停与全局按钮悬停。 |
| **边框分隔线** | `0x44AAAAAA` | 27% 不透明度浅灰 1px 分割线。 | 类别标题与全局按钮的底部边缘。 |
| **悬停文本** | `0xFFFFFFAA` | 柔和亮黄色文本。 | 鼠标悬停时的类别文本与按钮文本。 |
| **标准文本** | `0xFFFFFFFF` | 100% 纯白文本。 | 类别标题文本与规则计数徽章。 |
| **计数徽章** | `ChatFormatting.GRAY` | 原版灰色文本 (` (N rules)`)。 | 附加于类别标题后的后缀。 |
| **开关开启底色** | `0x4400FF00` | 27% 不透明度翡翠绿。 | `BooleanToggleWidget` 的 `✔ ON` 状态背景。 |
| **开关关闭底色** | `0x44FF0000` | 27% 不透明度宝石红。 | `BooleanToggleWidget` 的 `✖ OFF` 状态背景。 |

---

## 💻 渲染实现细节

### 現代 `GuiGraphicsExtractor` 管线
在 `CollapsibleCategoryRuleEntry.extractContent(...)` 内部：

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

## 🔊 音频与无障碍反馈

玩家与界面交互时，原版点击音效提供实质反馈：

* **事件**: `SoundEvents.UI_BUTTON_CLICK`
* **音量**: `1.0F`
* **音调**: `1.0F`
* **触发时机**:
  - 鼠标左键或右键点击类别标题。
  - 键盘按下空格键 / 回车键切换类别。
  - 键盘按下左方向键（折叠）或右方向键（展开）。
  - 鼠标点击 `[ Expand All ]` 或 `[ Collapse All ]` 按钮。

---

## 🔗 相关文档

* [[🏠 概览与首页|zh_cn-Home]]
* [[🗂️ 可折叠类别|zh_cn-Collapsible-Categories]]
* [[⌨️ 键盘导航与无障碍辅助|zh_cn-Keyboard-Navigation]]
* [[🧩 架构与 Mixin 子系统|zh_cn-Architecture-and-Mixins]]
