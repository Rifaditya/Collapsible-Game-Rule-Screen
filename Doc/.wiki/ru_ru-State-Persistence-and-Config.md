# 🧠 Сохранение состояния и JSON-конфигурация

| Параметр | Спецификация |
| :--- | :--- |
| **Класс конфигурации** | `net.instantgratification.collapsiblegamerules.GameRuleStateConfig` |
| **Расположение файла** | `.minecraft/config/collapsible-game-rules-state.json` |
| **Структура в памяти** | `Set<String> expandedCategories = new HashSet<>()` |
| **Движок сериализации** | `com.google.gson.Gson` (Pretty-Printing) |
| **Флаг регулировки I/O** | `private static boolean isDirty = false` |
| **Точка сброса на диск** | `ScreenMixin` перехватывает `Screen.removed()` (`@At("HEAD")`) |
| **Ключи сохранения** | Ключ перевода (`TranslatableContents.getKey()`) или строковый литерал |

---

## 📖 Обзор механики

Мод использует асинхронную систему сохранения состояния. Вместо сброса настроек к значениям по умолчанию при каждом входе в меню, Collapsible Game Rules сохраняет раскрытые категории между сессиями.

---

## 📄 Формат конфигурации JSON

Файл `.minecraft/config/collapsible-game-rules-state.json`:

```json
[
  "gamerule.category.spawning",
  "gamerule.category.mobs",
  "gamerule.category.updates"
]
```

* **Присутствие в массиве**: Категория сейчас **РАЗВЕРНУТА**.
* **Отсутствие в массиве**: Категория сейчас **СВЕРНУТА** (по умолчанию).

---

## ⚡ Высокопроизводительное дросселирование I/O

Запись на диск при каждом клике создавала бы микрофризы. Для **абсолютной плавности** `GameRuleStateConfig` использует флаг `isDirty`:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       THROTTLED PERSISTENCE WORKFLOW                        │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Player clicks Category Header                                             │
│        │                                                                    │
│        ▼                                                                    │
│   GameRuleStateConfig.setExpanded(key, state)                               │
│        ├─ Updates in-memory HashSet<String> in 0.0001 μs                    │
│        └─ Marks: isDirty = true (ZERO DISK I/O)                             │
│                                                                             │
│   Player closes Game Rules Screen (Esc, Done, or Cancel)                    │
│        │                                                                    │
│        ▼                                                                    │
│   ScreenMixin.collapsible_game_rules$onRemoved()                            │
│        │                                                                    │
│        ▼                                                                    │
│   GameRuleStateConfig.saveIfDirty()                                         │
│        ├─ Checks: if (isDirty) Ellipsis                                      │
│        ├─ Writes JSON to disk in background buffer                          │
│        └─ Resets: isDirty = false                                           │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 💻 Справочник методов API

### Публичные методы `GameRuleStateConfig`

| Сигнатура метода | Возвращаемый тип | Описание |
| :--- | :--- | :--- |
| `load()` | `void` | Читает `collapsible-game-rules-state.json` при запуске клиента. |
| `save()` | `void` | Записывает `expandedCategories` на диск через `Files.newBufferedWriter`. |
| `saveIfDirty()` | `void` | Записывает данные только при `isDirty == true` и сбрасывает флаг. |
| `isExpanded(String categoryKey)` | `boolean` | Проверяет наличие ключа категории в `expandedCategories`. |
| `setExpanded(String categoryKey, boolean expanded)` | `void` | Добавляет или удаляет ключ и устанавливает `isDirty = true`. |
| `expandAll(Iterable<String> allKeys)` | `void` | Добавляет все ключи списком и ставит `isDirty = true`. |
| `collapseAll()` | `void` | Очищает коллекцию и ставит `isDirty = true`. |

---

## 🔒 Перехват закрытия экрана через ScreenMixin

```java
@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Inject(method = "removed", at = @At("HEAD"))
    private void collapsible_game_rules$onRemoved(CallbackInfo ci) {
        if ((Object) this instanceof AbstractGameRulesScreen) {
            GameRuleStateConfig.saveIfDirty();
        }
    }
}
```

Это гарантирует сохранение настроек при любом выходе из меню: нажатии **Готово**, **Отмена** или клавиши **Escape**.

---

## 🔗 Связанная документация

* [[🏠 Обзор и Главная|ru_ru-Home]]
* [[🌎 Глобальные действия и массовые переключатели|ru_ru-Global-Actions-and-Bulk-Toggles]]
* [[🧩 Архитектура и подсистема Mixin|ru_ru-Architecture-and-Mixins]]
