# ⌨️ Navigasi Papan Ketik & Aksesibilitas

| Parameter | Spesifikasi |
| :--- | :--- |
| **Kelas Komponen** | `CollapsibleCategoryRuleEntry` |
| **Antarmuka Aksesibilitas**| `net.minecraft.client.gui.narration.NarratableEntry` |
| **Prioritas Narasi** | `NarrationPriority.HOVERED` |
| **Elemen yang Dinarasikan**| `NarratedElementType.TITLE` |
| **Tombol Alih Status** | `Spasi`, `Enter`, `Numpad Enter` |
| **Tombol Melipat** | `Panah Kiri (←)` (Hanya jika sedang terbuka) |
| **Tombol Membuka** | `Panah Kanan (→)` (Hanya jika sedang tertutup) |
| **Umpan Balik Suara** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 Gambaran Umum

Collapsible Game Rules mendukung kendali penuh melalui papan ketik serta narasi pembaca layar, sehingga pemain dapat menelusuri dan mengatur aturan tanpa menyentuh mouse.

---

## ⌨️ Daftar Pintasan Papan Ketik

Saat sebuah tajuk kategori disorot dalam daftar, tombol-tombol berikut aktif:

| Tombol | Konstanta GLFW | Aksi | Syarat | Suara |
| :--- | :--- | :--- | :--- | :--- |
| **Spasi** | `GLFW_KEY_SPACE` | Balikkan status lipatan | Selalu aktif | `UI_BUTTON_CLICK` |
| **Enter** | `GLFW_KEY_ENTER` | Balikkan status lipatan | Selalu aktif | `UI_BUTTON_CLICK` |
| **Numpad Enter**| `GLFW_KEY_KP_ENTER` | Balikkan status lipatan | Selalu aktif | `UI_BUTTON_CLICK` |
| **Panah Kiri (←)**| `GLFW_KEY_LEFT` | **Lipat Kategori** | `expanded == true` | `UI_BUTTON_CLICK` |
| **Panah Kanan (→)**| `GLFW_KEY_RIGHT` | **Buka Kategori** | `expanded == false` | `UI_BUTTON_CLICK` |

---

## 🔗 Dokumentasi Terkait

* [[🏠 Ringkasan & Beranda Utama|id_id-Home]]
* [[🗂️ Kategori yang Dapat Dilipat|id_id-Collapsible-Categories]]
* [[🖥️ HUD, Diagnostik & Rendering UI|id_id-HUD-and-Diagnostics]]
* [[🧩 Arsitektur & Subsistem Mixin|id_id-Architecture-and-Mixins]]
