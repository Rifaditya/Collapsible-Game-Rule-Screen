# 🌎 グローバル操作と一括切り替え

| 項目 | 仕様 |
| :--- | :--- |
| **コンポーネントクラス** | `net.instantgratification.collapsiblegamerules.ui.GlobalActionsRuleEntry` |
| **リスト内の固定位置** | インデックス `0` (`RuleList` の最上部) |
| **左ボタン** | `[ すべて展開 ]` (`gui.collapsible-game-rules.expand_all`) |
| **右ボタン** | `[ すべて折りたたむ ]` (`gui.collapsible-game-rules.collapse_all`) |
| **ホバー時のエフェクト** | `0x22FFFFFF` (カーソルがある側の半面に適用) |
| **下部区切り線** | `0x44AAAAAA` |
| **クリック音** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 概要

Modpack などで大量のルールが存在する場合、1つずつカテゴリをクリックして開閉するのは手間がかかります。
**グローバル操作バー** はリストの最上部（インデックス 0）に常時配置されており、ワンクリックで全カテゴリを一括展開・一括折りたたみできます。

---

## 🎨 レイアウト設計

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ◄──────────────── 左半面 ──────────────────►◄────────────── 右半面 ────────► │
│               [ すべて展開 ]                               [ すべて折りたたむ ] │
│ ─────────────────────────────────────────────────────────────────────────── │
└─────────────────────────────────────────────────────────────────────────────┘
```

* **左半面 (`mouseX < getX() + getWidth() / 2`)**: `expandAll` を実行。
* **右半面 (`mouseX >= getX() + getWidth() / 2`)**: `collapseAll` を実行。
* マウスを合わせた側の背景が `0x22FFFFFF` でハイライトされ、文字色が強調されます。

---

## ⚙️ 技術仕様

### 1. インデックス 0 への固定配置
`AbstractGameRulesScreenRuleListMixin` より:

```java
if (!this.collapsible_game_rules$allEntries.isEmpty()) {
    this.addEntry(new GlobalActionsRuleEntry(
        () -> {
            List<String> allKeys = this.collapsible_game_rules$allEntries.stream()
                .filter(e -> e instanceof AbstractGameRulesScreen.CategoryRuleEntry)
                .map(e -> {
                    Component lbl = ((CategoryRuleEntryAccessor) e).collapsible_game_rules$getLabel();
                    if (lbl.getContents() instanceof TranslatableContents translatable) {
                        return translatable.getKey();
                    }
                    return lbl.getString();
                })
                .toList();
            GameRuleStateConfig.expandAll(allKeys);
            this.collapsible_game_rules$updateVisibleEntries();
        },
        () -> {
            GameRuleStateConfig.collapseAll();
            this.collapsible_game_rules$updateVisibleEntries();
        }
    ));
}
```

---

## 🔗 関連ドキュメント

* [[🏠 概要とホームポータル|ja_jp-Home]]
* [[🗂️ 折りたたみ可能なカテゴリ|ja_jp-Collapsible-Categories]]
* [[🧠 状態保持と JSON 設定|ja_jp-State-Persistence-and-Config]]
* [[🧩 アーキテクチャと Mixin サブシステム|ja_jp-Architecture-and-Mixins]]
