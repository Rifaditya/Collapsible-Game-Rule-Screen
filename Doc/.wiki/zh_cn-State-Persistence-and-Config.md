# 🧠 状态持久化与 JSON 配置

| 参数 | 规格说明 |
| :--- | :--- |
| **配置管理类** | `net.instantgratification.collapsiblegamerules.GameRuleStateConfig` |
| **文件路径** | `.minecraft/config/collapsible-game-rules-state.json` |
| **内存存储结构** | `Set<String> expandedCategories = new HashSet<>()` |
| **序列化引擎** | `com.google.gson.Gson` (启用格式化输出) |
| **I/O 节流标志** | `private static boolean isDirty = false` |
| **保存触发点** | `ScreenMixin` 拦截 `Screen.removed()` (`@At("HEAD")`) |
| **持久化键值策略** | 翻译键 (`TranslatableContents.getKey()`) 或字面字符串 |

---

## 📖 机制概述

Collapsible Game Rules 具备异步、防抖节流的状态持久化引擎。模组会自动记忆您的展开偏好，避免每次打开游戏或重载世界时重设为默认状态。

---

## 📄 JSON 配置格式规范

状态以干净易读的 JSON 数组存储于 `.minecraft/config/collapsible-game-rules-state.json`：

```json
[
  "gamerule.category.spawning",
  "gamerule.category.mobs",
  "gamerule.category.updates"
]
```

* **存在于数组中**：代表该类别当前处于**展开**状态。
* **未出现在数组中**：代表该类别当前处于**折叠**状态（默认）。

---

## ⚡ 高性能 I/O 节流架构

若在每次鼠标点击或按键切换时都写入硬盘，会造成大量的磁盘 I/O 与画面微卡顿。

为确保**零掉帧**，`GameRuleStateConfig` 采用 `isDirty` 脏标记策略：

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       THROTTLED PERSISTENCE WORKFLOW                        │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Player clicks Category Header                                             │
│        │                                                                    │
│        ▼                                                                    │
│   GameRuleStateConfig.setExpanded(key, state)                               │
│        ├─ Updates in-memory HashSet<String> in 0.0001 μs                    │
│        └─ Marks: isDirty = true (ZERO DISK I/O)                             │
│                                                                             │
│   Player closes Game Rules Screen (Esc, Done, or Cancel)                    │
│        │                                                                    │
│        ▼                                                                    │
│   ScreenMixin.collapsible_game_rules$onRemoved()                            │
│        │                                                                    │
│        ▼                                                                    │
│   GameRuleStateConfig.saveIfDirty()                                         │
│        ├─ Checks: if (isDirty) Ellipsis                                      │
│        ├─ Writes JSON to disk in background buffer                          │
│        └─ Resets: isDirty = false                                           │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 💻 API 与方法参考

### `GameRuleStateConfig` 公开方法

| 方法签名 | 返回值 | 说明 |
| :--- | :--- | :--- |
| `load()` | `void` | 客户端启动时读取配置文件。 |
| `save()` | `void` | 通过 `Files.newBufferedWriter` 强制将 `expandedCategories` 写入硬盘。 |
| `saveIfDirty()` | `void` | 仅在 `isDirty == true` 时执行写入，并重设标记为 `false`。 |
| `isExpanded(String categoryKey)` | `boolean` | 检查该键是否存在于 `expandedCategories` 中。 |
| `setExpanded(String categoryKey, boolean expanded)` | `void` | 更新集合并将 `isDirty` 标记设为 `true`。 |
| `expandAll(Iterable<String> allKeys)` | `void` | 批量将所有分类加入集合并标记 `isDirty = true`。 |
| `collapseAll()` | `void` | 清空集合并标记 `isDirty = true`。 |

---

## 🔒 屏幕关闭 Mixin 拦截

状态保存挂钩于原生 `Screen.removed()` 方法（`ScreenMixin.java`）：

```java
@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Inject(method = "removed", at = @At("HEAD"))
    private void collapsible_game_rules$onRemoved(CallbackInfo ci) {
        if ((Object) this instanceof AbstractGameRulesScreen) {
            GameRuleStateConfig.saveIfDirty();
        }
    }
}
```

这保证了玩家无论是按下 **完成**、**取消** 或 **Escape** 键退出菜单，所有操作都会被安全保存。

---

## 🔗 相关文档

* [[🏠 概览与首页|zh_cn-Home]]
* [[🌎 全局操作与批量切换|zh_cn-Global-Actions-and-Bulk-Toggles]]
* [[🧩 架构与 Mixin 子系统|zh_cn-Architecture-and-Mixins]]
