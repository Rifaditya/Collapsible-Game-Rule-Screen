# ⌨️ Клавиатурная навигация и доступность

| Параметр | Спецификация |
| :--- | :--- |
| **Класс компонента** | `CollapsibleCategoryRuleEntry` |
| **Интерфейс доступности** | `net.minecraft.client.gui.narration.NarratableEntry` |
| **Приоритет озвучивания** | `NarrationPriority.HOVERED` |
| **Тип элемента диктора** | `NarratedElementType.TITLE` |
| **Клавиши переключения** | `GLFW_KEY_SPACE`, `GLFW_KEY_ENTER`, `GLFW_KEY_KP_ENTER` |
| **Клавиша сворачивания** | `GLFW_KEY_LEFT` (Только если категория развернута) |
| **Клавиша разворачивания** | `GLFW_KEY_RIGHT` (Только если категория свернута) |
| **Звуковой отклик** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 Обзор механики

Мод поддерживает полную клавиатурную навигацию и совместимость с экранным диктором, позволяя комфортно управлять настройками без использования мыши.

---

## ⌨️ Таблица горячих клавиш

Когда заголовок категории сфокусирован в `RuleList`, метод `keyPressed(KeyEvent event)` обрабатывает следующие клавиши:

| Клавиша | Константа GLFW | Действие | Условие | Звук |
| :--- | :--- | :--- | :--- | :--- |
| **Пробел** | `GLFW_KEY_SPACE` | Переключить состояние | Всегда | `UI_BUTTON_CLICK` |
| **Enter** | `GLFW_KEY_ENTER` | Переключить состояние | Всегда | `UI_BUTTON_CLICK` |
| **Enter на Numpad** | `GLFW_KEY_KP_ENTER` | Переключить состояние | Всегда | `UI_BUTTON_CLICK` |
| **Стрелка влево (←)** | `GLFW_KEY_LEFT` | **Свернуть категорию** | Только если `expanded == true` | `UI_BUTTON_CLICK` |
| **Стрелка вправо (→)** | `GLFW_KEY_RIGHT` | **Развернуть категорию** | Только если `expanded == false` | `UI_BUTTON_CLICK` |

---

## ⚙️ Техническая реализация

### 1. Обработка стрелок навигации
Поведение соответствует стандартным иерархическим деревьям ОС:

```java
@Override
public boolean keyPressed(KeyEvent event) {
    int keyCode = event.key();
    if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER || keyCode == GLFW.GLFW_KEY_SPACE) {
        this.toggleAction.run();
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        return true;
    } else if (keyCode == GLFW.GLFW_KEY_LEFT && this.expanded) {
        this.toggleAction.run();
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        return true;
    } else if (keyCode == GLFW.GLFW_KEY_RIGHT && !this.expanded) {
        this.toggleAction.run();
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        return true;
    }
    return super.keyPressed(event);
}
```

### 2. Поддержка экранного диктора (`updateNarration`)

```java
@Override
public NarrationPriority narrationPriority() {
    return NarrationPriority.HOVERED;
}

@Override
public void updateNarration(NarrationElementOutput output) {
    output.add(NarratedElementType.TITLE, this.label);
}
```

---

## 🔗 Связанная документация

* [[🏠 Обзор и Главная|ru_ru-Home]]
* [[🗂️ Сворачиваемые категории|ru_ru-Collapsible-Categories]]
* [[🖥️ HUD, диагностика и отрисовка интерфейса|ru_ru-HUD-and-Diagnostics]]
* [[🧩 Архитектура и подсистема Mixin|ru_ru-Architecture-and-Mixins]]
