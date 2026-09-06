# 🌎 Aksi Global & Pengalihan Massal

| Parameter | Spesifikasi |
| :--- | :--- |
| **Kelas Komponen** | `net.instantgratification.collapsiblegamerules.ui.GlobalActionsRuleEntry` |
| **Posisi pada Daftar** | Indeks `0` (Terpancang di bagian paling atas `RuleList`) |
| **Tombol Kiri** | `[ Perluas Semua ]` (`gui.collapsible-game-rules.expand_all`) |
| **Tombol Kanan** | `[ Ciutkan Semua ]` (`gui.collapsible-game-rules.collapse_all`) |
| **Sorotan Kursor** | `0x22FFFFFF` (Diterapkan pada separuh sisi yang disorot) |
| **Garis Pemisah Bawah** | `0x44AAAAAA` |
| **Suara Klik** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 Gambaran Umum

Saat menggunakan banyak mod, membuka atau menutup kategori satu per satu membutuhkan banyak waktu.
**Bilah Aksi Global** disematkan di **Indeks 0** daftar, memberikan tombol cepat untuk memperluas atau melipat seluruh kategori hanya dengan satu klik.

---

## 🎨 Tata Letak Terbagi

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ◄──────────────── Separuh Kiri ────────────►◄────────────── Separuh Kanan ──► │
│               [ Perluas Semua ]                            [ Ciutkan Semua ]│
│ ─────────────────────────────────────────────────────────────────────────── │
└─────────────────────────────────────────────────────────────────────────────┘
```

* **Separuh Kiri (`mouseX < getX() + getWidth() / 2`)**: Menjalankan `expandAll`.
* **Separuh Kanan (`mouseX >= getX() + getWidth() / 2`)**: Menjalankan `collapseAll`.

---

## ⚙️ Logika Pemasangan

```java
if (!this.collapsible_game_rules$allEntries.isEmpty()) {
    this.addEntry(new GlobalActionsRuleEntry(
        () -> {
            List<String> allKeys = this.collapsible_game_rules$allEntries.stream()
                .filter(e -> e instanceof AbstractGameRulesScreen.CategoryRuleEntry)
                .map(e -> {
                    Component lbl = ((CategoryRuleEntryAccessor) e).collapsible_game_rules$getLabel();
                    if (lbl.getContents() instanceof TranslatableContents translatable) {
                        return translatable.getKey();
                    }
                    return lbl.getString();
                })
                .toList();
            GameRuleStateConfig.expandAll(allKeys);
            this.collapsible_game_rules$updateVisibleEntries();
        },
        () -> {
            GameRuleStateConfig.collapseAll();
            this.collapsible_game_rules$updateVisibleEntries();
        }
    ));
}
```

---

## 🔗 Dokumentasi Terkait

* [[🏠 Ringkasan & Beranda Utama|id_id-Home]]
* [[🗂️ Kategori yang Dapat Dilipat|id_id-Collapsible-Categories]]
* [[🧠 Persistensi Status & Konfigurasi JSON|id_id-State-Persistence-and-Config]]
* [[🧩 Arsitektur & Subsistem Mixin|id_id-Architecture-and-Mixins]]
