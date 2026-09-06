# 🖥️ HUD, Diagnostik & Rendering UI

| Parameter | Spesifikasi |
| :--- | :--- |
| **Mesin Grafis** | `net.minecraft.client.gui.GuiGraphicsExtractor` |
| **Layar Terkait** | `AbstractGameRulesScreen` |
| **Warna Teks Sorotan** | `0xFFFFFFAA` (Kuning lembut berkilau) |
| **Warna Teks Normal** | `0xFFFFFFFF` (Putih jernih) |
| **Kotak Latar Sorotan** | `0x22FFFFFF` (Putih transparan halus) |
| **Garis Pembatas Bawah** | `0x44AAAAAA` (Garis abu-abu) |
| **Sakelar Hijau Zamrud**| `0x4400FF00` |
| **Sakelar Merah Delima** | `0x44FF0000` |

---

## 📖 Gambaran Umum

Dalam Minecraft 26.2, Mojang memperbarui pipeline rendering grafis ke `GuiGraphicsExtractor`. Collapsible Game Rules dibangun sepenuhnya di atas sistem modern ini untuk menjamin rendering antarmuka yang tajam dan bebas hambatan.

---

## 🎨 Palet Warna Antarmuka

| Komponen | Kode Warna ARGB | Keterangan |
| :--- | :--- | :--- |
| **Latar Sorotan** | `0x22FFFFFF` | Efek hover pada tajuk kategori dan bilah aksi global. |
| **Garis Pembatas**| `0x44AAAAAA` | Garis tipis pembatas kategori. |
| **Teks Sorotan** | `0xFFFFFFAA` | Aksen warna cerah saat kursor menyentuh entri. |
| **Teks Biasa** | `0xFFFFFFFF` | Warna teks utama dengan keterbacaan tinggi. |
| **Lencana Jumlah** | `ChatFormatting.GRAY` | Angka jumlah aturan anak di samping nama kategori. |

---

## 🔗 Dokumentasi Terkait

* [[🏠 Ringkasan & Beranda Utama|id_id-Home]]
* [[🗂️ Kategori yang Dapat Dilipat|id_id-Collapsible-Categories]]
* [[⌨️ Navigasi Papan Ketik & Aksesibilitas|id_id-Keyboard-Navigation]]
* [[🧩 Arsitektur & Subsistem Mixin|id_id-Architecture-and-Mixins]]
