# 🧩 Arsitektur & Subsistem Mixin

| Parameter | Spesifikasi |
| :--- | :--- |
| **Berkas Konfigurasi Mixin** | `src/main/resources/collapsible-game-rules.mixins.json` |
| **Nama Berkas Refmap** | `collapsible-game-rules-refmap.json` |
| **Tingkat Kompatibilitas** | `JAVA_25` |
| **Paket Induk** | `net.instantgratification.collapsiblegamerules` |
| **Paket Mixin** | `net.instantgratification.collapsiblegamerules.mixin` |
| **Jumlah Mixin** | `4` Mixin Sisi Klien (`3` Injektor + `1` Aksesor Antarmuka) |

---

## 📖 Struktur Paket Mod

```
net.instantgratification.collapsiblegamerules
├── CollapsibleGameRulesFabric.java           ──> Inisialisasi Mod, penjaga versi & dependensi
├── CollapsibleGameRulesFabricClient.java     ──> Inisialisasi Klien (memuat konfigurasi)
├── GameRuleStateConfig.java                  ──> Manajemen persistensi JSON (Set<String>)
├── mixin
│   ├── AbstractGameRulesScreenRuleListMixin.java ──> Pengatur daftar & rendering kategori lipat
│   ├── CategoryRuleEntryAccessor.java        ──> Aksesor antarmuka label kategori
│   ├── IntegerRuleEntryMixin.java            ──> Pembungkus aturan angka
│   └── ScreenMixin.java                      ──> Penyimpan otomatis saat keluar dari layar
├── preset
│   └── GameRulePresetEngine.java             ──> Definisi profil preset bawaan
├── ui
│   ├── BooleanToggleWidget.java              ──> Widget sakelar ON/OFF
│   ├── GlobalActionsRuleEntry.java           ──> Tajuk tombol aksi global (Indeks 0)
│   └── IntegerSliderWidget.java              ──> Widget slider nilai angka
└── util
    ├── CategoryPrettifier.java               ──> Parser pemformat kunci kategori mentah
    ├── DasikMetadataHelper.java              ──> Lapisan isolasi pemanggil DasikLibrary
    ├── GameRuleSliderHelper.java             ──> Rentang nilai aturan bawaan Vanilla
    └── ModVersionGuard.java                  ──> Pengaman integritas pemuat kelas runtime
```

---

## 🔗 Dokumentasi Terkait

* [[🏠 Ringkasan & Beranda Utama|id_id-Home]]
* [[🗂️ Kategori yang Dapat Dilipat|id_id-Collapsible-Categories]]
* [[🧠 Persistensi Status & Konfigurasi JSON|id_id-State-Persistence-and-Config]]
* [[📚 Integrasi API DasikLibrary|id_id-API-and-Library-Integration]]
