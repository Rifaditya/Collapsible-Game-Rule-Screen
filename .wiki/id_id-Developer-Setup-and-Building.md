# 🛠️ Penyiapan Pengembang & Build Gradle

| Parameter | Spesifikasi |
| :--- | :--- |
| **Versi Target Minecraft** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Toolchain Java** | **Java 25** (`release = 25`, `org.gradle.java.home=E:/JDK25`) |
| **Plugin Fabric Loom** | `net.fabricmc.fabric-loom` version `1.15.2` |
| **Fabric Loader** | `0.19.1` (`loader_version`) |
| **Fabric API** | `0.145.4+26.1.2` |
| **DasikLibrary** | `1.7.4` (Dependensi Wajib) |
| **Perintah Kompilasi** | `./gradlew build --no-daemon` |
| **Artefak Utama** | `build/libs/collapsible-game-rules-1.0.9+26.2.jar` |
| **Artefak Kode Sumber** | `build/libs/collapsible-game-rules-1.0.9+26.2-sources.jar` |

---

## 📖 Prasyarat & Penyiapan Ruang Kerja

1. **Java Development Kit (JDK)**: **JDK 25+** (misal Eclipse Temurin, OpenJDK 25).
2. **Sistem Build**: Gradle 9.3+ (melalui wrapper `./gradlew`).
3. **IDE**: IntelliJ IDEA atau VS Code dengan dukungan Java 25 & Loom.

---

## 🔨 Langkah Kompilasi & Build

### 1. Kloning Repositori
```bash
git clone https://github.com/Rifaditya/Collapsible-Game-Rule-Screen.git
cd Collapsible-Game-Rule-Screen/collapsible-game-rules
```

### 2. Atur Jalur JDK Lokal
Pastikan file `gradle.properties` mengarah ke folder instalasi JDK 25 Anda:
```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25
```

### 3. Kompilasi File JAR
Jalankan tugas build Gradle:
```bash
./gradlew build --no-daemon
```

Hasil kompilasi akan berada pada direktori `build/libs/`:
* `collapsible-game-rules-1.0.9+26.2.jar`
* `collapsible-game-rules-1.0.9+26.2-sources.jar`

---

## 📜 Konfigurasi Build (`build.gradle`)

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

## 🚀 Publikasi Otomatis

Mod menggunakan `me.modmuss50.mod-publish-plugin` untuk rilis otomatis:

```bash
./gradlew publishMods
```

* **Modrinth**: ID Proyek `lObgjyJl`
* **CurseForge**: ID Proyek `1468932`
* **GitHub Releases**: `Rifaditya/Collapsible-Game-Rule-Screen`

---

## 🔗 Dokumentasi Terkait

* [[🏠 Ringkasan & Beranda Utama|id_id-Home]]
* [[🧭 Matriks Kompatibilitas Versi|id_id-Version-Compatibility]]
* [[🧩 Arsitektur & Subsistem Mixin|id_id-Architecture-and-Mixins]]
* [[📚 Integrasi API DasikLibrary|id_id-API-and-Library-Integration]]
