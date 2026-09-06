# 🧭 版本兼容性矩阵

| 参数 | 规格说明 |
| :--- | :--- |
| **当前定位版本** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **模组版本** | `1.0.9+26.2` |
| **支持游戏版本** | Minecraft 26.2+ (现代主权时代) |
| **Java 环境需求** | **Java 25+** (`release = 25`, toolchain Java 25) |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` (`*`) |
| **核心依赖库** | **DasikLibrary** `>=1.7.0` (当前: `1.7.4`) |
| **构建工具** | Loom `1.15.2` / Gradle `9.3+` (`--no-daemon`) |
| **运行环境** | **仅客户端** (`"environment": "client"`) |
| **开源协议** | **GPL-3.0-or-later** |

---

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

---

## 📊 多时代架构与生命周期矩阵

Collapsible Game Rules 专为现代主权时代（`MC 26.2+`）原生构建，全面采用现代 GUI 渲染管线（`GuiGraphicsExtractor`）、现代 Java 25 Stream API（`.toList()`）以及零混淆运行时环境。

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

## 🔒 模组版本防护与类加载保护

为了防止存档损坏并避免在不兼容的游戏引擎版本上发生崩溃循环，Collapsible Game Rules 内置零依赖启动防护：`net.instantgratification.collapsiblegamerules.util.ModVersionGuard`。

### 防护检查机制
在 `ModInitializer.onInitialize()` 阶段执行：
```java
ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
```

若在缺少该类或缺失 Knot ClassLoader 绑定的不兼容版本中启动，模组将立即终止加载并打印结构化警告：

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

## 📦 依赖声明规范

模组于 `src/main/resources/fabric.mod.json` 中明确声明依赖：

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

## 🗄️ 发布归档与构建产物

官方发布版本存档于仓库的 `Archive Jar of all versions/` 目录：

* `collapsible-game-rules-1.0.9+26.2.jar` (现行发布版)
* `collapsible-game-rules-1.0.8+26.2.jar` (前期构建版)
* `collapsible-game-rules-1.0.7+26.2.jar` (特性重构版)

---

## 🔗 相关文档

* [[🏠 概览与首页|zh_cn-Home]]
* [[🛠️ 开发者环境配置与 Gradle 构建|zh_cn-Developer-Setup-and-Building]]
* [[📚 DasikLibrary API 集成|zh_cn-API-and-Library-Integration]]
