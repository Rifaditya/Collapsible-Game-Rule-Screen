# 🧠 狀態持久化與 JSON 設定

| 參數 | 規格說明 |
| :--- | :--- |
| **設定管理類別** | `net.instantgratification.collapsiblegamerules.GameRuleStateConfig` |
| **檔案路徑** | `.minecraft/config/collapsible-game-rules-state.json` |
| **記憶體儲存結構** | `Set<String> expandedCategories = new HashSet<>()` |
| **序列化引擎** | `com.google.gson.Gson` (啟用格式化輸出) |
| **I/O 節流旗標** | `private static boolean isDirty = false` |
| **儲存觸發點** | `ScreenMixin` 攔截 `Screen.removed()` (`@At("HEAD")`) |
| **持久化鍵值策略** | 翻譯鍵 (`TranslatableContents.getKey()`) 或字面字串 |

---

## 📖 機制概述

Collapsible Game Rules 具備非同步、防抖節流的狀態持久化引擎。模組會自動記憶您的展開偏好，避免每次開啟遊戲或重載世界時重設為預設狀態。

---

## 📄 JSON 設定格式規範

狀態以乾淨易讀的 JSON 陣列儲存於 `.minecraft/config/collapsible-game-rules-state.json`：

```json
[
  "gamerule.category.spawning",
  "gamerule.category.mobs",
  "gamerule.category.updates"
]
```

* **存在於陣列中**：代表該類別當前處於**展開**狀態。
* **未出現在陣列中**：代表該類別當前處於**折疊**狀態（預設）。

---

## ⚡ 高效能 I/O 節流架構

若在每次滑鼠點擊或按鍵切換時都寫入硬碟，會造成大量的磁碟 I/O 與畫面微卡頓。

為確保**零掉幀**，`GameRuleStateConfig` 採用 `isDirty` 髒標記策略：

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

## 💻 API 與方法參考

### `GameRuleStateConfig` 公開方法

| 方法簽名 | 回傳值 | 說明 |
| :--- | :--- | :--- |
| `load()` | `void` | 客戶端啟動時讀取設定檔。 |
| `save()` | `void` | 透過 `Files.newBufferedWriter` 強制將 `expandedCategories` 寫入硬碟。 |
| `saveIfDirty()` | `void` | 僅在 `isDirty == true` 時執行寫入，並重設標記為 `false`。 |
| `isExpanded(String categoryKey)` | `boolean` | 檢查該鍵是否存在於 `expandedCategories` 中。 |
| `setExpanded(String categoryKey, boolean expanded)` | `void` | 更新集合並將 `isDirty` 標記設為 `true`。 |
| `expandAll(Iterable<String> allKeys)` | `void` | 批次將所有分類加入集合並標記 `isDirty = true`。 |
| `collapseAll()` | `void` | 清空集合並標記 `isDirty = true`。 |

---

## 🔒 畫面關閉 Mixin 攔截

狀態儲存掛鉤於原生 `Screen.removed()` 方法（`ScreenMixin.java`）：

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

這保證了玩家無論是按下 **完成**、**取消** 或 **Escape** 鍵退出選單，所有操作都會被安全保存。

---

## 🔗 相關文件

* [[🏠 概覽與首頁|zh_tw-Home]]
* [[🌎 全域操作與批次切換|zh_tw-Global-Actions-and-Bulk-Toggles]]
* [[🧩 架構與 Mixin 子系統|zh_tw-Architecture-and-Mixins]]
