# 📜 Справочная таблица игровых правил

| Параметр | Спецификация |
| :--- | :--- |
| **Среда выполнения** | **Только клиент** (`"environment": "client"`) |
| **Серверные правила мода** | `0` (Исключительно улучшение интерфейса) |
| **Охват категорий ванили** | `100%` (`Player`, `Mobs`, `Spawning`, `Drops`, `Updates`, `Chat`, `Misc`) |
| **Поддержка категорий модов** | Динамическая через `DasikMetadataHelper` и `CategoryPrettifier` |
| **Механизм сохранения** | Клиентский файл `config/collapsible-game-rules-state.json` |

---

## 📖 Регламент области действия

> [!NOTE]
> **Чисто клиентский UI-мод**: Collapsible Game Rules занимается исключительно организацией графического интерфейса. Он **не** регистрирует серверные правила, не меняет тиковые вычисления и не затрагивает игровую логику. Все отображаемые правила относятся к ванильной игре или другим установленным модам.

---

## 🗂️ Стандартные ванильные категории и правила

При открытии экрана правил Collapsible Game Rules автоматически структурирует правила Minecraft 26.2 по следующим категориям:

| Категория | Входящие правила | Типичные настройки |
| :--- | :--- | :--- |
| **👤 Игрок (Player)** | `keepInventory`<br>`naturalRegeneration`<br>`playersSleepingPercentage`<br>`fallDamage`<br>`drowningDamage`<br>`fireDamage`<br>`freezeDamage` | Сохранение инвентаря при гибели, процент сна для пропуска ночи, отключение отдельных видов урона. |
| **⚔️ Мобы (Mobs)** | `mobGriefing`<br>`doMobSpawning`<br>`doMobLoot`<br>`universalAnger`<br>`forgiveDeadPlayers` | Запрет разрушений криперами, контроль добычи с мобов, регулировка длительности агрессии. |
| **🌱 Спавн (Spawning)** | `doMobSpawning`<br>`doPatrolSpawning`<br>`doTraderSpawning`<br>`doWardenSpawning`<br>`spawnRadius` | Регулировка частоты появления патрулей разбойников и торговцев, радиус точки возрождения. |
| **📦 Добыча (Drops)** | `doEntityDrops`<br>`doTileDrops`<br>`doMobLoot`<br>`globalSoundEvents` | Выпадение предметов из разрушенных блоков и существ, глобальные звуковые события. |
| **🌧️ Обновления (Updates)** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doFireTick`<br>`randomTickSpeed`<br>`maxCommandChainLength` | Остановка смены суток/погоды, распространение огня, скорость роста посевов. |
| **💬 Чат (Chat)** | `showDeathMessages`<br>`sendCommandFeedback`<br>`logAdminCommands`<br>`reducedDebugInfo` | Настройка оповещений о смертях, отчеты о командах, скрытие координат на F3. |
| **⚙️ Разное (Misc)** | `commandBlockOutput`<br>`maxEntityCramming`<br>`disableElytraMovementCheck` | Порог давки мобов, логи вывода командных блоков. |

---

## 🧩 Совместимость с модифицированными категориями

Любые категории правил, зарегистрированные другими модами (или созданные через `DynamicGameRuleManager` библиотеки `DasikLibrary`), автоматически обнаруживаются, группируются в сворачиваемую папку и форматируются на лету.

---

## 🔗 Связанная документация

* [[🏠 Обзор и Главная|ru_ru-Home]]
* [[🗂️ Сворачиваемые категории|ru_ru-Collapsible-Categories]]
* [[🎛️ Пресеты игровых правил и элементы управления|ru_ru-Game-Rule-Presets-and-Controls]]
* [[📚 Интеграция с API DasikLibrary|ru_ru-API-and-Library-Integration]]
