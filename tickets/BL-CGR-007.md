# 🎫 [BL-CGR-007] Fix `DasikSupportHelper.openKofi(Screen)` ABI Mismatch in `GlobalActionsRuleEntry` (`26.3` + Retire `1.0.45+26.2`)

- **Ticket ID**: `[BL-CGR-007]`
- **Category**: `[BUGFIX]`
- **Priority**: `[CRITICAL]`
- **Target Version**: `v26.3` (`1.0.49+26.3`) *(Single Sovereign Lead; retire obsolete `1.0.45+26.2` from `Fabric 26.2 (1)` profile per Director A1)*
- **Scope**: `src/main/java/net/instantgratification/collapsiblegamerules/ui/GlobalActionsRuleEntry.java:125` + `gradle.properties` (`dasik_library_version=1.13.2`) + `fabric.mod.json` (`"dasik-library": ">=1.11.2"`) + ABI bytecode contract test + Modrinth `26.3`/`Fabric 26.3` deploy + `Fabric 26.2 (1)` profile cleanup
- **Status**: `✅ RESOLVED`
- **Grilling Status**: `✅ PASSED`
- **Target Repository / Root**: `Collapsible Game Rule Screen/collapsible-game-rules`
- **Umbrella Reference**: [`BL-WS-051`](file:///E:/Minecraft%20Project/tickets/BL-WS-051.md)
- **Date Added**: `2026-11-25`

---

## 🎯 1. Problem Statement & Player Impact

`code_auditor.py --check-profiles` and hub archive scanning identified that `collapsible-game-rules-1.0.45+26.2.jar` and `collapsible-game-rules-1.0.48+26.3.jar` still invoke the removed bytecode descriptor `Lnet/dasik/social/api/config/DasikSupportHelper;openKofi(Lnet/minecraft/client/gui/screens/Screen;)V` inside `GlobalActionsRuleEntry.java:125` (`DasikSupportHelper.openKofi(null)`).

Because `DasikLibrary 1.13.x` widened `DasikSupportHelper.openKofi` to `Object` (moving the `Screen` overload strictly to `@Environment(EnvType.CLIENT) ClientSupportScreenHelper.openKofi(Screen)` per `BL-DASIK-008`), clicking the Ko-fi button in Collapsible Game Rules crashes with `NoSuchMethodError` against `DasikLibrary 1.13.2`. *(Stage 1 confirmed `collapsible-game-rules` is a standalone `26.3` repo with no `26.2` source directory or branch; `1.0.45+26.2` was superseded by `1.0.46+26.3`.)*

---

## 🔬 2. Architectural Blueprint

1. **Target File (`26.3` Standalone)**:
   - `src/main/java/net/instantgratification/collapsiblegamerules/ui/GlobalActionsRuleEntry.java` (line `125`).
   - Replace `DasikSupportHelper.openKofi(null)` with `ClientSupportScreenHelper.openKofi((Screen) null)` (importing `net.dasik.social.api.client.gui.ClientSupportScreenHelper` and `net.minecraft.client.gui.screens.Screen`).
   - Update `gradle.properties` `dasik_library_version=1.9.2` -> `1.13.2` and `fabric.mod.json` `"dasik-library": ">=1.9.0"` -> `">=1.11.2"` (`[PAT-20261022-001]`).
2. **Bytecode ABI Guard**:
   - Add pure-JDK `DataInputStream` constant-pool check in `CollapsibleGameRulesSidednessContractTest` asserting `GlobalActionsRuleEntry.class` has **ZERO** `DasikSupportHelper;openKofi(Lnet/minecraft/client/gui/screens/Screen;)V` references and contains `ClientSupportScreenHelper;openKofi(Lnet/minecraft/client/gui/screens/Screen;)V`.
3. **Rebuild, 5-Point Deploy & `1.0.45+26.2` Retirement**:
   - Bump `mod_version` to `1.0.49+26.3`, run `./gradlew.bat test build --no-daemon --console=plain`, deploy `1.0.49+26.3` to all 5 points (including BOTH `26.3` and `Fabric 26.3` Modrinth profiles), mark `1.0.45+26.2` as `(SKIP - SUPERSEDED BY 26.3 / ABI MISMATCH)` in `RELEASE_QUEUE.md`, remove obsolete `collapsible-game-rules-1.0.45+26.2.jar` from `ModrinthApp/profiles/Fabric 26.2 (1)/mods/`, and confirm `code_auditor.py --check-profiles` reports `0` `collapsible-game-rules` findings.

---

## 🛡️ 3. Blast Radius & Multi-Era Parity Table

| Dimension | Risk / Failure Mode | Blast Radius | Mitigation |
| :--- | :--- | :--- | :--- |
| **GameRules Screen UI (`26.3` `CategoryRuleEntry`)** | Unintended edits to `CategoryRuleEntry` or search/collapse widgets | UI crash on `26.3` | Touch ONLY `GlobalActionsRuleEntry.java:125` `openKofi(...)` invocation |
| **Dedicated Server Sidedness** | Client class leakage | Zero (`"environment": "client"` mod) | Preserve `"environment": "client"` isolation |
| **Legacy `1.0.45+26.2` Artifact** | Stale JAR left in `Fabric 26.2 (1)` profile crashing against `DasikLibrary 1.13.2` | `NoSuchMethodError` in `Fabric 26.2 (1)` | Retire `collapsible-game-rules-1.0.45+26.2.jar` from `Fabric 26.2 (1)/mods/` & mark superseded in `RELEASE_QUEUE.md` per Director A1 |

---

## 🚫 4. Anti-Goals (Creep Shield)

1. ❌ **Do NOT refactor `CategoryRuleEntry`, search bar, or collapsible tree layout** — touch only line `125` Ko-fi click invocation.
2. ❌ **Do NOT restore `DasikSupportHelper.openKofi(Screen)` inside `DasikLibrary`**.
3. ❌ **Do NOT bundle `BL-CGR-005` (Wiki) or `BL-CGR-006` (`DasikPromptRegistry`)**.

---

## 🏁 5. Client-Only 5-Pillar Verification Checklist

- [x] **Pillar 1 (Fuzz)**: Existing GameRule screen / filter fuzz tests pass.
- [x] **Pillar 2 (Concurrency)**: Existing concurrent UI state tests pass.
- [x] **Pillar 3 (GUI & Translation)**: Ko-fi tooltip/link keys verified intact.
- [x] **Pillar 4 (Bytecode Contract)**: `.class` constant-pool test asserts `0` `DasikSupportHelper;openKofi(Screen)V` references.
- [x] **Pillar 5 (Sidedness & Profile Audit)**: `code_auditor.py --check-profiles` reports `0` `collapsible-game-rules` ABI errors across all Modrinth profiles.
