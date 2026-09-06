# 🔍 智能搜索集成

| 参数 | 规格说明 |
| :--- | :--- |
| **拦截方法** | `populateChildren(Ljava/lang/String;)V` |
| **注入位置** | `@At("TAIL")` |
| **Mixin 类** | `AbstractGameRulesScreenRuleListMixin` |
| **查询标准化** | `filter.toLowerCase(java.util.Locale.ROOT)` |
| **搜索状态判断** | `isSearching = !currentFilter.isEmpty()` |
| **动态展开规则** | `isExpanded = isSearching || GameRuleStateConfig.isExpanded(key)` |
| **重建调用** | `collapsible_game_rules$updateVisibleEntries()` |

---

## 📖 机制概述

在原生 Minecraft 中，游戏规则搜索框通过比对规则名称与描述来筛选列表。若在折叠界面中直接采用原版逻辑，极易发生搜索结果被隐藏在未展开文件夹内的状况。

**Smart Search Integration** 通过即时监听搜索框输入来解决此问题。每当搜索框内存在有效关键字时，所有包含匹配规则的分类都会自动动态展开，让搜索结果一览无遗，无需额外手动点选。

---

## ⚙️ 智能搜索管线流程

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          SMART SEARCH PIPELINE                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Player types query into Search Bar (e.g. "fire")                          │
│        │                                                                    │
│        ▼                                                                    │
│   Vanilla AbstractGameRulesScreen.RuleList.populateChildren("fire")         │
│   (Filters the internal list to matching rules & their category headers)    │
│        │                                                                    │
│        ▼ (@Inject at TAIL)                                                  │
│   AbstractGameRulesScreenRuleListMixin.collapsible_game_rules$onPopulate... │
│        ├─ Stores normalized query: filter.toLowerCase(Locale.ROOT)          │
│        ├─ Captures filtered list: allEntries = new ArrayList<>(children())  │
│        └─ Calls updateVisibleEntries()                                      │
│             │                                                               │
│             ▼                                                               │
│        isSearching = !currentFilter.isEmpty() (Evaluates to TRUE)           │
│             │                                                               │
│             ▼                                                               │
│        Every present category header is forced isExpanded = TRUE            │
│        All matched child rules render immediately!                          │
│                                                                             │
│   Player clears Search Bar ("")                                             │
│        │                                                                    │
│        ▼                                                                    │
│   isSearching = FALSE ──> Reverts to persistent GameRuleStateConfig states! │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 💻 技术实现细节

### 1. `@Inject` 注入点
Mixin 拦截原版 `populateChildren` 方法的末尾（`TAIL`）：

```java
@Inject(method = "populateChildren(Ljava/lang/String;)V", at = @At("TAIL"))
private void collapsible_game_rules$onPopulateChildren(String filter, CallbackInfo ci) {
    this.collapsible_game_rules$currentFilter = (filter != null) ? filter.toLowerCase(java.util.Locale.ROOT) : "";
    // Save the currently generated list of all entries
    this.collapsible_game_rules$allEntries = new ArrayList<>(this.children());
    this.collapsible_game_rules$updateVisibleEntries();
}
```

### 2. 搜索状态动态判断
在 `updateVisibleEntries()` 中，动态计算类别的展开状态：

```java
boolean isSearching = !this.collapsible_game_rules$currentFilter.isEmpty();
final String finalPersistenceKey = persistenceKey;
boolean isExpanded = isSearching || GameRuleStateConfig.isExpanded(finalPersistenceKey);
```

### 3. 无损状态恢复
由于 `isSearching` 仅在过滤活跃期间生效，当玩家清空搜索框时，界面会立即回复为 `GameRuleStateConfig` 中记录的玩家偏好展开状态，绝不会覆盖或重设硬盘上的本地配置。

---

## 🔗 相关文档

* [[🏠 概览与首页|zh_cn-Home]]
* [[🗂️ 可折叠类别|zh_cn-Collapsible-Categories]]
* [[🧠 状态持久化与 JSON 配置|zh_cn-State-Persistence-and-Config]]
* [[🧩 架构与 Mixin 子系统|zh_cn-Architecture-and-Mixins]]
