# Developer Documentation

**Collapsible Game Rules** is a purely client-side UI enhancement for Minecraft's GameRules screen.

## Architecture
- It uses a Mixin into `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen$RuleList` to override how children are populated.
- It introduces `CollapsibleCategoryRuleEntry` which renders the category header as a clickable toggle button.
- State is managed within the Mixin and the visible entries list is dynamically rebuilt when categories are toggled.
- Depends on `dasik-library` for standard internal tooling.

## Getting Started
To integrate or contribute, clone the repository and run:
`./gradlew build`
