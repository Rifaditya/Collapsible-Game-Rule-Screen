# 💬 Brigadier 命令与范围说明

| 参数 | 规格说明 |
| :--- | :--- |
| **自定义 Brigadier 命令** | `0` (依架构设计不注册任何服务器命令) |
| **命令架构** | 纯客户端 GUI 图形界面 |
| **原版命令对齐度** | `100%` 完全兼容原版 `/gamerule` 命令 |
| **游戏内 GUI 开启路径** | 单人创建世界 $	o$ 游戏规则 \| 暂停菜单 $	o$ 游戏规则 |

---

## 📖 缺失策略强制规范

> [!NOTE]
> **零自定义服务端命令**：依据“即时满足（Instant Gratification）”设计哲学，**Collapsible Game Rules** 不会注册任何聊天命令（例如 `/collapsiblegamerules reload` 或 `/cgr config`）。所有配置与类别管理均直接在原生的游戏规则设置屏幕上通过交互操作完成，并提供即时视觉反馈。

---

## 💻 原版命令兼容性

由于本模组完全建立在 Minecraft 原生 `AbstractGameRulesScreen` 之上，通过标准聊天命令修改的任何规则数值，都会即时与本界面保持完全同步：

### 常见原版 `/gamerule` 命令

```bash
# Toggle mob griefing (creeper block damage)
/gamerule mobGriefing false

# Keep player inventory on death
/gamerule keepInventory true

# Set crop growth random tick speed
/gamerule randomTickSpeed 10

# Disable daylight cycle
/gamerule doDaylightCycle false
```

当您重新打开游戏规则界面时，所有通过命令变更的数值都会立即呈现在其对应的可折叠类别中。

---

## 🔗 相关文档

* [[🏠 概览与首页|zh_cn-Home]]
* [[📜 游戏规则参考表|zh_cn-GameRules-Reference]]
* [[🎛️ 游戏规则预设与控件|zh_cn-Game-Rule-Presets-and-Controls]]
