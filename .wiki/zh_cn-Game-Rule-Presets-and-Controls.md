# 🎛️ 游戏规则预设与控件

| 参数 | 规格说明 |
| :--- | :--- |
| **预设引擎类** | `net.instantgratification.collapsiblegamerules.preset.GameRulePresetEngine` |
| **预设数据结构** | `record Preset(String id, Component displayName, Map<String, Object> ruleValues)` |
| **交互组件** | `IntegerSliderWidget`, `BooleanToggleWidget` |
| **滑块辅助类** | `net.instantgratification.collapsiblegamerules.util.GameRuleSliderHelper` |
| **开启状态底色** | `0x4400FF00` (翡翠绿背景) |
| **关闭状态底色** | `0x44FF0000` (宝石红背景) |
| **内置预设组** | `builder` ("🏰 Builder Mode"), `fast_play` ("⚡ Fast Play"), `hardcore` ("💀 Hardcore Realism") |

---

## 📖 机制概述

Collapsible Game Rules 包含交互式 UI 控制组件与内置游戏规则预设，让玩家能一键应用整套世界规则设置，或通过滑块流畅微调数值，免去在文本框中手动键入数字的麻烦。

---

## 🏰 内置预设矩阵

`GameRulePresetEngine` 提供三种预先配置的游戏玩法预设：

| 预设 ID | 显示标题 | 配置游戏规则 | 设置数值 | 玩法影响 |
| :--- | :--- | :--- | :--- | :--- |
| `builder` | **🏰 Builder Mode** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doMobSpawning`<br>`keepInventory`<br>`mobGriefing`<br>`doFireTick` | `false`<br>`false`<br>`false`<br>`true`<br>`false`<br>`false` | 创造建筑专用：锁定日夜与天气、停止生物生成、禁用苦力怕破坏与火焰蔓延。 |
| `fast_play` | **⚡ Fast Play** | `randomTickSpeed`<br>`playersSleepingPercentage`<br>`keepInventory` | `10`<br>`0`<br>`true` | 快节奏生存：加速作物生长（3倍）、支持单人生存睡觉跳过夜晚、死亡保留物品。 |
| `hardcore` | **💀 Hardcore Realism** | `naturalRegeneration`<br>`doInsomnia`<br>`playersSleepingPercentage` | `false`<br>`true`<br>`100` | 极限生存：关闭自然回血（需依赖金苹果/药水）、强制幻翼生成。 |

---

## 🎚️ 交互数值滑块 (`IntegerSliderWidget`)

`IntegerSliderWidget` 将原始数字输入框替换为可拖拽调整的平滑滑块。

### 数学标准化公式

将整数数值 $v$ 转换为滑块位置：
$$\text{normalized} = \frac{\text{clamp}(v, \text{min}, \text{max}) - \text{min}}{\text{max} - \text{min}}$$

根据滑块位置 $p \in [0.0, 1.0]$ 计算整数数值：
$$\text{calculatedInt} = \text{min} + \text{round}\left(p \times (\text{max} - \text{min})\right)$$

### 原版规则数值边界 (`GameRuleSliderHelper`)

| 规则键名 | 最小值 ($	ext{min}$) | 最大值 ($	ext{max}$) | 原版默认值 |
| :--- | :--- | :--- | :--- |
| `randomTickSpeed` | `0` | `100` | `3` |
| `spawnRadius` | `0` | `32` | `10` |
| `playersSleepingPercentage` | `0` | `100` | `100` |
| `maxEntityCramming` | `0` | `100` | `24` |
| `maxCommandChainLength` | `0` | `65536` | `65536` |
| `commandModificationBlockLimit` | `0` | `65536` | `32768` |

---

## 🔘 二值开关组件 (`BooleanToggleWidget`)

`BooleanToggleWidget` 为布尔值规则提供即时视觉反馈：

* **状态: TRUE (`ON`)**：绿色字体 `✔ ON` 配翡翠绿半透明底色（`0x4400FF00`）。
* **状态: FALSE (`OFF`)**：红色字体 `✖ OFF` 配宝石红半透明底色（`0x44FF0000`）。
* **鼠标点击**：单击切换状态并回调 `onToggle.accept(newState)`。

```
┌─────────────────────────┐     ┌─────────────────────────┐
│         ✔ ON            │     │         ✖ OFF           │
│   (Green Tint 0x4400FF00)│     │   (Red Tint 0x44FF0000) │
└─────────────────────────┘     └─────────────────────────┘
```

---

## 🔗 相关文档

* [[🏠 概览与首页|zh_cn-Home]]
* [[🗂️ 可折叠类别|zh_cn-Collapsible-Categories]]
* [[📜 游戏规则参考表|zh_cn-GameRules-Reference]]
* [[🧩 架构与 Mixin 子系统|zh_cn-Architecture-and-Mixins]]
