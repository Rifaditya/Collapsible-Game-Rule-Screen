# 💬 Brigadier 指令與範圍說明

| 參數 | 規格說明 |
| :--- | :--- |
| **自訂 Brigadier 指令** | `0` (依架構設計不註冊任何伺服器指令) |
| **指令架構** | 純客戶端 GUI 圖形介面 |
| **原版指令對齊度** | `100%` 完全相容原版 `/gamerule` 指令 |
| **遊戲內 GUI 開啟路徑** | 單人建立世界 $	o$ 遊戲規則 \| 暫停選單 $	o$ 遊戲規則 |

---

## 📖 缺失策略強制規範

> [!NOTE]
> **零自訂伺服端指令**：依據「即時滿足（Instant Gratification）」設計哲學，**Collapsible Game Rules** 不會註冊任何聊天指令（例如 `/collapsiblegamerules reload` 或 `/cgr config`）。所有設定與類別管理均直接在原生的遊戲規則設定畫面上透過互動操作完成，並提供即時視覺回饋。

---

## 💻 原版指令相容性

由於本模組完全建立在 Minecraft 原生 `AbstractGameRulesScreen` 之上，透過標準聊天指令修改的任何規則數值，都會即時與本介面保持完全同步：

### 常見原版 `/gamerule` 指令

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

當您重新開啟遊戲規則畫面時，所有透過指令變更的數值都會立即呈現在其對應的可折疊類別中。

---

## 🔗 相關文件

* [[🏠 概覽與首頁|zh_tw-Home]]
* [[📜 遊戲規則參考表|zh_tw-GameRules-Reference]]
* [[🎛️ 遊戲規則預設與控制元件|zh_tw-Game-Rule-Presets-and-Controls]]
