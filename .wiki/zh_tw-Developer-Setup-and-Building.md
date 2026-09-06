# 🛠️ 開發者環境配置與 Gradle 構建

| 參數 | 規格說明 |
| :--- | :--- |
| **目標 Minecraft 版本** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Java 工具鏈** | **Java 25** (`release = 25`, `org.gradle.java.home=E:/JDK25`) |
| **Fabric Loom 外掛** | `net.fabricmc.fabric-loom` 版本 `1.15.2` |
| **Fabric Loader** | `0.19.1` (`loader_version`) |
| **Fabric API** | `0.145.4+26.1.2` |
| **DasikLibrary** | `1.7.4` (硬性依賴) |
| **建構指令** | `./gradlew build --no-daemon` |
| **主要編譯產物** | `build/libs/collapsible-game-rules-1.0.9+26.2.jar` |
| **源碼包產物** | `build/libs/collapsible-game-rules-1.0.9+26.2-sources.jar` |

---

## 📖 環境需求與前置準備

從原始碼編譯並為 Collapsible Game Rules 貢獻代碼，請確保環境滿足以下條件：

1. **Java 開發工具包 (JDK)**：**JDK 25+**（例如 Eclipse Temurin、OpenJDK 25）。
2. **建構系統**：Gradle 9.3+（透過 `./gradlew` 自動管理）。
3. **IDE**：IntelliJ IDEA、VS Code 或支援現代 Java 25 與 Loom 的開發環境。

---

## 🔨 編譯與構建流程

### 1. 複製程式碼倉庫
```bash
git clone https://github.com/Rifaditya/Collapsible-Game-Rule-Screen.git
cd Collapsible-Game-Rule-Screen/collapsible-game-rules
```

### 2. 設定本機 JDK
確認 `gradle.properties` 指向本機的 JDK 25 路徑：
```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25
```

### 3. 建構發布 JAR
執行 Gradle 建構任務：
```bash
./gradlew build --no-daemon
```

編譯完成的發布檔將產生於 `build/libs/`：
* `collapsible-game-rules-1.0.9+26.2.jar`
* `collapsible-game-rules-1.0.9+26.2-sources.jar`

---

## 📜 建構配置 (`build.gradle`)

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

## 🔒 執行時期安全防護：`ModVersionGuard`

所有發布組建均包含零依賴的類別載入驗證：

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

## 🚀 自動發布流程

專案整合 `me.modmuss50.mod-publish-plugin` 進行多平臺發布：

```bash
./gradlew publishMods
```

發布發行端點：
* **Modrinth**: Project ID `lObgjyJl`
* **CurseForge**: Project ID `1468932`
* **GitHub Releases**: `Rifaditya/Collapsible-Game-Rule-Screen`

---

## 🔗 相關文件

* [[🏠 概覽與首頁|zh_tw-Home]]
* [[🧭 版本相容性矩陣|zh_tw-Version-Compatibility]]
* [[🧩 架構與 Mixin 子系統|zh_tw-Architecture-and-Mixins]]
* [[📚 DasikLibrary API 整合|zh_tw-API-and-Library-Integration]]
