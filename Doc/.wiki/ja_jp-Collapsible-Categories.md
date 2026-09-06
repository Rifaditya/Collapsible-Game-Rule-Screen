# 🗂️ 折りたたみ可能なカテゴリ

| 項目 | 仕様 |
| :--- | :--- |
| **システムコンポーネント** | `CollapsibleCategoryRuleEntry` (内部クラス) |
| **基幹 Mixin** | `AbstractGameRulesScreenRuleListMixin` |
| **対象クラス** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList` |
| **展開状態アイコン** | 展開中: `▼ ` \| 折りたたみ中: `▶ ` |
| **ルール数バッジの形式** | ` (N rules)` (`ChatFormatting.GRAY`) |
| **ホバー時の背景色** | `0x22FFFFFF` (半透明ホワイト) |
| **下部区切り線** | `0x44AAAAAA` (落ち着いたグレー線) |
| **テキストカラー** | ホバー時: `0xFFFFFFAA` \| 通常時: `0xFFFFFFFF` |
| **クリック音** | `net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK` (音量: `1.0F`) |
| **ナレーション優先度** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 概要

バニラの Minecraft では、カテゴリ名は静的なテキストラベル（`CategoryRuleEntry`）として配置され、すべてのルールが1つの長いスクロールリストに表示されます。Mod が多数導入されている環境では、目的のルールを探すのが非常に困難になります。

**Collapsible Categories** は、これらのラベルをインタラクティブな `CollapsibleCategoryRuleEntry` に置き換え、カテゴリごとにクリックまたはキーボード操作で自由に開閉できるようにします。

---

## 🎨 画面構成とレイアウト

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ [ すべて展開 ]                                           [ すべて折りたたむ ] │ ◄── GlobalActionsRuleEntry (Index 0)
├─────────────────────────────────────────────────────────────────────────────┤
│ ▼ ⚔️ Mobs (14 rules)                                                        │ ◄── CollapsibleCategoryRuleEntry (展開状態)
│ ─────────────────────────────────────────────────────────────────────────── │
│   mobGriefing                                                     [ ON ]    │ ◄── 子 RuleEntry
│   doMobSpawning                                                   [ ON ]    │ ◄── 子 RuleEntry
│   doMobLoot                                                       [ ON ]    │ ◄── 子 RuleEntry
├─────────────────────────────────────────────────────────────────────────────┤
│ ▶ 👤 Player (8 rules)                                                       │ ◄── CollapsibleCategoryRuleEntry (折りたたみ状態)
│ ─────────────────────────────────────────────────────────────────────────── │
│ ▼ 🌧️ Updates (6 rules)                                                      │ ◄── CollapsibleCategoryRuleEntry (展開状態)
│ ─────────────────────────────────────────────────────────────────────────── │
│   doFireTick                                                      [ ON ]    │ ◄── 子 RuleEntry
│   randomTickSpeed                                                 [ 3  ]    │ ◄── 子 RuleEntry
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## ⚙️ 技術仕様

### 1. ルール数のカウントアルゴリズム
`updateVisibleEntries()` 内で、次のカテゴリヘッダーが出現するまでの子要素数を集計します:

```java
int childCount = 0;
for (int j = i + 1; j < this.collapsible_game_rules$allEntries.size(); j++) {
    if (this.collapsible_game_rules$allEntries.get(j) instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
        break;
    }
    childCount++;
}
```

### 2. レンダリング処理 (`extractContent`)
`GuiGraphicsExtractor` を使用:
1. **ホバーハイライト**: `[getX() - 2, getY()]` から `[getX() + getWidth() + 2, getY() + 24]` まで `0x22FFFFFF` を描画。
2. **プレフィックスとバッジ**: 状態に応じた矢印（`▼ ` または `▶ `）、カテゴリ名、およびグレーのルール数バッジ（` (N rules)`）。
3. **中央揃え**: `getContentXMiddle()` を中心に描画。
4. **下部区切り線**: `getY() + 23` の位置に `0x44AAAAAA` の線を引いて視覚的に区分。

### 3. クリックイベント処理
`mouseClicked(MouseButtonEvent event, boolean doubleClick)`:
* 左クリックまたは右クリックで開閉を反転し、`GameRuleStateConfig` に保存した上で `SoundEvents.UI_BUTTON_CLICK` を再生。

---

## 🔗 関連ドキュメント

* [[🏠 概要とホームポータル|ja_jp-Home]]
* [[🌎 グローバル操作と一括切り替え|ja_jp-Global-Actions-and-Bulk-Toggles]]
* [[⌨️ キーボードナビゲーションとアクセシビリティ|ja_jp-Keyboard-Navigation]]
* [[🧩 アーキテクチャと Mixin サブシステム|ja_jp-Architecture-and-Mixins]]
