# 🧩 架构与 Mixin 子系统

| 参数 | 规格说明 |
| :--- | :--- |
| **Mixin 配置文件** | `src/main/resources/collapsible-game-rules.mixins.json` |
| **Refmap 标识符** | `collapsible-game-rules-refmap.json` |
| **兼容性层级** | `JAVA_25` |
| **Default Require** | `1` |
| **根包路径** | `net.instantgratification.collapsiblegamerules` |
| **Mixin 包路径** | `net.instantgratification.collapsiblegamerules.mixin` |
| **Mixin 总数** | `4` 个客户端 Mixin（3 个注入器 + 1 个接口访问器） |

---

## 📖 系统包架构

```
net.instantgratification.collapsiblegamerules
├── CollapsibleGameRulesFabric.java           ──> Main ModInitializer (Guard & Hard Dependency check)
├── CollapsibleGameRulesFabricClient.java     ──> ClientModInitializer (Loads GameRuleStateConfig)
├── GameRuleStateConfig.java                  ──> Config & JSON state persistence (Set<String>)
├── mixin
│   ├── AbstractGameRulesScreenRuleListMixin.java ──> Core engine (List interception, CollapsibleCategoryRuleEntry)
│   ├── CategoryRuleEntryAccessor.java        ──> Interface Accessor for category Component label
│   ├── IntegerRuleEntryMixin.java            ──> Abstract wrapper for integer rule entries
│   └── ScreenMixin.java                      ──> Intercepts Screen.removed() to flush pending state
├── preset
│   └── GameRulePresetEngine.java             ──> Built-in preset definitions (Builder, Fast Play, Hardcore)
├── ui
│   ├── BooleanToggleWidget.java              ──> Interactive emerald green / ruby red toggle switch
│   ├── GlobalActionsRuleEntry.java           ──> Pinned index 0 bulk toggle header ([ Expand ] / [ Collapse ])
│   └── IntegerSliderWidget.java              ──> Draggable numeric slider with math normalization
└── util
    ├── CategoryPrettifier.java               ──> Regex & string parser for unlocalized category keys
    ├── DasikMetadataHelper.java              ──> Lazy-loaded isolation layer querying DasikLibrary
    ├── GameRuleSliderHelper.java             ──> Vanilla integer rule bounds and default values
    └── ModVersionGuard.java                  ──> Zero-dependency runtime Knot ClassLoader integrity check
```

---

## 🔍 Mixin 注入目标剖析

### 1. `AbstractGameRulesScreenRuleListMixin`
* **目标类**: `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList.class`
* **继承关系**: 繼承 `ContainerObjectSelectionList<AbstractGameRulesScreen.RuleEntry>`
* **职责**: 核心 UI 控制器。拦截规则列表填充、计算每个分类的子规则数、依据展开状态过滤可见项目，并负责渲染可折叠类别标题。

```java
@Mixin(AbstractGameRulesScreen.RuleList.class)
public abstract class AbstractGameRulesScreenRuleListMixin
        extends ContainerObjectSelectionList<AbstractGameRulesScreen.RuleEntry> {

    @Unique
    private List<AbstractGameRulesScreen.RuleEntry> collapsible_game_rules$allEntries = new ArrayList<>();

    @Unique
    private String collapsible_game_rules$currentFilter = "";

    @Inject(method = "populateChildren(Ljava/lang/String;)V", at = @At("TAIL"))
    private void collapsible_game_rules$onPopulateChildren(String filter, CallbackInfo ci) {
        this.collapsible_game_rules$currentFilter = (filter != null) ? filter.toLowerCase(java.util.Locale.ROOT) : "";
        this.collapsible_game_rules$allEntries = new ArrayList<>(this.children());
        this.collapsible_game_rules$updateVisibleEntries();
    }
}
```

---

### 2. `CategoryRuleEntryAccessor`
* **目标类**: `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.CategoryRuleEntry.class`
* **职责**: 提供访问原生类别条目私有 `Component label` 字段的字节码访问器。

```java
@Mixin(AbstractGameRulesScreen.CategoryRuleEntry.class)
public interface CategoryRuleEntryAccessor {
    @Accessor("label")
    Component collapsible_game_rules$getLabel();
}
```

---

### 3. `ScreenMixin`
* **目标类**: `net.minecraft.client.gui.screens.Screen.class`
* **职责**: 监听屏幕移除事件。每当 `AbstractGameRulesScreen` 关闭时，通过 `GameRuleStateConfig.saveIfDirty()` 将变更状态写入磁盘。

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

---

### 4. `IntegerRuleEntryMixin`
* **目标类**: `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.IntegerRuleEntry.class`
* **职责**: 为整数规则组件提供抽象封装与专用组件集成桥梁。

```java
@Mixin(AbstractGameRulesScreen.IntegerRuleEntry.class)
public abstract class IntegerRuleEntryMixin extends AbstractGameRulesScreen.RuleEntry {
    public IntegerRuleEntryMixin() {
        super(null);
    }
}
```

---

## 📜 Mixin 配置文件 (`collapsible-game-rules.mixins.json`)

```json
{
    "required": true,
    "package": "net.instantgratification.collapsiblegamerules.mixin",
    "refmap": "collapsible-game-rules-refmap.json",
    "compatibilityLevel": "JAVA_25",
    "client": [
        "ScreenMixin",
        "AbstractGameRulesScreenRuleListMixin",
        "CategoryRuleEntryAccessor",
        "IntegerRuleEntryMixin"
    ],
    "injectors": {
        "defaultRequire": 1
    }
}
```

---

## 🔗 相关文档

* [[🏠 概览与首页|zh_cn-Home]]
* [[🗂️ 可折叠类别|zh_cn-Collapsible-Categories]]
* [[🧠 状态持久化与 JSON 配置|zh_cn-State-Persistence-and-Config]]
* [[📚 DasikLibrary API 集成|zh_cn-API-and-Library-Integration]]
