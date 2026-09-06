# 🗂️ Kategori yang Dapat Dilipat

| Parameter | Spesifikasi |
| :--- | :--- |
| **Komponen Sistem** | `CollapsibleCategoryRuleEntry` (Kelas internal) |
| **Mixin Penampung** | `AbstractGameRulesScreenRuleListMixin` |
| **Kelas Target** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList` |
| **Ikon Status Lipatan** | Terbuka: `▼ ` \\| Tertutup: `▶ ` |
| **Format Lencana Jumlah** | ` (N rules)` (`ChatFormatting.GRAY`) |
| **Warna Sorotan Kursor** | `0x22FFFFFF` (Putih transparan 13%) |
| **Garis Pemisah Bawah** | `0x44AAAAAA` (Garis abu-abu halus) |
| **Warna Teks** | Kursor Menyentuh: `0xFFFFFFAA` \\| Normal: `0xFFFFFFFF` |
| **Suara Klik** | `net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK` (Volume: `1.0F`) |
| **Prioritas Narasi Layar**| `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 Gambaran Umum

Pada Minecraft Vanilla, judul kategori hanyalah teks statis (`CategoryRuleEntry`), dan seluruh aturan ditampilkan dalam satu daftar gulir panjang yang melelahkan saat modpack memiliki ratusan aturan.

**Collapsible Categories** menggantikan tajuk statis tersebut dengan widget interaktif `CollapsibleCategoryRuleEntry` yang dapat diklik atau dikendalikan via papan ketik untuk memperluas atau melipat kategori secara mandiri.

---

## 🎨 Tata Letak Visual

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ [ Perluas Semua ]                                         [ Ciutkan Semua ] │ ◄── GlobalActionsRuleEntry (Indeks 0)
├─────────────────────────────────────────────────────────────────────────────┤
│ ▼ ⚔️ Mobs (14 rules)                                                        │ ◄── CollapsibleCategoryRuleEntry (Terbuka)
│ ─────────────────────────────────────────────────────────────────────────── │
│   mobGriefing                                                     [ ON ]    │ ◄── Entri Aturan Anak
│   doMobSpawning                                                   [ ON ]    │ ◄── Entri Aturan Anak
│   doMobLoot                                                       [ ON ]    │ ◄── Entri Aturan Anak
├─────────────────────────────────────────────────────────────────────────────┤
│ ▶ 👤 Player (8 rules)                                                       │ ◄── CollapsibleCategoryRuleEntry (Tertutup)
│ ─────────────────────────────────────────────────────────────────────────── │
│ ▼ 🌧️ Updates (6 rules)                                                      │ ◄── CollapsibleCategoryRuleEntry (Terbuka)
│ ─────────────────────────────────────────────────────────────────────────── │
│   doFireTick                                                      [ ON ]    │ ◄── Entri Aturan Anak
│   randomTickSpeed                                                 [ 3  ]    │ ◄── Entri Aturan Anak
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## ⚙️ Rincian Teknis

### 1. Perhitungan Jumlah Aturan Anak
Di dalam `updateVisibleEntries()`, sistem menghitung aturan hingga bertemu tajuk kategori berikutnya:

```java
int childCount = 0;
for (int j = i + 1; j < this.collapsible_game_rules$allEntries.size(); j++) {
    if (this.collapsible_game_rules$allEntries.get(j) instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
        break;
    }
    childCount++;
}
```

### 2. Alur Rendering (`extractContent`)
Memanfaatkan `GuiGraphicsExtractor`:
1. **Sorotan Kursor**: Menggambar kotak latar `0x22FFFFFF` saat kursor berada di atas tajuk.
2. **Panah & Lencana**: Menampilkan ikon panah (`▼ ` atau `▶ `), nama kategori, serta lencana jumlah abu-abu (` (N rules)`).
3. **Penyelarasan Tengah**: Menggambar teks tepat di sumbu tengah `getContentXMiddle()`.
4. **Garis Pemisah**: Menggambar garis halus `0x44AAAAAA` di bagian bawah (`getY() + 23`).

### 3. Penanganan Klik
Klik kiri atau klik kanan membalikkan status buka/tutup, menyimpannya di `GameRuleStateConfig`, serta memutar suara `SoundEvents.UI_BUTTON_CLICK`.

---

## 🔗 Dokumentasi Terkait

* [[🏠 Ringkasan & Beranda Utama|id_id-Home]]
* [[🌎 Aksi Global & Pengalihan Massal|id_id-Global-Actions-and-Bulk-Toggles]]
* [[⌨️ Navigasi Papan Ketik & Aksesibilitas|id_id-Keyboard-Navigation]]
* [[🧩 Arsitektur & Subsistem Mixin|id_id-Architecture-and-Mixins]]
