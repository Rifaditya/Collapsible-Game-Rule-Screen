## [1.0.12+26.2]
### Core Changes
- Pre-cached directional display components (`▼`/`▶` labels with rule count badges) in `CategoryGroup`, eliminating per-frame string concatenation and component allocations in `extractContent()`.
- Pre-cached static action labels and narration components in `GlobalActionsRuleEntry` dropping frame-level allocations to zero.

## [1.0.11+26.2]
### Core Changes
- Wired `CategoryTreeBuilder` into `AbstractGameRulesScreenRuleListMixin`, ingesting rule entries in a single O(N) linear pass.
- Category toggling now operates at O(C + M) via pre-grouped `CategoryGroup` structures, eliminating repeated translation parsing and string prettification allocations on every click.

## [1.0.10+26.2]
### Core Changes
- Introduced immutable `CategoryGroup` record caching display titles and child rule lists, unlocking O(1) rule count metrics and eliminating repeated nested list scans.
- Added single-pass `CategoryTreeBuilder` grouping parser to ingest raw game rule entries linearly.
- Configured JUnit 5 Jupiter engine in `build.gradle` and added `CategoryTreeBuilderTest`.

## [1.0.6+26.2] - 2026-08-01
### Fixed
- Fixed syntax error in `ModVersionGuard.java` package declaration (`package net.instantgratification.collapsiblegamerules.util;`).

## [1.0.4+26.2] - 2026-07-22
### Core Changes
- Updated `ModVersionGuard` check target in `CollapsibleGameRulesFabric.java` to `net.minecraft.world.level.GameRules` for robust early initialization.

## [1.0.3+26.2] - 2026-07-22
### Core Changes
- Forward Compatibility & Version Guard setup for 26.2+.

## [1.0.2+R-26.1.2] - 2026-06-04
### Core Changes
- Officially declared support for Minecraft 26.1.2 and 26.2.
- Added translation fallback prettification for category headers.
- Refactored UI collapse states to persist by raw keys instead of display strings.
- Upgraded DasikLibrary dependency to 1.7.4.
- Added Doc/Marketing/ to .gitignore and sanitised internal codenames.

## [1.0.1] - 2026-05-16
### Core Changes
- Standardized refmap inclusion for stable Mixin transformation.
- Enforced `dasik-library >= 1.7.0`.

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
