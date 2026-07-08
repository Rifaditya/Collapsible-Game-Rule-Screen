# Version History: Collapsible Game Rules

## [1.0.2+R-26.1.2] - 2026-06-04
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
