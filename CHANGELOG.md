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