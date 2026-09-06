# 🧠 Persistensi Status & Konfigurasi JSON

| Parameter | Spesifikasi |
| :--- | :--- |
| **Kelas Konfigurasi** | `net.instantgratification.collapsiblegamerules.GameRuleStateConfig` |
| **Lokasi Berkas** | `.minecraft/config/collapsible-game-rules-state.json` |
| **Struktur Memori** | `Set<String> expandedCategories = new HashSet<>()` |
| **Mesin Serialisasi** | `com.google.gson.Gson` (Format Indentasi Rapi) |
| **Penanda Efisiensi** | `private static boolean isDirty = false` |
| **Pemicu Penyimpanan** | `ScreenMixin` menyadap `Screen.removed()` (`@At("HEAD")`) |

---

## 📖 Gambaran Umum

Status buka/tutup kategori disimpan secara otomatis ke dalam konfigurasi lokal, sehingga tata letak pilihan pemain tidak akan hilang saat berpindah dunia atau memuat ulang game.

---

## 📄 Struktur Berkas JSON

`.minecraft/config/collapsible-game-rules-state.json`:

```json
[
  "gamerule.category.spawning",
  "gamerule.category.mobs",
  "gamerule.category.updates"
]
```

* **Ada dalam daftar**: Kategori berstatus **TERBUKA**.
* **Tidak ada dalam daftar**: Kategori berstatus **TERLIPAT** (standar bawaan).

---

## ⚡ Efisiensi Penulisan Berkas (Throttled I/O)

Untuk menjaga **performa FPS tetap mulus tanpa stuttering**, mod menggunakan penanda memori `isDirty`. Berkas hanya ditulis ke disk saat pemain menutup layar aturan permainan, bukan pada setiap kali tombol diklik.

---

## 🔗 Dokumentasi Terkait

* [[🏠 Ringkasan & Beranda Utama|id_id-Home]]
* [[🌎 Aksi Global & Pengalihan Massal|id_id-Global-Actions-and-Bulk-Toggles]]
* [[🧩 Arsitektur & Subsistem Mixin|id_id-Architecture-and-Mixins]]
