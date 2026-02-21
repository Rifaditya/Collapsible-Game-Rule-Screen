# Changelog History

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
