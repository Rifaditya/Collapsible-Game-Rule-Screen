# 🛠️ Entwickler-Setup & Gradle-Builds

| Parameter | Spezifikation |
| :--- | :--- |
| **Ziel-Minecraft-Version** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Java Toolchain** | **Java 25** (`release = 25`, `org.gradle.java.home=E:/JDK25`) |
| **Fabric Loom Plugin** | `net.fabricmc.fabric-loom` Version `1.15.2` |
| **Fabric Loader** | `0.19.1` (`loader_version`) |
| **Fabric API** | `0.145.4+26.1.2` |
| **DasikLibrary** | `1.7.4` (Zwingend erforderlich) |
| **Build-Befehl** | `./gradlew build --no-daemon` |
| **Haupt-Build-Artefakt** | `build/libs/collapsible-game-rules-1.0.9+26.2.jar` |
| **Quellcode-Artefakt** | `build/libs/collapsible-game-rules-1.0.9+26.2-sources.jar` |

---

## 📖 Voraussetzungen & Umgebungseinrichtung

Um Collapsible Game Rules aus dem Quellcode zu kompilieren:

1. **Java Development Kit (JDK)**: **JDK 25+** (z. B. Eclipse Temurin, OpenJDK 25).
2. **Build-System**: Gradle 9.3+ (wird automatisch über `./gradlew` verwaltet).
3. **IDE**: IntelliJ IDEA, VS Code oder Entwicklungsumgebungen mit Java 25 & Loom-Unterstützung.

---

## 🔨 Workflow für Kompilierung & Build

### 1. Repository klonen
```bash
git clone https://github.com/Rifaditya/Collapsible-Game-Rule-Screen.git
cd Collapsible-Game-Rule-Screen/collapsible-game-rules
```

### 2. Lokales JDK konfigurieren
Stelle sicher, dass `gradle.properties` auf dein JDK 25 verweist:
```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25
```

### 3. Release-JAR bauen
Führe den Gradle-Build aus:
```bash
./gradlew build --no-daemon
```

Die fertigen Dateien werden im Ordner `build/libs/` generiert:
* `collapsible-game-rules-1.0.9+26.2.jar`
* `collapsible-game-rules-1.0.9+26.2-sources.jar`

---

## 📜 Build-Konfiguration (`build.gradle`)

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

## 🔒 Laufzeitsicherheit: `ModVersionGuard`

Alle Builds enthalten eine abhängigkeitsfreie ClassLoader-Prüfung:

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

## 🚀 Veröffentlichungsworkflow

Das Projekt verwendet `me.modmuss50.mod-publish-plugin`:

```bash
./gradlew publishMods
```

Distributionsendpunkte:
* **Modrinth**: Project ID `lObgjyJl`
* **CurseForge**: Project ID `1468932`
* **GitHub Releases**: `Rifaditya/Collapsible-Game-Rule-Screen`

---

## 🔗 Weiterführende Dokumentation

* [[🏠 Übersicht & Startseite|de_de-Home]]
* [[🧭 Versionskompatibilitätsmatrix|de_de-Version-Compatibility]]
* [[🧩 Architektur & Mixin-Subsystem|de_de-Architecture-and-Mixins]]
* [[📚 DasikLibrary API-Integration|de_de-API-and-Library-Integration]]
