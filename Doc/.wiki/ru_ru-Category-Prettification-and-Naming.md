# ✨ Форматирование и именование категорий

| Параметр | Спецификация |
| :--- | :--- |
| **Класс утилиты** | `net.instantgratification.collapsiblegamerules.util.CategoryPrettifier` |
| **Фасад метаданных** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **Условие форматирования** | `!Language.getInstance().has(key)` |
| **Удаление префикса** | Удаляет `"gamerule.category."` |
| **Разделители** | Точка `.` (пространство имен), регулярное выражение `[_-]` (слова) |
| **Источник метаданных** | `DynamicGameRuleManager.getGeneratedTranslations()` |

---

## 📖 Обзор механики

Сторонние моды нередко регистрируют категории игровых правил через сырые ключи (вроде `gamerule.category.better-bats.better_bats` или `gamerule.category.item_clumps`), забывая добавить перевод в `lang/en_us.json`. В стандартном Minecraft такие категории отображаются в виде нечитаемых строк.

**Category Prettification** динамически форматирует непереведенные ключи в аккуратные заголовки в регистре Title Case.

---

## ⚙️ Конвейер алгоритма форматирования

`CategoryPrettifier.prettifyCategoryKey(String key)` выполняет следующие шаги:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       CATEGORY PRETTIFICATION PIPELINE                      │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Input Key: "gamerule.category.better-bats.better_bats"                    │
│        │                                                                    │
│        ▼ [Step 1: Prefix Stripping]                                         │
│   Strip "gamerule.category." ──> "better-bats.better_bats"                  │
│        │                                                                    │
│        ▼ [Step 2: Namespace & Path Separation]                              │
│   Separate namespace "better-bats" and path "better_bats"                   │
│        │                                                                    │
│        ▼ [Step 3: Redundancy Normalization]                                 │
│   Compare normalized strings: "betterbats" == "betterbats"                  │
│   Deduplicate to single segment: "better_bats"                              │
│        │                                                                    │
│        ▼ [Step 4: Delimiter Splitting & Capitalization]                     │
│   Split by "[_-]" ──> ["better", "bats"]                                    │
│   Capitalize words ──> ["Better", "Bats"]                                   │
│        │                                                                    │
│        ▼ [Step 5: String Join]                                              │
│   Output Display Title: "Better Bats"                                       │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📊 Примеры преобразования ключей

| Исходный ключ правила | Итоговый заголовок | Примечания |
| :--- | :--- | :--- |
| `gamerule.category.better-bats.better_bats` | **Better Bats** | Удаляет повторяющиеся сегменты пространства имен. |
| `gamerule.category.minecraft.spawning` | **Spawning** | Отбрасывает стандартное пространство имен `minecraft`. |
| `gamerule.category.social_mobs.wolf_pack` | **Social Mobs Wolf Pack** | Объединяет разные части пути и пространства имен. |
| `gamerule.category.custom_rules` | **Custom Rules** | Заменяет подчеркивания пробелами с капитализацией. |
| `gamerule.category.instant-gratification.ore-multiplier` | **Instant Gratification Ore Multiplier** | Разделяет дефисы и делает слова заглавными. |

---

## 💻 Исходный код реализации

```java
public static String prettifyCategoryKey(String key) {
    if (key == null) {
        return "";
    }
    String name = key;
    if (name.startsWith("gamerule.category.")) {
        name = name.substring("gamerule.category.".length());
    }

    // Split namespace and path if dot is present
    int dotIndex = name.indexOf('.');
    if (dotIndex != -1) {
        String ns = name.substring(0, dotIndex);
        String path = name.substring(dotIndex + 1);
        
        // If the namespace is "minecraft", just drop it
        if (ns.equals("minecraft")) {
            name = path;
        } else {
            // Normalize for comparison
            String normNs = ns.replace("-", "").replace("_", "").toLowerCase(Locale.ROOT);
            String normPath = path.replace("-", "").replace("_", "").toLowerCase(Locale.ROOT);
            if (normPath.contains(normNs) || normNs.contains(normPath)) {
                name = path; // Use the path part since it's more specific or includes namespace
            } else {
                name = ns + " " + path;
            }
        }
    }

    // Split by underscore or dash
    String[] parts = name.split("[_-]");
    List<String> words = new ArrayList<>();
    for (String part : parts) {
        if (part.isEmpty()) {
            continue;
        }
        String capitalized = part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1);
        words.add(capitalized);
    }
    return String.join(" ", words);
}
```

---

## 🔗 Связанная документация

* [[🏠 Обзор и Главная|ru_ru-Home]]
* [[🗂️ Сворачиваемые категории|ru_ru-Collapsible-Categories]]
* [[📚 Интеграция с API DasikLibrary|ru_ru-API-and-Library-Integration]]
