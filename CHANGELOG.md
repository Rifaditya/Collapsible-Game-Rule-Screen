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