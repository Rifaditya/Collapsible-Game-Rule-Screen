# 🧭 バージョン互換性マトリックス

| 項目 | 仕様 |
| :--- | :--- |
| **アクティブ対象バージョン** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Mod バージョン** | `1.0.9+26.2` |
| **サポート対象ゲーム環境** | Minecraft 26.2+ (モダン Sovereign 時代) |
| **Java 要件** | **Java 25+** (`release = 25`, toolchain Java 25) |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` (`*`) |
| **コア前提 Mod** | **DasikLibrary** `>=1.7.0` (推奨: `1.7.4`) |
| **ビルドツール** | Loom `1.15.2` / Gradle `9.3+` (`--no-daemon`) |
| **動作環境** | **クライアント専用** (`"environment": "client"`) |
| **ライセンス** | **GPL-3.0-or-later** |

---

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge や Modrinth での一般リリースビルドに先んじた未リリースの最新コミットや開発中機能が含まれる場合があります。

---

## 📊 世代間ランタイムライフサイクルマトリックス

Collapsible Game Rules は、最新世代（`MC 26.2+`）向けに設計されており、`GuiGraphicsExtractor` レンダリングパイプライン、Java 25 Stream API (`.toList()`)、非難読化ランタイム環境を活用しています。

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       MULTI-ERA RUNTIME LIFECYCLE MATRIX                    │
├───────────────────┬─────────────┬──────────────┬───────────────┬────────────┤
│ Minecraft Anchor  │ Java Level  │ Loader Bound │ DasikLibrary  │ 状態       │
├───────────────────┼─────────────┼──────────────┼───────────────┼────────────┤
│ **MC 26.2+**      │ **Java 25** │ `>=0.19.1`   │ `>=1.7.0`     │ 🟢 対象    │
│ MC 26.1.2         │ Java 25     │ `>=0.18.4`   │ `>=1.7.0`     │ 🟡 前方互換│
│ MC 1.21.x         │ Java 21     │ N/A          │ N/A           │ ⚪ 非対応  │
│ MC 1.20.1         │ Java 17     │ N/A          │ N/A           │ ⚪ 非対応  │
└───────────────────┴─────────────┴──────────────┴───────────────┴────────────┘
```

---

## 🔒 バージョンガードと ClassLoader 保護

互換性のないランタイムでのワールド破損を防ぐため、当 Mod には `net.instantgratification.collapsiblegamerules.util.ModVersionGuard` が組み込まれています。

### 検証メカニズム
`ModInitializer.onInitialize()` にて実行:
```java
ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
```

クラスローダーで必要なクラスが解決できない場合、クラッシュループを防止して安全に終了します。

---

## 📦 依存関係の宣言

`src/main/resources/fabric.mod.json` より:

```json
{
  "schemaVersion": 1,
  "id": "collapsible-game-rules",
  "version": "${version}",
  "name": "Collapsible Game Rules",
  "description": "Makes the GameRules UI screens collapsible by category.",
  "authors": [
    "Dasik (Rifaditya)"
  ],
  "license": "GPL-3.0-or-later",
  "environment": "client",
  "entrypoints": {
    "main": [
      "net.instantgratification.collapsiblegamerules.CollapsibleGameRulesFabric"
    ],
    "client": [
      "net.instantgratification.collapsiblegamerules.CollapsibleGameRulesFabricClient"
    ]
  },
  "mixins": [
    "collapsible-game-rules.mixins.json"
  ],
  "depends": {
    "fabricloader": ">=0.19.1",
    "minecraft": ">=26.2-",
    "java": ">=25",
    "fabric-api": "*",
    "dasik-library": ">=1.7.0"
  }
}
```

---

## 🔗 関連ドキュメント

* [[🏠 概要とホームポータル|ja_jp-Home]]
* [[🛠️ 開発環境のセットアップと Gradle ビルド|ja_jp-Developer-Setup-and-Building]]
* [[📚 DasikLibrary API 統合|ja_jp-API-and-Library-Integration]]
