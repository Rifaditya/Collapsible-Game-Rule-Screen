# 📜 遊戲規則參考表

| 參數 | 規格說明 |
| :--- | :--- |
| **執行環境** | **僅客戶端** (`"environment": "client"`) |
| **自訂伺服端遊戲規則** | `0` (純 UI 介面重構範疇) |
| **原版分類覆蓋率** | `100%` (`Player`, `Mobs`, `Spawning`, `Drops`, `Updates`, `Chat`, `Misc`) |
| **模組自訂類別支援** | 透過 `DasikMetadataHelper` 與 `CategoryPrettifier` 動態支援 |
| **持久化機制** | 客戶端 `config/collapsible-game-rules-state.json` |

---

## 📖 缺失策略與範疇規範

> [!NOTE]
> **純客戶端 UI 模組**：Collapsible Game Rules 純粹為介面重構模組，**不會**註冊任何自訂的伺服端 GameRules、修改遊戲規則的刻計算邏輯或改變原版遊戲機制。畫面上顯示的所有規則皆為原版或由其他模組註冊的規則。

---

## 🗂️ 原版標準類別與規則分組

開啟遊戲規則設定畫面時，模組會自動將所有 Minecraft 26.2 規則整理至以下分類：

| 分類 | 預設包含規則 | 常用調整 |
| :--- | :--- | :--- |
| **👤 玩家 (Player)** | `keepInventory`<br>`naturalRegeneration`<br>`playersSleepingPercentage`<br>`fallDamage`<br>`drowningDamage`<br>`fireDamage`<br>`freezeDamage` | 死亡保留物品欄、修改睡覺跳過夜晚百分比、停用特定環境傷害。 |
| **⚔️ 生物 (Mobs)** | `mobGriefing`<br>`doMobSpawning`<br>`doMobLoot`<br>`universalAnger`<br>`forgiveDeadPlayers` | 防止苦力怕破壞方塊、控制生物掉落物、調整生物激怒狀態。 |
| **🌱 生成 (Spawning)** | `doMobSpawning`<br>`doPatrolSpawning`<br>`doTraderSpawning`<br>`doWardenSpawning`<br>`spawnRadius` | 調整巡邏隊或流浪商人生成頻率、設定世界重生點半徑。 |
| **📦 掉落物 (Drops)** | `doEntityDrops`<br>`doTileDrops`<br>`doMobLoot`<br>`globalSoundEvents` | 管理方塊破壞掉落物、擊殺生物掉落或全域音效廣播。 |
| **🌧️ 更新與循環 (Updates)** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doFireTick`<br>`randomTickSpeed`<br>`maxCommandChainLength` | 鎖定日夜與天氣、停止火焰蔓延、調整作物生長隨機刻速度。 |
| **💬 聊天與日誌 (Chat)** | `showDeathMessages`<br>`sendCommandFeedback`<br>`logAdminCommands`<br>`reducedDebugInfo` | 設定死亡訊息顯示、指令回饋、或在 F3 畫面隱藏座標。 |
| **⚙️ 雜項 (Misc)** | `commandBlockOutput`<br>`maxEntityCramming`<br>`disableElytraMovementCheck` | 調整生物擠壓傷害上限或指令方塊輸出日誌。 |

---

## 🧩 動態模組類別相容

由第三方模組註冊（或透過 `DasikLibrary` 的 `DynamicGameRuleManager` 產生）的任何自訂遊戲規則分類，均會自動辨識並產生獨立的折疊資料夾與格式化標題。

---

## 🔗 相關文件

* [[🏠 概覽與首頁|zh_tw-Home]]
* [[🗂️ 可折疊類別|zh_tw-Collapsible-Categories]]
* [[🎛️ 遊戲規則預設與控制元件|zh_tw-Game-Rule-Presets-and-Controls]]
* [[📚 DasikLibrary API 整合|zh_tw-API-and-Library-Integration]]
