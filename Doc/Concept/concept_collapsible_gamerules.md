# Concept: Collapsible Game Rules

## Philosophy Fit

**Collection**: Instant Gratification
**Reasoning**: "Respect the Player's Time, Not the Game's Rules." The Game Rules screen can become overwhelming with many mods installed. This mod reduces friction by organizing these rules into collapsible category headers, allowing players to find what they need instantly without scrolling through a massive list.

## Core Mechanics

### 1. Categorized Game Rule Headers
Game Rules within the `InWorldGameRulesScreen` (and `WorldCreationGameRulesScreen` / `AbstractGameRulesScreen`) will be grouped under their respective `GameRuleCategory`.
Each Category will have a clickable header.

### 2. Collapsible UI
Clicking a category header will toggle the visibility of all Game Rules within that category.
The collapse state should ideally be remembered or default to closed for non-vanilla categories (or all categories) depending on preference, but a simple toggle is the core requirement.

## Implementation Details (Zenith Standard)

1. **Target**: `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen$RuleList` and its entries.
2. We will need to inject or wrap the `RuleList` creation to insert our custom `CategoryListEntry` that acts as a toggle button.
3. We will need to manage the visibility of the child entries based on the category's toggle state.

## Configuration (GameRules)
None required. This is a purely client-side UI enhancement.

## Project Metadata
- Version Format: `1.0.0+build.N`
- Internal Dependency: `"dasik-library": "*"` (Standalone)

## Assets Needed
- Category toggle icons (e.g., expanded/collapsed arrows) if vanilla button widgets aren't sufficient.
- Lang keys for any new UI text.

## Implementation Checklist
- [ ] Feature 1: Categorized Game Rule Headers
- [ ] Feature 2: Collapsible UI
- [ ] Platform Docs updated (CurseForge/Modrinth)
