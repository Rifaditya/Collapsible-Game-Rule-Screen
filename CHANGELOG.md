## [1.0.9+26.2] - 2026-08-03

### Added & Fixed
- **Widescreen Dashboard Layout (520px)**: Expanded the rule selection list container from ~300px to a spacious 520px widescreen layout.
- **Right-Anchored Category Badges**: Anchored `[X rules]` badges to the far right margin of category cards, eliminating scrollbar overlaps.
- **Duplicate Arrow Fix**: Stripped pre-existing arrow characters from translation strings to guarantee a single crisp directional arrow (`▼` when expanded, `▶` when collapsed).
- **Dynamic Gold & Cyan Accents**: Gold accent bars when expanded, Cyan accent bars when collapsed.

## [1.0.8+26.2] - 2026-08-03

### Fixed & Enhanced
- **Boolean Rule Widgets**: Injected custom `BooleanToggleWidget` into `BooleanRuleEntry` replacing default grey buttons with emerald green `[✔ ON]` / ruby red `[✖ OFF]` styled toggles.
- **Integer Rule Sliders**: Injected `IntegerSliderWidget` into `IntegerRuleEntry` replacing plain text edit boxes with interactive sliders and `-` / `+` step controls.
- **Category Header Padding & Clipping Fix**: Fixed category header scroll clipping under the top search bar and added dark glass pill header cards.
- **Reset-to-Default `[↺]` Buttons**: Added per-rule single-click reset buttons for modified game rules.

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