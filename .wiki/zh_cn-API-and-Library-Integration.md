# 📚 DasikLibrary API 集成

| 参数 | 规格说明 |
| :--- | :--- |
| **核心依赖库** | `net.dasik.social:dasik-library` |
| **版本约束条件** | `dasik-library: >=1.7.0` (当前构建: `1.7.4`) |
| **类隔离辅助类** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **查询之 API 方法** | `DynamicGameRuleManager.getGeneratedTranslations()` |
| **运行时强制验证** | 在 `CollapsibleGameRulesFabric.onInitialize()` 阶段强制检查 |

---

## 📖 机制概述

Collapsible Game Rules 与 **DasikLibrary** 深度集成，为现代 Minecraft 模组提供动态分类本地化翻译与元数据格式化支持。

为了维护 JVM 的绝对稳定性，所有对 `DasikLibrary` 的直接类引用都被隔离在专用的延迟加载辅助类（`DasikMetadataHelper`）中。

---

## 🔒 类加载隔离架构

在现代 Java 运行时中，在热点类（如 Mixin）中直接引用其他外部库类，将会触发 JVM 立即加载目标库。若该库缺失，JVM 将在模组优雅报错之前直接抛出致命的 `NoClassDefFoundError`。

通过将所有对 `DynamicGameRuleManager` 的调用隔离在 `DasikMetadataHelper` 内，JVM 仅在明确调用 `DasikMetadataHelper` 时才会尝试加载 `DasikLibrary` 类：

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

## 💻 元数据查询实现

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

## 🛡️ 硬性依赖强制验证

依据核心主权架构规范，模组在 `ModInitializer.onInitialize()` 阶段严格确认 `dasik-library` 是否已加载：

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

## 🔗 相关文档

* [[🏠 概览与首页|zh_cn-Home]]
* [[🧭 版本兼容性矩阵|zh_cn-Version-Compatibility]]
* [[✨ 类别名称美化与格式化|zh_cn-Category-Prettification-and-Naming]]
* [[🧩 架构与 Mixin 子系统|zh_cn-Architecture-and-Mixins]]
