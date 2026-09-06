# 📜 Tabela de Referência de Regras de Jogo

| Parâmetro | Especificação |
| :--- | :--- |
| **Ambiente de Execução** | **Apenas Cliente** (`"environment": "client"`) |
| **Regras de Servidor Próprias** | `0` (Escopo exclusivo de organização visual) |
| **Cobertura de Categorias Vanilla** | `100%` (`Player`, `Mobs`, `Spawning`, `Drops`, `Updates`, `Chat`, `Misc`) |
| **Suporte a Categorias de Mods** | Dinâmico via `DasikMetadataHelper` e `CategoryPrettifier` |
| **Mecanismo de Persistência** | Arquivo local `config/collapsible-game-rules-state.json` |

---

## 📖 Mandato de Escopo da Interface

> [!NOTE]
> **Mod Puramente de Interface no Cliente**: O Collapsible Game Rules atua estritamente na reorganização visual de menus. Ele **não** adiciona regras de servidor, não modifica cálculos de tiques e não afeta a lógica interna do jogo. Todas as opções exibidas vêm do jogo nativo ou de outros mods instalados.

---

## 🗂️ Categorias e Regras Vanilla Padrão

Ao abrir o menu de regras, o Collapsible Game Rules organiza todas as regras do Minecraft 26.2 nas seguintes categorias:

| Categoria | Regras Inclusas | Ajustes Populares |
| :--- | :--- | :--- |
| **👤 Jogador (Player)** | `keepInventory`<br>`naturalRegeneration`<br>`playersSleepingPercentage`<br>`fallDamage`<br>`drowningDamage`<br>`fireDamage`<br>`freezeDamage` | Manter itens ao morrer, porcentagem de sono para pular a noite ou desligar tipos de dano. |
| **⚔️ Monstros (Mobs)** | `mobGriefing`<br>`doMobSpawning`<br>`doMobLoot`<br>`universalAnger`<br>`forgiveDeadPlayers` | Evitar que creepers quebrem blocos, controlar itens deixados por criaturas e fúria. |
| **🌱 Geração (Spawning)** | `doMobSpawning`<br>`doPatrolSpawning`<br>`doTraderSpawning`<br>`doWardenSpawning`<br>`spawnRadius` | Ajustar frequência de patrulhas e vendedores ambulantes, raio inicial de renascimento. |
| **📦 Queda de Itens (Drops)** | `doEntityDrops`<br>`doTileDrops`<br>`doMobLoot`<br>`globalSoundEvents` | Itens gerados por blocos quebrados e criaturas eliminadas, sons globais. |
| **🌧️ Atualizações (Updates)** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doFireTick`<br>`randomTickSpeed`<br>`maxCommandChainLength` | Pausar ciclos de tempo e clima, propagação de chamas ou velocidade de colheitas. |
| **💬 Chat (Chat)** | `showDeathMessages`<br>`sendCommandFeedback`<br>`logAdminCommands`<br>`reducedDebugInfo` | Avisos de mortes, relatórios de comandos ou ocultar coordenadas na tela F3. |
| **⚙️ Diversos (Misc)** | `commandBlockOutput`<br>`maxEntityCramming`<br>`disableElytraMovementCheck` | Limite de superlotação de entidades e mensagens de blocos de comando. |

---

## 🧩 Compatibilidade com Categorias de Mods

Categorias criadas por mods de terceiros (ou pelo `DynamicGameRuleManager` da `DasikLibrary`) são identificadas automaticamente, agrupadas em pastas dobráveis e formatadas em tempo real.

---

## 🔗 Documentação Relacionada

* [[🏠 Visão Geral & Página Inicial|pt_br-Home]]
* [[🗂️ Categorias Dobráveis|pt_br-Collapsible-Categories]]
* [[🎛️ Predefinições de Regras de Jogo & Controles|pt_br-Game-Rule-Presets-and-Controls]]
* [[📚 Integração com a API do DasikLibrary|pt_br-API-and-Library-Integration]]
