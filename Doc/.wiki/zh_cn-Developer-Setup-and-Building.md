# 🛠️ 开发者环境配置与 Gradle 构建

| 参数 | 规格说明 |
| :--- | :--- |
| **目标 Minecraft 版本** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Java 工具链** | **Java 25** (`release = 25`, `org.gradle.java.home=E:/JDK25`) |
| **Fabric Loom 插件** | `net.fabricmc.fabric-loom` 版本 `1.15.2` |
| **Fabric Loader** | `0.19.1` (`loader_version`) |
| **Fabric API** | `0.145.4+26.1.2` |
| **DasikLibrary** | `1.7.4` (硬性依赖) |
| **构建命令** | `./gradlew build --no-daemon` |
| **主要编译产物** | `build/libs/collapsible-game-rules-1.0.9+26.2.jar` |
| **源码包产物** | `build/libs/collapsible-game-rules-1.0.9+26.2-sources.jar` |

---

## 📖 环境需求与前置准备

从源代码编译并为 Collapsible Game Rules 贡献代码，请确保环境满足以下条件：

1. **Java 開發工具包 (JDK)**：**JDK 25+**（例如 Eclipse Temurin、OpenJDK 25）。
2. **建構系統**：Gradle 9.3+（透過 `./gradlew` 自動管理）。
3. **IDE**：IntelliJ IDEA、VS Code 或支援現代 Java 25 與 Loom 的開發環境。

---

## 🔨 编译与构建流程

### 1. 克隆代码仓库
```bash
git clone https://github.com/Rifaditya/Collapsible-Game-Rule-Screen.git
cd Collapsible-Game-Rule-Screen/collapsible-game-rules
```

### 2. 配置本地 JDK
确认 `gradle.properties` 指向本地的 JDK 25 路径：
```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25
```

### 3. 构建发布 JAR
执行 Gradle 构建任务：
```bash
./gradlew build --no-daemon
```

编译完成的发布文件将生成于 `build/libs/`：
* `collapsible-game-rules-1.0.9+26.2.jar`
* `collapsible-game-rules-1.0.9+26.2-sources.jar`

---

## 📜 构建配置 (`build.gradle`)

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

## 🔒 运行时安全防护：`ModVersionGuard`

所有发布组件均包含零依赖的类加载验证：

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

## 🚀 自动发布流程

项目集成 `me.modmuss50.mod-publish-plugin` 进行多平台发布：

```bash
./gradlew publishMods
```

发布发行端点：
* **Modrinth**: Project ID `lObgjyJl`
* **CurseForge**: Project ID `1468932`
* **GitHub Releases**: `Rifaditya/Collapsible-Game-Rule-Screen`

---

## 🔗 相关文档

* [[🏠 概览与首页|zh_cn-Home]]
* [[🧭 版本兼容性矩阵|zh_cn-Version-Compatibility]]
* [[🧩 架构与 Mixin 子系统|zh_cn-Architecture-and-Mixins]]
* [[📚 DasikLibrary API 集成|zh_cn-API-and-Library-Integration]]
