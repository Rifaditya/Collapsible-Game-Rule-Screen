# 🧭 Матрица совместимости версий

| Параметр | Спецификация |
| :--- | :--- |
| **Текущая целевая версия** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Версия мода** | `1.0.9+26.2` |
| **Поддерживаемые выпуски игры** | Minecraft 26.2+ (Современная суверенная эра) |
| **Требования Java** | **Java 25+** (`release = 25`, toolchain Java 25) |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` (`*`) |
| **Базовая библиотека** | **DasikLibrary** `>=1.7.0` (Текущая: `1.7.4`) |
| **Инструменты сборки** | Loom `1.15.2` / Gradle `9.3+` (`--no-daemon`) |
| **Окружение** | **Только клиент** (`"environment": "client"`) |
| **Лицензия** | **GPL-3.0-or-later** |

---

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или функции в разработке до публичных релизов на CurseForge и Modrinth.

---

## 📊 Матрица многоэпохального жизненного цикла

Collapsible Game Rules создан для современной эры (`MC 26.2+`), используя графический конвейер `GuiGraphicsExtractor`, современные Stream API Java 25 (`.toList()`) и чистую среду выполнения без обфускации.

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                           MULTI-ERA RUNTIME LIFECYCLE MATRIX                            │
├───────────────────┬─────────────┬──────────────┬───────────────┬────────────────────────┤
│ Minecraft Anchor  │ Java Level  │ Loader Bound │ DasikLibrary  │ Lifecycle Status       │
├───────────────────┼─────────────┼──────────────┼───────────────┼────────────────────────┤
│ **MC 26.2+**      │ **Java 25** │ `>=0.19.1`   │ `>=1.7.0`     │ 🟢 Active Target (Loom)│
│ MC 26.1.2         │ Java 25     │ `>=0.18.4`   │ `>=1.7.0`     │ 🟡 Forward Compatible  │
│ MC 1.21.x         │ Java 21     │ N/A          │ N/A           │ ⚪ Unsupported (Modern)│
│ MC 1.20.1         │ Java 17     │ N/A          │ N/A           │ ⚪ Unsupported (Modern)│
└───────────────────┴─────────────┴──────────────┴───────────────┴────────────────────────┘
```

---

## 🔒 Защита ClassLoader через ModVersionGuard

Для предотвращения повреждения сохранений и аварийных циклов на неподдерживаемых выпусках игры, мод включает запускной страж `net.instantgratification.collapsiblegamerules.util.ModVersionGuard`.

### Механизм проверки
Во время `ModInitializer.onInitialize()` выполняется:
```java
ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
```

Если запуск произошел в несовместимой версии без указанного класса или привязок Knot ClassLoader, мод останавливает выполнение со структурированным предупреждением:

```
=====================================================================
 [PRE-RELEASE / VERSION GUARD WARNING] Collapsible Game Rules
---------------------------------------------------------------------
 CRITICAL: Incompatible Minecraft Game Runtime or Missing Class!
 Required Class : net.minecraft.world.level.gamerules.GameRules
 Status         : UNRESOLVED AT RUNTIME

 Safety Protection:
 Execution halted to prevent unreleased/incompatible build deployment
 or broken world state save corruption.

 Troubleshooting Steps:
 1. Verify target Minecraft version (26.2+ release drop).
 2. Ensure all required dependencies (Fabric API, DasikLibrary) are loaded.
 3. Build/Download a verified matching release JAR from Modrinth/CurseForge.
=====================================================================
```

---

## 📦 Декларация зависимостей

В файле `src/main/resources/fabric.mod.json`:

```json
{
  "schemaVersion": 1,
  "id": "collapsible-game-rules",
  "version": "${version}",
  "name": "Collapsible Game Rules",
  "description": "Makes the GameRules UI screens collapsible by category.",
  "authors": [
    "Dasik (Rifaditya)"
  ],
  "license": "GPL-3.0-or-later",
  "environment": "client",
  "entrypoints": {
    "main": [
      "net.instantgratification.collapsiblegamerules.CollapsibleGameRulesFabric"
    ],
    "client": [
      "net.instantgratification.collapsiblegamerules.CollapsibleGameRulesFabricClient"
    ]
  },
  "mixins": [
    "collapsible-game-rules.mixins.json"
  ],
  "depends": {
    "fabricloader": ">=0.19.1",
    "minecraft": ">=26.2-",
    "java": ">=25",
    "fabric-api": "*",
    "dasik-library": ">=1.7.0"
  }
}
```

---

## 🗄️ Архивы релизов и артефакты сборки

Официальные сборки заархивированы в папке `Archive Jar of all versions/`:

* `collapsible-game-rules-1.0.9+26.2.jar` (Текущий релиз)
* `collapsible-game-rules-1.0.8+26.2.jar` (Предыдущая сборка)
* `collapsible-game-rules-1.0.7+26.2.jar` (Обновление функций)

---

## 🔗 Связанная документация

* [[🏠 Обзор и Главная|ru_ru-Home]]
* [[🛠️ Среда разработки и сборка Gradle|ru_ru-Developer-Setup-and-Building]]
* [[📚 Интеграция с API DasikLibrary|ru_ru-API-and-Library-Integration]]
