# 🎫 [BL-CGR-007] Fix `DasikSupportHelper.openKofi(Screen)` ABI Mismatch in `GlobalActionsRuleEntry` (`v26.2` & `v26.3`)

- **Ticket ID**: `[BL-CGR-007]`
- **Category**: `[BUGFIX]`
- **Priority**: `[CRITICAL]`
- **Target Version**: `v26.2` (`1.0.46+26.2`+), `v26.3` (`1.0.49+26.3`+) *(audit all anchors in Stage 1)*
- **Scope**: `src/main/java/net/instantgratification/collapsiblegamerules/ui/GlobalActionsRuleEntry.java:125` + ABI bytecode contract test + Modrinth profile deploy
- **Status**: `📌 DEFERRED`
- **Grilling Status**: `✅ PASSED`
- **Target Repository / Root**: `Collapsible Game Rule Screen/collapsible-game-rules`
- **Umbrella Reference**: [`BL-WS-051`](file:///E:/Minecraft%20Project/tickets/BL-WS-051.md)
- **Date Added**: `2026-11-25`

---

## 🎯 1. Problem Statement & Player Impact

`code_auditor.py --check-profiles` and hub archive scanning identified that `collapsible-game-rules-1.0.45+26.2.jar` and `collapsible-game-rules-1.0.48+26.3.jar` still invoke the removed bytecode descriptor `Lnet/dasik/social/api/config/DasikSupportHelper;openKofi(Lnet/minecraft/client/gui/screens/Screen;)V` inside `GlobalActionsRuleEntry.java:125` (`DasikSupportHelper.openKofi(null)`).

Because `DasikLibrary 1.13.x` widened `DasikSupportHelper.openKofi` to `Object` (moving the `Screen` overload strictly to `@Environment(EnvType.CLIENT) ClientSupportScreenHelper.openKofi(Screen)` per `BL-DASIK-008`), clicking the Ko-fi button in Collapsible Game Rules crashes with `NoSuchMethodError` against `DasikLibrary 1.13.2`.

---

## 🔬 2. Architectural Blueprint

1. **Target File**:
   - `src/main/java/net/instantgratification/collapsiblegamerules/ui/GlobalActionsRuleEntry.java` (line `125`) across affected branch/anchor directories (Stage 1 audits whether `26.2` lives on a branch/subproject or root repo).
   - Replace `DasikSupportHelper.openKofi(null)` with `net.dasik.social.api.client.gui.ClientSupportScreenHelper.openKofi((net.minecraft.client.gui.screens.Screen) null)` (or `DasikSupportHelper.openKofi((Object) null)`).
2. **Bytecode ABI Guard**:
   - Add pure-JDK `DataInputStream` constant-pool check in test suite asserting `GlobalActionsRuleEntry.class` has **ZERO** `DasikSupportHelper;openKofi(Lnet/minecraft/client/gui/screens/Screen;)V` references.
3. **Rebuild & 5-Point Deploy**:
   - Bump `mod_version`, run `./gradlew.bat test build --no-daemon --console=plain`, deploy via 5-Point distribution (including Modrinth `26.3` and `Fabric 26.2 (1)` profiles), and confirm `code_auditor.py --check-profiles` reports `0` `collapsible-game-rules` findings.

---

## 🛡️ 3. Blast Radius & Multi-Era Parity Table

| Dimension | Risk / Failure Mode | Blast Radius | Mitigation |
| :--- | :--- | :--- | :--- |
| **GameRules Screen UI (`26.3` `CategoryRuleEntry`)** | Unintended edits to `CategoryRuleEntry` or search/collapse widgets | UI crash on `26.3` | Touch ONLY `GlobalActionsRuleEntry.java:125` `openKofi(...)` invocation |
| **Dedicated Server Sidedness** | Client class leakage | Zero (`"environment": "client"` mod) | Preserve `@Environment(EnvType.CLIENT)` isolation |
| **World Saves / GameRule Values** | None | Zero | Touches only Ko-fi link click handler |

---

## 🚫 4. Anti-Goals (Creep Shield)

1. ❌ **Do NOT refactor `CategoryRuleEntry`, search bar, or collapsible tree layout** — touch only line `125` Ko-fi click invocation.
2. ❌ **Do NOT restore `DasikSupportHelper.openKofi(Screen)` inside `DasikLibrary`**.
3. ❌ **Do NOT bundle `BL-CGR-005` (Wiki) or `BL-CGR-006` (`DasikPromptRegistry`)**.

---

## 🏁 5. Client-Only 5-Pillar Verification Checklist

- [ ] **Pillar 1 (Fuzz)**: Existing GameRule screen / filter fuzz tests pass.
- [ ] **Pillar 2 (Concurrency)**: Existing concurrent UI state tests pass.
- [ ] **Pillar 3 (GUI & Translation)**: Ko-fi tooltip/link keys verified intact.
- [ ] **Pillar 4 (Bytecode Contract)**: `.class` constant-pool test asserts `0` `DasikSupportHelper;openKofi(Screen)V` references.
- [ ] **Pillar 5 (Sidedness & Profile Audit)**: `code_auditor.py --check-profiles` reports `0` `collapsible-game-rules` ABI errors across all Modrinth profiles.
