# 🎛️ ゲームルールプリセットとコントロール

| 項目 | 仕様 |
| :--- | :--- |
| **プリセットエンジン** | `net.instantgratification.collapsiblegamerules.preset.GameRulePresetEngine` |
| **プリセットデータ形式** | `record Preset(String id, Component displayName, Map<String, Object> ruleValues)` |
| **専用ウィジェット** | `IntegerSliderWidget`, `BooleanToggleWidget` |
| **スライダーヘルパー** | `net.instantgratification.collapsiblegamerules.util.GameRuleSliderHelper` |
| **トグル ON 背景色** | `0x4400FF00` (エメラルドグリーン) |
| **トグル OFF 背景色** | `0x44FF0000` (ルビーレッド) |
| **組み込みプリセット** | `builder` ("🏰 建築家モード"), `fast_play` ("⚡ スピードラン"), `hardcore` ("💀 ハードコア") |

---

## 📖 概要

Collapsible Game Rules は、数値を直接入力する手動テキストボックスを直感的なスライダーに置き換え、さらにプレイスタイルに応じたルール設定を一括適用できるプリセットを提供します。

---

## 🏰 組み込みプリセット一覧

| ID | プリセット名 | 対象ゲームルール | 適用値 | プレイ体験への影響 |
| :--- | :--- | :--- | :--- | :--- |
| `builder` | **🏰 建築家モード** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doMobSpawning`<br>`keepInventory`<br>`mobGriefing`<br>`doFireTick` | `false`<br>`false`<br>`false`<br>`true`<br>`false`<br>`false` | 建築作業に集中: 時間と天候を固定し、モブや延焼、クリーパーの爆発を無効化。 |
| `fast_play` | **⚡ スピードラン** | `randomTickSpeed`<br>`playersSleepingPercentage`<br>`keepInventory` | `10`<br>`0`<br>`true` | テンポの速いサバイバル: 作物の成長速度を約3倍にし、1人睡眠で朝に。 |
| `hardcore` | **💀 ハードコア** | `naturalRegeneration`<br>`doInsomnia`<br>`playersSleepingPercentage` | `false`<br>`true`<br>`100` | 過酷なサバイバル: 自然回復を停止（金リンゴやポーションが必須）、ファントム出現。 |

---

## 🎚️ 数値スライダー (`IntegerSliderWidget`)

テキスト入力欄を置き換え、ドラッグで直感的に数値を変更できます。

バニラルールの範囲:
* `randomTickSpeed`: 0 〜 100
* `spawnRadius`: 0 〜 32
* `playersSleepingPercentage`: 0 〜 100
* `maxEntityCramming`: 0 〜 100

---

## 🔘 ブール値トグルスイッチ (`BooleanToggleWidget`)

* **ON (有効)**: エメラルドグリーンの背景と `✔ ON` 表示。
* **OFF (無効)**: ルビーレッドの背景と `✖ OFF` 表示。

---

## 🔗 関連ドキュメント

* [[🏠 概要とホームポータル|ja_jp-Home]]
* [[🗂️ 折りたたみ可能なカテゴリ|ja_jp-Collapsible-Categories]]
* [[📜 ゲームルールリファレンステーブル|ja_jp-GameRules-Reference]]
* [[🧩 アーキテクチャと Mixin サブシステム|ja_jp-Architecture-and-Mixins]]
