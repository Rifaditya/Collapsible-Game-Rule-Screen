# 📚 Integración con la API de DasikLibrary

| Parámetro | Especificación |
| :--- | :--- |
| **Dependencia Principal** | `net.dasik.social:dasik-library` |
| **Límite de Versión** | `dasik-library: >=1.7.0` (Compilación: `1.7.4`) |
| **Clase Aisladora** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **Método de API Consultado** | `DynamicGameRuleManager.getGeneratedTranslations()` |
| **Comprobación en Inicio** | Verificación estricta en `CollapsibleGameRulesFabric.onInitialize()` |

---

## 📖 Visión General

Collapsible Game Rules se conecta directamente con **DasikLibrary** para obtener traducciones y metadatos dinámicos de categorías en mods modernos.

Para mantener la estabilidad de la JVM, todas las referencias a clases de `DasikLibrary` se encuentran aisladas en una clase auxiliar de carga diferida (`DasikMetadataHelper`).

---

## 🔒 Arquitectura de Aislamiento de Carga de Clases

En Java, hacer referencia directa a una clase foránea dentro de una clase activa (como un Mixin) desencadenaría la carga inmediata de dicha biblioteca. Si no estuviera instalada, la JVM arrojaría un error fatal `NoClassDefFoundError`.

Al canalizar las llamadas de `DynamicGameRuleManager` mediante `DasikMetadataHelper`, la JVM sólo intentará cargar las clases de `DasikLibrary` cuando sea seguro:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      CLASSLOADING ISOLATION PATTERN                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   AbstractGameRulesScreenRuleListMixin                                      │
│        │                                                                    │
│        ▼ (Checks FabricLoader.isModLoaded("dasik-library"))                 │
│   ┌─────────────────────────────────────────────────────────────────────┐   │
│   │ if (FabricLoader.getInstance().isModLoaded("dasik-library")) {      │   │
│   │     categoryKey = DasikMetadataHelper.getCategoryTranslation(...);  │   │
│   │ }                                                                   │   │
│   └─────────────────────────────────────────────────────────────────────┘   │
│        │                                                                    │
│        ▼ (Only loads DasikMetadataHelper when confirmed safe)               │
│   DasikMetadataHelper ──> net.dasik.social.api.gamerule.DynamicGameRuleManager│
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 💻 Implementación de Consulta de Metadatos

```java
public final class DasikMetadataHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger("collapsible-game-rules");

    private DasikMetadataHelper() {}

    /**
     * Retrieves the localized name for a category from DasikLibrary metadata.
     * Enforced Hard Dependency: This method makes direct calls to DasikLibrary.
     */
    public static String getCategoryTranslation(String categoryLabel) {
        Map<String, String> translations =
                DynamicGameRuleManager.getGeneratedTranslations();

        return translations.getOrDefault(
                "gamerule.category." + categoryLabel.toLowerCase(Locale.ROOT),
                categoryLabel
        );
    }
}
```

---

## 🛡️ Verificación Obligatoria de Dependencias

```java
public class CollapsibleGameRulesFabric implements ModInitializer {
    public static final String MOD_ID = "collapsible-game-rules";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
        LOGGER.info("Initializing Collapsible Game Rules [Core Align 2.1]");

        // Hard Dependency Enforcement
        if (!FabricLoader.getInstance().isModLoaded("dasik-library")) {
            throw new RuntimeException("Collapsible Game Rules requires DasikLibrary to function. Please install it.");
        }
    }
}
```

---

## 🔗 Documentación Relacionada

* [[🏠 Resumen y Portal Principal|es_es-Home]]
* [[🧭 Matriz de Compatibilidad de Versiones|es_es-Version-Compatibility]]
* [[✨ Embellecimiento y Nombres de Categorías|es_es-Category-Prettification-and-Naming]]
* [[🧩 Arquitectura y Subsistema Mixin|es_es-Architecture-and-Mixins]]
