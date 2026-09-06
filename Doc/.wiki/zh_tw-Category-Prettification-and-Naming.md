# ✨ 類別名稱美化與格式化

| 參數 | 規格說明 |
| :--- | :--- |
| **核心工具類別** | `net.instantgratification.collapsiblegamerules.util.CategoryPrettifier` |
| **元資料外觀類別** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **回退觸發條件** | `!Language.getInstance().has(key)` |
| **字首去除規則** | 移除 `"gamerule.category."` |
| **分隔符號處理** | 以 `.` 分割命名空間，以正規表示式 `[_-]` 分割單字 |
| **動態元資料來源**| `DynamicGameRuleManager.getGeneratedTranslations()` |

---

## 📖 機制概述

第三方模組在註冊自訂遊戲規則分類時，經常使用原始翻譯鍵（例如 `gamerule.category.better-bats.better_bats` 或 `gamerule.category.item_clumps`），卻未在 `lang/en_us.json` 中提供在地化字串。在原版 Minecraft 中，這會導致介面上顯示醜陋且難以閱讀的鍵名。

**Category Prettification** 動態清理、格式化並將未翻譯的分類鍵轉化為排版規範、易於閱讀的 Title Case（首字母大寫）標題。

---

## ⚙️ 格式化演算法管線

`CategoryPrettifier.prettifyCategoryKey(String key)` 執行以下轉換步驟：

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       CATEGORY PRETTIFICATION PIPELINE                      │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Input Key: "gamerule.category.better-bats.better_bats"                    │
│        │                                                                    │
│        ▼ [Step 1: Prefix Stripping]                                         │
│   Strip "gamerule.category." ──> "better-bats.better_bats"                  │
│        │                                                                    │
│        ▼ [Step 2: Namespace & Path Separation]                              │
│   Separate namespace "better-bats" and path "better_bats"                   │
│        │                                                                    │
│        ▼ [Step 3: Redundancy Normalization]                                 │
│   Compare normalized strings: "betterbats" == "betterbats"                  │
│   Deduplicate to single segment: "better_bats"                              │
│        │                                                                    │
│        ▼ [Step 4: Delimiter Splitting & Capitalization]                     │
│   Split by "[_-]" ──> ["better", "bats"]                                    │
│   Capitalize words ──> ["Better", "Bats"]                                   │
│        │                                                                    │
│        ▼ [Step 5: String Join]                                              │
│   Output Display Title: "Better Bats"                                       │
│                                                                             │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📊 格式化範例對照表

| 原始分類鍵 | 轉換後顯示標籤 | 轉換說明 |
| :--- | :--- | :--- |
| `gamerule.category.better-bats.better_bats` | **Better Bats** | 去除重複的命名空間與路徑。 |
| `gamerule.category.minecraft.spawning` | **Spawning** | 自動忽略預設的 `minecraft` 命名空間。 |
| `gamerule.category.social_mobs.wolf_pack` | **Social Mobs Wolf Pack** | 合併相異的命名空間與路徑單元。 |
| `gamerule.category.custom_rules` | **Custom Rules** | 將底線替換為空格並將單字字首大寫。 |
| `gamerule.category.instant-gratification.ore-multiplier` | **Instant Gratification Ore Multiplier** | 分割連字號並將所有單字字首大寫。 |

---

## 💻 原始碼實作

```java
public static String prettifyCategoryKey(String key) {
    if (key == null) {
        return "";
    }
    String name = key;
    if (name.startsWith("gamerule.category.")) {
        name = name.substring("gamerule.category.".length());
    }

    // Split namespace and path if dot is present
    int dotIndex = name.indexOf('.');
    if (dotIndex != -1) {
        String ns = name.substring(0, dotIndex);
        String path = name.substring(dotIndex + 1);
        
        // If the namespace is "minecraft", just drop it
        if (ns.equals("minecraft")) {
            name = path;
        } else {
            // Normalize for comparison
            String normNs = ns.replace("-", "").replace("_", "").toLowerCase(Locale.ROOT);
            String normPath = path.replace("-", "").replace("_", "").toLowerCase(Locale.ROOT);
            if (normPath.contains(normNs) || normNs.contains(normPath)) {
                name = path; // Use the path part since it's more specific or includes namespace
            } else {
                name = ns + " " + path;
            }
        }
    }

    // Split by underscore or dash
    String[] parts = name.split("[_-]");
    List<String> words = new ArrayList<>();
    for (String part : parts) {
        if (part.isEmpty()) {
            continue;
        }
        String capitalized = part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1);
        words.add(capitalized);
    }
    return String.join(" ", words);
}
```

---

## 🔗 相關文件

* [[🏠 概覽與首頁|zh_tw-Home]]
* [[🗂️ 可折疊類別|zh_tw-Collapsible-Categories]]
* [[📚 DasikLibrary API 整合|zh_tw-API-and-Library-Integration]]
