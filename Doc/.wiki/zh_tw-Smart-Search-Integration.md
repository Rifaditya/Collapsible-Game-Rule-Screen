# 🔍 智慧搜尋整合

| 參數 | 規格說明 |
| :--- | :--- |
| **攔截方法** | `populateChildren(Ljava/lang/String;)V` |
| **注入位置** | `@At("TAIL")` |
| **Mixin 類別** | `AbstractGameRulesScreenRuleListMixin` |
| **查詢常態化** | `filter.toLowerCase(java.util.Locale.ROOT)` |
| **搜尋狀態判斷** | `isSearching = !currentFilter.isEmpty()` |
| **動態展開規則** | `isExpanded = isSearching || GameRuleStateConfig.isExpanded(key)` |
| **重建呼叫** | `collapsible_game_rules$updateVisibleEntries()` |

---

## 📖 機制概述

在原生 Minecraft 中，遊戲規則搜尋列透過比對規則名稱與描述來篩選清單。若在折疊介面中直接採用原版邏輯，極易發生搜尋結果被隱藏在未展開資料夾內的狀況。

**Smart Search Integration** 透過即時監聽搜尋列輸入來解決此問題。每當搜尋框內存在有效關鍵字時，所有包含匹配規則的分類都會自動動態展開，讓搜尋結果一覽無遺，無需額外手動點選。

---

## ⚙️ 智慧搜尋管線流程

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

## 💻 技術實作細節

### 1. `@Inject` 注入點
Mixin 攔截原版 `populateChildren` 方法的末尾（`TAIL`）：

```java
@Inject(method = "populateChildren(Ljava/lang/String;)V", at = @At("TAIL"))
private void collapsible_game_rules$onPopulateChildren(String filter, CallbackInfo ci) {
    this.collapsible_game_rules$currentFilter = (filter != null) ? filter.toLowerCase(java.util.Locale.ROOT) : "";
    // Save the currently generated list of all entries
    this.collapsible_game_rules$allEntries = new ArrayList<>(this.children());
    this.collapsible_game_rules$updateVisibleEntries();
}
```

### 2. 搜尋狀態動態判斷
在 `updateVisibleEntries()` 中，動態計算類別的展開狀態：

```java
boolean isSearching = !this.collapsible_game_rules$currentFilter.isEmpty();
final String finalPersistenceKey = persistenceKey;
boolean isExpanded = isSearching || GameRuleStateConfig.isExpanded(finalPersistenceKey);
```

### 3. 無損狀態復原
由於 `isSearching` 僅在過濾活躍期間生效，當玩家清空搜尋列時，介面會立即回復為 `GameRuleStateConfig` 中記錄的玩家偏好展開狀態，絕不會覆蓋或重設硬碟上的本機設定。

---

## 🔗 相關文件

* [[🏠 概覽與首頁|zh_tw-Home]]
* [[🗂️ 可折疊類別|zh_tw-Collapsible-Categories]]
* [[🧠 狀態持久化與 JSON 設定|zh_tw-State-Persistence-and-Config]]
* [[🧩 架構與 Mixin 子系統|zh_tw-Architecture-and-Mixins]]
