# Developer Documentation

**Collapsible Game Rules** is a purely client-side UI enhancement for Minecraft's GameRules screen.

## Architecture
- It uses a Mixin into `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen$RuleList` to override how children are populated.
- It uses a Mixin into `net.minecraft.client.gui.screens.Screen` (`ScreenMixin`) to hook into screen removal, ensuring state is saved correctly without violating inheritance laws.
- It introduces `CollapsibleCategoryRuleEntry` which renders the category header as a clickable toggle button.
- It introduces `GlobalActionsRuleEntry` pinned at index 0 for bulk toggling.
- State is managed within the Mixin and the visible entries list is dynamically rebuilt when categories are toggled.
- Persistence is handled by `GameRuleStateConfig`, using an `isDirty` flag to throttle disk I/O.
- Depends on `dasik-library` for standard internal tooling.

## DasikLibrary Integration
The mod uses `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` as an isolation layer to query `net.dasik.social.api.gamerule.DynamicGameRuleManager` metadata. This ensures the mod remains functional even if the library is not installed, preventing `NoClassDefFoundError` during class loading. It queries the `getGeneratedTranslations()` map to find human-readable category names for specific namespaces (e.g., `ig_ore_` -> `Ore Multiplier`).

## Getting Started
To integrate or contribute, clone the repository and run:
`./gradlew build`

### Building from Source
Ensure you have **Java 25** and **Gradle 9.3.0** installed. The project uses the latest Fabric Loom for 26.1.2 development.

### Persistence Hook
If you are extending the UI, keep in mind that `GameRuleStateConfig.saveIfDirty()` must be called upon screen removal for changes to persist. Standard rule entries are automatically handled by the `ScreenMixin`.
