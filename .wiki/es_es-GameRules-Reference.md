# 📜 Tabla de Referencia de GameRules

| Parámetro | Especificación |
| :--- | :--- |
| **Entorno de Ejecución** | **Sólo Cliente** (`"environment": "client"`) |
| **Reglas de Servidor Propias** | `0` (Alcance exclusivo de interfaz de usuario) |
| **Cobertura de Categorías Vanilla** | `100%` (`Player`, `Mobs`, `Spawning`, `Drops`, `Updates`, `Chat`, `Misc`) |
| **Soporte para Categorías de Mods** | Dinámico mediante `DasikMetadataHelper` y `CategoryPrettifier` |
| **Mecanismo de Persistencia** | Archivo local `config/collapsible-game-rules-state.json` |

---

## 📖 Mandato de Alcance de la Interfaz

> [!NOTE]
> **Mod Exclusivo de Interfaz del Cliente**: Collapsible Game Rules es estrictamente un reorganizador visual. **No** añade reglas de juego del lado del servidor, no altera cálculos de ticks ni altera la lógica del juego. Todas las reglas mostradas proceden de Vanilla o de otros mods instalados.

---

## 🗂️ Categorías y Reglas Estándar de Vanilla

Al abrir la pantalla de reglas, Collapsible Game Rules organiza automáticamente todas las reglas de Minecraft 26.2 en las siguientes categorías:

| Categoría | Reglas Organizadas | Ajustes Comunes |
| :--- | :--- | :--- |
| **👤 Jugador (Player)** | `keepInventory`<br>`naturalRegeneration`<br>`playersSleepingPercentage`<br>`fallDamage`<br>`drowningDamage`<br>`fireDamage`<br>`freezeDamage` | Mantener inventario al morir, porcentaje para saltar la noche al dormir o desactivar tipos de daño. |
| **⚔️ Criaturas (Mobs)** | `mobGriefing`<br>`doMobSpawning`<br>`doMobLoot`<br>`universalAnger`<br>`forgiveDeadPlayers` | Evitar daño de bloques por creepers, controlar el botín de entidades o ajustar la ira. |
| **🌱 Generación (Spawning)** | `doMobSpawning`<br>`doPatrolSpawning`<br>`doTraderSpawning`<br>`doWardenSpawning`<br>`spawnRadius` | Frecuencia de patrullas o comerciantes errantes, y radio del punto de reaparición inicial. |
| **📦 Botín (Drops)** | `doEntityDrops`<br>`doTileDrops`<br>`doMobLoot`<br>`globalSoundEvents` | Caída de objetos de bloques rotos y entidades eliminadas, eventos de audio globales. |
| **🌧️ Actualizaciones (Updates)** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doFireTick`<br>`randomTickSpeed`<br>`maxCommandChainLength` | Detener ciclos climáticos o diurnos, propagación de fuego o velocidad de cultivos. |
| **💬 Chat (Chat)** | `showDeathMessages`<br>`sendCommandFeedback`<br>`logAdminCommands`<br>`reducedDebugInfo` | Mensajes de muerte en el chat, registros de comandos u ocultar coordenadas en F3. |
| **⚙️ Varios (Misc)** | `commandBlockOutput`<br>`maxEntityCramming`<br>`disableElytraMovementCheck` | Límite de amontonamiento de entidades o salida de bloques de comandos. |

---

## 🧩 Compatibilidad Dinámica con Mods

Cualquier categoría de reglas registrada por otros mods (o generada mediante `DynamicGameRuleManager` de `DasikLibrary`) es detectada de forma automática, estructurada en una carpeta desplegable y formateada en tiempo real.

---

## 🔗 Documentación Relacionada

* [[🏠 Resumen y Portal Principal|es_es-Home]]
* [[🗂️ Categorías Desplegables|es_es-Collapsible-Categories]]
* [[🎛️ Ajustes Preestablecidos y Controles de Game Rules|es_es-Game-Rule-Presets-and-Controls]]
* [[📚 Integración con la API de DasikLibrary|es_es-API-and-Library-Integration]]
