## [1.0.37+26.2]
### Core Changes
- Added dynamic scroll boundary recalculation via `refreshScrollAmount()`, eliminating abrupt viewport jumps when collapsing large rule categories.
- Anchored viewport directly to the clicked category card (`scrollToEntry(clickedEntry)`) when toggling, keeping the active card visible and centered.
- Added instant scroll offset reset to `0.0` upon activating "Collapse All".

## [1.0.36+26.2]
### Core Changes
- Upgraded configuration file structure to versioned JSON schema (`schemaVersion: 1`).
- Implemented automatic legacy config migration parsing raw string arrays and converting them safely to the versioned schema format on load.
- Expanded `GameRuleStateConfigTest` to verify versioned schema formatting, legacy array migration, and modern schema loading.

## [1.0.35+26.2]
### Core Changes
- Caught `JsonSyntaxException` during config parsing, recovering safely to clean default settings upon encountering corrupted JSON syntax.
- Parameterized config I/O with `loadFromPath()` and `saveToPath()`, decoupling persistence paths for unit testing.
- Created `GameRuleStateConfigTest` JUnit 5 test suite verifying persistence cycles, missing file tolerance, corrupted JSON recovery, and dirty state tracking.

## [1.0.34+26.2]
### Core Changes
- Added automated JUnit 5 test suite in `CategoryPrettifierTest` asserting translation key parsing, namespace stripping, redundant path deduplication, and delimiters.
- Enhanced `CategoryPrettifier` delimiter parsing to include whitespace when capitalizing compound multi-token category words.

## [1.0.33+26.2]
### Core Changes
- Refined category title dynamic clearance margins before the 14x14 reset icon plate (`titleRight = resetX - 4`).
- Aligned hardware scissor clipping boundaries for smooth hover marquee scrolling text without edge distortion.

## [1.0.32+26.2]
### Core Changes
- Refactored category reset button from text-padded `↺ Reset` into a compact 14x14 icon-only plate (`↺`) with hover tooltip (`Reset category to defaults`), reclaiming ~34px of horizontal space to eliminate category title truncation.
- Centered icon inside 14x14 geometry and aligned mouse click hitboxes.
- Retained full verbal screen reader narration (`NarratedElementType.HINT`).

## [1.0.15+26.2]
### Core Changes
- Added visual modified-from-default rule counter badges (`[● X mod / Y rules]` in warm gold when modified, `[Y rules]` in gray when default).
- Added updated screen reader narration support for category cards announcing category title alongside rule count and modification status.
- Preserved zero-allocation render path via pre-cached badge components and `withModifiedCount()` group derivation.

## [1.0.14+26.2]
### Core Changes
- Redesigned category headers from centered text into a clean modern card layout: directional arrows (`▶` / `▼`) and category names are pinned to the left edge, and rule count badges (`[X rules]`) are right-anchored before the scrollbar.
- Added subtle elevated glassmorphic card plate on hover and an accent status bar (warm gold when expanded, crisp lime when collapsed).
- Pre-cached `expandedLeft`, `collapsedLeft`, and `countBadge` components directly on `CategoryGroup` preserving 0B/frame heap allocation.

## [1.0.13+26.2]
### Core Changes
- Pre-cached and reused persistent `CollapsibleCategoryRuleEntry` and `GlobalActionsRuleEntry` instances across category toggle clicks, eliminating widget and lambda re-allocations during list rebuilds.

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
