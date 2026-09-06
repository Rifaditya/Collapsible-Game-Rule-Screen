# 🧭 Matrice de Compatibilité des Versions

| Paramètre | Spécification |
| :--- | :--- |
| **Version Cible Active** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Version du Mod** | `1.0.9+26.2` |
| **Versions du Jeu Supportées**| Minecraft 26.2+ (Ère Souveraine Moderne) |
| **Prérequis Java** | **Java 25+** (`release = 25`, toolchain Java 25) |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` (`*`) |
| **Dépendance Centrale** | **DasikLibrary** `>=1.7.0` (Active : `1.7.4`) |
| **Outils de Build** | Loom `1.15.2` / Gradle `9.3+` (`--no-daemon`) |
| **Environnement** | **Client Uniquement** (`"environment": "client"`) |
| **Licence** | **GPL-3.0-or-later** |

---

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

---

## 📊 Matrice Multi-Ères du Cycle de Vie

Collapsible Game Rules est développé pour l'ère moderne (`MC 26.2+`), exploitant le pipeline de rendu moderne `GuiGraphicsExtractor`, l'API Stream de Java 25 (`.toList()`) et des environnements d'exécution sans obfusquation.

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

## 🔒 Garde de Version et Protection de ClassLoader

Pour protéger les sauvegardes et éviter les boucles de plantage lors de mises à jour incompatibles, le mod inclut la classe `net.instantgratification.collapsiblegamerules.util.ModVersionGuard`.

### Mécanisme de Vérification
Dans `ModInitializer.onInitialize()` :
```java
ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
```

Si le mod démarre sur une version incompatible sans la classe demandée ou sans les liaisons de Knot ClassLoader, il interrompt son chargement avec une alerte structurée :

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

## 📦 Déclaration des Dépendances

Dans `src/main/resources/fabric.mod.json` :

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

## 🗄️ Archives des Versions

Les versions officielles publiées sont archivées dans le dossier `Archive Jar of all versions/` :

* `collapsible-game-rules-1.0.9+26.2.jar` (Version Active)
* `collapsible-game-rules-1.0.8+26.2.jar` (Build Précédent)
* `collapsible-game-rules-1.0.7+26.2.jar` (Révision des Fonctionnalités)

---

## 🔗 Documentation Associée

* [[🏠 Aperçu & Accueil|fr_fr-Home]]
* [[🛠️ Environnement Développeur & Builds Gradle|fr_fr-Developer-Setup-and-Building]]
* [[📚 Intégration de l'API DasikLibrary|fr_fr-API-and-Library-Integration]]
