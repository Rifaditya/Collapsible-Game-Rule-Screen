# Audit Helper for Moderators

This document provides a quick overview for human moderators (e.g., CurseForge, Modrinth) to verify the safety and scope of this mod.

## Scope
- purely client-side UI mod.
- Modifies `AbstractGameRulesScreen$RuleList` to add collapsible functionality.

## Network
- NO network packets are sent or received by this mod.

## File System
- NO files are read or written outside of standard Minecraft/Fabric procedures. No config file is generated.

## Mixins
- `AbstractGameRulesScreenRuleListMixin`: Injects into `populateChildren` to replace the standard list generation with a dynamic, collapsible list.

This mod relies solely on `fabric-api` and `dasik-library`.
