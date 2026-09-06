# 💬 Brigadier コマンドと適用範囲

| 項目 | 仕様 |
| :--- | :--- |
| **専用 Brigadier コマンド** | `0` (設計上、新規コマンドなし) |
| **コマンドアーキテクチャ** | 完全なクライアントサイドグラフィカルユーザーインターフェース (GUI) |
| **バニラコマンド互換性** | ネイティブの `/gamerule` コマンドと `100%` 互換 |
| **UI へのアクセス方法** | ワールド作成画面 $\to$ ゲームルール \| ポーズメニュー $\to$ ゲームルール |

---

## 📖 コマンド非登録ポリシー

> [!NOTE]
> **サーバーコマンド非登録**: Instant Gratification（即時満足）の設計哲学に基づき、Collapsible Game Rules は独自のチャットコマンド（`/cgr config` や `/collapsiblegamerules reload` など）を登録しません。すべての操作は設定画面上で直接完結します。

---

## 💻 バニラコマンドとの連携

当 Mod はバニラの `AbstractGameRulesScreen` の上位で動作するため、チャットから実行された `/gamerule` の変更内容は画面上にも即座に反映されます。

### 代表的な `/gamerule` コマンド

```bash
# クリーパーなどのモブによる地形破壊を無効化
/gamerule mobGriefing false

# 死亡時にインベントリを保持
/gamerule keepInventory true

# ランダムティックスピード（作物の成長速度）を調整
/gamerule randomTickSpeed 10

# 昼夜のサイクルを停止
/gamerule doDaylightCycle false
```

コマンドで値を変更した後にゲームルール画面を開くと、折りたたみカテゴリ内の対応するルールに最新の値が反映されます。

---

## 🔗 関連ドキュメント

* [[🏠 概要とホームポータル|ja_jp-Home]]
* [[📜 ゲームルールリファレンステーブル|ja_jp-GameRules-Reference]]
* [[🎛️ ゲームルールプリセットとコントロール|ja_jp-Game-Rule-Presets-and-Controls]]
