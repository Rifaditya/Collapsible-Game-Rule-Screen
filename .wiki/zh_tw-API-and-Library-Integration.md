# 📚 DasikLibrary API 整合

| 參數 | 規格說明 |
| :--- | :--- |
| **核心依賴庫** | `net.dasik.social:dasik-library` |
| **版本約束條件** | `dasik-library: >=1.7.0` (當前組建: `1.7.4`) |
| **類別隔離輔助類** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **查詢之 API 方法** | `DynamicGameRuleManager.getGeneratedTranslations()` |
| **執行時期強制驗證** | 在 `CollapsibleGameRulesFabric.onInitialize()` 階段強制檢查 |

---

## 📖 機制概述

Collapsible Game Rules 與 **DasikLibrary** 深度整合，為現代 Minecraft 模組提供動態分類在地化翻譯與元資料格式化支援。

為了維護 JVM 的絕對穩定性，所有對 `DasikLibrary` 的直接類別引用都被隔離在專用的延遲載入輔助類別（`DasikMetadataHelper`）中。

---

## 🔒 類別載入隔離架構

在現代 Java 執行時期中，在熱點類別（如 Mixin）中直接引用其他外部程式庫類別，將會觸發 JVM 立即載入目標函式庫。若該函式庫缺失，JVM 將在模組優雅回報錯誤之前直接拋出致命的 `NoClassDefFoundError`。

透過將所有對 `DynamicGameRuleManager` 的呼叫隔離在 `DasikMetadataHelper` 內，JVM 僅在明確呼叫 `DasikMetadataHelper` 時才會嘗試載入 `DasikLibrary` 類別：

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      CLASSLOADING ISOLATION PATTERN                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   AbstractGameRulesScreenRuleListMixin                                      │
│        │                                                                    │
│        ▼ (Checks FabricLoader.isModLoaded("dasik-library"))                 │
│   ┌─────────────────────────────────────────────────────────────────────┐   │
│   │ if (FabricLoader.getInstance().isModLoaded("dasik-library")) {      │   │
│   │     categoryKey = DasikMetadataHelper.getCategoryTranslation(...);  │   │
│   │ }                                                                   │   │
│   └─────────────────────────────────────────────────────────────────────┘   │
│        │                                                                    │
│        ▼ (Only loads DasikMetadataHelper when confirmed safe)               │
│   DasikMetadataHelper ──> net.dasik.social.api.gamerule.DynamicGameRuleManager│
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 💻 元資料查詢實作

在 `DasikMetadataHelper.java` 中：

```java
public final class DasikMetadataHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger("collapsible-game-rules");

    private DasikMetadataHelper() {}

    /**
     * Retrieves the localized name for a category from DasikLibrary metadata.
     * Enforced Hard Dependency: This method makes direct calls to DasikLibrary.
     */
    public static String getCategoryTranslation(String categoryLabel) {
        Map<String, String> translations =
                DynamicGameRuleManager.getGeneratedTranslations();

        return translations.getOrDefault(
                "gamerule.category." + categoryLabel.toLowerCase(Locale.ROOT),
                categoryLabel
        );
    }
}
```

---

## 🛡️ 硬性依賴強制驗證

依據核心主權架構規範，模組在 `ModInitializer.onInitialize()` 階段嚴格確認 `dasik-library` 是否已載入：

```java
public class CollapsibleGameRulesFabric implements ModInitializer {
    public static final String MOD_ID = "collapsible-game-rules";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
        LOGGER.info("Initializing Collapsible Game Rules [Core Align 2.1]");

        // Hard Dependency Enforcement
        if (!FabricLoader.getInstance().isModLoaded("dasik-library")) {
            throw new RuntimeException("Collapsible Game Rules requires DasikLibrary to function. Please install it.");
        }
    }
}
```

---

## 🔗 相關文件

* [[🏠 概覽與首頁|zh_tw-Home]]
* [[🧭 版本相容性矩陣|zh_tw-Version-Compatibility]]
* [[✨ 類別名稱美化與格式化|zh_tw-Category-Prettification-and-Naming]]
* [[🧩 架構與 Mixin 子系統|zh_tw-Architecture-and-Mixins]]
