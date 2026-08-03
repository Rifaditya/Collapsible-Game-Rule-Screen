# Version History: Collapsible Game Rules

## [1.0.11+26.2] - 2026-08-03
- **2-Line Dynamic Header Cards**: Long category names (like `MCA Inclusive Expressions`) expand card height dynamically so the full title fits across 2 lines without any '...' truncation.
- **Folder Icon Badges (`📂` / `📁`)**: Replaced text arrows with modern folder icons (`📂` when expanded, `📁` when collapsed) and stripped all legacy `►`/`▼` characters.
- **Cyan Pill Badges (`[ X Rules ]`)**: Styled rule count badges as dark glass cyan pill tags anchored on the far right.

## [1.0.10+26.2] - 2026-08-03
- **Screen-Level Widescreen Resizing (`AbstractGameRulesScreenMixin`)**: Mixed into `AbstractGameRulesScreen.init()` to resize `RuleList` to a spacious 560px widescreen layout centered on screen.
- **Double Arrow Removal**: Added regex arrow stripper `^[\\s\\u25BA\\u25B6\\u25BC\\u25BD>►▼▶]+` to eliminate duplicate arrow symbols (`► ▼`).
- **Smart Title Truncation**: Truncates long category titles with `...` if they approach the right-anchored count badge.
- **Dark Glassmorphic Category Styling**: Premium dark glass pill cards (`0xDD12141C`) with Gold (`0xFFFFAA00`) and Electric Cyan (`0xFF00E5FF`) accent indicators.
- **Enhanced Global Header Bar**: Upgraded `GlobalActionsRuleEntry` with Expand All, Collapse All, and Preset controls.

## [1.0.9+26.2] - 2026-08-03
- **Widescreen Dashboard Layout (520px)**: Expanded the rule selection list container from ~300px to a spacious 520px widescreen layout.
- **Right-Anchored Category Badges**: Anchored `[X rules]` badges to the far right margin of category cards, eliminating scrollbar overlaps.
- **Duplicate Arrow Fix**: Stripped pre-existing arrow characters from translation strings to guarantee a single crisp directional arrow (`▼` when expanded, `▶` when collapsed).
- **Dynamic Gold & Cyan Accents**: Gold accent bars when expanded, Cyan accent bars when collapsed.

## [1.0.8+26.2] - 2026-08-03
- **Boolean Rule Widgets**: Injected custom `BooleanToggleWidget` into `BooleanRuleEntry` replacing default grey buttons with emerald green `[✔ ON]` / ruby red `[✖ OFF]` styled toggles.
- **Integer Rule Sliders**: Injected `IntegerSliderWidget` into `IntegerRuleEntry` replacing plain text edit boxes with interactive sliders and `-` / `+` step controls.
- **Category Header Padding & Clipping Fix**: Fixed category header scroll clipping under the top search bar and added dark glass pill header cards.
- **Reset-to-Default `[↺]` Buttons**: Added per-rule single-click reset buttons for modified game rules.

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
