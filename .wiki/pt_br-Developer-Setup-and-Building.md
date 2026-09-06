# 🛠️ Configuração de Desenvolvedor & Compilação Gradle

| Parâmetro | Especificação |
| :--- | :--- |
| **Versão Alvo do Minecraft** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Toolchain Java** | **Java 25** (`release = 25`, `org.gradle.java.home=E:/JDK25`) |
| **Plugin Fabric Loom** | `net.fabricmc.fabric-loom` versão `1.15.2` |
| **Fabric Loader** | `0.19.1` (`loader_version`) |
| **Fabric API** | `0.145.4+26.1.2` |
| **DasikLibrary** | `1.7.4` (Requisito Obrigatório) |
| **Comando de Compilação** | `./gradlew build --no-daemon` |
| **Artefato Principal** | `build/libs/collapsible-game-rules-1.0.9+26.2.jar` |
| **Artefato de Fontes** | `build/libs/collapsible-game-rules-1.0.9+26.2-sources.jar` |

---

## 📖 Pré-requisitos e Preparação do Ambiente

Para compilar e contribuir com o código-fonte do Collapsible Game Rules:

1. **Java Development Kit (JDK)**: **JDK 25+** (ex: Eclipse Temurin, OpenJDK 25).
2. **Sistema de Build**: Gradle 9.3+ (gerenciado automaticamente pelo script `./gradlew`).
3. **IDE**: IntelliJ IDEA, VS Code ou ambiente com suporte a Java 25 e Loom.

---

## 🔨 Fluxo de Compilação e Build

### 1. Clonando o Repositório
```bash
git clone https://github.com/Rifaditya/Collapsible-Game-Rule-Screen.git
cd Collapsible-Game-Rule-Screen/collapsible-game-rules
```

### 2. Configurando o JDK Local
Certifique-se de que o `gradle.properties` aponte para o caminho do seu JDK 25:
```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25
```

### 3. Compilando o JAR Final
Execute a tarefa Gradle:
```bash
./gradlew build --no-daemon
```

Os artefatos compilados estarão na pasta `build/libs/`:
* `collapsible-game-rules-1.0.9+26.2.jar`
* `collapsible-game-rules-1.0.9+26.2-sources.jar`

---

## 📜 Configuração de Build (`build.gradle`)

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

## 🔒 Proteção em Execução: `ModVersionGuard`

Todas as builds realizam verificação de carregamento de classes sem dependências extras:

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

## 🚀 Publicação Automatizada

O projeto utiliza `me.modmuss50.mod-publish-plugin`:

```bash
./gradlew publishMods
```

Destinos de distribuição:
* **Modrinth**: Project ID `lObgjyJl`
* **CurseForge**: Project ID `1468932`
* **GitHub Releases**: `Rifaditya/Collapsible-Game-Rule-Screen`

---

## 🔗 Documentação Relacionada

* [[🏠 Visão Geral & Página Inicial|pt_br-Home]]
* [[🧭 Matriz de Compatibilidade de Versões|pt_br-Version-Compatibility]]
* [[🧩 Arquitetura & Subsistema Mixin|pt_br-Architecture-and-Mixins]]
* [[📚 Integração com a API do DasikLibrary|pt_br-API-and-Library-Integration]]
