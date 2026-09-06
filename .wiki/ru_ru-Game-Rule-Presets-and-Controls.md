# 🎛️ Пресеты игровых правил и элементы управления

| Параметр | Спецификация |
| :--- | :--- |
| **Класс движка пресетов** | `net.instantgratification.collapsiblegamerules.preset.GameRulePresetEngine` |
| **Структура пресета** | `record Preset(String id, Component displayName, Map<String, Object> ruleValues)` |
| **Интерактивные виджеты** | `IntegerSliderWidget`, `BooleanToggleWidget` |
| **Вспомогательный класс** | `net.instantgratification.collapsiblegamerules.util.GameRuleSliderHelper` |
| **Фон активного режима** | `0x4400FF00` (Изумрудно-зеленый фон) |
| **Фон выключенного режима** | `0x44FF0000` (Рубиново-красный фон) |
| **Встроенные пресеты** | `builder` ("🏰 Режим строителя"), `fast_play` ("⚡ Быстрая игра"), `hardcore` ("💀 Хардкорный реализм") |

---

## 📖 Обзор механики

Collapsible Game Rules предоставляет интерактивные элементы управления и готовые игровые пресеты, позволяющие активировать целые конфигурации мира в один клик или плавно менять числа ползунками вместо ручного ввода цифр в текстовые поля.

---

## 🏰 Матрица встроенных пресетов

`GameRulePresetEngine` включает три базовых профиля:

| ID пресета | Название | Правило игры | Настроенное значение | Влияние на геймплей |
| :--- | :--- | :--- | :--- | :--- |
| `builder` | **🏰 Режим строителя** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doMobSpawning`<br>`keepInventory`<br>`mobGriefing`<br>`doFireTick` | `false`<br>`false`<br>`false`<br>`true`<br>`false`<br>`false` | Идеально для строительства: фиксирует время и погоду, выключает мобов, криперов и огонь. |
| `fast_play` | **⚡ Быстрая игра** | `randomTickSpeed`<br>`playersSleepingPercentage`<br>`keepInventory` | `10`<br>`0`<br>`true` | Быстрое выживание: ускоряет рост посевов ($3\times$), пропуск ночи одним игроком, сохранение инвентаря. |
| `hardcore` | **💀 Хардкорный реализм** | `naturalRegeneration`<br>`doInsomnia`<br>`playersSleepingPercentage` | `false`<br>`true`<br>`100` | Экстремальное выживание: отключает регенерацию здоровья (нужны яблоки/зелья), включает фантомов. |

---

## 🎚️ Числовой ползунок (`IntegerSliderWidget`)

`IntegerSliderWidget` заменяет поля ввода чисел на удобные ползунки.

### Математические формулы нормализации

Определение положения ползунка из целого числа $v$:
$$\text{normalized} = \frac{\text{clamp}(v, \text{min}, \text{max}) - \text{min}}{\text{max} - \text{min}}$$

Вычисление целого числа из позиции ползунка $p \in [0.0, 1.0]$:
$$\text{calculatedInt} = \text{min} + \text{round}\left(p \times (\text{max} - \text{min})\right)$$

### Границы ванильных правил (`GameRuleSliderHelper`)

| Ключ правила | Минимум ($	ext{min}$) | Максимум ($	ext{max}$) | Ванильное значение |
| :--- | :--- | :--- | :--- |
| `randomTickSpeed` | `0` | `100` | `3` |
| `spawnRadius` | `0` | `32` | `10` |
| `playersSleepingPercentage` | `0` | `100` | `100` |
| `maxEntityCramming` | `0` | `100` | `24` |
| `maxCommandChainLength` | `0` | `65536` | `65536` |
| `commandModificationBlockLimit` | `0` | `65536` | `32768` |

---

## 🔘 Двухпозиционный переключатель (`BooleanToggleWidget`)

`BooleanToggleWidget` обеспечивает наглядную обратную связь:

* **Состояние: ИСТИНА (`ON`)**: Текст `✔ ON` зеленым шрифтом с полупрозрачным фоном `0x4400FF00`.
* **Состояние: ЛОЖЬ (`OFF`)**: Текст `✖ OFF` красным шрифтом с полупрозрачным фоном `0x44FF0000`.
* **Клик мыши**: Переключает состояние и выполняет колбэк `onToggle.accept(newState)`.

```
┌─────────────────────────┐     ┌─────────────────────────┐
│         ✔ ON            │     │         ✖ OFF           │
│   (Green Tint 0x4400FF00)│     │   (Red Tint 0x44FF0000) │
└─────────────────────────┘     └─────────────────────────┘
```

---

## 🔗 Связанная документация

* [[🏠 Обзор и Главная|ru_ru-Home]]
* [[🗂️ Сворачиваемые категории|ru_ru-Collapsible-Categories]]
* [[📜 Справочная таблица игровых правил|ru_ru-GameRules-Reference]]
* [[🧩 Архитектура и подсистема Mixin|ru_ru-Architecture-and-Mixins]]
