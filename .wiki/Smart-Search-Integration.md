# 🔍 Smart Search Integration

| Parameter | Specification |
| :--- | :--- |
| **Intercepted Method** | `populateChildren(Ljava/lang/String;)V` |
| **Injection Point** | `@At("TAIL")` |
| **Mixin Class** | `AbstractGameRulesScreenRuleListMixin` |
| **Query Normalization** | `filter.toLowerCase(java.util.Locale.ROOT)` |
| **Active Query Flag** | `isSearching = !currentFilter.isEmpty()` |
| **Dynamic State Rule** | `isExpanded = isSearching || GameRuleStateConfig.isExpanded(key)` |
| **Rebuild Method** | `collapsible_game_rules$updateVisibleEntries()` |

---

## 📖 Overview

In vanilla Minecraft, the Game Rules search bar filters the rule list by matching rule names or descriptions against player input. In a collapsible interface, a naive implementation would risk hiding matching rules inside closed category folders.

**Smart Search Integration** solves this problem by monitoring search query changes in real time. Whenever a search query is active, any category containing a matching rule automatically expands, displaying the search results immediately without requiring extra clicks.

---

## ⚙️ How Smart Search Works

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

## 💻 Technical Implementation Details

### 1. The `@Inject` Hook
The mixin intercepts the conclusion of vanilla's `populateChildren` method:

```java
@Inject(method = "populateChildren(Ljava/lang/String;)V", at = @At("TAIL"))
private void collapsible_game_rules$onPopulateChildren(String filter, CallbackInfo ci) {
    this.collapsible_game_rules$currentFilter = (filter != null) ? filter.toLowerCase(java.util.Locale.ROOT) : "";
    // Save the currently generated list of all entries
    this.collapsible_game_rules$allEntries = new ArrayList<>(this.children());
    this.collapsible_game_rules$updateVisibleEntries();
}
```

### 2. Search-Driven Expansion Evaluation
Inside `updateVisibleEntries()`, the expansion state is computed dynamically:

```java
boolean isSearching = !this.collapsible_game_rules$currentFilter.isEmpty();
final String finalPersistenceKey = persistenceKey;
boolean isExpanded = isSearching || GameRuleStateConfig.isExpanded(finalPersistenceKey);
```

### 3. Non-Destructive State Recovery
Because `isSearching` is an ephemeral condition evaluated only during active filtering, clearing the search box immediately restores each category's exact manual collapse state from `GameRuleStateConfig` without overwriting the player's saved preferences on disk.

---

## 🔗 Related Documentation

* [[Overview & Home|Home]]
* [[Collapsible Categories|Collapsible-Categories]]
* [[State Persistence & JSON Config|State-Persistence-and-Config]]
* [[Architecture & Mixin Subsystem|Architecture-and-Mixins]]
