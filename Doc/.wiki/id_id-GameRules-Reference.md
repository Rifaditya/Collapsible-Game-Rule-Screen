# 📜 Tabel Referensi GameRules

| Parameter | Spesifikasi |
| :--- | :--- |
| **Lingkungan Runtime** | **Klien Saja** (`"environment": "client"`) |
| **Aturan Game Server Mod** | `0` (Murni antarmuka pengguna grafis) |
| **Cakupan Kategori Vanilla** | `100%` (`Player`, `Mobs`, `Spawning`, `Drops`, `Updates`, `Chat`, `Misc`) |
| **Dukungan Kategori Mod** | Dinamis via `DasikMetadataHelper` & `CategoryPrettifier` |
| **Format Persistensi** | Berkas klien `config/collapsible-game-rules-state.json` |

---

## 📖 Batasan Cakupan Antarmuka

> [!NOTE]
> **Mod Khusus Antarmuka Pengguna Klien**: Collapsible Game Rules adalah mod visual. Mod ini **tidak menambahkan** aturan permainan di sisi server, tidak mengubah logika komputasi tick dunia, dan tidak mempengaruhi mekanisme bawaan game. Seluruh aturan yang ditampilkan berasal dari Vanilla atau mod lain.

---

## 🗂️ Kategori & Aturan Permainan Standar Vanilla

Saat layar dibuka, aturan bawaan Minecraft 26.2 secara otomatis dikelompokkan ke dalam kategori-kategori berikut:

| Kategori | Contoh Aturan Permainan | Penyesuaian Umum |
| :--- | :--- | :--- |
| **👤 Pemain (Player)** | `keepInventory`<br>`naturalRegeneration`<br>`playersSleepingPercentage`<br>`fallDamage`<br>`drowningDamage`<br>`fireDamage`<br>`freezeDamage` | Mempertahankan tas saat mati, persentase tidur untuk melewati malam, menyalakan/mematikan tipe luka tertentu. |
| **⚔️ Monster (Mobs)** | `mobGriefing`<br>`doMobSpawning`<br>`doMobLoot`<br>`universalAnger`<br>`forgiveDeadPlayers` | Mencegah kerusakan ledakan creeper, mengatur drop barang dari monster, mengendalikan amarah monster. |
| **🌱 Pemunculan (Spawning)** | `doMobSpawning`<br>`doPatrolSpawning`<br>`doTraderSpawning`<br>`doWardenSpawning`<br>`spawnRadius` | Kemunculan monster, pedagang keliling, patroli penjarah, serta jarak radius titik pemunculan awal. |
| **📦 Jarahan (Drops)** | `doEntityDrops`<br>`doTileDrops`<br>`doMobLoot`<br>`globalSoundEvents` | Hasil hancuran blok dan entitas yang dikalahkan, serta siaran suara global. |
| **🌧️ Pembaruan (Updates)** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doFireTick`<br>`randomTickSpeed`<br>`maxCommandChainLength` | Menghentikan siklus matahari atau cuaca, mematikan penyebaran api, atau mengatur laju tanaman. |
| **💬 Obrolan (Chat)** | `showDeathMessages`<br>`sendCommandFeedback`<br>`logAdminCommands`<br>`reducedDebugInfo` | Pesan kematian di obrolan, laporan perintah admin, atau menyembunyikan koordinat di F3. |
| **⚙️ Lain-lain (Misc)** | `commandBlockOutput`<br>`maxEntityCramming`<br>`disableElytraMovementCheck` | Batas penumpukan entitas di satu blok, pesan blok perintah, dan toleransi kecepatan sayap elytra. |

---

## 🔗 Dokumentasi Terkait

* [[🏠 Ringkasan & Beranda Utama|id_id-Home]]
* [[🗂️ Kategori yang Dapat Dilipat|id_id-Collapsible-Categories]]
* [[🎛️ Preset Aturan Permainan & Kontrol|id_id-Game-Rule-Presets-and-Controls]]
* [[📚 Integrasi API DasikLibrary|id_id-API-and-Library-Integration]]
