# 🖥️ HUD, диагностика и отрисовка интерфейса

| Параметр | Спецификация |
| :--- | :--- |
| **Движок отрисовки** | `net.minecraft.client.gui.GuiGraphicsExtractor` |
| **Экранный контекст** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen` |
| **Цвет текста при наведении** | `0xFFFFFFAA` (Мягкая желтая подсветка) |
| **Обычный цвет текста** | `0xFFFFFFFF` (Яркий белый) |
| **Фон заголовка при наведении** | `0x22FFFFFF` (Полупрозрачный белый прямоугольник) |
| **Разделитель категорий** | `0x44AAAAAA` (Горизонтальная линия) |
| **Цвет включенного переключателя** | `0x4400FF00` (Изумрудно-зеленый) |
| **Цвет выключенного переключателя** | `0x44FF0000` (Рубиново-красный) |
| **Озвучивание доступности** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 Обзор механики

Minecraft 26.2 существенно модернизировал графический конвейер клиента, переведя стандартные операции отрисовки на подсистему `GuiGraphicsExtractor`.

Collapsible Game Rules изначально оптимизирован под этот движок, обеспечивая прямое заполнение векторных областей, центрирование текста и поддержку экранного диктора без просадок FPS.

---

## 🎨 Цветовая палитра интерфейса

| Компонент | Шестнадцатеричный ARGB | Описание | Место применения |
| :--- | :--- | :--- | :--- |
| **Заливка при наведении** | `0x22FFFFFF` | 13% полупрозрачный белый оверлей. | Заголовки категорий и кнопки панели действий. |
| **Разделительная черта** | `0x44AAAAAA` | 27% полупрозрачная серая линия толщиной 1px. | Нижняя грань заголовков и панели действий. |
| **Текст при наведении** | `0xFFFFFFAA` | Мягкий желтый оттенок. | Названия категорий и кнопок при наведении мыши. |
| **Стандартный текст** | `0xFFFFFFFF` | 100% белый цвет. | Названия категорий и значок количества правил. |
| **Значок количества правил** | `ChatFormatting.GRAY` | Ванильный серый цвет (` (N rules)`). | Суффикс после названия категории. |
| **Фон включенного режима** | `0x4400FF00` | 27% полупрозрачный зеленый. | Фон переключателя `BooleanToggleWidget` в режиме `✔ ON`. |
| **Фон выключенного режима** | `0x44FF0000` | 27% полупрозрачный красный. | Фон переключателя `BooleanToggleWidget` в режиме `✖ OFF`. |

---

## 💻 Детали реализации отрисовки

```java
@Override
public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
    // 1. Premium Highlight on hover
    if (hovered) {
        graphics.fill(this.getX() - 2, this.getY(), this.getX() + this.getWidth() + 2, this.getY() + 24, 0x22FFFFFF);
    }

    // 2. Directional arrow, label, and child count badge
    String prefix = this.expanded ? "▼ " : "▶ ";
    Component countBadge = Component.literal(" (" + this.childCount + " rules)").withStyle(ChatFormatting.GRAY);
    Component display = Component.literal(prefix).append(this.label).append(countBadge);

    // 3. Centered text with dynamic hover tint
    graphics.centeredText(Minecraft.getInstance().font, display,
            this.getContentXMiddle(), this.getContentY() + 5, hovered ? 0xFFFFFFAA : 0xFFFFFFFF);
    
    // 4. Subtle separating line at the bottom
    graphics.fill(this.getX() + 10, this.getY() + 23, this.getX() + this.getWidth() - 10, this.getY() + 24, 0x44AAAAAA);
}
```

---

## 🔊 Звуковые сигналы и обратная связь

* **Событие**: `SoundEvents.UI_BUTTON_CLICK`
* **Громкость**: `1.0F`
* **Высота тона**: `1.0F`
* **Условия срабатывания**:
  - Клик левой или правой кнопкой мыши по категории.
  - Нажатие Пробела или Enter на заголовке категории.
  - Стрелка влево (свернуть) или Стрелка вправо (развернуть).
  - Клики по кнопкам «Развернуть все» и «Свернуть все».

---

## 🔗 Связанная документация

* [[🏠 Обзор и Главная|ru_ru-Home]]
* [[🗂️ Сворачиваемые категории|ru_ru-Collapsible-Categories]]
* [[⌨️ Клавиатурная навигация и доступность|ru_ru-Keyboard-Navigation]]
* [[🧩 Архитектура и подсистема Mixin|ru_ru-Architecture-and-Mixins]]
