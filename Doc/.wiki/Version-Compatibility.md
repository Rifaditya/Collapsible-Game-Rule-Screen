# 🧭 Version Compatibility Matrix

| Parameter | Specification |
| :--- | :--- |
| **Active Version Anchor** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Mod Version** | `1.0.9+26.2` |
| **Supported Game Releases** | Minecraft 26.2+ (Modern Sovereign Era) |
| **Java Requirement** | **Java 25+** (`release = 25`, toolchain Java 25) |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` (`*`) |
| **Core Dependency** | **DasikLibrary** `>=1.7.0` (Active: `1.7.4`) |
| **Build Tooling** | Loom `1.15.2` / Gradle `9.3+` (`--no-daemon`) |
| **Environment** | **Client-Side Only** (`"environment": "client"`) |
| **License** | **GPL-3.0-or-later** |

---

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## 📊 Multi-Era Architecture & Lifecycle Matrix

Collapsible Game Rules is built natively for the **Modern Sovereign Era** (`MC 26.2+`), taking full advantage of modern UI rendering pipelines (`GuiGraphicsExtractor`), modern Java 25 stream APIs (`.toList()`), and zero-obfuscation runtime environments.

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

## 🔒 Mod Version Guard & ClassLoader Protection

To protect player worlds from accidental corruption and prevent crash loops on unsupported engine drops, Collapsible Game Rules includes a zero-dependency startup guard located in `net.instantgratification.collapsiblegamerules.util.ModVersionGuard`.

### Guard Verification Mechanism
During `ModInitializer.onInitialize()`, the mod executes:
```java
ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
```

If launched on an incompatible Minecraft version lacking the specified class or missing the Knot ClassLoader bindings, the mod halts execution with a structured warning banner:

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

## 📦 Dependency Declaration Breakdown

The mod declares its exact dependencies in `src/main/resources/fabric.mod.json`:

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

### Dependency Analysis Table

| Dependency ID | Version Constraint | Type | Rationale & Enforcement |
| :--- | :--- | :--- | :--- |
| `minecraft` | `>=26.2-` | Game Core | Open-ended lower bound for 26.2+ Annual Drop readiness. |
| `fabricloader` | `>=0.19.1` | Mod Loader | Native Java 25 Mixin subsystem support and Knot loader resolution. |
| `java` | `>=25` | Toolchain | Required for modern record patterns, sealed interfaces, and memory efficiency. |
| `fabric-api` | `*` | Hook Layer | Standard Fabric lifecycle events and client rendering hooks. |
| `dasik-library`| `>=1.7.0` | Shared Core | Hard requirement for dynamic category metadata and social AI parity. |

---

## 🗄️ Release Archives & Build Artifacts

Official builds are archived in the repository's `Archive Jar of all versions/` directory:

* `collapsible-game-rules-1.0.9+26.2.jar` (Active Release)
* `collapsible-game-rules-1.0.8+26.2.jar` (Previous Build)
* `collapsible-game-rules-1.0.7+26.2.jar` (Feature Overhaul)

---

## 🔗 Related Documentation

* [[Master Wiki Portal|Home]]
* [[MC 26.2 Version Portal|26.2-Home]]
* [[Developer Setup & Gradle Builds|26.2-Developer-Setup-and-Building]]
* [[DasikLibrary API Integration|26.2-API-and-Library-Integration]]
