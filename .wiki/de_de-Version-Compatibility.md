# 🧭 Versionskompatibilitätsmatrix

| Parameter | Spezifikation |
| :--- | :--- |
| **Aktive Zielversion** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Mod-Version** | `1.0.9+26.2` |
| **Unterstützte Spielversionen** | Minecraft 26.2+ (Moderne Ära) |
| **Java-Anforderung** | **Java 25+** (`release = 25`, toolchain Java 25) |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` (`*`) |
| **Kern-Abhängigkeit** | **DasikLibrary** `>=1.7.0` (Aktiv: `1.7.4`) |
| **Build-Werkzeuge** | Loom `1.15.2` / Gradle `9.3+` (`--no-daemon`) |
| **Umgebung** | **Nur Client** (`"environment": "client"`) |
| **Lizenz** | **GPL-3.0-or-later** |

---

> 📌 **Repository-Quellcode-Hinweis**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der neuere unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Builds auf CurseForge und Modrinth enthalten kann.

---

## 📊 Mehr-Epochen-Laufzeitmatrix

Collapsible Game Rules wurde für die moderne Minecraft-Ära (`MC 26.2+`) entwickelt und nutzt die moderne Rendering-Pipeline `GuiGraphicsExtractor`, die Java 25 Stream-APIs (`.toList()`) und eine unverschleierte Laufzeitumgebung.

```
┌─────────────────────────────────────────────────────────────────────────────┐
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

## 🔒 Versionswächter & ClassLoader-Schutz

Um Spielstände vor Beschädigungen zu bewahren und Absturzschleifen bei inkompatiblen Versionen zu verhindern, besitzt die Mod den Wächter `net.instantgratification.collapsiblegamerules.util.ModVersionGuard`.

### Prüfmechanismus
Während `ModInitializer.onInitialize()` wird ausgeführt:
```java
ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
```

Sollte die erforderliche Klasse oder Knot-ClassLoader-Bindung fehlen, bricht die Mod den Ladevorgang mit einer strukturierten Warnung ab:

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

## 📦 Deklaration der Abhängigkeiten

In `src/main/resources/fabric.mod.json`:

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

## 🗄️ Versionsarchiv & Build-Artefakte

Offizielle Builds werden im Ordner `Archive Jar of all versions/` archiviert:

* `collapsible-game-rules-1.0.9+26.2.jar` (Aktueller Release)
* `collapsible-game-rules-1.0.8+26.2.jar` (Vorheriger Build)
* `collapsible-game-rules-1.0.7+26.2.jar` (Funktionsüberarbeitung)

---

## 🔗 Weiterführende Dokumentation

* [[🏠 Übersicht & Startseite|de_de-Home]]
* [[🛠️ Entwickler-Setup & Gradle-Builds|de_de-Developer-Setup-and-Building]]
* [[📚 DasikLibrary API-Integration|de_de-API-and-Library-Integration]]
