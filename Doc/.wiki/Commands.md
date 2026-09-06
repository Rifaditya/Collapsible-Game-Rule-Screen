# 💬 Brigadier Commands & Absence Scope

| Parameter | Specification |
| :--- | :--- |
| **Custom Brigadier Commands**| `0` (None Registered by Design) |
| **Command Architecture** | Purely Client-Side GUI Interface |
| **Vanilla Command Parity** | `100%` Compatible with vanilla `/gamerule` commands |
| **In-Game GUI Access** | Singleplayer Create World $\to$ Game Rules \| Pause Menu $\to$ Game Rules |

---

## 📖 Absence Policy Mandate

> [!NOTE]
> **Zero Custom Server Commands**: In accordance with the **Instant Gratification** philosophy, **Collapsible Game Rules** does not register custom chat commands (e.g. `/collapsiblegamerules reload` or `/cgr config`). All configuration and category management is handled interactively directly on the native Game Rules GUI screen with instant visual feedback.

---

## 💻 Vanilla Command Compatibility

Because the mod operates entirely on top of Minecraft's native `AbstractGameRulesScreen`, any changes made via standard in-game chat commands remain fully synchronized with the UI:

### Common Vanilla `/gamerule` Commands

```bash
# Toggle mob griefing (creeper block damage)
/gamerule mobGriefing false

# Keep player inventory on death
/gamerule keepInventory true

# Set crop growth random tick speed
/gamerule randomTickSpeed 10

# Disable daylight cycle
/gamerule doDaylightCycle false
```

When you re-open the Game Rules screen, all values set via chat commands will be reflected immediately inside their respective collapsible categories.

---

## 🔗 Related Documentation

* [[Overview & Home|Home]]
* [[GameRules Reference Table|GameRules-Reference]]
* [[Game Rule Presets & Widgets|Game-Rule-Presets-and-Controls]]
