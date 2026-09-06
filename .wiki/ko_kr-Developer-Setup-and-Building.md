# 🛠️ 개발자 환경 설정 및 Gradle 빌드

| 매개변수 | 세부 명세 |
| :--- | :--- |
| **대상 Minecraft 버전** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Java 툴체인** | **Java 25** (`release = 25`, `org.gradle.java.home=E:/JDK25`) |
| **Fabric Loom 플러그인** | `net.fabricmc.fabric-loom` version `1.15.2` |
| **Fabric Loader** | `0.19.1` (`loader_version`) |
| **Fabric API** | `0.145.4+26.1.2` |
| **DasikLibrary** | `1.7.4` (필수 선행 라이브러리) |
| **빌드 명령어** | `./gradlew build --no-daemon` |
| **생성 바이너리** | `build/libs/collapsible-game-rules-1.0.9+26.2.jar` |
| **소스 코드 JAR** | `build/libs/collapsible-game-rules-1.0.9+26.2-sources.jar` |

---

## 📖 필수 환경 및 요구 사항

소스 코드로부터 직접 빌드하기 위한 환경 요구 사항:

1. **JDK (Java Development Kit)**: **JDK 25+** (예: Eclipse Temurin, OpenJDK 25).
2. **빌드 시스템**: Gradle 9.3+ (포함된 `./gradlew` 래퍼 사용).
3. **IDE**: IntelliJ IDEA 또는 VS Code (Java 25 및 Loom 플러그인 지원).

---

## 🔨 빌드 절차

### 1. 저장소 복제
```bash
git clone https://github.com/Rifaditya/Collapsible-Game-Rule-Screen.git
cd Collapsible-Game-Rule-Screen/collapsible-game-rules
```

### 2. 로컬 JDK 경로 설정
`gradle.properties`에서 JDK 25 설치 경로 확인:
```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25
```

### 3. JAR 빌드 실행
Gradle 빌드 명령 실행:
```bash
./gradlew build --no-daemon
```

생성물 위치: `build/libs/`
* `collapsible-game-rules-1.0.9+26.2.jar`
* `collapsible-game-rules-1.0.9+26.2-sources.jar`

---

## 📜 빌드 스크립트 (`build.gradle`)

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

## 🚀 자동화된 배포

`me.modmuss50.mod-publish-plugin`을 통한 배포:

```bash
./gradlew publishMods
```

* **Modrinth**: 프로젝트 ID `lObgjyJl`
* **CurseForge**: 프로젝트 ID `1468932`
* **GitHub Releases**: `Rifaditya/Collapsible-Game-Rule-Screen`

---

## 🔗 관련 문서

* [[🏠 개요 및 홈 포털|ko_kr-Home]]
* [[🧭 버전 호환성 매트릭스|ko_kr-Version-Compatibility]]
* [[🧩 아키텍처 및 Mixin 서브시스템|ko_kr-Architecture-and-Mixins]]
* [[📚 DasikLibrary API 통합|ko_kr-API-and-Library-Integration]]
