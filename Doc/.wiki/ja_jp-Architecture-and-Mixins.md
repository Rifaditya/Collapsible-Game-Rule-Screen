# 🧩 アーキテクチャと Mixin サブシステム

| 項目 | 仕様 |
| :--- | :--- |
| **Mixin 設定ファイル** | `src/main/resources/collapsible-game-rules.mixins.json` |
| **Refmap ファイル名** | `collapsible-game-rules-refmap.json` |
| **互換性レベル** | `JAVA_25` |
| **ベースパッケージ** | `net.instantgratification.collapsiblegamerules` |
| **Mixin パッケージ** | `net.instantgratification.collapsiblegamerules.mixin` |
| **Mixin 数** | クライアント用 `4` つ (インジェクション 3 + アクセサ 1) |

---

## 📖 パッケージ構成

```
net.instantgratification.collapsiblegamerules
├── CollapsibleGameRulesFabric.java           ──> Mod 初期化と依存関係・ガード検証
├── CollapsibleGameRulesFabricClient.java     ──> クライアント初期化 (設定読み込み)
├── GameRuleStateConfig.java                  ──> JSON 永続化エンジン (Set<String>)
├── mixin
│   ├── AbstractGameRulesScreenRuleListMixin.java ──> リスト制御とカテゴリ描画
│   ├── CategoryRuleEntryAccessor.java        ──> カテゴリラベル取得用インターフェース
│   ├── IntegerRuleEntryMixin.java            ──> 数値ルール用ラッパー
│   └── ScreenMixin.java                      ──> 画面終了時の自動保存フック
├── preset
│   └── GameRulePresetEngine.java             ──> 組み込みプリセット定義
├── ui
│   ├── BooleanToggleWidget.java              ──> トグルスイッチウィジェット
│   ├── GlobalActionsRuleEntry.java           ──> 最上部の一括操作ヘッダー
│   └── IntegerSliderWidget.java              ──> 数値スライダーウィジェット
└── util
    ├── CategoryPrettifier.java               ──> 未翻訳キー整形エンジン
    ├── DasikMetadataHelper.java              ──> DasikLibrary 隔離呼び出しレイヤー
    ├── GameRuleSliderHelper.java             ──> バニラルールの範囲定義
    └── ModVersionGuard.java                  ──> ランタイム整合性チェック
```

---

## 🔗 関連ドキュメント

* [[🏠 概要とホームポータル|ja_jp-Home]]
* [[🗂️ 折りたたみ可能なカテゴリ|ja_jp-Collapsible-Categories]]
* [[🧠 状態保持と JSON 設定|ja_jp-State-Persistence-and-Config]]
* [[📚 DasikLibrary API 統合|ja_jp-API-and-Library-Integration]]
