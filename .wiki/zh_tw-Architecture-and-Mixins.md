# 🧩 架構與 Mixin 子系統

| 參數 | 規格說明 |
| :--- | :--- |
| **Mixin 設定檔** | `src/main/resources/collapsible-game-rules.mixins.json` |
| **Refmap 標識符** | `collapsible-game-rules-refmap.json` |
| **相容性層級** | `JAVA_25` |
| **Default Require** | `1` |
| **根套件路徑** | `net.instantgratification.collapsiblegamerules` |
| **Mixin 套件路徑** | `net.instantgratification.collapsiblegamerules.mixin` |
| **Mixin 總數** | `4` 個客戶端 Mixin（3 個注入器 + 1 個介面存取器） |

---

## 📖 系統套件架構

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

## 🔍 Mixin 注入目標剖析

### 1. `AbstractGameRulesScreenRuleListMixin`
* **目標類別**: `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList.class`
* **繼承關係**: 繼承 `ContainerObjectSelectionList<AbstractGameRulesScreen.RuleEntry>`
* **職責**: 核心 UI 控制器。攔截規則清單填充、計算每個分類的子規則數、依據展開狀態過濾可見項目，並負責渲染可折疊類別標題。

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
* **目標類別**: `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.CategoryRuleEntry.class`
* **職責**: 提供存取原生類別條目私有 `Component label` 欄位的位元組碼存取器。

```java
@Mixin(AbstractGameRulesScreen.CategoryRuleEntry.class)
public interface CategoryRuleEntryAccessor {
    @Accessor("label")
    Component collapsible_game_rules$getLabel();
}
```

---

### 3. `ScreenMixin`
* **目標類別**: `net.minecraft.client.gui.screens.Screen.class`
* **職責**: 監聽畫面移除事件。每當 `AbstractGameRulesScreen` 關閉時，透過 `GameRuleStateConfig.saveIfDirty()` 將變更狀態寫入磁碟。

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
* **目標類別**: `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.IntegerRuleEntry.class`
* **職責**: 為整數規則元件提供抽象封裝與專用元件整合橋樑。

```java
@Mixin(AbstractGameRulesScreen.IntegerRuleEntry.class)
public abstract class IntegerRuleEntryMixin extends AbstractGameRulesScreen.RuleEntry {
    public IntegerRuleEntryMixin() {
        super(null);
    }
}
```

---

## 📜 Mixin 設定檔 (`collapsible-game-rules.mixins.json`)

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

## 🔗 相關文件

* [[🏠 概覽與首頁|zh_tw-Home]]
* [[🗂️ 可折疊類別|zh_tw-Collapsible-Categories]]
* [[🧠 狀態持久化與 JSON 設定|zh_tw-State-Persistence-and-Config]]
* [[📚 DasikLibrary API 整合|zh_tw-API-and-Library-Integration]]
