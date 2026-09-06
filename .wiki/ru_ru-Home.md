# ⚒️ Collapsible Game Rules Wiki (Сворачиваемые правила игры)

<div align="center">

<img src="https://raw.githubusercontent.com/Rifaditya/Collapsible-Game-Rule-Screen/main/Images/2026-08-04_11.06.33.png" alt="Collapsible Game Rules Banner" width="800">

<p align="center">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge&logo=minecraft" alt="Minecraft 26.2+">
  <img src="https://img.shields.io/badge/Fabric-0.145.4+-blue?style=for-the-badge&logo=fabric" alt="Fabric">
  <img src="https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk" alt="Java 25">
  <img src="https://img.shields.io/badge/DasikLibrary-1.7.4-purple?style=for-the-badge" alt="DasikLibrary 1.7.4">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License GPLv3">
</p>

</div>

---

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до публичных релизов на CurseForge и Modrinth.

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 🧭 Добро пожаловать в Collapsible Game Rules

**Collapsible Game Rules** преобразует стандартный экран настроек игровых правил Minecraft в удобный, структурированный интерфейс с поддержкой сворачивания категорий, интеллектуального поиска, навигации с клавиатуры, сохранения состояния и встроенных пресетов.

---

## 🌟 Основные возможности и быстрый указатель

| Возможность | Описание | Справочное руководство |
| :--- | :--- | :--- |
| **🗂️ Сворачиваемые категории** | Заменяет статические заголовки интерактивными переключателями (`▼`/`▶`) со значками количества правил. | [[🗂️ Сворачиваемые категории\|ru_ru-Collapsible-Categories]] |
| **🌎 Панель глобальных действий** | Закрепленные сверху кнопки «Развернуть все» и «Свернуть все» для массового управления. | [[🌎 Глобальные действия и массовые переключатели\|ru_ru-Global-Actions-and-Bulk-Toggles]] |
| **🔍 Интеллектуальный поиск** | Автоматически разворачивает категории с совпадениями прямо во время ввода поискового запроса. | [[🔍 Интеллектуальный поиск\|ru_ru-Smart-Search-Integration]] |
| **⌨️ Клавиатурная навигация** | Полная поддержка управления с клавиатуры (Пробел, Enter, стрелки влево/вправо) и озвучивание для доступности. | [[⌨️ Клавиатурная навигация и доступность\|ru_ru-Keyboard-Navigation]] |
| **🧠 Сохранение состояния** | Запоминает состояния категорий локально в файле `config/collapsible-game-rules-state.json`. | [[🧠 Сохранение состояния и JSON-конфигурация\|ru_ru-State-Persistence-and-Config]] |
| **✨ Форматирование названий** | Динамически форматирует необработанные ключи категорий сторонних модов в читаемый Title Case. | [[✨ Форматирование и именование категорий\|ru_ru-Category-Prettification-and-Naming]] |
| **🎛️ Пресеты правил и ползунки** | Встроенные пресеты (Строитель, Быстрая игра, Хардкор), интерактивные числовые ползунки и переключатели. | [[🎛️ Пресеты игровых правил и элементы управления\|ru_ru-Game-Rule-Presets-and-Controls]] |
| **🧩 Интеграция с DasikLibrary** | Запрашивает переводы из `DynamicGameRuleManager` для первоклассной локализации категорий. | [[📚 Интеграция с API DasikLibrary\|ru_ru-API-and-Library-Integration]] |

---

## 🚀 Быстрый старт и установка

1. Установите **[Fabric Loader](https://fabricmc.net/)** (`>=0.19.1`).
2. Загрузите **[Fabric API](https://modrinth.com/mod/fabric-api)**.
3. Загрузите **[DasikLibrary](https://modrinth.com/mod/dasik-library)** (`>=1.7.0`, **Обязательная зависимость**).
4. Поместите файл `collapsible-game-rules-1.0.9+26.2.jar` в папку `.minecraft/mods`.
5. Запустите Minecraft с **Java 25+**.

---

## 📚 Полный указатель документации

```
Корень Wiki
├── 🧭 Матрица совместимости версий ------------> [[🧭 Матрица совместимости версий|ru_ru-Version-Compatibility]]
├── 🎮 Основные механики интерфейса
│   ├── Сворачиваемые категории ---------------> [[🗂️ Сворачиваемые категории|ru_ru-Collapsible-Categories]]
│   ├── Глобальные действия и переключатели ---> [[🌎 Глобальные действия и массовые переключатели|ru_ru-Global-Actions-and-Bulk-Toggles]]
│   ├── Интеллектуальный поиск ----------------> [[🔍 Интеллектуальный поиск|ru_ru-Smart-Search-Integration]]
│   └── Клавиатурная навигация и доступность --> [[⌨️ Клавиатурная навигация и доступность|ru_ru-Keyboard-Navigation]]
├── ⚙️ Конфигурация и пресеты
│   ├── Сохранение состояния и JSON ------------> [[🧠 Сохранение состояния и JSON-конфигурация|ru_ru-State-Persistence-and-Config]]
│   ├── Форматирование и именование категорий --> [[✨ Форматирование и именование категорий|ru_ru-Category-Prettification-and-Naming]]
│   └── Пресеты правил и элементы управления --> [[🎛️ Пресеты игровых правил и элементы управления|ru_ru-Game-Rule-Presets-and-Controls]]
├── 📋 Область действия и справочник
│   ├── Справочная таблица игровых правил -----> [[📜 Справочная таблица игровых правил|ru_ru-GameRules-Reference]]
│   ├── Команды Brigadier и область действия --> [[💬 Команды Brigadier и область действия|ru_ru-Commands]]
│   └── Достижения и область действия ---------> [[🏆 Достижения и область действия|ru_ru-Advancements]]
└── 💻 Техническая архитектура и разработка
    ├── HUD, диагностика и отрисовка UI -------> [[🖥️ HUD, диагностика и отрисовка интерфейса|ru_ru-HUD-and-Diagnostics]]
    ├── Среда разработки и сборка Gradle ------> [[🛠️ Среда разработки и сборка Gradle|ru_ru-Developer-Setup-and-Building]]
    ├── Архитектура и подсистема Mixin --------> [[🧩 Архитектура и подсистема Mixin|ru_ru-Architecture-and-Mixins]]
    └── Интеграция с API DasikLibrary ---------> [[📚 Интеграция с API DasikLibrary|ru_ru-API-and-Library-Integration]]
```

---

## 📜 Авторы и лицензия

* **Автор и ведущий разработчик**: **Dasik (Rifaditya)**
* **Лицензия**: **GNU General Public License v3.0 (GPLv3)**
* **Репозиторий**: [GitHub Repository](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen)
* **Проект на Modrinth**: [Modrinth](https://modrinth.com/mod/collapsible-game-rules) (`lObgjyJl`)
* **Проект на CurseForge**: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/collapsible-game-rules) (`1468932`)
