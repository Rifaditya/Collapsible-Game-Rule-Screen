# 📚 Integrasi API DasikLibrary

| Parameter | Spesifikasi |
| :--- | :--- |
| **Dependensi Inti** | `net.dasik.social:dasik-library` |
| **Batasan Versi** | `dasik-library: >=1.7.0` (Aktif: `1.7.4`) |
| **Lapisan Isolasi** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **Metode API** | `DynamicGameRuleManager.getGeneratedTranslations()` |
| **Pemeriksaan Awal** | Validasi wajib di `CollapsibleGameRulesFabric.onInitialize()` |

---

## 📖 Gambaran Umum

Collapsible Game Rules bekerja sama secara erat dengan **DasikLibrary** untuk mendapatkan nama terjemahan dan metadata aturan permainan yang didaftarkan secara dinamis oleh berbagai mod modern.

---

## 🔒 Pola Isolasi ClassLoading

Untuk mencegah galat `NoClassDefFoundError` pada saat JVM memuat kelas awal, pemanggilan ke `DasikLibrary` dipisahkan secara aman di dalam `DasikMetadataHelper`.

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      CLASSLOADING ISOLATION PATTERN                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   AbstractGameRulesScreenRuleListMixin                                      │
│        │                                                                    │
│        ▼ (Memeriksa FabricLoader.isModLoaded("dasik-library"))              │
│   ┌─────────────────────────────────────────────────────────────────────┐   │
│   │ if (FabricLoader.getInstance().isModLoaded("dasik-library")) {      │   │
│   │     categoryKey = DasikMetadataHelper.getCategoryTranslation(...);  │   │
│   │ }                                                                   │   │
│   └─────────────────────────────────────────────────────────────────────┘   │
│        │                                                                    │
│        ▼ (Hanya dimuat ketika pustaka terkonfirmasi aktif)                  │
│   DasikMetadataHelper ──> net.dasik.social.api.gamerule.DynamicGameRuleManager│
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 🛡️ Pemeriksaan Wajib Saat Startup

```java
public class CollapsibleGameRulesFabric implements ModInitializer {
    public static final String MOD_ID = "collapsible-game-rules";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
        LOGGER.info("Initializing Collapsible Game Rules [Core Align 2.1]");

        // Validasi dependensi wajib
        if (!FabricLoader.getInstance().isModLoaded("dasik-library")) {
            throw new RuntimeException("Collapsible Game Rules requires DasikLibrary to function. Please install it.");
        }
    }
}
```

---

## 🔗 Dokumentasi Terkait

* [[🏠 Ringkasan & Beranda Utama|id_id-Home]]
* [[🧭 Matriks Kompatibilitas Versi|id_id-Version-Compatibility]]
* [[✨ Perapian & Penamaan Kategori|id_id-Category-Prettification-and-Naming]]
* [[🧩 Arsitektur & Subsistem Mixin|id_id-Architecture-and-Mixins]]
