# 📋 Collapsible Game Rules Release Queue & Backlog

This file tracks which built versions (from `../Archive Jar of all versions/`) have been manually uploaded to Modrinth/CurseForge.
Open this file in your editor and change `[ ]` to `[x]` when you publish a version.

## 🚀 Published & Backlog Queue

- [ ] **`1.0.42+26.2`** - ModMenu native creator support metadata and Ko-fi links (`fabric.mod.json`).
- [ ] **`1.0.41+26.2`** - Category reset tooltip localization migration to `Component.translatable` (`AbstractGameRulesScreenRuleListMixin` + `en_us.json`).
- [ ] **`1.0.40+26.2`** - Platform manifest metadata (`modrinth` custom block) and git remote contact URLs parity (`fabric.mod.json`).
- [ ] **`1.0.39+26.2`** - License header standardization across 100% of Java source files (`// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3`).
- [ ] **`1.0.38+26.2`** - List width expansion to 308px (matching bottom Done/Cancel buttons), margin alignment, and wider rule label splitting (`AbstractGameRulesScreenRuleListMixin`, `GlobalActionsRuleEntry`, `GameRuleEntryMixin`).
- [ ] **`1.0.37+26.2`** - Scroll jump prevention on collapse, header anchoring, and refreshScrollAmount (`AbstractGameRulesScreenRuleListMixin`).
- [ ] **`1.0.36+26.2`** - Extensible versioned config schema (v1), legacy array migration, and automated tests (`GameRuleStateConfig`).
- [ ] **`1.0.35+26.2`** - Corrupted config JSON recovery, path abstraction, and test suite (`GameRuleStateConfig` + `GameRuleStateConfigTest`).
- [ ] **`1.0.34+26.2`** - Category Prettifier unit test suite and enhanced multi-word delimiter capitalization (`CategoryPrettifierTest`).
- [ ] **`1.0.33+26.2`** - Responsive category title clearance tuning and scissor boundary optimization (`CollapsibleCategoryRuleEntry`).
- [ ] **`1.0.32+26.2`** - Compact 14x14 category reset icon button (`↺`) with hover tooltip, reclaiming ~34px horizontal clearance to eliminate title truncation.
- [ ] **`1.0.31+26.2`** - Category reset input dispatch, dynamic live modification tracking, sound, and persistence (`CollapsibleCategoryRuleEntry`).
- [ ] **`1.0.30+26.2`** - Category reset button UI plate and visual inertia (`CollapsibleCategoryRuleEntry`).
- [ ] **`1.0.29+26.2`** - Category reset engine and value reversion checks (`CategoryResetHelper` + 100% unit tests).
- [ ] **`1.0.28+26.2`** - Rule tooltip & description search query substring highlighting (`RuleEntryAccessor` + `highlightSequence`).
- [ ] **`1.0.27+26.2`** - Rule label search query substring highlighting (`GameRuleEntryMixin` + `SearchHighlightHelper`).
- [ ] **`1.0.26+26.2`** - Search highlight engine (`SearchHighlightHelper`) with styled yellow span partitioning and 100% test coverage.
- [ ] **`1.0.25+26.2`** - Live search category match count badges (`[● X matches]`) and dynamic accessibility narration.
- [ ] **`1.0.24+26.2`** - Wired IntegerSliderWidget into IntegerRuleEntry for bounded rules with graceful unbounded fallback (includes critical Mixin superclass shadow fix).
- [ ] **`1.0.23+26.2`** (SKIP - BUGGED) - Bounded integer slider metadata & helper registry (superseded by 1.0.24+26.2).
- [ ] **`1.0.22+26.2`** (SKIP - BUGGED) - Wired BooleanToggleWidget into BooleanRuleEntry (superseded by 1.0.24+26.2).
- [ ] **`1.0.21+26.2`** - Modernized BooleanToggleWidget with high-contrast emerald/ruby pill aesthetics, border lines, and sound feedback.
- [ ] **`1.0.20+26.2`** - Global action immediate config persistence and enhanced screen reader usage narration.
- [ ] **`1.0.19+26.2`** - Global action bounded hitbox input validation, UI button click audio, and aligned hairline footer.
- [ ] **`1.0.18+26.2`** - Global action toolbar symmetrical card geometry, 6px gap, centered directional labels, and category-matching accents.
- [ ] **`1.0.17+26.2`** - Horizontal marquee scrolling text on hover for long category titles with scissor clipping.
- [ ] **`1.0.16+26.2`** - Fixed long category title collision with responsive ellipsis truncation and full-title hover tooltip.
- [ ] **`1.0.15+26.2`** - Visual modified-from-default rule counters with golden badge indicators and screen reader narration.
- [ ] **`1.0.14+26.2`** - Left-aligned category titles and right-anchored count badges with card hover highlight.
- [ ] **`1.0.13+26.2`** - Toggle-path entry reuse and view splice eliminating GC churn on list rebuilds.
- [ ] **`1.0.12+26.2`** - Render-path zero-allocation caching (`expandedDisplay`/`collapsedDisplay` and `GlobalActionsRuleEntry` static labels).
- [ ] **`1.0.11+26.2`** - Single-pass Mixin ingestion eliminating O(N²) nested scans and zero-allocation category toggling.
- [x] **`1.0.10+26.2`** - Category Data Model (`CategoryGroup`), linear `CategoryTreeBuilder`, and JUnit 5 test harness.
- [x] **`1.0.9+26.2`** (2026-08-04) - Fixed startup crash by removing obsolete `BooleanRuleEntryMixin` from `mixins.json`.
- [x] **`1.0.8+26.2`** (SUPERSEDED) (2026-08-04) - Screenshot & Documentation Asset Update, canonical raw GitHub URL fixes, and repository synchronization.
- [x] **`1.0.7+26.2`** (2026-08-02) - Game Rules Control Center Overhaul (Interactive Sliders, Toggle Switches, Presets Engine, Reset-to-Default, Category Metrics).
- [x] **`1.0.6+26.2`** (SUPERSEDED) (2026-08-01) - Fixed syntax error in `ModVersionGuard.java` package declaration.
- [x] **`1.0.0+build.8`** (2026-03-02) - - Fixed Minecraft 26.1 `KeyEvent` signature changes in `AbstractGameRulesScreenRuleListMixin.java` fixing build failure.
- [x] **`1.0.0+build.9`** (2026-04-13) - - Upgraded Minecraft dependency constraint to `26.1.2`. - - Migrated mixin rendering pipelines from `GuiGraphics` to `GuiGraphicsExtractor` to comply with the 26.1.2 UI rendering engine refactor.
- [x] **`1.0.0+build.10`** (SUPERSEDED) (2026-04-15) - - **Added**: Global Actions UI entry providing "Expand All" and "Collapse All" buttons. - - **Added**: Enhanced Keyboard Navigation — Left Arrow collapses, Right Arrow expands categories.
- [x] **`1.0.0+build.12`** (2026-04-15) - - **Dependencies**: Upgraded `DasikLibrary` dependency to `build.17`. - - **Added**: `DasikMetadataHelper` for ClassLoading safety. Isolates `DasikLibrary` references to prevent runtime crashes if the library is missing.
- [x] **`1.0.0+build.13`** (SUPERSEDED) (2026-04-15) - - **Compliance**: Added `pack.mcmeta` with explicit `min_format` and `max_format` (Format 84) to satisfy Snapshot 26.1.2 validation requirements. - - **Dependencies**: Upgraded `DasikLibrary` dependency to `build.18`.
- [x] **`1.0.0+build.14`** (SUPERSEDED) (2026-04-15) - - **Fixed**: Downgraded Mixin `compatibilityLevel` to `JAVA_22` to resolve Fabric/Knot subsystem warnings while maintaining Java 25 runtime support. - - **Dependencies**: Upgraded `DasikLibrary` dependency to `build.19`.
- [x] **`1.0.0+build.15`** (SUPERSEDED) (2026-04-16) - - **Upgraded**: Fabric Loader to `0.19.1` for native Java 25 Mixin subsystem support. - - **Minecraft Support**: Shifted to `~26.x` compatible range (`>=26.1`) for **Minecraft 26.2** readiness.
- [x] **`1.0.1`** (SUPERSEDED) (2026-05-16) - - **Production Stability**: Standardized refmap inclusion for stable Mixin transformation. - - **Dependency Hardening**: Enforced `dasik-library >= 1.7.0` for social AI parity.
- [x] **`1.0.2+R-26.1.2`** (2026-06-04) - - **Minecraft Support**: Officially declared and aligned compatibility ranges to support both **Minecraft 26.1.2** and **Minecraft 26.2**. - - **Category Prettification**: Added intelligent translation fallback prettification for unlocalized game rule category keys (e.g., converting `"gamerule.category.better-bats.better_bats"` to `"Better Bats"`).
- [x] **`1.0.3+26.2`** (2026-07-22) - Forward Compatibility & Version Guard.
- [x] **`1.0.4+26.2`** (2026-07-22) - ModVersionGuard Knot ClassLoader Patch.
- [x] **`1.0.5+26.2`** (2026-07-22) - ModVersionGuard GameRules check target patch.
