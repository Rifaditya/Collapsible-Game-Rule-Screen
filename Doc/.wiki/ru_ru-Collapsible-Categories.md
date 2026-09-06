# 🗂️ Сворачиваемые категории

| Параметр | Спецификация |
| :--- | :--- |
| **Системный компонент** | `CollapsibleCategoryRuleEntry` (Внутренний класс) |
| **Миксин** | `AbstractGameRulesScreenRuleListMixin` |
| **Целевой класс** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList` |
| **Индикаторы состояния** | Развернуто: `▼ ` \| Свернуто: `▶ ` |
| **Формат значка количества** | ` (N rules)` (`ChatFormatting.GRAY`) |
| **Подсветка при наведении** | `0x22FFFFFF` (25% полупрозрачный белый прямоугольник) |
| **Цвет разделительной линии** | `0x44AAAAAA` (Тонкая нижняя черта) |
| **Цвет текста** | При наведении: `0xFFFFFFAA` \| Обычный: `0xFFFFFFFF` |
| **Звук взаимодействия** | `net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK` (Громкость: `1.0F`) |
| **Тип озвучивания** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 Обзор механики

В стандартном Minecraft заголовки категорий отображаются как статичные надписи (`CategoryRuleEntry`), а все дочерние правила выстраиваются в один огромный список. Когда моды добавляют десятки новых параметров, навигация становится крайне неудобной.

**Collapsible Categories** заменяет статичные метки на интерактивные виджеты `CollapsibleCategoryRuleEntry`, которые можно свободно сворачивать и разворачивать.

---

## 🎨 Визуальная компоновка и иерархия

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ [ Expand All ]                                             [ Collapse All ] │ ◄── GlobalActionsRuleEntry (Index 0)
├─────────────────────────────────────────────────────────────────────────────┤
│ ▼ ⚔️ Mobs (14 rules)                                                        │ ◄── CollapsibleCategoryRuleEntry (Expanded)
│ ─────────────────────────────────────────────────────────────────────────── │
│   mobGriefing                                                     [ ON ]    │ ◄── Child RuleEntry
│   doMobSpawning                                                   [ ON ]    │ ◄── Child RuleEntry
│   doMobLoot                                                       [ ON ]    │ ◄── Child RuleEntry
├─────────────────────────────────────────────────────────────────────────────┤
│ ▶ 👤 Player (8 rules)                                                       │ ◄── CollapsibleCategoryRuleEntry (Collapsed)
│ ─────────────────────────────────────────────────────────────────────────── │
│ ▼ 🌧️ Updates (6 rules)                                                      │ ◄── CollapsibleCategoryRuleEntry (Expanded)
│ ─────────────────────────────────────────────────────────────────────────── │
│   doFireTick                                                      [ ON ]    │ ◄── Child RuleEntry
│   randomTickSpeed                                                 [ 3  ]    │ ◄── Child RuleEntry
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## ⚙️ Технические механизмы

### 1. Алгоритм подсчета дочерних правил
В методе `updateVisibleEntries()` список `allEntries` сканируется для подсчета количества правил до следующего заголовка категории:

```java
int childCount = 0;
for (int j = i + 1; j < this.collapsible_game_rules$allEntries.size(); j++) {
    if (this.collapsible_game_rules$allEntries.get(j) instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
        break;
    }
    childCount++;
}
```

### 2. Конвейер отрисовки категории (`extractContent`)
Отрисовка выполняется через интерфейс `GuiGraphicsExtractor`:
1. **Подсветка при наведении**: Рисуется прямоугольник `0x22FFFFFF` в области `[getX() - 2, getY()]` — `[getX() + getWidth() + 2, getY() + 24]`.
2. **Стрелка и текст**: Добавляется префикс (`▼ ` или `▶ `), имя категории и серый значок количества (` (N rules)`).
3. **Выравнивание по центру**: Текст центрируется по горизонтали на `getContentXMiddle()` со смещением `getContentY() + 5`.
4. **Нижний разделитель**: Линия `0x44AAAAAA` на координате `getY() + 23` отделяет категории друг от друга.

### 3. Обработка кликов мыши
В методе `mouseClicked(MouseButtonEvent event, boolean doubleClick)`:
* **Левый клик (`event.button() == 0`)** или **Правый клик (`event.button() == 1`)**:
  1. Выполняет `toggleAction.run()`.
  2. Обновляет состояние в `GameRuleStateConfig`.
  3. Воспроизводит звук щелчка: `SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)`.
  4. Вызывает `updateVisibleEntries()` для добавления или скрытия дочерних строк.
  5. Пересчитывает размеры списка через `updateSizeAndPosition(...)`.

### 4. Доступность и экранный диктор
Класс реализует интерфейс `NarratableEntry`:
* **Приоритет**: `NarrationPriority.HOVERED`
* **Озвучивание**: Заголовок категории передается как `NarratedElementType.TITLE` для экранных дикторов.

---

## 🔗 Связанная документация

* [[🏠 Обзор и Главная|ru_ru-Home]]
* [[🌎 Глобальные действия и массовые переключатели|ru_ru-Global-Actions-and-Bulk-Toggles]]
* [[⌨️ Клавиатурная навигация и доступность|ru_ru-Keyboard-Navigation]]
* [[🧩 Архитектура и подсистема Mixin|ru_ru-Architecture-and-Mixins]]
