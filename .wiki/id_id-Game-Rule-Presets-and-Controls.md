# 🎛️ Preset Aturan Permainan & Kontrol

| Parameter | Spesifikasi |
| :--- | :--- |
| **Mesin Preset** | `net.instantgratification.collapsiblegamerules.preset.GameRulePresetEngine` |
| **Struktur Preset** | `record Preset(String id, Component displayName, Map<String, Object> ruleValues)` |
| **Widget Kontrol** | `IntegerSliderWidget`, `BooleanToggleWidget` |
| **Pembantu Slider** | `net.instantgratification.collapsiblegamerules.util.GameRuleSliderHelper` |
| **Warna Sakelar Aktif** | `0x4400FF00` (Hijau zamrud) |
| **Warna Sakelar Nonaktif** | `0x44FF0000` (Merah delima) |
| **Preset Bawaan** | `builder` ("🏰 Mode Pembangun"), `fast_play` ("⚡ Main Cepat"), `hardcore` ("💀 Realisme Hardcore") |

---

## 📖 Gambaran Umum

Collapsible Game Rules menyederhanakan konfigurasi melalui slider angka interaktif serta preset bawaan yang dapat langsung diterapkan hanya dengan satu kali klik.

---

## 🏰 Daftar Preset Bawaan

| ID | Nama Profil | Aturan Permainan Terkait | Nilai Diterapkan | Pengaruh Permainan |
| :--- | :--- | :--- | :--- | :--- |
| `builder` | **🏰 Mode Pembangun** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doMobSpawning`<br>`keepInventory`<br>`mobGriefing`<br>`doFireTick` | `false`<br>`false`<br>`false`<br>`true`<br>`false`<br>`false` | Ideal untuk membangun: membekukan waktu dan cuaca, mematikan monster, creeper, dan penyebaran api. |
| `fast_play` | **⚡ Main Cepat** | `randomTickSpeed`<br>`playersSleepingPercentage`<br>`keepInventory` | `10`<br>`0`<br>`true` | Bertahan hidup cepat: tanaman tumbuh $3\\times$ lebih lebat, cukup 1 pemain tidur, inventaris tersimpan. |
| `hardcore` | **💀 Realisme Hardcore**| `naturalRegeneration`<br>`doInsomnia`<br>`playersSleepingPercentage` | `false`<br>`true`<br>`100` | Tantangan berat: regenerasi darah alami mati (wajib apel emas/ramuan), phantom aktif. |

---

## 🎚️ Slider Angka (`IntegerSliderWidget`)

Menggantikan kotak teks ketik manual dengan slider geser yang nyaman.

Rentang Aturan Standar Vanilla:
* `randomTickSpeed`: 0 hingga 100
* `spawnRadius`: 0 hingga 32
* `playersSleepingPercentage`: 0 hingga 100
* `maxEntityCramming`: 0 hingga 100

---

## 🔘 Sakelar Nilai Boolean (`BooleanToggleWidget`)

* **Aktif (ON)**: Menampilkan teks `✔ ON` dengan warna hijau berlatar `0x4400FF00`.
* **Mati (OFF)**: Menampilkan teks `✖ OFF` dengan warna merah berlatar `0x44FF0000`.

---

## 🔗 Dokumentasi Terkait

* [[🏠 Ringkasan & Beranda Utama|id_id-Home]]
* [[🗂️ Kategori yang Dapat Dilipat|id_id-Collapsible-Categories]]
* [[📜 Tabel Referensi GameRules|id_id-GameRules-Reference]]
* [[🧩 Arsitektur & Subsistem Mixin|id_id-Architecture-and-Mixins]]
