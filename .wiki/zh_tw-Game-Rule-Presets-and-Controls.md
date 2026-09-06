# 🎛️ 遊戲規則預設與控制元件

| 參數 | 規格說明 |
| :--- | :--- |
| **預設引擎類別** | `net.instantgratification.collapsiblegamerules.preset.GameRulePresetEngine` |
| **預設資料結構** | `record Preset(String id, Component displayName, Map<String, Object> ruleValues)` |
| **互動元件** | `IntegerSliderWidget`, `BooleanToggleWidget` |
| **滑桿輔助類別** | `net.instantgratification.collapsiblegamerules.util.GameRuleSliderHelper` |
| **開啟狀態底色** | `0x4400FF00` (翡翠綠背景) |
| **關閉狀態底色** | `0x44FF0000` (寶石紅背景) |
| **內建預設組** | `builder` ("🏰 Builder Mode"), `fast_play` ("⚡ Fast Play"), `hardcore` ("💀 Hardcore Realism") |

---

## 📖 機制概述

Collapsible Game Rules 包含互動式 UI 控制元件與內建遊戲規則預設，讓玩家能一鍵套用整套世界規則設定，或透過滑桿流暢微調數值，免去在文字框中手動鍵入數字的麻煩。

---

## 🏰 內建預設矩陣

`GameRulePresetEngine` 提供三種預先配置的遊戲玩法預設：

| 預設 ID | 顯示標題 | 配置遊戲規則 | 設定數值 | 玩法影響 |
| :--- | :--- | :--- | :--- | :--- |
| `builder` | **🏰 Builder Mode** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doMobSpawning`<br>`keepInventory`<br>`mobGriefing`<br>`doFireTick` | `false`<br>`false`<br>`false`<br>`true`<br>`false`<br>`false` | 創造建築專用：鎖定日夜與天氣、停止生物生成、停用苦力怕破壞與火焰蔓延。 |
| `fast_play` | **⚡ Fast Play** | `randomTickSpeed`<br>`playersSleepingPercentage`<br>`keepInventory` | `10`<br>`0`<br>`true` | 快節奏生存：加速作物生長（3倍）、支援單人生存睡覺跳過夜晚、死亡保留物品。 |
| `hardcore` | **💀 Hardcore Realism** | `naturalRegeneration`<br>`doInsomnia`<br>`playersSleepingPercentage` | `false`<br>`true`<br>`100` | 極限生存：關閉自然回血（需依賴金蘋果/藥水）、強制幻翼生成。 |

---

## 🎚️ 互動數值滑桿 (`IntegerSliderWidget`)

`IntegerSliderWidget` 將原始數字輸入框替換為可拖曳調整的平滑滑桿。

### 數學常態化公式

將整數數值 $v$ 轉換為滑桿位置：
$$\text{normalized} = \frac{\text{clamp}(v, \text{min}, \text{max}) - \text{min}}{\text{max} - \text{min}}$$

根據滑桿位置 $p \in [0.0, 1.0]$ 計算整數數值：
$$\text{calculatedInt} = \text{min} + \text{round}\left(p \times (\text{max} - \text{min})\right)$$

### 原版規則數值邊界 (`GameRuleSliderHelper`)

| 規則鍵名 | 最小值 ($	ext{min}$) | 最大值 ($	ext{max}$) | 原版預設值 |
| :--- | :--- | :--- | :--- |
| `randomTickSpeed` | `0` | `100` | `3` |
| `spawnRadius` | `0` | `32` | `10` |
| `playersSleepingPercentage` | `0` | `100` | `100` |
| `maxEntityCramming` | `0` | `100` | `24` |
| `maxCommandChainLength` | `0` | `65536` | `65536` |
| `commandModificationBlockLimit` | `0` | `65536` | `32768` |

---

## 🔘 二值開關元件 (`BooleanToggleWidget`)

`BooleanToggleWidget` 為布林值規則提供即時視覺回饋：

* **狀態: TRUE (`ON`)**：綠色字體 `✔ ON` 配翡翠綠半透明底色（`0x4400FF00`）。
* **狀態: FALSE (`OFF`)**：紅色字體 `✖ OFF` 配寶石紅半透明底色（`0x44FF0000`）。
* **滑鼠點擊**：單擊切換狀態並回呼 `onToggle.accept(newState)`。

```
┌─────────────────────────┐     ┌─────────────────────────┐
│         ✔ ON            │     │         ✖ OFF           │
│   (Green Tint 0x4400FF00)│     │   (Red Tint 0x44FF0000) │
└─────────────────────────┘     └─────────────────────────┘
```

---

## 🔗 相關文件

* [[🏠 概覽與首頁|zh_tw-Home]]
* [[🗂️ 可折疊類別|zh_tw-Collapsible-Categories]]
* [[📜 遊戲規則參考表|zh_tw-GameRules-Reference]]
* [[🧩 架構與 Mixin 子系統|zh_tw-Architecture-and-Mixins]]
