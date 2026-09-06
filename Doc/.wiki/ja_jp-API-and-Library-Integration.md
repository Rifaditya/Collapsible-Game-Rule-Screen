# 📚 DasikLibrary API 統合

| 項目 | 仕様 |
| :--- | :--- |
| **前提ライブラリ** | `net.dasik.social:dasik-library` |
| **推奨バージョン** | `dasik-library: >=1.7.0` (検証済み: `1.7.4`) |
| **クラスローダー隔離層** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **呼び出し API** | `DynamicGameRuleManager.getGeneratedTranslations()` |
| **起動時チェック** | `CollapsibleGameRulesFabric.onInitialize()` にて必須検証 |

---

## 📖 概要

Collapsible Game Rules は **DasikLibrary** と緊密に連携し、最新の Mod によって動的に登録されたゲームルールのメタデータおよび翻訳をリアルタイムに取得・表示します。

---

## 🔒 クラスローダー隔離アーキテクチャ

前提ライブラリが存在しない環境での不要なクラッシュを回避するため、`DasikMetadataHelper` を通じて遅延ロード（Lazy Loading）される安全な構造を採用しています。

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      CLASSLOADING ISOLATION PATTERN                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   AbstractGameRulesScreenRuleListMixin                                      │
│        │                                                                    │
│        ▼ (FabricLoader.isModLoaded("dasik-library") を確認)                 │
│   ┌─────────────────────────────────────────────────────────────────────┐   │
│   │ if (FabricLoader.getInstance().isModLoaded("dasik-library")) {      │   │
│   │     categoryKey = DasikMetadataHelper.getCategoryTranslation(...);  │   │
│   │ }                                                                   │   │
│   └─────────────────────────────────────────────────────────────────────┘   │
│        │                                                                    │
│        ▼ (ライブラリの存在が確認できた場合のみクラスを読み込む)             │
│   DasikMetadataHelper ──> net.dasik.social.api.gamerule.DynamicGameRuleManager│
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 🛡️ 起動時の前提 Mod チェック

```java
public class CollapsibleGameRulesFabric implements ModInitializer {
    public static final String MOD_ID = "collapsible-game-rules";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
        LOGGER.info("Initializing Collapsible Game Rules [Core Align 2.1]");

        // 必須 Mod の存在チェック
        if (!FabricLoader.getInstance().isModLoaded("dasik-library")) {
            throw new RuntimeException("Collapsible Game Rules requires DasikLibrary to function. Please install it.");
        }
    }
}
```

---

## 🔗 関連ドキュメント

* [[🏠 概要とホームポータル|ja_jp-Home]]
* [[🧭 バージョン互換性マトリックス|ja_jp-Version-Compatibility]]
* [[✨ カテゴリ名の整形とフォーマット|ja_jp-Category-Prettification-and-Naming]]
* [[🧩 アーキテクチャと Mixin サブシステム|ja_jp-Architecture-and-Mixins]]
