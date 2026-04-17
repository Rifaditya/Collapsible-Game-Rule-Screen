## [1.0.0+build.15] - 2026-04-15
- **Upgraded**: Fabric Loader `0.19.1`, Fabric API `0.145.4+26.1.2`.
- **Restored**: Native `JAVA_25` Mixin compatibility.
- **Dependencies**: Upgraded `DasikLibrary` to `build.20`.

## [1.0.0+build.14] - 2026-04-15
- **Fixed**: Mixin `compatibilityLevel` warning (set to `JAVA_22`).
- **Dependencies**: Upgraded `DasikLibrary` dependency to `build.19`.

## [1.0.0+build.13] - 2026-04-15
- **Compliance**: Added `pack.mcmeta` for Snapshot 26.1.2.
- **Dependencies**: Upgraded `DasikLibrary` dependency to `build.18`.

## [1.0.0+build.12] - 2026-04-15
- **Dependencies**: Upgraded `DasikLibrary` dependency to `build.17`.
- **Added**: `DasikMetadataHelper` for ClassLoading safety. Isolates `DasikLibrary` references to prevent runtime crashes if the library is missing.
- **Fixed**: Critical World Options lockout caused by Mixin inheritance violation. Migrated to `ScreenMixin` targeting the base `removed()` method.
- **Synced**: Comprehensive documentation audit and sync across all platform pages (Modrinth, CurseForge) and player guides.
- **Verified**: Achieved **Zenith Sovereign Engineering** compliance. Verified against Snapshot 26.1 (wildcard `26.*` standard).
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
