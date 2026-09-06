# ⌨️ キーボードナビゲーションとアクセシビリティ

| 項目 | 仕様 |
| :--- | :--- |
| **対象コンポーネント** | `CollapsibleCategoryRuleEntry` |
| **アクセシビリティインターフェース** | `net.minecraft.client.gui.narration.NarratableEntry` |
| **ナレーション優先度** | `NarrationPriority.HOVERED` |
| **ナレーション要素** | `NarratedElementType.TITLE` |
| **開閉切り替えキー** | `Space`, `Enter`, `テンキー Enter` |
| **折りたたみキー** | `左矢印キー (←)` (展開中のみ) |
| **展開キー** | `右矢印キー (→)` (折りたたみ中のみ) |
| **操作フィードバック音** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 概要

Collapsible Game Rules は、完全なキーボード操作およびスクリーンリーダーによるナレーションに対応しています。マウスを使用せずにすべてのゲームルールを自在に探索・切り替えることができます。

---

## ⌨️ キーバインド一覧

リスト内でカテゴリヘッダーにフォーカスがあるとき、以下のキー入力を受け付けます:

| キー | GLFW 定数 | 動作 | 条件 | サウンド |
| :--- | :--- | :--- | :--- | :--- |
| **Space** | `GLFW_KEY_SPACE` | 開閉状態の反転 | 常に有効 | `UI_BUTTON_CLICK` |
| **Enter** | `GLFW_KEY_ENTER` | 開閉状態の反転 | 常に有効 | `UI_BUTTON_CLICK` |
| **テンキー Enter** | `GLFW_KEY_KP_ENTER` | 開閉状態の反転 | 常に有効 | `UI_BUTTON_CLICK` |
| **左矢印 (←)** | `GLFW_KEY_LEFT` | **カテゴリを折りたたむ** | `expanded == true` のみ | `UI_BUTTON_CLICK` |
| **右矢印 (→)** | `GLFW_KEY_RIGHT` | **カテゴリを展開する** | `expanded == false` のみ | `UI_BUTTON_CLICK` |

ファイルツリー等で広く用いられている標準的な操作体系を採用しているため、直感的にリストを操作できます。

---

## 🔗 関連ドキュメント

* [[🏠 概要とホームポータル|ja_jp-Home]]
* [[🗂️ 折りたたみ可能なカテゴリ|ja_jp-Collapsible-Categories]]
* [[🖥️ HUD、診断、UI レンダリング|ja_jp-HUD-and-Diagnostics]]
* [[🧩 アーキテクチャと Mixin サブシステム|ja_jp-Architecture-and-Mixins]]
