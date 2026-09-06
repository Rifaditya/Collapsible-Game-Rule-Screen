# ✨ Embellecimiento y Nombres de Categorías

| Parámetro | Especificación |
| :--- | :--- |
| **Clase de Utilidad** | `net.instantgratification.collapsiblegamerules.util.CategoryPrettifier` |
| **Fachada de Metadatos** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **Condición de Formato** | `!Language.getInstance().has(key)` |
| **Eliminación de Prefijos**| Quita `"gamerule.category."` |
| **Separadores Tratados** | Punto `.` (espacio de nombres) y expresión regular `[_-]` (palabras) |
| **Proveedor Dinámico** | `DynamicGameRuleManager.getGeneratedTranslations()` |

---

## 📖 Visión General

A menudo, los mods de terceros registran categorías con claves internas sin traducir (como `gamerule.category.better-bats.better_bats` o `gamerule.category.item_clumps`). En Minecraft Vanilla, esto causa que aparezcan textos crudos y poco legibles en pantalla.

**Category Prettification** limpia, formatea y transforma dinámicamente estas claves sin traducir en títulos limpios y legibles en formato Title Case.

---

## ⚙️ Flujo del Algoritmo de Formateo

`CategoryPrettifier.prettifyCategoryKey(String key)` ejecuta los siguientes pasos:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       CATEGORY PRETTIFICATION PIPELINE                      │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Input Key: "gamerule.category.better-bats.better_bats"                    │
│        │                                                                    │
│        ▼ [Step 1: Prefix Stripping]                                         │
│   Strip "gamerule.category." ──> "better-bats.better_bats"                  │
│        │                                                                    │
│        ▼ [Step 2: Namespace & Path Separation]                              │
│   Separate namespace "better-bats" and path "better_bats"                   │
│        │                                                                    │
│        ▼ [Step 3: Redundancy Normalization]                                 │
│   Compare normalized strings: "betterbats" == "betterbats"                  │
│   Deduplicate to single segment: "better_bats"                              │
│        │                                                                    │
│        ▼ [Step 4: Delimiter Splitting & Capitalization]                     │
│   Split by "[_-]" ──> ["better", "bats"]                                    │
│   Capitalize words ──> ["Better", "Bats"]                                   │
│        │                                                                    │
│        ▼ [Step 5: String Join]                                              │
│   Output Display Title: "Better Bats"                                       │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📊 Tabla de Ejemplos de Transformación

| Clave Original de la Regla | Etiqueta Mostrada | Notas de Transformación |
| :--- | :--- | :--- |
| `gamerule.category.better-bats.better_bats` | **Better Bats** | Desduplica el espacio de nombres y la ruta repetidos. |
| `gamerule.category.minecraft.spawning` | **Spawning** | Descarta el prefijo predeterminado de `minecraft`. |
| `gamerule.category.social_mobs.wolf_pack` | **Social Mobs Wolf Pack** | Combina partes diferenciadas del espacio de nombres y la ruta. |
| `gamerule.category.custom_rules` | **Custom Rules** | Sustituye guiones bajos por espacios y pone mayúsculas iniciales. |
| `gamerule.category.instant-gratification.ore-multiplier` | **Instant Gratification Ore Multiplier** | Divide guiones y pone en mayúscula cada término. |

---

## 💻 Implementación en Código Fuente

```java
public static String prettifyCategoryKey(String key) {
    if (key == null) {
        return "";
    }
    String name = key;
    if (name.startsWith("gamerule.category.")) {
        name = name.substring("gamerule.category.".length());
    }

    // Split namespace and path if dot is present
    int dotIndex = name.indexOf('.');
    if (dotIndex != -1) {
        String ns = name.substring(0, dotIndex);
        String path = name.substring(dotIndex + 1);
        
        // If the namespace is "minecraft", just drop it
        if (ns.equals("minecraft")) {
            name = path;
        } else {
            // Normalize for comparison
            String normNs = ns.replace("-", "").replace("_", "").toLowerCase(Locale.ROOT);
            String normPath = path.replace("-", "").replace("_", "").toLowerCase(Locale.ROOT);
            if (normPath.contains(normNs) || normNs.contains(normPath)) {
                name = path; // Use the path part since it's more specific or includes namespace
            } else {
                name = ns + " " + path;
            }
        }
    }

    // Split by underscore or dash
    String[] parts = name.split("[_-]");
    List<String> words = new ArrayList<>();
    for (String part : parts) {
        if (part.isEmpty()) {
            continue;
        }
        String capitalized = part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1);
        words.add(capitalized);
    }
    return String.join(" ", words);
}
```

---

## 🔗 Documentación Relacionada

* [[🏠 Resumen y Portal Principal|es_es-Home]]
* [[🗂️ Categorías Desplegables|es_es-Collapsible-Categories]]
* [[📚 Integración con la API de DasikLibrary|es_es-API-and-Library-Integration]]
