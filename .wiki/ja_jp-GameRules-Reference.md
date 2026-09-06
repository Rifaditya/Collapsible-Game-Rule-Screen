# 📜 ゲームルールリファレンステーブル

| 項目 | 仕様 |
| :--- | :--- |
| **動作環境** | **クライアント専用** (`"environment": "client"`) |
| **Mod 固有のサーバーゲームルール** | `0` (UI レイヤーのみで動作) |
| **バニラカテゴリの網羅率** | `100%` (`Player`, `Mobs`, `Spawning`, `Drops`, `Updates`, `Chat`, `Misc`) |
| **Mod カテゴリの動的サポート** | `DasikMetadataHelper` および `CategoryPrettifier` による自動整形 |
| **状態保持メカニズム** | `config/collapsible-game-rules-state.json` |

---

## 📖 UI 動作ポリシー

> [!NOTE]
> **クライアント専用 UI Mod**: Collapsible Game Rules は、設定画面の階層化と操作性を改善する Mod です。サーバー側のゲームルール判定やティック処理には一切干渉しません。表示されるルールはすべてバニラまたは導入済み Mod に由来します。

---

## 🗂️ 標準バニラカテゴリと収録ルール

画面を開くと、Minecraft 26.2 のゲームルールが以下のフォルダ構造に自動分類されます:

| カテゴリ | 含まれる主なルール | 主な設定内容 |
| :--- | :--- | :--- |
| **👤 プレイヤー (Player)** | `keepInventory`<br>`naturalRegeneration`<br>`playersSleepingPercentage`<br>`fallDamage`<br>`drowningDamage`<br>`fireDamage`<br>`freezeDamage` | 死亡時のアイテム保持、睡眠による夜のスキップ割合、各種ダメージ判定の有効化/無効化。 |
| **⚔️ モブ (Mobs)** | `mobGriefing`<br>`doMobSpawning`<br>`doMobLoot`<br>`universalAnger`<br>`forgiveDeadPlayers` | クリーパーによる破壊制御、ドロップアイテムの有無、怒り状態の共有設定。 |
| **🌱 スポーン (Spawning)** | `doMobSpawning`<br>`doPatrolSpawning`<br>`doTraderSpawning`<br>`doWardenSpawning`<br>`spawnRadius` | モブや行商人、巡回隊のスポーン設定、初期スポーン地点の半径。 |
| **📦 ドロップ (Drops)** | `doEntityDrops`<br>`doTileDrops`<br>`doMobLoot`<br>`globalSoundEvents` | ブロック破壊時のアイテムドロップ、モブドロップ、全体サウンドイベント。 |
| **🌧️ 更新・環境 (Updates)** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doFireTick`<br>`randomTickSpeed`<br>`maxCommandChainLength` | 昼夜・天候のサイクル、延焼の進行、作物の成長速度（ランダムティック）。 |
| **💬 チャット (Chat)** | `showDeathMessages`<br>`sendCommandFeedback`<br>`logAdminCommands`<br>`reducedDebugInfo` | 死亡メッセージの表示、コマンド実行ログ、デバッグ画面の情報制限。 |
| **⚙️ その他 (Misc)** | `commandBlockOutput`<br>`maxEntityCramming`<br>`disableElytraMovementCheck` | コマンドブロックの出力、エンティティの密集上限、エリトラの飛行速度チェック。 |

---

## 🔗 関連ドキュメント

* [[🏠 概要とホームポータル|ja_jp-Home]]
* [[🗂️ 折りたたみ可能なカテゴリ|ja_jp-Collapsible-Categories]]
* [[🎛️ ゲームルールプリセットとコントロール|ja_jp-Game-Rule-Presets-and-Controls]]
* [[📚 DasikLibrary API 統合|ja_jp-API-and-Library-Integration]]
