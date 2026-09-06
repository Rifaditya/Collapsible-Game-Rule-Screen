# 🛠️ Environnement Développeur & Builds Gradle

| Paramètre | Spécification |
| :--- | :--- |
| **Version Minecraft Cible** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Toolchain Java** | **Java 25** (`release = 25`, `org.gradle.java.home=E:/JDK25`) |
| **Plugin Fabric Loom** | `net.fabricmc.fabric-loom` version `1.15.2` |
| **Fabric Loader** | `0.19.1` (`loader_version`) |
| **Fabric API** | `0.145.4+26.1.2` |
| **DasikLibrary** | `1.7.4` (Dépendance Requise) |
| **Commande de Build** | `./gradlew build --no-daemon` |
| **Artefact Principal** | `build/libs/collapsible-game-rules-1.0.9+26.2.jar` |
| **Artefact des Sources** | `build/libs/collapsible-game-rules-1.0.9+26.2-sources.jar` |

---

## 📖 Prérequis & Configuration de l'Environnement

Pour compiler et contribuer à Collapsible Game Rules depuis le code source :

1. **Java Development Kit (JDK)** : **JDK 25+** (ex. Eclipse Temurin, OpenJDK 25).
2. **Système de Build** : Gradle 9.3+ (géré via le script wrapper `./gradlew`).
3. **IDE** : IntelliJ IDEA, VS Code ou tout environnement compatible Java 25 et Loom.

---

## 🔨 Flux de Compilation et de Build

### 1. Cloner le Dépôt
```bash
git clone https://github.com/Rifaditya/Collapsible-Game-Rule-Screen.git
cd Collapsible-Game-Rule-Screen/collapsible-game-rules
```

### 2. Configurer le JDK Local
Vérifiez que `gradle.properties` pointe vers votre installation de JDK 25 :
```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25
```

### 3. Compiler le JAR de Publication
Lancez la tâche Gradle :
```bash
./gradlew build --no-daemon
```

Les fichiers générés se trouveront dans `build/libs/` :
* `collapsible-game-rules-1.0.9+26.2.jar`
* `collapsible-game-rules-1.0.9+26.2-sources.jar`

---

## 📜 Configuration du Build (`build.gradle`)

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

## 🔒 Sécurité à l'Exécution : `ModVersionGuard`

Chaque build intègre une vérification sans dépendance supplémentaire :

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

## 🚀 Publication Automatisée

Le projet s'appuie sur `me.modmuss50.mod-publish-plugin` :

```bash
./gradlew publishMods
```

Plateformes de publication :
* **Modrinth** : Project ID `lObgjyJl`
* **CurseForge** : Project ID `1468932`
* **GitHub Releases** : `Rifaditya/Collapsible-Game-Rule-Screen`

---

## 🔗 Documentation Associée

* [[🏠 Aperçu & Accueil|fr_fr-Home]]
* [[🧭 Matrice de Compatibilité des Versions|fr_fr-Version-Compatibility]]
* [[🧩 Architecture & Sous-système Mixin|fr_fr-Architecture-and-Mixins]]
* [[📚 Intégration de l'API DasikLibrary|fr_fr-API-and-Library-Integration]]
