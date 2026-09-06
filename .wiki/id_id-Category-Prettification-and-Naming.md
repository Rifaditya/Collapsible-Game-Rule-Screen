# ✨ Perapian & Penamaan Kategori

| Parameter | Spesifikasi |
| :--- | :--- |
| **Kelas Utilitas** | `net.instantgratification.collapsiblegamerules.util.CategoryPrettifier` |
| **Fasad Metadata** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **Syarat Pemformatan** | `!Language.getInstance().has(key)` (Belum diterjemahkan) |
| **Pembersihan Awalan** | Menghapus `"gamerule.category."` |
| **Pemisah yang Dikelola**| Titik `.` (namespace) dan tanda `[_-]` (kata) |
| **Sumber Terjemahan** | `DynamicGameRuleManager.getGeneratedTranslations()` |

---

## 📖 Gambaran Umum

Banyak mod menambahkan aturan permainan baru dengan kunci teknis mentah (seperti `gamerule.category.better-bats.better_bats`) tanpa menyertakan terjemahan dalam `lang/en_us.json`.

**Category Prettifier** mendeteksi kunci mentah tersebut dan mengubahnya menjadi judul Title Case yang rapi dan nyaman dibaca (misal: `Better Bats`).

---

## 📊 Contoh Hasil Transformasi

| Kunci Kategori Mentah | Judul yang Ditampilkan | Rincian Pemrosesan |
| :--- | :--- | :--- |
| `gamerule.category.better-bats.better_bats` | **Better Bats** | Menghilangkan duplikasi namespace dan path. |
| `gamerule.category.minecraft.spawning` | **Spawning** | Membuang awalan namespace Vanilla `minecraft`. |
| `gamerule.category.social_mobs.wolf_pack` | **Social Mobs Wolf Pack** | Mengubah garis bawah menjadi spasi dan mengkapitalkan kata. |
| `gamerule.category.custom_rules` | **Custom Rules** | Mengkapitalkan setiap suku kata. |

---

## 🔗 Dokumentasi Terkait

* [[🏠 Ringkasan & Beranda Utama|id_id-Home]]
* [[🗂️ Kategori yang Dapat Dilipat|id_id-Collapsible-Categories]]
* [[📚 Integrasi API DasikLibrary|id_id-API-and-Library-Integration]]
