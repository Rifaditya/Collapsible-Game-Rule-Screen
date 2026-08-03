# 📋 Collapsible Game Rules Release Queue & Backlog

This file tracks which built versions (from `/Archive/builds/`) have been manually uploaded to Modrinth/CurseForge.
Open this file in your editor and change `[ ]` to `[x]` when you publish a version.

## 🚀 Published & Backlog Queue

- [ ] **`1.0.10+26.2`** (2026-08-03) - Screen-Level 560px Widescreen Resizing, regex arrow stripper, title truncation, dark glass cards, and presets bar.
- [ ] **`1.0.9+26.2`** (2026-08-03) - Widescreen Dashboard (520px), right-anchored count badges, duplicate arrow fix, Gold/Cyan category card accents.
- [ ] **`1.0.8+26.2`** (2026-08-03) - Visual Overhaul: BooleanToggleWidget green/red toggles, IntegerSliderWidget sliders, category header scroll clipping fix, and Reset-to-Default buttons.
- [ ] **`1.0.7+26.2`** (2026-08-02) - Game Rules Control Center Overhaul (Interactive Sliders, Toggle Switches, Presets Engine, Reset-to-Default, Category Metrics).
- [ ] **`1.0.6+26.2`** (2026-08-01) - Fixed syntax error in `ModVersionGuard.java` package declaration.
- [x] **`1.0.0+build.8`** (2026-03-02) - - Fixed Minecraft 26.1 `KeyEvent` signature changes in `AbstractGameRulesScreenRuleListMixin.java` fixing build failure.
- [x] **`1.0.0+build.9`** (2026-04-13) - - Upgraded Minecraft dependency constraint to `26.1.2`. - - Migrated mixin rendering pipelines from `GuiGraphics` to `GuiGraphicsExtractor` to comply with the 26.1.2 UI rendering engine refactor.
- [x] **`1.0.0+build.10`** (2026-04-15) - - **Added**: Global Actions UI entry providing "Expand All" and "Collapse All" buttons. - - **Added**: Enhanced Keyboard Navigation — Left Arrow collapses, Right Arrow expands categories.
- [x] **`1.0.0+build.12`** (2026-04-15) - - **Dependencies**: Upgraded `DasikLibrary` dependency to `build.17`. - - **Added**: `DasikMetadataHelper` for ClassLoading safety. Isolates `DasikLibrary` references to prevent runtime crashes if the library is missing.
- [x] **`1.0.0+build.13`** (2026-04-15) - - **Compliance**: Added `pack.mcmeta` with explicit `min_format` and `max_format` (Format 84) to satisfy Snapshot 26.1.2 validation requirements. - - **Dependencies**: Upgraded `DasikLibrary` dependency to `build.18`.
- [x] **`1.0.0+build.14`** (2026-04-15) - - **Fixed**: Downgraded Mixin `compatibilityLevel` to `JAVA_22` to resolve Fabric/Knot subsystem warnings while maintaining Java 25 runtime support. - - **Dependencies**: Upgraded `DasikLibrary` dependency to `build.19`.
- [x] **`1.0.0+build.15`** (2026-04-16) - - **Upgraded**: Fabric Loader to `0.19.1` for native Java 25 Mixin subsystem support. - - **Minecraft Support**: Shifted to `~26.x` compatible range (`>=26.1`) for **Minecraft 26.2** readiness.
- [x] **`1.0.1`** (2026-05-16) - - **Production Stability**: Standardized refmap inclusion for stable Mixin transformation. - - **Dependency Hardening**: Enforced `dasik-library >= 1.7.0` for social AI parity.
- [x] **`1.0.2+R-26.1.2`** (2026-06-04) - - **Minecraft Support**: Officially declared and aligned compatibility ranges to support both **Minecraft 26.1.2** and **Minecraft 26.2**. - - **Category Prettification**: Added intelligent translation fallback prettification for unlocalized game rule category keys (e.g., converting `"gamerule.category.better-bats.better_bats"` to `"Better Bats"`).
- [x] **`1.0.3+26.2`** (2026-07-22) - Forward Compatibility & Version Guard.
- [x] **`1.0.4+26.2`** (2026-07-22) - ModVersionGuard Knot ClassLoader Patch.
- [x] **`1.0.5+26.2`** (2026-07-22) - ModVersionGuard GameRules check target patch.
