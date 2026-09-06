# 🧭 Matriks Kompatibilitas Versi

| Parameter | Spesifikasi |
| :--- | :--- |
| **Target Aktif Utama** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Versi Mod** | `1.0.9+26.2` |
| **Versi Game yang Didukung** | Minecraft 26.2+ (Era Sovereign Modern) |
| **Persyaratan Java** | **Java 25+** (`release = 25`, toolchain Java 25) |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` (`*`) |
| **Dependensi Inti** | **DasikLibrary** `>=1.7.0` (Aktif: `1.7.4`) |
| **Alat Kompilasi** | Loom `1.15.2` / Gradle `9.3+` (`--no-daemon`) |
| **Lingkungan Kerja** | **Klien Saja** (`"environment": "client"`) |
| **Lisensi** | **GPL-3.0-or-later** |

---

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam pengembangan sebelum rilis publik di CurseForge dan Modrinth.

---

## 📊 Matriks Siklus Hidup Lintas Era

Collapsible Game Rules dirancang secara khusus untuk era modern (`MC 26.2+`), memanfaatkan pipeline rendering grafis `GuiGraphicsExtractor`, Stream API Java 25 (`.toList()`), serta lingkungan runtime tanpa obfuskasi.

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       MULTI-ERA RUNTIME LIFECYCLE MATRIX                    │
├───────────────────┬─────────────┬──────────────┬───────────────┬────────────┤
│ Minecraft Anchor  │ Java Level  │ Loader Bound │ DasikLibrary  │ Status     │
├───────────────────┼─────────────┼──────────────┼───────────────┼────────────┤
│ **MC 26.2+**      │ **Java 25** │ `>=0.19.1`   │ `>=1.7.0`     │ 🟢 Aktif   │
│ MC 26.1.2         │ Java 25     │ `>=0.18.4`   │ `>=1.7.0`     │ 🟡 Kompat. │
│ MC 1.21.x         │ Java 21     │ N/A          │ N/A           │ ⚪ Tidak   │
│ MC 1.20.1         │ Java 17     │ N/A          │ N/A           │ ⚪ Tidak   │
└───────────────────┴─────────────┴──────────────┴───────────────┴────────────┘
```

---

## 🔒 Penjaga Versi & Perlindungan ClassLoader

Untuk mencegah kerusakan data dunia saat dijalankan pada runtime yang salah, mod menyertakan pengaman `net.instantgratification.collapsiblegamerules.util.ModVersionGuard`.

### Mekanisme Pemeriksaan
Dijalankan pada `ModInitializer.onInitialize()`:
```java
ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
```

---

## 📦 Deklarasi Dependensi

Dalam `src/main/resources/fabric.mod.json`:

```json
{
  "schemaVersion": 1,
  "id": "collapsible-game-rules",
  "version": "${version}",
  "name": "Collapsible Game Rules",
  "description": "Makes the GameRules UI screens collapsible by category.",
  "authors": [
    "Dasik (Rifaditya)"
  ],
  "license": "GPL-3.0-or-later",
  "environment": "client",
  "entrypoints": {
    "main": [
      "net.instantgratification.collapsiblegamerules.CollapsibleGameRulesFabric"
    ],
    "client": [
      "net.instantgratification.collapsiblegamerules.CollapsibleGameRulesFabricClient"
    ]
  },
  "mixins": [
    "collapsible-game-rules.mixins.json"
  ],
  "depends": {
    "fabricloader": ">=0.19.1",
    "minecraft": ">=26.2-",
    "java": ">=25",
    "fabric-api": "*",
    "dasik-library": ">=1.7.0"
  }
}
```

---

## 🔗 Dokumentasi Terkait

* [[🏠 Ringkasan & Beranda Utama|id_id-Home]]
* [[🛠️ Penyiapan Pengembang & Build Gradle|id_id-Developer-Setup-and-Building]]
* [[📚 Integrasi API DasikLibrary|id_id-API-and-Library-Integration]]
