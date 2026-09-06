# 📜 游戏规则参考表

| 参数 | 规格说明 |
| :--- | :--- |
| **执行环境** | **仅客户端** (`"environment": "client"`) |
| **自定义服务端游戏规则** | `0` (纯 UI 界面重构范畴) |
| **原版分类覆盖率** | `100%` (`Player`, `Mobs`, `Spawning`, `Drops`, `Updates`, `Chat`, `Misc`) |
| **模组自定义类别支持** | 通过 `DasikMetadataHelper` 与 `CategoryPrettifier` 动态支持 |
| **持久化机制** | 客户端 `config/collapsible-game-rules-state.json` |

---

## 📖 缺失策略与范畴规范

> [!NOTE]
> **纯客户端 UI 模组**：Collapsible Game Rules 纯粹为界面重构模组，**不会**注册任何自定义的服务端 GameRules、修改游戏规则的刻计算逻辑或改变原版游戏机制。界面上显示的所有规则皆为原版或由其他模组注册的规则。

---

## 🗂️ 原版标准类别与规则分组

打开游戏规则设置界面时，模组会自动将所有 Minecraft 26.2 规则整理至以下分类：

| 分类 | 默认包含规则 | 常用调整 |
| :--- | :--- | :--- |
| **👤 玩家 (Player)** | `keepInventory`<br>`naturalRegeneration`<br>`playersSleepingPercentage`<br>`fallDamage`<br>`drowningDamage`<br>`fireDamage`<br>`freezeDamage` | 死亡保留物品栏、修改睡觉跳过夜晚百分比、禁用特定环境伤害。 |
| **⚔️ 生物 (Mobs)** | `mobGriefing`<br>`doMobSpawning`<br>`doMobLoot`<br>`universalAnger`<br>`forgiveDeadPlayers` | 防止苦力怕破坏方块、控制生物掉落物、调整生物激怒状态。 |
| **🌱 生成 (Spawning)** | `doMobSpawning`<br>`doPatrolSpawning`<br>`doTraderSpawning`<br>`doWardenSpawning`<br>`spawnRadius` | 调整巡逻队或流浪商人生成频率、设置世界出生点半径。 |
| **📦 掉落物 (Drops)** | `doEntityDrops`<br>`doTileDrops`<br>`doMobLoot`<br>`globalSoundEvents` | 管理方块破坏掉落物、击杀生物掉落或全局音效广播。 |
| **🌧️ 更新与循环 (Updates)** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doFireTick`<br>`randomTickSpeed`<br>`maxCommandChainLength` | 锁定日夜与天气、停止火焰蔓延、调整作物生长随机刻速度。 |
| **💬 聊天与日志 (Chat)** | `showDeathMessages`<br>`sendCommandFeedback`<br>`logAdminCommands`<br>`reducedDebugInfo` | 设置死亡消息显示、命令反馈、或在 F3 屏幕隐藏坐标。 |
| **⚙️ 杂项 (Misc)** | `commandBlockOutput`<br>`maxEntityCramming`<br>`disableElytraMovementCheck` | 调整生物挤压伤害上限或命令方块输出日志。 |

---

## 🧩 动态模组类别兼容

由第三方模组注册（或通过 `DasikLibrary` 的 `DynamicGameRuleManager` 生成）的任何自定义游戏规则分类，均会自动识别并生成独立的折叠文件夹与格式化标题。

---

## 🔗 相关文档

* [[🏠 概览与首页|zh_cn-Home]]
* [[🗂️ 可折叠类别|zh_cn-Collapsible-Categories]]
* [[🎛️ 游戏规则预设与控件|zh_cn-Game-Rule-Presets-and-Controls]]
* [[📚 DasikLibrary API 集成|zh_cn-API-and-Library-Integration]]
