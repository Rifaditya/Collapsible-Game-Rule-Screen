# 🔍 スマート検索統合

| 項目 | 仕様 |
| :--- | :--- |
| **フック対象メソッド** | `populateChildren(Ljava/lang/String;)V` |
| **インジェクション位置** | `@At("TAIL")` |
| **Mixin クラス** | `AbstractGameRulesScreenRuleListMixin` |
| **正規化処理** | `filter.toLowerCase(java.util.Locale.ROOT)` |
| **検索中フラグ** | `isSearching = !currentFilter.isEmpty()` |
| **展開判定条件** | `isExpanded = isSearching || GameRuleStateConfig.isExpanded(key)` |

---

## 📖 概要

バニラのゲームルール画面には検索機能が備わっていますが、折りたたみ機能と組み合わせた場合、通常は折りたたまれたカテゴリの中に検索結果が隠れてしまう問題が生じます。

当 Mod の **スマート検索統合** は、検索バーへの入力をリアルタイムに監視し、一致するルールが存在するカテゴリを一時的に自動展開します。

---

## ⚙️ スマート検索の実行フロー

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          SMART SEARCH PIPELINE                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   プレイヤーが検索バーに入力（例: "fire"）                                  │
│        │                                                                    │
│        ▼                                                                    │
│   バニラの RuleList.populateChildren("fire") が実行                         │
│        │                                                                    │
│        ▼ (@Inject at TAIL)                                                  │
│   AbstractGameRulesScreenRuleListMixin が検知                               │
│        ├─ 検索文字列を保存                                                  │
│        ├─ フィルタ後の全エントリをキャプチャ                                │
│        └─ updateVisibleEntries() を呼び出し                                 │
│             │                                                               │
│             ▼                                                               │
│        isSearching が TRUE と評価                                           │
│             │                                                               │
│             ▼                                                               │
│        一致するルールを含むカテゴリが強制的に isExpanded = TRUE になり展開  │
│                                                                             │
│   検索バーを空にする ("")                                                   │
│        │                                                                    │
│        ▼                                                                    │
│   isSearching = FALSE ──> 保存されていた GameRuleStateConfig の状態に復帰  │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

検索バーをクリアすると、ユーザーが手動で設定していた元の開閉状態に自動的に戻ります。

---

## 🔗 関連ドキュメント

* [[🏠 概要とホームポータル|ja_jp-Home]]
* [[🗂️ 折りたたみ可能なカテゴリ|ja_jp-Collapsible-Categories]]
* [[🧠 状態保持と JSON 設定|ja_jp-State-Persistence-and-Config]]
* [[🧩 アーキテクチャと Mixin サブシステム|ja_jp-Architecture-and-Mixins]]
