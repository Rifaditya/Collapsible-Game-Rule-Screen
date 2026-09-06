# 🛠️ Configuración de Desarrollador y Compilación Gradle

| Parámetro | Especificación |
| :--- | :--- |
| **Versión de Minecraft Objetivo** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Cadena de Herramientas Java** | **Java 25** (`release = 25`, `org.gradle.java.home=E:/JDK25`) |
| **Plugin Fabric Loom** | `net.fabricmc.fabric-loom` versión `1.15.2` |
| **Fabric Loader** | `0.19.1` (`loader_version`) |
| **Fabric API** | `0.145.4+26.1.2` |
| **DasikLibrary** | `1.7.4` (Requisito Obligatorio) |
| **Comando de Compilación** | `./gradlew build --no-daemon` |
| **Artefacto Principal** | `build/libs/collapsible-game-rules-1.0.9+26.2.jar` |
| **Artefacto de Fuentes** | `build/libs/collapsible-game-rules-1.0.9+26.2-sources.jar` |

---

## 📖 Prerrequisitos y Configuración de Entorno

Para compilar y colaborar en Collapsible Game Rules desde el código fuente, asegúrate de contar con:

1. **Java Development Kit (JDK)**: **JDK 25+** (por ejemplo, Eclipse Temurin, OpenJDK 25).
2. **Sistema de Compilación**: Gradle 9.3+ (gestionado mediante `./gradlew`).
3. **IDE**: IntelliJ IDEA, VS Code o entorno compatible con Java 25 y Loom.

---

## 🔨 Flujo de Compilación y Construcción

### 1. Clonar el Repositorio
```bash
git clone https://github.com/Rifaditya/Collapsible-Game-Rule-Screen.git
cd Collapsible-Game-Rule-Screen/collapsible-game-rules
```

### 2. Configurar JDK Local
Verifica que `gradle.properties` apunte a la ruta de tu JDK 25:
```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25
```

### 3. Compilar el JAR de Lanzamiento
Ejecuta la tarea de Gradle:
```bash
./gradlew build --no-daemon
```

Los artefactos finales se generarán en `build/libs/`:
* `collapsible-game-rules-1.0.9+26.2.jar`
* `collapsible-game-rules-1.0.9+26.2-sources.jar`

---

## 📜 Configuración de Compilación (`build.gradle`)

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

## 🔒 Seguridad en Ejecución: `ModVersionGuard`

Todas las compilaciones incluyen verificación de carga de clases sin dependencias:

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

## 🚀 Publicación de Versiones

El proyecto utiliza `me.modmuss50.mod-publish-plugin` para distribución automatizada:

```bash
./gradlew publishMods
```

Plataformas de destino:
* **Modrinth**: Project ID `lObgjyJl`
* **CurseForge**: Project ID `1468932`
* **GitHub Releases**: `Rifaditya/Collapsible-Game-Rule-Screen`

---

## 🔗 Documentación Relacionada

* [[🏠 Resumen y Portal Principal|es_es-Home]]
* [[🧭 Matriz de Compatibilidad de Versiones|es_es-Version-Compatibility]]
* [[🧩 Arquitectura y Subsistema Mixin|es_es-Architecture-and-Mixins]]
* [[📚 Integración con la API de DasikLibrary|es_es-API-and-Library-Integration]]
