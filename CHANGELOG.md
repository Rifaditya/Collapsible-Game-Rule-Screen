## [1.0.18+26.2]
- **Global Action Toolbar Symmetrical Card Geometry & Accents**: Refactored the top global actions toolbar from a raw split text row into two balanced, symmetrical glassmorphic action cards separated by a 6px central gap.
- **Directional Icons & Category-Matching Accent Bars**: Features centered directional icons (`▼ Expand All` and `▶ Collapse All`) with a warm gold 2px accent strip for the Expand card and a crisp lime 2px accent strip for the Collapse card, matching category card aesthetics.
- **Dynamic Card Hover Elevation**: Card backgrounds illuminate cleanly on hover (`0x24FFFFFF`) with text highlights (`0xFFFFFFAA`) and 0B heap allocation per frame.

## [1.0.17+26.2]
- **Horizontal Marquee Scrolling Text on Hover**: When hovering over a category header with a long title, the text smoothly scrolls sideways (ticker / marquee effect) within a hardware scissor box, allowing players to read the complete category title in-place directly on the card plate.
- **Natural Timing & Ping-Pong Animation**: Features an initial 1000ms pause, smooth ~30px/sec scroll across the overflow distance, 1000ms end pause, and ping-pong return.
- **Hardware Scissor Clipping**: Uses `graphics.enableScissor()` to ensure scrolling text never bleeds into the left status indicator or the right-anchored rule count badge.
- **Dual Readability Support**: Preserves the hover tooltip alongside the marquee scroll, providing both rapid glanceability and seamless in-card animation with 0B heap allocation per frame.

## [1.0.16+26.2]
- **Long Category Title Collision Fix**: Resolved a visual text collision bug where long category titles (e.g. `Natural Reproduction - Toggles`) extended across the header and drew directly over the right-anchored rule count badge.
- **Responsive Ellipsis Truncation**: Category titles now calculate available width dynamically and truncate with `...` (`font.substrByWidth`), guaranteeing a clean clearance gap before the rule count badge.
- **Full Title Hover Tooltip**: When a category title is truncated due to width constraints, hovering over the title area displays the complete untruncated category name in a standard Minecraft tooltip.
- **Zero-Allocation Layout Caching**: Pre-calculated `FormattedCharSequence` references are cached on width/badge changes, preserving 0B/frame heap allocation during active list scrolling.

## [1.0.15+26.2]
- **Visual Modified-From-Default Rule Counters**: Added dynamic golden metric badges (`[● X mod / Y rules]`) and default gray badges (`[Y rules]`) on category headers, providing instant category-level visibility into customized world rules.
- **Enhanced Screen Reader Narration**: Updated narration output on category cards to report both category title and rule counts/modified metrics for full accessibility compliance.
- **Zero-Allocation Architecture Preserved**: Added `withModifiedCount()` factory and decoupled static badge generation to maintain 0B heap allocations per render frame.

## [1.0.14+26.2]
- **Left-Aligned Titles & Right-Anchored Metrics**: Redesigned category headers from centered text into a clean modern card layout: directional arrows (`▶` / `▼`) and category names are pinned to the left edge, and rule count badges (`[X rules]`) are right-anchored before the scrollbar.
- **Card-Style Plate & Visual Indicator**: Added a subtle elevated glassmorphic card plate on hover and an accent status bar (warm gold when expanded, crisp lime when collapsed).
- **Render-Path Zero-Allocation Kept**: Pre-cached `expandedLeft`, `collapsedLeft`, and `countBadge` components directly on `CategoryGroup` preserving 0B/frame heap allocation.

## [1.0.13+26.2]
- **Toggle-Path Entry Reuse & View Splice**: Pre-cached and reused persistent `CollapsibleCategoryRuleEntry` and `GlobalActionsRuleEntry` instances across category toggle clicks, eliminating widget and lambda re-allocations during list rebuilds.

## [1.0.12+26.2]
- **Render-Path Zero-Allocation Caching**: Pre-cached directional display components (`▼`/`▶` labels with rule count badges) in `CategoryGroup`, eliminating per-frame string concatenation and component allocations in `extractContent()`.
- **Global Actions Static Label Caching**: Pre-cached static action labels and narration components in `GlobalActionsRuleEntry` dropping frame-level allocations to zero.

## [1.0.11+26.2]
- **Eliminated O(N²) Nested List Scans**: Wired `CategoryTreeBuilder` into `AbstractGameRulesScreenRuleListMixin`, ingesting rule entries in a single O(N) linear pass.
- **Zero-Allocation Category Toggling**: Category toggling now operates at O(C + M) via pre-grouped `CategoryGroup` structures, eliminating repeated translation parsing and string prettification allocations on every click.

## [1.0.10+26.2]
- **Category Data Model Architecture**: Introduced immutable `CategoryGroup` record caching display titles and child rule lists, unlocking O(1) rule count metrics and eliminating repeated nested list scans.
- **Linear Category Tree Builder**: Added single-pass `CategoryTreeBuilder` grouping parser to ingest raw game rule entries linearly.
- **Automated Unit Test Harness**: Configured JUnit 5 Jupiter engine in `build.gradle` and added `CategoryTreeBuilderTest` testing immutability, empty list handling, and rule count metrics.

## [1.0.9+26.2] - 2026-08-04

### Fixed
- **Mixin Configuration Fix**: Removed obsolete `BooleanRuleEntryMixin` entry from `collapsible-game-rules.mixins.json` fixing `ClassNotFoundException` startup crash.

## [1.0.8+26.2] - 2026-08-04

### Changed
- **Screenshot & Documentation Asset Update**: Compressed and updated main mod screenshot asset (`Images/2026-08-04_11.06.33.png`).
- **Canonical GitHub Raw URLs**: Updated all documentation pages (`README.md`, `CurseForge`, `Modrinth`) to reference canonical raw GitHub image URLs.
- **Repository Synchronization**: Synchronized workspace code and release archives with GitHub repository.

## [1.0.7+26.2] - 2026-08-02

### Added
- **Game Rules Control Center Overhaul**: Total redesign of the Game Rules Screen into a 2-pane navigation layout.
- **Interactive Integer Sliders (`IntegerSliderWidget`)**: Added dynamic numeric sliders with step buttons (`-1`, `+1`), live value formatting, and direct text input.
- **Visual Boolean Toggle Switches (`BooleanToggleWidget`)**: Added emerald green `[ON]` / ruby red `[OFF]` toggle switches.
- **Game Rules Preset Engine (`GameRulePresetEngine`)**: 1-click apply built-in presets (Vanilla Defaults, Builder Mode, Fast Survival, Hardcore Realism) and custom JSON preset saving/loading.
- **Single-Click Reset (`[↺]`) & Category Metrics**: Added per-rule and per-category reset buttons and category rule count badges (`▼ Category (X/Y modified)`).

## [1.0.6+26.2] - 2026-08-01

### Fixed
- **ModVersionGuard Package Declaration Fix**: Fixed syntax error in `ModVersionGuard.java` package declaration (`package net.instantgratification.collapsiblegamerules.util;`).

## [1.0.5+26.2] - 2026-07-22

### ⚠️ Version Guard Notice
- Includes zero-dependency `ModVersionGuard` pre-release protection. Halts startup with an explicit warning banner if run on incompatible Minecraft drops or missing core dependencies (`dasik-library`, `fabric-api`) to prevent world save corruption.

### Fixed
- **ModVersionGuard ClassLoader Patch**: Corrected `ModVersionGuard` check target package to `net.minecraft.world.level.gamerules.GameRules`.

## [1.0.4+26.2] - 2026-07-22
- **Fixed**: Updated `ModVersionGuard` startup check target to Knot ClassLoader resolution.

## [1.0.3+26.2] - 2026-07-22
- **Forward Compatibility & Version Guard**: Configured `fabric.mod.json` with `"minecraft": ">=26.2-"` for open-ended forward compatibility. Added zero-dependency `ModVersionGuard` check on startup to display human-readable guidance if an incompatible Minecraft API version is encountered.

## [1.0.2+R-26.1.2] - 2026-06-04
- **Minecraft Support**: Officially declared and aligned compatibility ranges to support both **Minecraft 26.1.2** and **Minecraft 26.2**.
- **Category Prettification**: Added intelligent translation fallback prettification for unlocalized game rule category keys (e.g., converting `"gamerule.category.better-bats.better_bats"` to `"Better Bats"`).
- **Persistence Robustness**: Refactored the UI collapse persistence layer to track states using translation keys instead of localized display strings, preventing state loss on language changes.
- **DasikLibrary Alignment**: Bumped dependency version to `1.7.4` to match active workspace alignment.
- **Compliance Updates**: Ignored `Doc/Marketing/` in `.gitignore` to prevent accidental tracking of marketing hype and sanitised internal codenames in files.

## [1.0.1] - 2026-05-16
- **Production Stability**: Standardized refmap inclusion for stable Mixin transformation.
- **Dependency Hardening**: Enforced `dasik-library >= 1.7.0` for social AI parity.

## [1.0.0+build.15] - 2026-04-16
- **Upgraded**: Fabric Loader to `0.19.1` for native Java 25 Mixin subsystem support.
- **Minecraft Support**: Shifted to `~26.x` compatible range (`>=26.1`) for **Minecraft 26.2** readiness.
- **Upgraded**: Fabric API to `0.145.4+26.1.2`.
- **Restored**: Mixin `compatibilityLevel` to `JAVA_25` — native, warning-free.
- **Dependencies**: Upgraded `DasikLibrary` to `build.20`.

## [1.0.0+build.14] - 2026-04-15
- **Fixed**: Downgraded Mixin `compatibilityLevel` to `JAVA_22` to resolve Fabric/Knot subsystem warnings while maintaining Java 25 runtime support.
- **Dependencies**: Upgraded `DasikLibrary` dependency to `build.19`.