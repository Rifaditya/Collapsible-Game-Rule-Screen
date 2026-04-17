## [1.0.0+build.15] - 2026-04-15
### Core Changes
- Upgraded Fabric Loader to `0.19.1` — native Java 25 Mixin support, zero warnings.
- Upgraded Fabric API to `0.145.4+26.1.2`.
- Restored `compatibilityLevel: JAVA_25` in all Mixin configs.
- Upgraded `DasikLibrary` dependency to `build.20`.

## [1.0.0+build.14] - 2026-04-15
### Core Changes
- Downgraded Mixin compatibility level to `JAVA_22` for stable Fabric Knot integration.
- Upgraded `DasikLibrary` dependency to `build.19`.

## [1.0.0+build.13] - 2026-04-15
### Core Changes
- Added `pack.mcmeta` for full Minecraft 26.1.2 compliance (Major Format 84).
- Upgraded `DasikLibrary` dependency to `build.18`.

## [1.0.0+build.12] - 2026-04-15
### Core Changes
- Fixed critical World Options lockout via new `ScreenMixin`.
- Implemented `DasikMetadataHelper` for safe library integration.
- Standardized verification headers to `26.*` wildcard.
- Upgraded `DasikLibrary` dependency to `build.17`.
- Added Sovereign Engineering verification citations.

## [1.0.0+build.10] - 2026-04-15
### Core Changes
- Added Global Actions UI entry ("Expand All" / "Collapse All").
- Added Enhanced Keyboard Navigation.
- Integrated Smart Search expansion.

## [1.0.0+build.9] - 2026-04-13
### Core Changes
- Migrated mixin rendering pipelines to `GuiGraphicsExtractor` (MC 26.1.2 UI engine refactor).

## [1.0.0+build.8] - 2026-03-02
### Core Changes
- Fixed compilation error caused by Minecraft 26.1 `KeyEvent` signature changes in `AbstractGameRulesScreenRuleListMixin.java`.


## [1.0.0+build.5] - 2026-02-21
### Core Changes
- Processed user feedback removing references to Instant Gratification Collection.
- Downloaded and placed GPL-3.0 LICENSE file in repository root.
