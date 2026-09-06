# 🌎 Глобальные действия и массовые переключатели

| Параметр | Спецификация |
| :--- | :--- |
| **Класс компонента** | `net.instantgratification.collapsiblegamerules.ui.GlobalActionsRuleEntry` |
| **Позиция в списке** | Индекс `0` (Всегда закреплен в самом верху `RuleList`) |
| **Левая кнопка** | `[ Expand All ]` (`gui.collapsible-game-rules.expand_all`) |
| **Правая кнопка** | `[ Collapse All ]` (`gui.collapsible-game-rules.collapse_all`) |
| **Центр левой кнопки** | `this.getX() + this.getWidth() / 4` |
| **Центр правой кнопки** | `this.getX() + 3 * this.getWidth() / 4` |
| **Подсветка при наведении** | `0x22FFFFFF` (Подсвечивает активную половину) |
| **Нижняя линия** | `0x44AAAAAA` |
| **Звук клика** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 Обзор механики

В больших сборках с сотнями правил раскрывать или закрывать категории по одной долго и утомительно.

**Панель глобальных действий** закреплена на **индексе 0** экрана правил и предоставляет мгновенное сворачивание и разворачивание всех доступных категорий в один клик.

---

## 🎨 Визуальная компоновка и разделенный дизайн

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ◄──────────────── Left Half ───────────────►◄────────────── Right Half ───► │
│               [ Expand All ]                               [ Collapse All ] │
│ ─────────────────────────────────────────────────────────────────────────── │
└─────────────────────────────────────────────────────────────────────────────┘
```

* **Левая зона (`mouseX < getX() + getWidth() / 2`)**: Вызывает `expandAll`.
* **Правая зона (`mouseX >= getX() + getWidth() / 2`)**: Вызывает `collapseAll`.
* **Наведение**: Подсвечивает активную половину цветом `0x22FFFFFF` и меняет цвет надписи на `0xFFFFFFAA`.

---

## ⚙️ Технические механизмы

### 1. Закрепление на индексе 0
В методе `updateVisibleEntries()` панели передаются обработчики:

```java
if (!this.collapsible_game_rules$allEntries.isEmpty()) {
    this.addEntry(new GlobalActionsRuleEntry(
        () -> {
            List<String> allKeys = this.collapsible_game_rules$allEntries.stream()
                .filter(e -> e instanceof AbstractGameRulesScreen.CategoryRuleEntry)
                .map(e -> {
                    Component lbl = ((CategoryRuleEntryAccessor) e).collapsible_game_rules$getLabel();
                    if (lbl.getContents() instanceof TranslatableContents translatable) {
                        return translatable.getKey();
                    }
                    return lbl.getString();
                })
                .toList();
            GameRuleStateConfig.expandAll(allKeys);
            this.collapsible_game_rules$updateVisibleEntries();
        },
        () -> {
            GameRuleStateConfig.collapseAll();
            this.collapsible_game_rules$updateVisibleEntries();
        }
    ));
}
```

### 2. Извлечение идентификаторов категорий
1. Фильтрует записи до `CategoryRuleEntry`.
2. Получает `label` через `CategoryRuleEntryAccessor`.
3. Если есть `TranslatableContents`, извлекает ключ перевода (`gamerule.category.spawning`).
4. При отсутствии перевода использует строковое значение.
5. Собирает неизменяемый список Java 25 (`.toList()`) и передает в `GameRuleStateConfig.expandAll(allKeys)`.

### 3. Диспетчеризация клика
В методе `GlobalActionsRuleEntry.mouseClicked(MouseButtonEvent event, boolean doubleClick)`:
```java
if (event.button() == 0 || event.button() == 1) {
    double mouseX = event.x();
    if (mouseX < this.getX() + this.getWidth() / 2.0) {
        this.expandAll.run();
    } else {
        this.collapseAll.run();
    }
    Minecraft.getInstance().getSoundManager().play(
        SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)
    );
    return true;
}
```

---

## 🌐 Ключи локализации

```json
{
  "gui.collapsible-game-rules.expand_all": "Expand All",
  "gui.collapsible-game-rules.collapse_all": "Collapse All"
}
```

---

## 🔗 Связанная документация

* [[🏠 Обзор и Главная|ru_ru-Home]]
* [[🗂️ Сворачиваемые категории|ru_ru-Collapsible-Categories]]
* [[🧠 Сохранение состояния и JSON-конфигурация|ru_ru-State-Persistence-and-Config]]
* [[🧩 Архитектура и подсистема Mixin|ru_ru-Architecture-and-Mixins]]
