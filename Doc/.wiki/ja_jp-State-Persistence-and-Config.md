# 🧠 状態保持と JSON 設定

| 項目 | 仕様 |
| :--- | :--- |
| **設定管理クラス** | `net.instantgratification.collapsiblegamerules.GameRuleStateConfig` |
| **ファイル保存先** | `.minecraft/config/collapsible-game-rules-state.json` |
| **メモリ内データ構造** | `Set<String> expandedCategories = new HashSet<>()` |
| **シリアライズ** | `com.google.gson.Gson` (インデント整形有効) |
| **遅延保存フラグ** | `private static boolean isDirty = false` |
| **保存トリガー** | `ScreenMixin` が `Screen.removed()` を捕捉 (`@At("HEAD")`) |

---

## 📖 概要

カテゴリの開閉状態は自動的にローカルの JSON 設定ファイルに保存されます。ワールドを切り替えたり、ゲームを再起動しても前回の整理状態がそのまま維持されます。

---

## 📄 JSON ファイル構造

`.minecraft/config/collapsible-game-rules-state.json`:

```json
[
  "gamerule.category.spawning",
  "gamerule.category.mobs",
  "gamerule.category.updates"
]
```

* **配列内にキーが存在する場合**: そのカテゴリは **展開状態** となります。
* **配列内にキーが存在しない場合**: そのカテゴリは **折りたたみ状態** となります。

---

## ⚡ パフォーマンスを守る遅延書き込み

クリックごとにファイルを書き込むとディスク I/O が発生し、ゲームプレイがカクつく原因になります。当 Mod ではメモリ内のフラグ `isDirty` を使用し、設定画面を閉じた瞬間にまとめてファイルへ書き込みます。

これにより、高速な操作でもフレームレートを落とさずスムーズに動作します。

---

## 🔗 関連ドキュメント

* [[🏠 概要とホームポータル|ja_jp-Home]]
* [[🌎 グローバル操作と一括切り替え|ja_jp-Global-Actions-and-Bulk-Toggles]]
* [[🧩 アーキテクチャと Mixin サブシステム|ja_jp-Architecture-and-Mixins]]
