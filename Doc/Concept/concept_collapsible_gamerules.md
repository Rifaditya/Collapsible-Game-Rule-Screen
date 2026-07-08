# Concept: Collapsible Game Rules

## Identity
**modid**: `collapsible-game-rules`
**Reasoning**: "Respect the Player's Time, Not the Game's Rules." 
The Game Rules screen becomes bloated with every mod adding its own settings. This mod restores order by providing collapsible category headers, allowing players to focus on what matters instantly.

---

## Core Mechanics

### 1. Persistent Collapsible Headers
- **Visuals**: Dynamic arrow indicators (▼ for expanded, ▶ for collapsed).
- **Interactions**:
    - **Single Click**: Toggles the category's expansion state.
    - **Hover Effect**: Subtle background highlight on the header entry for visual feedback.
    - **Keyboard Support**: Space/Enter toggles the state when the header is focused.
- **Persistence**: Remembers which categories were collapsed across screen sessions. 

### 2. Smart Search Expansion
- **Behavior**: If a user types into the search bar, any category containing a matching GameRule MUST automatically expand to show the results.
- **Reset**: When clearing the search, categories should ideally return to their previous manual state.

### 3. "Global Actions" Header
- A special entry at the top of the list (or integrated into the UI) providing:
    - **Collapse All**: Collapses every category.
    - **Expand All**: Expands every category.

### 4. Dynamic Integration (DasikLibrary)
- Leverages `DasikLibrary` metadata (if available) to group rules by Mod ID or specific sub-headings defined by modders.
- Defaults to standard Minecraft `GameRuleCategory` groups if no extra metadata is found.

---

## Technical Specifications (Standard UI)

### Implementation Strategy
1. **Screen Override**: Mixin into `AbstractGameRulesScreen$RuleList`.
2. **State Management**: Use a client-side `Config` or `Set<String>` to track persistent collapse states.
3. **Rendering**: Custom `RuleEntry` implementation for the Category Header with `GuiGraphics` calls for vector arrows rather than simple text.
4. **Search Hook**: Wrap the `populateChildren(String filter)` method to ensure search-driven expansion.

### Visual Requirements
- **Color Palette**: Respects Vanilla texture colors but uses a premium highlight (e.g., 0xFFFFFFAA for hover).
- **Animations**: (Optional/Stretch) Minor scale bounce or smooth expansion if compatible with Minecraft's standard list scrolling logic.

---

## Configuration
None required. Client-side only.

## Assets Needed
- **Arrow Textures**: Custom 8x8 or 16x16 arrow icons (if not using vanilla character codes).
- **Language Strings**: 
    - `gui.collapsible-game-rules.expand_all`
    - `gui.collapsible-game-rules.collapse_all`

---

## Feature Parity Checklist
- [x] Feature 1: Persistent Collapsible Headers
- [x] Feature 2: Smart Search Expansion
- [x] Feature 3: Global "Collapse/Expand All" Actions
- [x] Feature 4: Keyboard Navigation Support
- [x] Feature 5: DasikLibrary Metadata Integration


---

## Philosophy Fit
**Summary:** Makes the GameRules UI screen collapsible by category. Feature-rich reorganization with Smart Search, Keyboard Navigation, and Persistent UI states. (Minecraft 26.*)

**No Backports:** This mod strictly targets **Minecraft 26.1.2 ("Tiny Takeover" Release)**. Older versions are unsupported to ensure absolute stability with the modern UI engine.

## Project Metadata
- **Version Format**: `1.0.0+build.N`
- **Internal Dependency**: `"dasik-library": "*"` (Standalone)
- **Target Platform**: Minecraft 26.* (Fabric)
