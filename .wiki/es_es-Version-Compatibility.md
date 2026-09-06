# 🧭 Matriz de Compatibilidad de Versiones

| Parámetro | Especificación |
| :--- | :--- |
| **Versión Objetivo Activa** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Versión del Mod** | `1.0.9+26.2` |
| **Lanzamientos Soportados** | Minecraft 26.2+ (Era Soberana Moderna) |
| **Requisito de Java** | **Java 25+** (`release = 25`, toolchain Java 25) |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` (`*`) |
| **Dependencia Principal** | **DasikLibrary** `>=1.7.0` (Activa: `1.7.4`) |
| **Herramientas de Compilación** | Loom `1.15.2` / Gradle `9.3+` (`--no-daemon`) |
| **Entorno** | **Sólo Cliente** (`"environment": "client"`) |
| **Licencia** | **GPL-3.0-or-later** |

---

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

---

## 📊 Matriz de Ciclo de Vida Multi-Era

Collapsible Game Rules está desarrollado nativamente para la era moderna (`MC 26.2+`), aprovechando el pipeline gráfico moderno `GuiGraphicsExtractor`, la API Stream de Java 25 (`.toList()`) y entornos limpios sin ofuscación.

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                           MULTI-ERA RUNTIME LIFECYCLE MATRIX                            │
├───────────────────┬─────────────┬──────────────┬───────────────┬────────────────────────┤
│ Minecraft Anchor  │ Java Level  │ Loader Bound │ DasikLibrary  │ Lifecycle Status       │
├───────────────────┼─────────────┼──────────────┼───────────────┼────────────────────────┤
│ **MC 26.2+**      │ **Java 25** │ `>=0.19.1`   │ `>=1.7.0`     │ 🟢 Active Target (Loom)│
│ MC 26.1.2         │ Java 25     │ `>=0.18.4`   │ `>=1.7.0`     │ 🟡 Forward Compatible  │
│ MC 1.21.x         │ Java 21     │ N/A          │ N/A           │ ⚪ Unsupported (Modern)│
│ MC 1.20.1         │ Java 17     │ N/A          │ N/A           │ ⚪ Unsupported (Modern)│
└───────────────────┴─────────────┴──────────────┴───────────────┴────────────────────────┘
```

---

## 🔒 Guardia de Versión y Protección de ClassLoader

Para proteger las partidas de posibles corrupciones y evitar bucles de fallos en versiones no compatibles, el mod incluye un guardián de inicio: `net.instantgratification.collapsiblegamerules.util.ModVersionGuard`.

### Mecanismo de Verificación
Durante `ModInitializer.onInitialize()`, se ejecuta:
```java
ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
```

Si se ejecuta en una versión incompatible sin dicha clase o sin enlaces de Knot ClassLoader, el mod detiene su carga con un aviso estructurado:

```
=====================================================================
 [PRE-RELEASE / VERSION GUARD WARNING] Collapsible Game Rules
---------------------------------------------------------------------
 CRITICAL: Incompatible Minecraft Game Runtime or Missing Class!
 Required Class : net.minecraft.world.level.gamerules.GameRules
 Status         : UNRESOLVED AT RUNTIME

 Safety Protection:
 Execution halted to prevent unreleased/incompatible build deployment
 or broken world state save corruption.

 Troubleshooting Steps:
 1. Verify target Minecraft version (26.2+ release drop).
 2. Ensure all required dependencies (Fabric API, DasikLibrary) are loaded.
 3. Build/Download a verified matching release JAR from Modrinth/CurseForge.
=====================================================================
```

---

## 📦 Declaración de Dependencias

En `src/main/resources/fabric.mod.json`:

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

## 🗄️ Archivos de Versiones Anteriores

Las versiones oficiales se encuentran archivadas en la carpeta `Archive Jar of all versions/`:

* `collapsible-game-rules-1.0.9+26.2.jar` (Versión Activa)
* `collapsible-game-rules-1.0.8+26.2.jar` (Compilación Previa)
* `collapsible-game-rules-1.0.7+26.2.jar` (Renovación de Funciones)

---

## 🔗 Documentación Relacionada

* [[🏠 Resumen y Portal Principal|es_es-Home]]
* [[🛠️ Configuración de Desarrollador y Compilación Gradle|es_es-Developer-Setup-and-Building]]
* [[📚 Integración con la API de DasikLibrary|es_es-API-and-Library-Integration]]
