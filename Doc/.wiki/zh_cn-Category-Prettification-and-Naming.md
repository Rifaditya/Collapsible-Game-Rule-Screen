# ✨ 类别名称美化与格式化

| 参数 | 规格说明 |
| :--- | :--- |
| **核心工具类** | `net.instantgratification.collapsiblegamerules.util.CategoryPrettifier` |
| **元数据门面类** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **回退触发条件** | `!Language.getInstance().has(key)` |
| **前缀去除规则** | 移除 `"gamerule.category."` |
| **分隔符处理** | 以 `.` 分割命名空间，以正则表达式 `[_-]` 分割单词 |
| **动态元数据提供源**| `DynamicGameRuleManager.getGeneratedTranslations()` |

---

## 📖 机制概述

第三方模组在注册自定义游戏规则分类时，经常使用原始翻译键（例如 `gamerule.category.better-bats.better_bats` 或 `gamerule.category.item_clumps`），却未在 `lang/en_us.json` 中提供本地化字符串。在原版 Minecraft 中，这会导致界面上显示丑陋且难以阅读的键名。

**Category Prettification** 动态清理、格式化并将未翻译的分类键转化为排版规范、易于阅读的 Title Case（首字母大写）标题。

---

## ⚙️ 格式化算法管线

`CategoryPrettifier.prettifyCategoryKey(String key)` 执行以下转换步骤：

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

## 📊 格式化示例对照表

| 原始分类键 | 转换后显示标签 | 转换说明 |
| :--- | :--- | :--- |
| `gamerule.category.better-bats.better_bats` | **Better Bats** | 去除重复的命名空间与路径。 |
| `gamerule.category.minecraft.spawning` | **Spawning** | 自动忽略默认的 `minecraft` 命名空间。 |
| `gamerule.category.social_mobs.wolf_pack` | **Social Mobs Wolf Pack** | 合并相异的命名空间与路径单元。 |
| `gamerule.category.custom_rules` | **Custom Rules** | 将下划线替换为空格并将单词首字母大写。 |
| `gamerule.category.instant-gratification.ore-multiplier` | **Instant Gratification Ore Multiplier** | 分割连字符并将所有单词首字母大写。 |

---

## 💻 源代码实现

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

## 🔗 相关文档

* [[🏠 概览与首页|zh_cn-Home]]
* [[🗂️ 可折叠类别|zh_cn-Collapsible-Categories]]
* [[📚 DasikLibrary API 集成|zh_cn-API-and-Library-Integration]]
