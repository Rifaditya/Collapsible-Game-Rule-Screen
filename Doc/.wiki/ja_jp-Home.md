# ⚒️ Collapsible Game Rules Wiki (折りたたみ可能なゲームルール)

<div align="center">

<img src="https://raw.githubusercontent.com/Rifaditya/Collapsible-Game-Rule-Screen/main/Images/2026-08-04_11.06.33.png" alt="Collapsible Game Rules Banner" width="800">

<p align="center">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge&logo=minecraft" alt="Minecraft 26.2+">
  <img src="https://img.shields.io/badge/Fabric-0.145.4+-blue?style=for-the-badge&logo=fabric" alt="Fabric">
  <img src="https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk" alt="Java 25">
  <img src="https://img.shields.io/badge/DasikLibrary-1.7.4-purple?style=for-the-badge" alt="DasikLibrary 1.7.4">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License GPLv3">
</p>

</div>

---

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般リリースビルドに先んじた未リリースの最新コミットや開発中機能が含まれる場合があります。

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 🧭 Collapsible Game Rules へようこそ

**Collapsible Game Rules** は、Minecraft のゲームルール設定画面をカテゴリごとに美しく折りたためる構造へと刷新するクライアントサイド Mod です。検索時の自動展開、フルキーボード操作、ローカルでの状態保持、直感的なゲームルールプリセット機能を提供します。

---

## 🌟 主な機能とクイックインデックス

| 機能 | 説明 | リファレンスガイド |
| :--- | :--- | :--- |
| **🗂️ 折りたたみ可能なカテゴリ** | 静的なヘッダーを、ルール数バッジ付きの対話型ウィジェット（`▼`/`▶`）に置き換えます。 | [[🗂️ 折りたたみ可能なカテゴリ\|ja_jp-Collapsible-Categories]] |
| **🌎 グローバル操作バー** | リスト上部に固定された「すべて展開」「すべて折りたたむ」ボタンでワンクリック一括操作。 | [[🌎 グローバル操作と一括切り替え\|ja_jp-Global-Actions-and-Bulk-Toggles]] |
| **🔍 スマート検索統合** | 検索バーに文字を入力すると、一致するルールが含まれるカテゴリが自動的に展開されます。 | [[🔍 スマート検索統合\|ja_jp-Smart-Search-Integration]] |
| **⌨️ キーボードナビゲーション** | スペースキー、Enter、左右の矢印キーによる完全なキーボード操作と画面ナレーションに対応。 | [[⌨️ キーボードナビゲーションとアクセシビリティ\|ja_jp-Keyboard-Navigation]] |
| **🧠 状態の永続化** | 開閉状態を `config/collapsible-game-rules-state.json` に保存し、次回起動時も維持します。 | [[🧠 状態保持と JSON 設定\|ja_jp-State-Persistence-and-Config]] |
| **✨ カテゴリ名の整形** | Mod の未翻訳キーを自動的に読みやすいタイトルケース形式に整形して表示します。 | [[✨ カテゴリ名の整形とフォーマット\|ja_jp-Category-Prettification-and-Naming]] |
| **🎛️ プリセットとコントロール** | ワンクリックで適用できるゲームモードプロファイル（建築家、スピードラン、ハードコア）と数値スライダー。 | [[🎛️ ゲームルールプリセットとコントロール\|ja_jp-Game-Rule-Presets-and-Controls]] |
| **🧩 DasikLibrary API 統合** | `DynamicGameRuleManager` から動的翻訳を照会し、Mod カテゴリのローカライズをサポートします。 | [[📚 DasikLibrary API 統合\|ja_jp-API-and-Library-Integration]] |

---

## 🚀 クイックスタートとインストール

1. **[Fabric Loader](https://fabricmc.net/)** (`>=0.19.1`) をインストールします。
2. **[Fabric API](https://modrinth.com/mod/fabric-api)** を導入します。
3. **[DasikLibrary](https://modrinth.com/mod/dasik-library)** (`>=1.7.0`、**必須前提 Mod**) を導入します。
4. `collapsible-game-rules-1.0.9+26.2.jar` を `.minecraft/mods` フォルダに配置します。
5. **Java 25+** 環境で Minecraft を起動します。

---

## 📚 ドキュメント目次

```
Wiki ルート
├── 🧭 バージョン互換性マトリックス --------> [[🧭 バージョン互換性マトリックス|ja_jp-Version-Compatibility]]
├── 🎮 コアインターフェース機能
│   ├── 折りたたみ可能なカテゴリ ------------> [[🗂️ 折りたたみ可能なカテゴリ|ja_jp-Collapsible-Categories]]
│   ├── グローバル操作と一括切り替え --------> [[🌎 グローバル操作と一括切り替え|ja_jp-Global-Actions-and-Bulk-Toggles]]
│   ├── スマート検索統合 --------------------> [[🔍 スマート検索統合|ja_jp-Smart-Search-Integration]]
│   └── キーボードナビゲーションとアクセシビリティ -> [[⌨️ キーボードナビゲーションとアクセシビリティ|ja_jp-Keyboard-Navigation]]
├── ⚙️ 設定とプリセット
│   ├── 状態保持と JSON 設定 ----------------> [[🧠 状態保持と JSON 設定|ja_jp-State-Persistence-and-Config]]
│   ├── カテゴリ名の整形とフォーマット ------> [[✨ カテゴリ名の整形とフォーマット|ja_jp-Category-Prettification-and-Naming]]
│   └── ゲームルールプリセットとコントロール -> [[🎛️ ゲームルールプリセットとコントロール|ja_jp-Game-Rule-Presets-and-Controls]]
├── 📋 適用範囲とリファレンス
│   ├── ゲームルールリファレンステーブル ----> [[📜 ゲームルールリファレンステーブル|ja_jp-GameRules-Reference]]
│   ├── Brigadier コマンドと適用範囲 --------> [[💬 Brigadier コマンドと適用範囲|ja_jp-Commands]]
│   └── 進捗と適用範囲 ----------------------> [[🏆 進捗と適用範囲|ja_jp-Advancements]]
└── 💻 技術アーキテクチャと開発
    ├── HUD、診断、UI レンダリング ---------> [[🖥️ HUD、診断、UI レンダリング|ja_jp-HUD-and-Diagnostics]]
    ├── 開発環境のセットアップと Gradle -----> [[🛠️ 開発環境のセットアップと Gradle ビルド|ja_jp-Developer-Setup-and-Building]]
    ├── アーキテクチャと Mixin サブシステム -> [[🧩 アーキテクチャと Mixin サブシステム|ja_jp-Architecture-and-Mixins]]
    └── DasikLibrary API 統合 ---------------> [[📚 DasikLibrary API 統合|ja_jp-API-and-Library-Integration]]
```

---

## 📜 クレジットとライセンス

* **作者・リード開発者**: **Dasik (Rifaditya)**
* **ライセンス**: **GNU General Public License v3.0 (GPLv3)**
* **GitHub リポジトリ**: [GitHub Repository](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen)
* **Modrinth ページ**: [Modrinth](https://modrinth.com/mod/collapsible-game-rules) (`lObgjyJl`)
* **CurseForge ページ**: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/collapsible-game-rules) (`1468932`)
