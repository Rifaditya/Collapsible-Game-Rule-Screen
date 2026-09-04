# Version History: Collapsible Game Rules

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
- **Mixin Configuration Fix**: Removed obsolete `BooleanRuleEntryMixin` entry from `collapsible-game-rules.mixins.json` fixing `ClassNotFoundException` startup crash.

## [1.0.8+26.2] - 2026-08-04
- **Screenshot & Documentation Asset Update**: Compressed and updated main mod screenshot asset (`Images/2026-08-04_11.06.33.png`).
- **Canonical GitHub Raw URLs**: Updated all documentation pages (`README.md`, `CurseForge`, `Modrinth`) to reference canonical raw GitHub image URLs.
- **Repository Synchronization**: Synchronized workspace code and release archives with GitHub repository.

## [1.0.7+26.2] - 2026-08-02
- **Game Rules Control Center Overhaul**: Total redesign of the Game Rules Screen into a 2-pane navigation layout.
- **Interactive Integer Sliders (`IntegerSliderWidget`)**: Added dynamic numeric sliders with step buttons (`-1`, `+1`), live value formatting, and direct text input.
- **Visual Boolean Toggle Switches (`BooleanToggleWidget`)**: Added emerald green `[ON]` / ruby red `[OFF]` toggle switches.
- **Game Rules Preset Engine (`GameRulePresetEngine`)**: 1-click apply built-in presets (Vanilla Defaults, Builder Mode, Fast Survival, Hardcore Realism) and custom JSON preset saving/loading.
- **Single-Click Reset (`[↺]`) & Category Metrics**: Added per-rule and per-category reset buttons and category rule count badges (`▼ Category (X/Y modified)`).

## [1.0.6+26.2] - 2026-08-01
- **ModVersionGuard Package Declaration Fix**: Fixed syntax error in `ModVersionGuard.java` package declaration (`package net.instantgratification.collapsiblegamerules.util;`).

## [1.0.5+26.2] - 2026-07-22
- **ModVersionGuard ClassLoader Patch**: Corrected `ModVersionGuard` check target package to `net.minecraft.world.level.gamerules.GameRules`.
- **Minecraft Support**: Officially declared and aligned compatibility ranges to support both **Minecraft 26.1.2** and **Minecraft 26.2**.
- **Category Prettification**: Added intelligent translation fallback prettification for unlocalized game rule category keys (e.g., converting `"gamerule.category.better-bats.better_bats"` to `"Better Bats"`).
- **Persistence Robustness**: Refactored the UI collapse persistence layer to track states using translation keys instead of localized display strings, preventing state loss on language changes.
- **DasikLibrary Alignment**: Bumped dependency version to `1.7.4` to match active workspace alignment.
- **Compliance Updates**: Ignored `Doc/Marketing/` in `.gitignore` to prevent accidental tracking of marketing hype and sanitised internal codenames in files.

## [1.0.1] - 2026-05-16
- **Production Stability**: Standardized refmap inclusion for stable Mixin transformation.
- **Dependency Hardening**: Enforced `dasik-library >= 1.7.0` for social AI parity.

## [1.0.0+build.16] - 2026-05-10
- **Core 2.1 Alignment**: Achieved full compliance with the Sovereign Standard.
- **Hard Dependency Enforcement**: Implemented explicit "Info Crash" if `dasik-library` is missing.
- **Metadata**: Standardized author to `Dasik (Rifaditya)`.
- **Infrastructure**: Upgraded to Loom `1.15.2` and enabled Gradle parallel execution.
- **Versioning**: Shifted minimum Minecraft requirement to `26.1.2` for absolute stability.

## [1.0.0+build.15] - 2026-04-16
- **Upgraded**: Fabric Loader to `0.19.1` for native Java 25 Mixin subsystem support.
- **Minecraft Support**: Shifted to `~26.x` compatible range (`>=26.1`) for **Minecraft 26.2** readiness.
- **Upgraded**: Fabric API to `0.145.4+26.1.2`.
- **Restored**: Mixin `compatibilityLevel` to `JAVA_25` — native, warning-free.
- **Dependencies**: Upgraded `DasikLibrary` to `build.20`.

## [1.0.0+build.14] - 2026-04-15
- **Fixed**: Downgraded Mixin `compatibilityLevel` to `JAVA_22` to resolve Fabric/Knot subsystem warnings while maintaining Java 25 runtime support.
- **Dependencies**: Upgraded `DasikLibrary` dependency to `build.19`.

## [1.0.0+build.13] - 2026-04-15
- **Compliance**: Added `pack.mcmeta` with explicit `min_format` and `max_format` (Format 84) to satisfy Snapshot 26.1.2 validation requirements.
- **Dependencies**: Upgraded `DasikLibrary` dependency to `build.18`.

## [1.0.0+build.12] - 2026-04-15
- **Dependencies**: Upgraded `DasikLibrary` dependency to `build.17`.
- **Added**: `DasikMetadataHelper` for ClassLoading safety. Isolates `DasikLibrary` references to prevent runtime crashes if the library is missing.
- **Fixed**: Critical World Options lockout caused by Mixin inheritance violation. Migrated to `ScreenMixin` targeting the base `removed()` method.
- **Synced**: Comprehensive documentation audit and sync across all platform pages (Modrinth, CurseForge) and player guides.
- **Verified**: Achieved **Core Sovereign Engineering** compliance. Verified against Snapshot 26.1 (wildcard `26.*` standard).
- **Standardized**: Internal Mixin member prefixes to `collapsible_game_rules$`.
- **Modernized**: Updated Stream API usage to Java 25 (`.toList()`).

## [1.0.0+build.10] - 2026-04-15
- **Added**: Global Actions UI entry providing "Expand All" and "Collapse All" buttons.
- **Added**: Enhanced Keyboard Navigation — Left Arrow collapses, Right Arrow expands categories.
- **Added**: Smart Search Integration — matching categories now automatically expand during search.
- **Added**: Full English localization via `en_us.json`.
- **Optimized**: High-performance persistence layer moves disk I/O to screen exit via `isDirty` flag.
- **Optimized**: Static logger implementation to reduce overhead during UI population.
- **Synced**: Environment alignment with Minecraft **26.1.2 ("Tiny Takeover")**, Fabric Loader **0.18.4**, and **DasikLibrary Build 16**.

## [1.0.0+build.9] - 2026-04-13
- Upgraded Minecraft dependency constraint to `26.1.2`.
- Migrated mixin rendering pipelines from `GuiGraphics` to `GuiGraphicsExtractor` to comply with the 26.1.2 UI rendering engine refactor.

## [1.0.0+build.8] - 2026-03-02
- Fixed Minecraft 26.1 `KeyEvent` signature changes in `AbstractGameRulesScreenRuleListMixin.java` fixing build failure.
