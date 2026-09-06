# ✨ カテゴリ名の整形とフォーマット

| 項目 | 仕様 |
| :--- | :--- |
| **ユーティリティクラス** | `net.instantgratification.collapsiblegamerules.util.CategoryPrettifier` |
| **メタデータヘルパー** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **整形発動条件** | `!Language.getInstance().has(key)` (未翻訳の場合のみ) |
| **プレフィックス削除** | `"gamerule.category."` を自動除去 |
| **区切り文字の処理** | `.` (名前空間) および `[_-]` (単語区切り) を解析 |
| **動的翻訳データソース** | `DynamicGameRuleManager.getGeneratedTranslations()` |

---

## 📖 概要

一部の Mod は、言語ファイル（`lang/en_us.json` など）でカテゴリ名を定義せず、内部識別用のキー（例: `gamerule.category.better-bats.better_bats`）がそのまま表示されてしまうことがあります。

**Category Prettifier** は、未翻訳の識別キーを自動解析し、人間が読みやすい整形済みの英語タイトルケース（例: `Better Bats`）へと動的に変換します。

---

## 📊 変換例

| 生のカテゴリキー | 整形後の表示名 | 変換ロジック |
| :--- | :--- | :--- |
| `gamerule.category.better-bats.better_bats` | **Better Bats** | 重複する名前空間とパスを統合 |
| `gamerule.category.minecraft.spawning` | **Spawning** | バニラの `minecraft` 名前空間を省略 |
| `gamerule.category.social_mobs.wolf_pack` | **Social Mobs Wolf Pack** | アンダースコアをスペースに変換し大文字化 |
| `gamerule.category.custom_rules` | **Custom Rules** | 単語ごとにキャピタライズ |

---

## 🔗 関連ドキュメント

* [[🏠 概要とホームポータル|ja_jp-Home]]
* [[🗂️ 折りたたみ可能なカテゴリ|ja_jp-Collapsible-Categories]]
* [[📚 DasikLibrary API 統合|ja_jp-API-and-Library-Integration]]
