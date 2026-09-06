# 📜 Tableau de Référence des Règles de Jeu

| Paramètre | Spécification |
| :--- | :--- |
| **Environnement d'Exécution** | **Client Uniquement** (`"environment": "client"`) |
| **Règles Serveur du Mod** | `0` (Périmètre exclusif d'interface utilisateur) |
| **Couverture des Catégories Vanilla**| `100%` (`Player`, `Mobs`, `Spawning`, `Drops`, `Updates`, `Chat`, `Misc`) |
| **Prise en Charge des Mods** | Dynamique via `DasikMetadataHelper` et `CategoryPrettifier` |
| **Mécanisme de Sauvegarde** | Fichier client `config/collapsible-game-rules-state.json` |

---

## 📖 Mandato de Portée de l'Interface

> [!NOTE]
> **Mod d'Interface Purement Client** : Collapsible Game Rules est un outil de réorganisation visuelle. Il **n'ajoute aucune** règle de jeu côté serveur, ne modifie pas le calcul des ticks et n'altère pas la logique du jeu. Toutes les options affichées proviennent du jeu Vanilla ou d'autres mods.

---

## 🗂️ Catégories et Règles Vanilla Standard

À l'ouverture du menu, Collapsible Game Rules regroupe automatiquement les règles de Minecraft 26.2 dans les dossiers suivants :

| Catégorie | Règles Incluses | Ajustements Fréquents |
| :--- | :--- | :--- |
| **👤 Joueur (Player)** | `keepInventory`<br>`naturalRegeneration`<br>`playersSleepingPercentage`<br>`fallDamage`<br>`drowningDamage`<br>`fireDamage`<br>`freezeDamage` | Conserver l'inventaire à la mort, pourcentage pour passer la nuit en dormant, couper des types de dégâts. |
| **⚔️ Monstres (Mobs)** | `mobGriefing`<br>`doMobSpawning`<br>`doMobLoot`<br>`universalAnger`<br>`forgiveDeadPlayers` | Bloquer les dégâts de creepers, gérer le butin des créatures ou ajuster la colère. |
| **🌱 Apparition (Spawning)** | `doMobSpawning`<br>`doPatrolSpawning`<br>`doTraderSpawning`<br>`doWardenSpawning`<br>`spawnRadius` | Fréquence des patrouilles et marchands ambulants, rayon du point de réapparition initial. |
| **📦 Butin (Drops)** | `doEntityDrops`<br>`doTileDrops`<br>`doMobLoot`<br>`globalSoundEvents` | Objets lâchés par les blocs détruits et entités éliminées, sons globaux. |
| **🌧️ Mises à Jour (Updates)** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doFireTick`<br>`randomTickSpeed`<br>`maxCommandChainLength` | Figer la météo ou le cycle jour/nuit, stopper le feu ou ajuster la vitesse des cultures. |
| **💬 Tchat (Chat)** | `showDeathMessages`<br>`sendCommandFeedback`<br>`logAdminCommands`<br>`reducedDebugInfo` | Messages de mort, retours de commandes ou masquer les coordonnées sur l'écran F3. |
| **⚙️ Divers (Misc)** | `commandBlockOutput`<br>`maxEntityCramming`<br>`disableElytraMovementCheck` | Limite d'entassement d'entités ou messages des blocs de commande. |

---

## 🧩 Compatibilité avec les Catégories de Mods

Toute catégorie ajoutée par un mod tiers (ou générée par `DynamicGameRuleManager` de `DasikLibrary`) est reconnue automatiquement, placée dans un dossier repliable et mise en forme instantanément.

---

## 🔗 Documentation Associée

* [[🏠 Aperçu & Accueil|fr_fr-Home]]
* [[🗂️ Catégories Repliables|fr_fr-Collapsible-Categories]]
* [[🎛️ Préréglages de Règles de Jeu & Contrôles|fr_fr-Game-Rule-Presets-and-Controls]]
* [[📚 Intégration de l'API DasikLibrary|fr_fr-API-and-Library-Integration]]
