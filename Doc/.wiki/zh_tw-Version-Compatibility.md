# 🧭 版本相容性矩陣

| 參數 | 規格說明 |
| :--- | :--- |
| **當前定位版本** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **模組版本** | `1.0.9+26.2` |
| **支援遊戲版本** | Minecraft 26.2+ (現代主權時代) |
| **Java 環境需求** | **Java 25+** (`release = 25`, toolchain Java 25) |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` (`*`) |
| **核心依賴庫** | **DasikLibrary** `>=1.7.0` (當前: `1.7.4`) |
| **建構工具** | Loom `1.15.2` / Gradle `9.3+` (`--no-daemon`) |
| **運行環境** | **僅客戶端** (`"environment": "client"`) |
| **開源協議** | **GPL-3.0-or-later** |

---

> 📌 **倉庫源碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

---

## 📊 多時代架構與生命週期矩陣

Collapsible Game Rules 專為現代主權時代（`MC 26.2+`）原生建構，全面採用現代 GUI 渲染管線（`GuiGraphicsExtractor`）、現代 Java 25 Stream API（`.toList()`）以及零混淆執行時期環境。

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                           MULTI-ERA RUNTIME LIFECYCLE MATRIX                            │
├───────────────────┬─────────────┬──────────────┬───────────────┬────────────────────────┤
│ Minecraft Anchor  │ Java Level  │ Loader Bound │ DasikLibrary  │ Lifecycle Status       │
├───────────────────┼─────────────┼──────────────┼───────────────┼────────────────────────┤
│ **MC 26.2+**      │ **Java 25** │ `>=0.19.1`   │ `>=1.7.0`     │ 🟢 Active Target (Loom)│
│ MC 26.1.2         │ Java 25     │ `>=0.18.4`   │ `>=1.7.0`     │ 🟡 Forward Compatible  │
│ MC 1.21.x         │ Java 21     │ N/A          │ N/A           │ ⚪ Unsupported (Modern)│
│ MC 1.20.1         │ Java 17     │ N/A          │ N/A           │ ⚪ Unsupported (Modern)│
└───────────────────┴─────────────┴──────────────┴───────────────┴────────────────────────┘
```

---

## 🔒 模組版本防護與類別載入保護

為了防止存檔損毀並避免在不相容的遊戲引擎版本上發生當機循環，Collapsible Game Rules 內建零依賴啟動防護：`net.instantgratification.collapsiblegamerules.util.ModVersionGuard`。

### 防護檢查機制
在 `ModInitializer.onInitialize()` 階段執行：
```java
ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
```

若在缺少該類別或缺失 Knot ClassLoader 綁定的不相容版本中啟動，模組將立即終止載入並印出結構化警告：

```
=====================================================================
 [PRE-RELEASE / VERSION GUARD WARNING] Collapsible Game Rules
---------------------------------------------------------------------
 CRITICAL: Incompatible Minecraft Game Runtime or Missing Class!
 Required Class : net.minecraft.world.level.gamerules.GameRules
 Status         : UNRESOLVED AT RUNTIME

 Safety Protection:
 Execution halted to prevent unreleased/incompatible build deployment
 or broken world state save corruption.

 Troubleshooting Steps:
 1. Verify target Minecraft version (26.2+ release drop).
 2. Ensure all required dependencies (Fabric API, DasikLibrary) are loaded.
 3. Build/Download a verified matching release JAR from Modrinth/CurseForge.
=====================================================================
```

---

## 📦 依賴宣告規範

模組於 `src/main/resources/fabric.mod.json` 中明確宣告依賴：

```json
{
  "schemaVersion": 1,
  "id": "collapsible-game-rules",
  "version": "${version}",
  "name": "Collapsible Game Rules",
  "description": "Makes the GameRules UI screens collapsible by category.",
  "authors": [
    "Dasik (Rifaditya)"
  ],
  "license": "GPL-3.0-or-later",
  "environment": "client",
  "entrypoints": {
    "main": [
      "net.instantgratification.collapsiblegamerules.CollapsibleGameRulesFabric"
    ],
    "client": [
      "net.instantgratification.collapsiblegamerules.CollapsibleGameRulesFabricClient"
    ]
  },
  "mixins": [
    "collapsible-game-rules.mixins.json"
  ],
  "depends": {
    "fabricloader": ">=0.19.1",
    "minecraft": ">=26.2-",
    "java": ">=25",
    "fabric-api": "*",
    "dasik-library": ">=1.7.0"
  }
}
```

---

## 🗄️ 發布歸檔與建構產物

官方發布版本存檔於倉庫的 `Archive Jar of all versions/` 目錄：

* `collapsible-game-rules-1.0.9+26.2.jar` (現行發布版)
* `collapsible-game-rules-1.0.8+26.2.jar` (前期建構版)
* `collapsible-game-rules-1.0.7+26.2.jar` (特性重構版)

---

## 🔗 相關文件

* [[🏠 概覽與首頁|zh_tw-Home]]
* [[🛠️ 開發者環境配置與 Gradle 構建|zh_tw-Developer-Setup-and-Building]]
* [[📚 DasikLibrary API 整合|zh_tw-API-and-Library-Integration]]
