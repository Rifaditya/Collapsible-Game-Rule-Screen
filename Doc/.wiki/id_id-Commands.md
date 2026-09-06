# 💬 Perintah Brigadier & Cakupan

| Parameter | Spesifikasi |
| :--- | :--- |
| **Perintah Brigadier Tambahan** | `0` (Tidak ada yang didaftarkan secara sengaja) |
| **Arsitektur Perintah** | Antarmuka Pengguna Grafis (GUI) murni di sisi klien |
| **Kompatibilitas Perintah Vanilla** | `100%` Kompatibel dengan perintah asli `/gamerule` |
| **Akses Antarmuka di Dalam Game** | Buat Dunia $\\to$ Aturan Permainan \\| Menu Jeda $\\to$ Aturan Permainan |

---

## 📖 Kebijakan Ketiadaan Perintah

> [!NOTE]
> **Nol Perintah Server**: Mengikuti filosofi Instant Gratification (Kepuasan Instan), Collapsible Game Rules tidak mendaftarkan perintah obrolan kustom (seperti `/cgr config` atau `/collapsiblegamerules reload`). Seluruh penyesuaian dilakukan secara visual di layar aturan permainan.

---

## 💻 Integrasi Perintah Vanilla

Karena mod bekerja di atas `AbstractGameRulesScreen` bawaan, setiap nilai aturan yang diubah melalui perintah obrolan akan langsung diselaraskan ke dalam antarmuka:

### Contoh Perintah `/gamerule` Populer

```bash
# Menonaktifkan kerusakan blok oleh monster (ledakan creeper)
/gamerule mobGriefing false

# Mempertahankan inventaris saat pemain mati
/gamerule keepInventory true

# Kecepatan kutu acak (pertumbuhan tanaman)
/gamerule randomTickSpeed 10

# Menghentikan siklus siang dan malam
/gamerule doDaylightCycle false
```

Saat membuka kembali layar aturan permainan, seluruh nilai yang diubah melalui perintah akan otomatis tampil di dalam kategori lipat masing-masing.

---

## 🔗 Dokumentasi Terkait

* [[🏠 Ringkasan & Beranda Utama|id_id-Home]]
* [[📜 Tabel Referensi GameRules|id_id-GameRules-Reference]]
* [[🎛️ Preset Aturan Permainan & Kontrol|id_id-Game-Rule-Presets-and-Controls]]
