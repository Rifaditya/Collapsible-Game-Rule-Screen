# 📚 Интеграция с API DasikLibrary

| Параметр | Спецификация |
| :--- | :--- |
| **Базовая библиотека** | `net.dasik.social:dasik-library` |
| **Ограничение версии** | `dasik-library: >=1.7.0` (Сборка: `1.7.4`) |
| **Класс-изолятор** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **Метод API** | `DynamicGameRuleManager.getGeneratedTranslations()` |
| **Проверка при запуске** | Строгая проверка в `CollapsibleGameRulesFabric.onInitialize()` |

---

## 📖 Обзор механики

Collapsible Game Rules тесно интегрируется с библиотекой **DasikLibrary** для получения динамических переводов категорий и метаданных.

Для сохранения стабильности JVM все прямые обращения к классам `DasikLibrary` изолированы в отдельном классе отложенной загрузки (`DasikMetadataHelper`).

---

## 🔒 Архитектура изоляции загрузки классов

В Java прямое обращение к внешнему классу внутри миксина вызывает немедленную загрузку сторонней библиотеки виртуальной машиной. Если библиотека отсутствует, JVM немедленно выбросит фатальную ошибку `NoClassDefFoundError`.

Изоляция вызовов `DynamicGameRuleManager` внутри `DasikMetadataHelper` гарантирует, что JVM начнет загрузку только тогда, когда это будет проверено и безопасно:

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

## 💻 Реализация запроса метаданных

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

## 🛡️ Обязательный контроль зависимостей

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

## 🔗 Связанная документация

* [[🏠 Обзор и Главная|ru_ru-Home]]
* [[🧭 Матрица совместимости версий|ru_ru-Version-Compatibility]]
* [[✨ Форматирование и именование категорий|ru_ru-Category-Prettification-and-Naming]]
* [[🧩 Архитектура и подсистема Mixin|ru_ru-Architecture-and-Mixins]]
