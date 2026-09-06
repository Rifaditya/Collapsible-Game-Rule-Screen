# ⌨️ 键盘导航与无障碍辅助

| 参数 | 规格说明 |
| :--- | :--- |
| **组件类** | `CollapsibleCategoryRuleEntry` |
| **无障碍接口** | `net.minecraft.client.gui.narration.NarratableEntry` |
| **朗读优先级** | `NarrationPriority.HOVERED` |
| **朗读元素类型** | `NarratedElementType.TITLE` |
| **切换快捷键** | `GLFW_KEY_SPACE`, `GLFW_KEY_ENTER`, `GLFW_KEY_KP_ENTER` |
| **收起按键** | `GLFW_KEY_LEFT` (仅展开时有效) |
| **展开按键** | `GLFW_KEY_RIGHT` (仅折叠时有效) |
| **音效反馈** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 机制概述

Collapsible Game Rules 完全遵循键盘导航与无障碍朗读规范，让玩家能够完全通过键盘、控制器或屏幕朗读器流畅操作复杂的游戏规则菜单，无需仰赖鼠标。

---

## ⌨️ 按键事件对照表

当分类标题在 `RuleList` 中处于聚焦状态时，`keyPressed(KeyEvent event)` 会处理以下按键操作：

| 按键绑定 | GLFW 常量 | 触发操作 | 生效条件 | 音效反馈 |
| :--- | :--- | :--- | :--- | :--- |
| **Space** | `GLFW_KEY_SPACE` | 切换展开/折叠 | 始终生效 | `UI_BUTTON_CLICK` |
| **Enter** | `GLFW_KEY_ENTER` | 切换展开/折叠 | 始终生效 | `UI_BUTTON_CLICK` |
| **Numpad Enter** | `GLFW_KEY_KP_ENTER` | 切换展开/折叠 | 始终生效 | `UI_BUTTON_CLICK` |
| **Left Arrow (←)** | `GLFW_KEY_LEFT` | **折叠分类** | 仅当 `expanded == true` | `UI_BUTTON_CLICK` |
| **Right Arrow (→)** | `GLFW_KEY_RIGHT` | **展开分类** | 仅当 `expanded == false` | `UI_BUTTON_CLICK` |

---

## ⚙️ 技术实现细节

### 1. 方向键逻辑处理
遵循标准操作系统树状导航惯例：

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

### 2. 屏幕朗读朗诵支持 (`updateNarration`)
实现原版无障碍协议：

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

## 🔗 相关文档

* [[🏠 概览与首页|zh_cn-Home]]
* [[🗂️ 可折叠类别|zh_cn-Collapsible-Categories]]
* [[🖥️ HUD、诊断与 UI 渲染|zh_cn-HUD-and-Diagnostics]]
* [[🧩 架构与 Mixin 子系统|zh_cn-Architecture-and-Mixins]]
