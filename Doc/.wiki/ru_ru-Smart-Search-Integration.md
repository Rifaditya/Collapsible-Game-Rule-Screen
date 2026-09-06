# 🔍 Интеллектуальный поиск

| Параметр | Спецификация |
| :--- | :--- |
| **Перехватываемый метод** | `populateChildren(Ljava/lang/String;)V` |
| **Точка внедрения** | `@At("TAIL")` |
| **Класс миксина** | `AbstractGameRulesScreenRuleListMixin` |
| **Нормализация запроса** | `filter.toLowerCase(java.util.Locale.ROOT)` |
| **Флаг активного поиска** | `isSearching = !currentFilter.isEmpty()` |
| **Правило раскрытия** | `isExpanded = isSearching || GameRuleStateConfig.isExpanded(key)` |
| **Метод обновления** | `collapsible_game_rules$updateVisibleEntries()` |

---

## 📖 Обзор механики

В ванильном интерфейсе поле поиска фильтрует правила по имени или описанию. В сворачиваемом списке прямолинейная реализация скрыла бы подходящие правила внутри закрытых категорий.

**Интеллектуальный поиск** динамически отслеживает строку поиска: пока в ней есть текст, любая категория с совпадениями принудительно раскрывается, гарантируя видимость результатов без дополнительных кликов.

---

## ⚙️ Схема работы интеллектуального поиска

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          SMART SEARCH PIPELINE                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Player types query into Search Bar (e.g. "fire")                          │
│        │                                                                    │
│        ▼                                                                    │
│   Vanilla AbstractGameRulesScreen.RuleList.populateChildren("fire")         │
│   (Filters the internal list to matching rules & their category headers)    │
│        │                                                                    │
│        ▼ (@Inject at TAIL)                                                  │
│   AbstractGameRulesScreenRuleListMixin.collapsible_game_rules$onPopulate... │
│        ├─ Stores normalized query: filter.toLowerCase(Locale.ROOT)          │
│        ├─ Captures filtered list: allEntries = new ArrayList<>(children())  │
│        └─ Calls updateVisibleEntries()                                      │
│             │                                                               │
│             ▼                                                               │
│        isSearching = !currentFilter.isEmpty() (Evaluates to TRUE)           │
│             │                                                               │
│             ▼                                                               │
│        Every present category header is forced isExpanded = TRUE            │
│        All matched child rules render immediately!                          │
│                                                                             │
│   Player clears Search Bar ("")                                             │
│        │                                                                    │
│        ▼                                                                    │
│   isSearching = FALSE ──> Reverts to persistent GameRuleStateConfig states! │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 💻 Технические детали

### 1. Точка внедрения `@Inject`
Миксин перехватывает завершение метода `populateChildren`:

```java
@Inject(method = "populateChildren(Ljava/lang/String;)V", at = @At("TAIL"))
private void collapsible_game_rules$onPopulateChildren(String filter, CallbackInfo ci) {
    this.collapsible_game_rules$currentFilter = (filter != null) ? filter.toLowerCase(java.util.Locale.ROOT) : "";
    // Save the currently generated list of all entries
    this.collapsible_game_rules$allEntries = new ArrayList<>(this.children());
    this.collapsible_game_rules$updateVisibleEntries();
}
```

### 2. Расчет состояния раскрытия
Внутри `updateVisibleEntries()`:

```java
boolean isSearching = !this.collapsible_game_rules$currentFilter.isEmpty();
final String finalPersistenceKey = persistenceKey;
boolean isExpanded = isSearching || GameRuleStateConfig.isExpanded(finalPersistenceKey);
```

### 3. Безопасное восстановление состояния
Флаг `isSearching` действует только при активном поиске. Как только поисковая строка очищается, категории возвращаются к сохраненным состояниям из `GameRuleStateConfig`, не затирая настройки пользователя на диске.

---

## 🔗 Связанная документация

* [[🏠 Обзор и Главная|ru_ru-Home]]
* [[🗂️ Сворачиваемые категории|ru_ru-Collapsible-Categories]]
* [[🧠 Сохранение состояния и JSON-конфигурация|ru_ru-State-Persistence-and-Config]]
* [[🧩 Архитектура и подсистема Mixin|ru_ru-Architecture-and-Mixins]]
