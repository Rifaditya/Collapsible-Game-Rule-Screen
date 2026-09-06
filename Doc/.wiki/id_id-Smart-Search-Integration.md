# 🔍 Integrasi Pencarian Pintar

| Parameter | Spesifikasi |
| :--- | :--- |
| **Metode yang Disadap** | `populateChildren(Ljava/lang/String;)V` |
| **Titik Injeksi** | `@At("TAIL")` |
| **Kelas Mixin** | `AbstractGameRulesScreenRuleListMixin` |
| **Normalisasi Kueri** | `filter.toLowerCase(java.util.Locale.ROOT)` |
| **Indikator Pencarian** | `isSearching = !currentFilter.isEmpty()` |
| **Kondisi Perluas** | `isExpanded = isSearching || GameRuleStateConfig.isExpanded(key)` |

---

## 📖 Gambaran Umum

Bilah pencarian Vanilla menyaring aturan berdasarkan nama. Namun pada daftar lipat biasa, aturan yang cocok bisa tersembunyi di dalam kategori yang sedang tertutup.

**Integrasi Pencarian Pintar** memantau ketikan secara langsung: begitu ada teks di dalam kolom pencarian, semua kategori yang berisi aturan yang cocok akan **otomatis diperluas**, memperlihatkan hasil pencarian tanpa perlu dibuka manual.

---

## ⚙️ Alur Kerja Pencarian Pintar

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          SMART SEARCH PIPELINE                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Pemain mengetik kueri di bilah pencarian (contoh: "fire")                 │
│        │                                                                    │
│        ▼                                                                    │
│   Vanilla RuleList.populateChildren("fire") menyaring daftar aturan         │
│        │                                                                    │
│        ▼ (@Inject at TAIL)                                                  │
│   AbstractGameRulesScreenRuleListMixin menangkap daftar hasil filter        │
│        ├─ Menyimpan kueri yang dinormalisasi                                │
│        ├─ Mengambil seluruh entri hasil filter                              │
│        └─ Memanggil updateVisibleEntries()                                  │
│             │                                                               │
│             ▼                                                               │
│        isSearching bernilai TRUE                                            │
│             │                                                               │
│             ▼                                                               │
│        Kategori yang cocok dipaksa isExpanded = TRUE                        │
│        Seluruh aturan yang cocok tampil di layar seketika!                  │
│                                                                             │
│   Pemain mengosongkan bilah pencarian ("")                                  │
│        │                                                                    │
│        ▼                                                                    │
│   isSearching = FALSE ──> Status lipatan kembali ke pengaturan sebelumnya!  │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

Saat kolom pencarian dibersihkan, kondisi lipatan pengguna sebelumnya akan dipulihkan secara otomatis tanpa mengubah berkas konfigurasi.

---

## 🔗 Dokumentasi Terkait

* [[🏠 Ringkasan & Beranda Utama|id_id-Home]]
* [[🗂️ Kategori yang Dapat Dilipat|id_id-Collapsible-Categories]]
* [[🧠 Persistensi Status & Konfigurasi JSON|id_id-State-Persistence-and-Config]]
* [[🧩 Arsitektur & Subsistem Mixin|id_id-Architecture-and-Mixins]]
