# ⚒️ Collapsible Game Rules Wiki (Aturan Permainan yang Dapat Dilipat)

<div align="center">

<img src="https://raw.githubusercontent.com/Rifaditya/Collapsible-Game-Rule-Screen/main/Images/2026-08-04_11.06.33.png" alt="Collapsible Game Rules Banner" width="800">

<p align="center">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge&logo=minecraft" alt="Minecraft 26.2+">
  <img src="https://img.shields.io/badge/Fabric-0.145.4+-blue?style=for-the-badge&logo=fabric" alt="Fabric">
  <img src="https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk" alt="Java 25">
  <img src="https://img.shields.io/badge/DasikLibrary-1.7.4-purple?style=for-the-badge" alt="DasikLibrary 1.7.4">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License GPLv3">
</p>

</div>

---

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 🧭 Selamat Datang di Collapsible Game Rules

**Collapsible Game Rules** merombak tampilan layar aturan permainan (Game Rules) standar Minecraft menjadi antarmuka lipat berbasis kategori yang rapi, dilengkapi dengan pemekaran otomatis saat mencari, navigasi papan ketik penuh, persistensi status lokal, dan preset siap pakai.

---

## 🌟 Fitur Utama & Indeks Cepat

| Fitur | Deskripsi | Panduan Referensi |
| :--- | :--- | :--- |
| **🗂️ Kategori yang Dapat Dilipat** | Mengganti tajuk statis dengan widget interaktif (`▼`/`▶`) disertai lencana jumlah aturan. | [[🗂️ Kategori yang Dapat Dilipat\|id_id-Collapsible-Categories]] |
| **🌎 Bilah Aksi Global** | Tombol di bagian paling atas untuk "Perluas Semua" dan "Ciutkan Semua" dengan satu klik. | [[🌎 Aksi Global & Pengalihan Massal\|id_id-Global-Actions-and-Bulk-Toggles]] |
| **🔍 Integrasi Pencarian Pintar** | Otomatis memperluas kategori yang cocok segera setelah teks diketik pada kolom pencarian. | [[🔍 Integrasi Pencarian Pintar\|id_id-Smart-Search-Integration]] |
| **⌨️ Navigasi Papan Ketik** | Dukungan penuh papan ketik (Spasi, Enter, panah kiri/kanan) serta narasi aksesibilitas layar. | [[⌨️ Navigasi Papan Ketik & Aksesibilitas\|id_id-Keyboard-Navigation]] |
| **🧠 Persistensi Status** | Mengingat kategori yang diperluas atau dilipat di `config/collapsible-game-rules-state.json`. | [[🧠 Persistensi Status & Konfigurasi JSON\|id_id-State-Persistence-and-Config]] |
| **✨ Perapian Nama Kategori** | Memformat kunci teknis mod yang belum diterjemahkan menjadi judul Title Case yang rapi. | [[✨ Perapian & Penamaan Kategori\|id_id-Category-Prettification-and-Naming]] |
| **🎛️ Preset & Kontrol** | Profil permainan sekali klik (Pembangun, Main Cepat, Hardcore) dan slider angka berkelanjutan. | [[🎛️ Preset Aturan Permainan & Kontrol\|id_id-Game-Rule-Presets-and-Controls]] |
| **🧩 Integrasi DasikLibrary** | Menanyakan terjemahan dinamis dari `DynamicGameRuleManager` untuk lokalisasi kategori mod. | [[📚 Integrasi API DasikLibrary\|id_id-API-and-Library-Integration]] |

---

## 🚀 Panduan Memulai Cepat & Pemasangan

1. Pasang **[Fabric Loader](https://fabricmc.net/)** (`>=0.19.1`).
2. Unduh **[Fabric API](https://modrinth.com/mod/fabric-api)**.
3. Unduh **[DasikLibrary](https://modrinth.com/mod/dasik-library)** (`>=1.7.0`, **Dependensi Wajib**).
4. Tempatkan file `collapsible-game-rules-1.0.9+26.2.jar` ke dalam folder `.minecraft/mods`.
5. Jalankan Minecraft menggunakan **Java 25+**.

---

## 📚 Daftar Isi Dokumentasi

```
Root Wiki
├── 🧭 Matriks Kompatibilitas Versi ----------> [[🧭 Matriks Kompatibilitas Versi|id_id-Version-Compatibility]]
├── 🎮 Mekanika Antarmuka Pengguna
│   ├── Kategori yang Dapat Dilipat ------------> [[🗂️ Kategori yang Dapat Dilipat|id_id-Collapsible-Categories]]
│   ├── Aksi Global & Pengalihan Massal --------> [[🌎 Aksi Global & Pengalihan Massal|id_id-Global-Actions-and-Bulk-Toggles]]
│   ├── Integrasi Pencarian Pintar -------------> [[🔍 Integrasi Pencarian Pintar|id_id-Smart-Search-Integration]]
│   └── Navigasi Papan Ketik & Aksesibilitas --> [[⌨️ Navigasi Papan Ketik & Aksesibilitas|id_id-Keyboard-Navigation]]
├── ⚙️ Konfigurasi & Preset
│   ├── Persistensi Status & Konfigurasi JSON -> [[🧠 Persistensi Status & Konfigurasi JSON|id_id-State-Persistence-and-Config]]
│   ├── Perapian & Penamaan Kategori -----------> [[✨ Perapian & Penamaan Kategori|id_id-Category-Prettification-and-Naming]]
│   └── Preset Aturan Permainan & Kontrol ------> [[🎛️ Preset Aturan Permainan & Kontrol|id_id-Game-Rule-Presets-and-Controls]]
├── 📋 Cakupan & Referensi Permainan
│   ├── Tabel Referensi GameRules --------------> [[📜 Tabel Referensi GameRules|id_id-GameRules-Reference]]
│   ├── Perintah Brigadier & Cakupan -----------> [[💬 Perintah Brigadier & Cakupan|id_id-Commands]]
│   └── Kemajuan & Cakupan ---------------------> [[🏆 Kemajuan & Cakupan|id_id-Advancements]]
└── 💻 Arsitektur Teknis & Pengembangan
    ├── HUD, Diagnostik & Rendering UI ---------> [[🖥️ HUD, Diagnostik & Rendering UI|id_id-HUD-and-Diagnostics]]
    ├── Penyiapan Pengembang & Build Gradle ----> [[🛠️ Penyiapan Pengembang & Build Gradle|id_id-Developer-Setup-and-Building]]
    ├── Arsitektur & Subsistem Mixin -----------> [[🧩 Arsitektur & Subsistem Mixin|id_id-Architecture-and-Mixins]]
    └── Integrasi API DasikLibrary -------------> [[📚 Integrasi API DasikLibrary|id_id-API-and-Library-Integration]]
```

---

## 📜 Kredit & Lisensi

* **Pembuat & Pengembang Utama**: **Dasik (Rifaditya)**
* **Lisensi**: **GNU General Public License v3.0 (GPLv3)**
* **Repositori GitHub**: [GitHub Repository](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen)
* **Proyek Modrinth**: [Modrinth](https://modrinth.com/mod/collapsible-game-rules) (`lObgjyJl`)
* **Proyek CurseForge**: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/collapsible-game-rules) (`1468932`)
