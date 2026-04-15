# Changelog History

## [1.0.0+build.10] - 2026-04-15
### Added
- **Global Actions UI**: Inserted a specialized rule entry at index 0 featuring "Expand All" and "Collapse All" functionality.
- **Enhanced Navigation**: Explicit support for `GLFW_KEY_LEFT` (Collapse) and `GLFW_KEY_RIGHT` (Expand) keyboard traversal.
- **Smart Search**: Automated expansion of categories containing matching GameRule titles during active searching.
- **Localization**: Implemented `en_us.json` for all mod-specific UI components.

### Optimized
- **Persistence Strategy**: Introduced `isDirty` throttling for `GameRuleStateConfig`, deferring all disk I/O until `AbstractGameRulesScreen.removed()` to prevent lag during menu manipulation.
- **Logging**: Moved logger instances to static final constants to reduce class instantiation overhead in loop-bound logic.

### Synced
- Aligned environment with **Minecraft 26.1.2 ("Tiny Takeover")**, **Fabric Loader 0.18.4**, and **DasikLibrary Build 16**.

## [1.0.0+build.9] - 2026-04-13
### Changed
- **Rendering**: Migrated mixin rendering pipelines from `GuiGraphics` to `GuiGraphicsExtractor` to comply with the 26.1.2 UI rendering engine refactor.
- **Dependencies**: Target Minecraft version bumped to `26.1.2`.

## [1.0.0+build.8] - 2026-03-02
### Fixed
- **Build Failure**: Resolved Minecraft 26.1 `KeyEvent` signature changes in `AbstractGameRulesScreenRuleListMixin.java`.

## [1.0.0+build.7] - 2026-02-21

### Fixed

- **Compatibility**: Reverted Mixin compatibility level from `JAVA_25` to `JAVA_22` to resolve warning.

## [1.0.0+build.6] - 2026-02-21

### Changed

- **Documentation**: Replaced "Architect" with "Creator" in Platform Page Author roles.

## [1.0.0+build.5] - 2026-02-21

### Added

- Added GPLv3 LICENSE file to repository root.
- Expanded documentation to clarify modded category support.

### Changed

- Converted project standalone documentation to remove collection references.

## [1.0.0+build.4] - 2026-02-21

### Fixed

- Added `CategoryRuleEntryAccessor` to `collapsible-game-rules.mixins.json` to prevent `IllegalClassLoadError` during game start.

## [1.0.0+build.3] - 2026-02-21

### Fixed

- Resolved a critical crash caused by an unsupported nested inner class within the `AbstractGameRulesScreenRuleListMixin` when rendering the game rules UI.

## [1.0.0+build.2] - 2026-02-21

### Fixed

- Resolved Sanitary Verification audit violations by removing Intermediary mappings and tech debt comments.

## [1.0.0+build.1] - 2026-02-21

### Added

- Initial release.
- Makes the GameRules UI screens collapsible by category.
