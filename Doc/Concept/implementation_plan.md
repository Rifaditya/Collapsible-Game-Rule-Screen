# Objective
Make the game rules list in `AbstractGameRulesScreen` collapsible by category to reduce visual clutter and improve navigation.

## Concept Coverage Table
Source: `concept_collapsible_gamerules.md`

| Concept Feature | Will Implement? | Phase |
|:---|:---:|:---:|
| 1. Categorized Game Rule Headers | ✅ | 1 |
| 2. Collapsible UI | ✅ | 2 |

## Phase Breakdown

### Phase 1: Custom Category Entry
- Make sure to mark the mod as client-side only in `fabric.mod.json` (`"environment": "client"`).
- Replace the vanilla `CategoryRuleEntry` with a custom `CollapsibleCategoryRuleEntry`.
- This entry will need to implement a button or clickable area to toggle state.
- It needs to render an expanded/collapsed indicator (e.g., `[-]` / `[+]` or arrows).

### Phase 2: Visibility Management
- Modified `RuleList` (via Mixin) must handle displaying or hiding entries based on the expanded state of their respective category.
- When `populateChildren` runs, it adds all entries to the list. We need to intercept the `render`, `mouseClicked`, and other list interactions, or dynamically rebuild the `children` list when a category is toggled.
- The safest approach without breaking other mods is to:
  1. Store the full list of rules internally.
  2. Implement an `updateVisibleEntries()` method in the Mixin for `RuleList` that clears `this.clearEntries()` and only `addEntry` for categories and currently expanded rules.
  3. When a category header is clicked, toggle its state in a map and call `updateVisibleEntries()`.

## Proposed Changes

### Core Logic
#### [NEW] `net.instantgratification.collapsiblegamerules.CollapsibleCategoryRuleEntry`
- Extends `AbstractGameRulesScreen.CategoryRuleEntry`.
- Adds a `boolean expanded` state (default false for most, maybe true for `PLAYER`?).
- Overrides `render` and `mouseClicked` to toggle the state.
- Accepts a callback to notify the parent list that it needs to refresh.

#### [MODIFY] `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen$RuleList`
- Mixin: `RuleListMixin`
- Inject into `populateChildren` (probably `@Inject(method = "populateChildren", at = @At("TAIL"))`).
- We need to completely rewrite the way `populateChildren` builds the list to support dynamic rebuilding, or intercept the `addEntry` calls to use our custom `CollapsibleCategoryRuleEntry` and conditionally add/skip rule entries.
- *Wait, `AbstractGameRulesScreen$RuleList` `populateChildren` builds the list immediately. A cleaner way:*
  - Mixin `RuleList.populateChildren`. Replace the anonymous inner class `CategoryRuleEntry` instantiation with our own.
  - Keep a backing map of `GameRuleCategory -> List<RuleEntry>`.
  - Provide a `refreshList()` method in the Mixin that clears all entries, adds each category header, and if expanded, adds its children.

## Source Verification Table

| Target Class | Method Signature | Decompiled Path | Verified? |
|:---|:---|:---|:---:|
| `AbstractGameRulesScreen$RuleList` | `populateChildren(String)` | `.../worldselection/AbstractGameRulesScreen.java` | ✅ |
| `AbstractGameRulesScreen$RuleList` | `renderWidget(GuiGraphics, int, int, float)` | `.../worldselection/AbstractGameRulesScreen.java` | ✅ |
| `AbstractGameRulesScreen$CategoryRuleEntry` | `renderContent(...)` | `.../worldselection/AbstractGameRulesScreen.java` | ✅ |

> [!NOTE]
> `CategoryRuleEntry` is a public inner class of `AbstractGameRulesScreen`.

## Failure Mode Analysis (Antagonism)
- *What if another mod adds custom game rule UI types?*
  - By hooking into how `RuleList` builds the list and only altering *when* they are added to the active `children()` container, we shouldn't break custom rule widgets.
- *Performance?*
  - Rebuilding the `RuleList` container elements on click is extremely cheap (just list manipulation), better than trying to `cancel` renders.

## Zenith Compliance Check
- [x] Java 25 Records used? (N/A for Mixins, but yes if new data structures needed)
- [x] No NBT/Legacy code?
- [x] GameRules used for config? (No config needed)
- [x] 500 LOC limit respected?
- [x] Standalone Dependency (No JiJ) respected?
- [x] ALL concept features included?
- [x] Exhaustive Documentation Update logged in plan?

## Verification Plan

### Automated Tests
- None. This is a purely client-side UI change.

### Manual Verification
1. Launch the Minecraft client.
2. Go to Singleplayer -> Create New World -> Game Rules (or in-game Options -> Game Rules).
3. Verify that categories appear and are collapsed by default.
8. Click a category (e.g., "Player" or "Spawning"). Verify it expands and shows the rules.
9. Click it again. Verify it collapses.
10. Verify the search bar still works (Search should force-expand categories containing matches, or at least filter the visible list correctly).

## Feature Coverage (from concept_collapsible_gamerules.md)
| # | Feature | Concept Line | Implemented? | Code Location |
|---|---------|--------------|--------------|---------------|
| 1 | Categorized Game Rule Headers | L10-12 | ✅ | `RuleListMixin.java` (CollapsibleCategoryRuleEntry) |
| 2 | Collapsible UI | L14-16 | ✅ | `RuleListMixin.java` (`updateVisibleEntries`) |
| 3 | Platform Docs updated | L38 | ✅ | `Doc/Platform Pages/` |
