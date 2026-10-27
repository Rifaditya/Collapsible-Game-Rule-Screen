# 🎫 [BL-CGR-002] Adopt 5-Pillar Testing & Sidedness Verification Harness

- **Ticket ID**: `[BL-CGR-002]`
- **Category**: `[TECH_DEBT]`
- **Priority**: `[MEDIUM]`
- **Scope**: `Universal (>=26.1.2- / Modern 26.3 Sovereign)`
- **Status**: ✅ RESOLVED
- **Target Repository / Root**: `Collapsible Game Rule Screen\collapsible-game-rules`

---

## 🎯 1. Problem Statement & Player Impact
Currently, `collapsible-game-rules` lacks the studio's mandatory 5-Pillar Testing Harness. As a client-only GUI mod (`"environment": "client"`), without automated property and config fuzzing, concurrency stress tests, optional GUI / translation AST validation, headless client state machine simulation, and bytecode sidedness/license contracts, code refactors risk regressions, subtle multi-thread race conditions during search filtering, config serialization corruption, and Knot ClassLoader crashes. Adopting the Client-Only 5-Pillar Adaptation Doctrine guarantees rock-solid stability and zero regressions across all supported versions (`>=26.1.2-`).

## 🔬 2. Architectural Blueprint & Target Classes
- Test Package: `net.instantgratification.collapsiblegamerules` (in `src/test/java/`)
- Target Test Classes to Implement (Client-Only 5-Pillar Adaptation):
  1. `CollapsibleGameRulesStressFuzzTest.java` (Pillar 1 - Chaos Property & Config Fuzzing):
     - Executes 20,000 to 50,000 randomized iterations fuzzing `GameRuleStateConfig`, `CategoryPrettifier`, `SearchHighlightHelper`, and `GameRuleSliderHelper` with malformed strings, nulls, corrupted JSON, unicode surrogate pairs, and extreme bounds under the Freedom Over Anti-Crash policy.
  2. `CollapsibleGameRulesConcurrencyLoadTest.java` (Pillar 2 - Virtual Concurrency Load):
     - Stresses mod state, `GameRuleStateConfig`, `CategoryTreeBuilder`, and search highlight caches across 50 virtual worker threads to verify thread-safety, zero deadlocks, and zero `ConcurrentModificationException`.
  3. `CollapsibleGameRulesGuiIntegrationTest.java` (Pillar 3 - Optional GUI & Translation AST Verification):
     - Headlessly verifies ModMenu metadata in `fabric.mod.json` (`modmenu.donate`, `modmenu.kofi`), and asserts 100% key coverage across `assets/collapsiblegamerules/lang/en_us.json` for all categories, buttons, and badges with zero missing keys or broken format specifiers.
  4. `CollapsibleGameRulesStateSimulationTest.java` (Pillar 4 - Headless Client State Machine Simulation):
     - Headlessly simulates client GUI state machine lifecycles without requiring OpenGL or GLFW (`-Djava.awt.headless=true`): category toggle transitions (expand/collapse), global toolbar bulk actions, live search query filtering, and category reset to defaults.
  5. `CollapsibleGameRulesSidednessContractTest.java` (Pillar 5 - Sidedness & License Hygiene):
     - Automated source and bytecode scanner asserting `"environment": "client"` in `fabric.mod.json`, zero unmapped `Class.forName()` calls (verifying complete purge of `ModVersionGuard`), valid Mixin registration in `collapsible-game-rules.mixins.json`, and 100% single-line GPLv3 license headers.

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **Universal Modern Anchor (>=26.1.2- / 26.3 Sovereign Lead - Java 25)**:
  - Fabric Loom 1.15+ test runner on Java 25.
  - Headless test execution isolated from OpenGL/GLFW display dependencies.
  - Saturated arithmetic bounds and Freedom Over Anti-Crash config recovery.
  - Standalone single-codebase deployment supporting all 26.x releases.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Headless Environment Display Errors: Zero `MinecraftClient.getInstance()`, `Window`, `RenderSystem`, or GLFW native calls in unit tests to prevent fatal `HeadlessException`.
- Knot ClassLoader Runtime Safety: Total elimination of obsolete `ModVersionGuard.java` and unremapped `Class.forName()` to prevent fatal production classloading crashes.
- Corrupted Config Recovery: Malformed or truncated `config/collapsible-gamerules.json` resets gracefully to defaults without crashing the game client.
- Concurrent Search & Collapse Mutations: Atomic references and immutable snapshots prevent `ConcurrentModificationException` during rapid search typing and category toggling.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. **Step 0**: Fail-Fast Quarantine Sweep — Fix `RELEASE_QUEUE.md` merge markers, purge `ModVersionGuard.java`, remove `ModVersionGuard` invocation from `CollapsibleGameRulesFabric.java`, and commit working tree baseline.
2. **Step 1**: Implement `CollapsibleGameRulesSidednessContractTest.java` (Pillar 5 - Sidedness & License Hygiene).
3. **Step 2**: Implement `CollapsibleGameRulesStressFuzzTest.java` (Pillar 1 - Chaos Property & Config Fuzzing).
4. **Step 3**: Implement `CollapsibleGameRulesConcurrencyLoadTest.java` (Pillar 2 - Virtual Concurrency Load).
5. **Step 4**: Implement `CollapsibleGameRulesGuiIntegrationTest.java` (Pillar 3) and `CollapsibleGameRulesStateSimulationTest.java` (Pillar 4).
6. **Step 5**: Full compilation, SemVer bump (`1.0.47+26.3`), 5-point archive distribution, queue update, backlog sweep, and git lockstep push (`Build = Push`).

## 🏁 6. 5-Pillar Verification Checklist
- [x] **Step 0 (Quarantine Sweep)**: `code_auditor.py` reports 0 critical errors; clean working tree.
- [x] **Pillar 1 (Chaos Fuzzing)**: Chaos property & config fuzzing passes 20k-50k iterations.
- [x] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [x] **Pillar 3 (GUI & Lang AST)**: ModMenu metadata and `en_us.json` translation keys validated headlessly.
- [x] **Pillar 4 (State Simulation)**: Client state machine transitions (toggle, search, reset) pass headlessly without OpenGL.
- [x] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts `"environment": "client"`, 0 `ModVersionGuard` references, 0 unmapped `Class.forName`, and 100% GPLv3 headers.
- [x] **Clean Build & 5-Point Deploy**: Clean `./gradlew test build` and 5-point distribution parity.
