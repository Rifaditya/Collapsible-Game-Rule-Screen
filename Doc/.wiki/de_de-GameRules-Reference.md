# 📜 Spielregeln-Referenztabelle

| Parameter | Spezifikation |
| :--- | :--- |
| **Ausführungsumgebung** | **Nur Client** (`"environment": "client"`) |
| **Eigene Server-Spielregeln** | `0` (Reiner Umfang der UI-Neustrukturierung) |
| **Abdeckung der Vanilla-Kategorien**| `100%` (`Player`, `Mobs`, `Spawning`, `Drops`, `Updates`, `Chat`, `Misc`) |
| **Unterstützung für Mod-Kategorien**| Dynamisch über `DasikMetadataHelper` und `CategoryPrettifier` |
| **Speichermechanismus** | Clientseitige `config/collapsible-game-rules-state.json` |

---

## 📖 Richtlinie & Umfang

> [!NOTE]
> **Reine Client-UI-Mod**: Collapsible Game Rules ist ausschließlich ein Menüorganisator. Sie fügt **keine** serverseitigen Spielregeln hinzu, modifiziert keine Tick-Berechnungen und ändert keine Spiellogik. Alle angezeigten Regeln stammen aus Vanilla oder anderen installierten Mods.

---

## 🗂️ Standard-Vanilla-Kategorien und Regeln

Beim Öffnen des Menüs sortiert Collapsible Game Rules alle Regeln aus Minecraft 26.2 in folgende Ordner:

| Kategorie | Enthaltene Regeln | Häufige Anpassungen |
| :--- | :--- | :--- |
| **👤 Spieler (Player)** | `keepInventory`<br>`naturalRegeneration`<br>`playersSleepingPercentage`<br>`fallDamage`<br>`drowningDamage`<br>`fireDamage`<br>`freezeDamage` | Inventar beim Tod behalten, Schlaf-Prozentsatz zum Überspringen der Nacht, Schadensarten abstellen. |
| **⚔️ Mobs (Mobs)** | `mobGriefing`<br>`doMobSpawning`<br>`doMobLoot`<br>`universalAnger`<br>`forgiveDeadPlayers` | Blockschäden durch Creeper verhindern, Mob-Beute steuern, Dauer von Zorn einstellen. |
| **🌱 Spawning (Spawning)** | `doMobSpawning`<br>`doPatrolSpawning`<br>`doTraderSpawning`<br>`doWardenSpawning`<br>`spawnRadius` | Häufigkeit von Patrouillen und Händlern steuern, Spawn-Radius bei Welterstellung festlegen. |
| **📦 Beute (Drops)** | `doEntityDrops`<br>`doTileDrops`<br>`doMobLoot`<br>`globalSoundEvents` | Beutedrops von abgebauten Blöcken und erlegten Kreaturen, globale Sounds. |
| **🌧️ Updates (Updates)** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doFireTick`<br>`randomTickSpeed`<br>`maxCommandChainLength` | Tag- und Wetterzyklen anhalten, Feuerausbreitung stoppen, Wachstumsrate anpassen. |
| **💬 Chat (Chat)** | `showDeathMessages`<br>`sendCommandFeedback`<br>`logAdminCommands`<br>`reducedDebugInfo` | Todesnachrichten im Chat, Befehls-Feedback oder Koordinatenanzeige auf F3 anpassen. |
| **⚙️ Sonstiges (Misc)** | `commandBlockOutput`<br>`maxEntityCramming`<br>`disableElytraMovementCheck` | Schadensgrenze bei Mob-Gedränge einstellen oder Ausgaben von Befehlsblöcken verwalten. |

---

## 🧩 Dynamische Mod-Kategorien-Kompatibilität

Jede von Drittanbieter-Mods registrierte Regelkategorie (oder über `DynamicGameRuleManager` der `DasikLibrary` generierte Kategorie) wird automatisch erkannt, in einem einklappbaren Ordner platziert und in Echtzeit formatiert.

---

## 🔗 Weiterführende Dokumentation

* [[🏠 Übersicht & Startseite|de_de-Home]]
* [[🗂️ Einklappbare Kategorien|de_de-Collapsible-Categories]]
* [[🎛️ Spielregel-Voreinstellungen & Steuerelemente|de_de-Game-Rule-Presets-and-Controls]]
* [[📚 DasikLibrary API-Integration|de_de-API-and-Library-Integration]]
