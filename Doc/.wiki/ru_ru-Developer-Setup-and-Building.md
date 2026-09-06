# 🛠️ Среда разработки и сборка Gradle

| Параметр | Спецификация |
| :--- | :--- |
| **Целевая версия Minecraft** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Тулчейн Java** | **Java 25** (`release = 25`, `org.gradle.java.home=E:/JDK25`) |
| **Плагин Fabric Loom** | `net.fabricmc.fabric-loom` версии `1.15.2` |
| **Fabric Loader** | `0.19.1` (`loader_version`) |
| **Fabric API** | `0.145.4+26.1.2` |
| **DasikLibrary** | `1.7.4` (Обязательное требование) |
| **Команда сборки** | `./gradlew build --no-daemon` |
| **Главный артефакт сборки** | `build/libs/collapsible-game-rules-1.0.9+26.2.jar` |
| **Артефакт исходников** | `build/libs/collapsible-game-rules-1.0.9+26.2-sources.jar` |

---

## 📖 Предварительные требования и настройка окружения

Чтобы компилировать и вносить изменения в Collapsible Game Rules из исходного кода, убедитесь в наличии:

1. **Java Development Kit (JDK)**: **JDK 25+** (например, Eclipse Temurin, OpenJDK 25).
2. **Система сборки**: Gradle 9.3+ (управляется автоматически через скрипт `./gradlew`).
3. **IDE**: IntelliJ IDEA, VS Code или среда с поддержкой Java 25 и Loom.

---

## 🔨 Процесс сборки и компиляции

### 1. Клонирование репозитория
```bash
git clone https://github.com/Rifaditya/Collapsible-Game-Rule-Screen.git
cd Collapsible-Game-Rule-Screen/collapsible-game-rules
```

### 2. Настройка локального JDK
Убедитесь, что в `gradle.properties` указан путь к вашему JDK 25:
```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25
```

### 3. Сборка релизного JAR
Запустите задачу Gradle:
```bash
./gradlew build --no-daemon
```

Скомпилированные файлы появятся в каталоге `build/libs/`:
* `collapsible-game-rules-1.0.9+26.2.jar`
* `collapsible-game-rules-1.0.9+26.2-sources.jar`

---

## 📜 Конфигурация сборки (`build.gradle`)

```groovy
plugins {
    id 'net.fabricmc.fabric-loom' version '1.15.2'
    id 'maven-publish'
    id 'me.modmuss50.mod-publish-plugin' version '2.0.0-beta.1'
}

version = project.mod_version
group = project.maven_group

base {
    archivesName = project.archives_base_name
}

repositories {
    mavenLocal()
    maven { url = "https://maven.fabricmc.net/" }
    mavenCentral()
}

dependencies {
    minecraft "com.mojang:minecraft:${project.minecraft_version}"

    implementation "net.fabricmc:fabric-loader:${project.fabric_loader_version}"
    implementation "net.fabricmc.fabric-api:fabric-api:${project.fabric_version}"
    
    // Standalone Library (No JiJ)
    implementation "net.dasik.social:dasik-library:${project.dasik_library_version}"
}

tasks.withType(JavaCompile).configureEach {
    it.options.release = 25
}

loom {
    mixin {
        defaultRefmapName = "collapsible-game-rules-refmap.json"
    }
}
```

---

## 🔒 Безопасность во время выполнения: `ModVersionGuard`

Все сборки проверяют целостность загрузчика классов:

```java
public class CollapsibleGameRulesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
        
        // Hard Dependency Enforcement
        if (!FabricLoader.getInstance().isModLoaded("dasik-library")) {
            throw new RuntimeException("Collapsible Game Rules requires DasikLibrary to function. Please install it.");
        }
    }
}
```

---

## 🚀 Публикация релизов

Проект использует плагин `me.modmuss50.mod-publish-plugin`:

```bash
./gradlew publishMods
```

Платформы дистрибуции:
* **Modrinth**: Project ID `lObgjyJl`
* **CurseForge**: Project ID `1468932`
* **GitHub Releases**: `Rifaditya/Collapsible-Game-Rule-Screen`

---

## 🔗 Связанная документация

* [[🏠 Обзор и Главная|ru_ru-Home]]
* [[🧭 Матрица совместимости версий|ru_ru-Version-Compatibility]]
* [[🧩 Архитектура и подсистема Mixin|ru_ru-Architecture-and-Mixins]]
* [[📚 Интеграция с API DasikLibrary|ru_ru-API-and-Library-Integration]]
