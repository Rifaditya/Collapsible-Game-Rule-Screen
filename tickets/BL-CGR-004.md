# 🎫 [BL-CGR-004] Package Baseline Refmap for Production Mixin Stability

- **Ticket ID**: `[BL-CGR-004]`
- **Category**: `[BUGFIX]`
- **Priority**: `[HIGH]`
- **Scope**: `Modern 26.3 Sovereign Lead`
- **Status**: ✅ RESOLVED
- **Target Repository / Root**: `Collapsible Game Rule Screen\collapsible-game-rules`

---

## 🎯 1. Problem Statement & Player Impact
In published release JARs running on Minecraft 26.3, players observe the following startup warning in their client logs:
```text
[FabricLoader/Mixin/WARN]: Reference map 'collapsible-game-rules-refmap.json' for collapsible-game-rules.mixins.json could not be read. If this is a development environment you can ignore this message
```
Because Minecraft 26.x anchors compile against direct/official Mojang mappings without an intermediary reobfuscation step, Fabric Loom's annotation processor does not dynamically emit a refmap JSON file. However, `collapsible-game-rules.mixins.json` explicitly references `"refmap": "collapsible-game-rules-refmap.json"`. Without a static baseline refmap present in `src/main/resources/`, the file is completely missing from the root of packaged JARs. While Mixins still apply under un-obfuscated runtime names, the warning causes player confusion, log noise, and diagnostic friction.

## 🔬 2. Architectural Blueprint & Target Files
- Target File 1: `src/main/resources/collapsible-game-rules-refmap.json`
  - Populate with the studio-standard baseline JSON refmap:
    ```json
    {
      "mappings": {},
      "data": {}
    }
    ```
- Target File 2: `src/test/java/net/instantgratification/collapsiblegamerules/CollapsibleGameRulesSidednessContractTest.java`
  - Add test asserting that if `refmap` is declared in `collapsible-game-rules.mixins.json`, the referenced file MUST physically exist under `src/main/resources/` with valid JSON structure.
- Target File 3: `gradle.properties`
  - Calibrate SemVer: bump `mod_version` from `1.0.47+26.3` to `1.0.48+26.3`.
- Target File 4: `Doc/Develop/Changelogs/History.md` & `RELEASE_QUEUE.md`
  - Record the `1.0.48+26.3` release entry and queue status.

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **Universal Modern Anchor (26.3 Sovereign Lead - Java 25)**:
  - Aligns with studio reference implementations (`Better Dogs 26.3`, `Ore Amplifier 26.3`) where static baseline refmaps ensure 100% clean Knot ClassLoader and FabricLoader startup sequences.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Missing Refmap Warning: Completely eliminated in both development and production launcher instances.
- Packaging Hygiene: `processResources` automatically includes `collapsible-game-rules-refmap.json` into the root of the `.jar` without additional Gradle task complexity.
- Test Guard: Automated Pillar 5 assertion prevents future regressions if mixin configurations are modified.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. **Step 1**: Pre-Step Rebase & SemVer Bump — Pull latest changes and bump `mod_version` to `1.0.48+26.3` in `gradle.properties`.
2. **Step 2**: Author Baseline Refmap — Create `src/main/resources/collapsible-game-rules-refmap.json`.
3. **Step 3**: Pillar 5 Regression Guard — Extend `CollapsibleGameRulesSidednessContractTest.java` to assert refmap file existence and validity.
4. **Step 4**: Headless Test Suite & Build Verification — Run `./gradlew test` (all tests green) and `./gradlew build` to generate `collapsible-game-rules-1.0.48+26.3.jar`.
5. **Step 5**: Archive Synchronization & Release Queue Alignment — Verify 4-point archive deploy (`build/libs/`, `Archive Jar of all versions/MC 26.3/`, `minecraft-mod-release-hub/archives/Collapsible Game Rules/`), update `RELEASE_QUEUE.md` and `History.md`.
6. **Step 6**: Pre-Commit Secret Scan & Lockstep Git Push (`Build = Push`).

## 🏁 6. 5-Pillar Verification Checklist
- [x] Pillar 1: Fuzzing tests pass without exception
- [x] Pillar 2: Virtual concurrency load tests execute cleanly
- [x] Pillar 3: GUI translation AST validation passes
- [x] Pillar 4: Client state machine simulation completes 100% green
- [x] Pillar 5: Sidedness contract asserts refmap presence, client environment, and GPLv3 headers
- [x] Production Verification: `tar -tf` on built JAR confirms `collapsible-game-rules-refmap.json` is packaged at root
