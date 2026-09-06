# 🛠️ 開発環境のセットアップと Gradle ビルド

| 項目 | 仕様 |
| :--- | :--- |
| **対象 Minecraft バージョン** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Java ツールチェーン** | **Java 25** (`release = 25`, `org.gradle.java.home=E:/JDK25`) |
| **Fabric Loom プラグイン** | `net.fabricmc.fabric-loom` version `1.15.2` |
| **Fabric Loader** | `0.19.1` (`loader_version`) |
| **Fabric API** | `0.145.4+26.1.2` |
| **DasikLibrary** | `1.7.4` (必須前提ライブラリ) |
| **ビルドコマンド** | `./gradlew build --no-daemon` |
| **生成バイナリ** | `build/libs/collapsible-game-rules-1.0.9+26.2.jar` |
| **ソース JAR** | `build/libs/collapsible-game-rules-1.0.9+26.2-sources.jar` |

---

## 📖 前提条件と環境構築

ソースコードからビルドおよび開発を行うための前提環境:

1. **JDK (Java Development Kit)**: **JDK 25+** (例: Eclipse Temurin, OpenJDK 25).
2. **ビルドツール**: Gradle 9.3+ (付属の `./gradlew` ラッパーを使用).
3. **IDE**: IntelliJ IDEA または VS Code (Java 25 および Loom 対応環境).

---

## 🔨 ビルド手順

### 1. リポジトリのクローン
```bash
git clone https://github.com/Rifaditya/Collapsible-Game-Rule-Screen.git
cd Collapsible-Game-Rule-Screen/collapsible-game-rules
```

### 2. JDK パスの設定
`gradle.properties` でローカルの JDK 25 パスを確認:
```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25
```

### 3. JAR のビルド
ビルドタスクを実行:
```bash
./gradlew build --no-daemon
```

出力先: `build/libs/`
* `collapsible-game-rules-1.0.9+26.2.jar`
* `collapsible-game-rules-1.0.9+26.2-sources.jar`

---

## 📜 ビルド設定 (`build.gradle`)

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

## 🚀 プラットフォーム公開

`me.modmuss50.mod-publish-plugin` による自動公開に対応:

```bash
./gradlew publishMods
```

* **Modrinth**: プロジェクト ID `lObgjyJl`
* **CurseForge**: プロジェクト ID `1468932`
* **GitHub Releases**: `Rifaditya/Collapsible-Game-Rule-Screen`

---

## 🔗 関連ドキュメント

* [[🏠 概要とホームポータル|ja_jp-Home]]
* [[🧭 バージョン互換性マトリックス|ja_jp-Version-Compatibility]]
* [[🧩 アーキテクチャと Mixin サブシステム|ja_jp-Architecture-and-Mixins]]
* [[📚 DasikLibrary API 統合|ja_jp-API-and-Library-Integration]]
