# 🌎 전체 작업 및 일괄 토글

| 매개변수 | 세부 명세 |
| :--- | :--- |
| **컴포넌트 클래스** | `net.instantgratification.collapsiblegamerules.ui.GlobalActionsRuleEntry` |
| **목록 내 고정 위치** | 인덱스 `0` (`RuleList`의 최상단) |
| **왼쪽 버튼** | `[ 모두 펼치기 ]` (`gui.collapsible-game-rules.expand_all`) |
| **오른쪽 버튼** | `[ 모두 접기 ]` (`gui.collapsible-game-rules.collapse_all`) |
| **마우스 호버 강조** | `0x22FFFFFF` (커서가 위치한 절반 영역에 적용) |
| **하단 구분선** | `0x44AAAAAA` |
| **클릭 사운드** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 개요

수많은 모드가 추가되어 규칙이 방대할 때, 카테고리를 하나하나 클릭하여 조작하는 것은 번거롭습니다.
**전체 작업 바**는 목록 최상단(인덱스 0)에 상시 고정되어 있어, 한 번의 클릭으로 모든 카테고리를 펼치거나 접을 수 있습니다.

---

## 🎨 분할 레이아웃 디자인

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ◄──────────────── 왼쪽 절반 ───────────────►◄────────────── 오른쪽 절반 ───► │
│               [ 모두 펼치기 ]                              [ 모두 접기 ]    │
│ ─────────────────────────────────────────────────────────────────────────── │
└─────────────────────────────────────────────────────────────────────────────┘
```

* **왼쪽 영역 (`mouseX < getX() + getWidth() / 2`)**: `expandAll` 실행.
* **오른쪽 영역 (`mouseX >= getX() + getWidth() / 2`)**: `collapseAll` 실행.

---

## ⚙️ 일괄 처리 로직

```java
if (!this.collapsible_game_rules$allEntries.isEmpty()) {
    this.addEntry(new GlobalActionsRuleEntry(
        () -> {
            List<String> allKeys = this.collapsible_game_rules$allEntries.stream()
                .filter(e -> e instanceof AbstractGameRulesScreen.CategoryRuleEntry)
                .map(e -> {
                    Component lbl = ((CategoryRuleEntryAccessor) e).collapsible_game_rules$getLabel();
                    if (lbl.getContents() instanceof TranslatableContents translatable) {
                        return translatable.getKey();
                    }
                    return lbl.getString();
                })
                .toList();
            GameRuleStateConfig.expandAll(allKeys);
            this.collapsible_game_rules$updateVisibleEntries();
        },
        () -> {
            GameRuleStateConfig.collapseAll();
            this.collapsible_game_rules$updateVisibleEntries();
        }
    ));
}
```

---

## 🔗 관련 문서

* [[🏠 개요 및 홈 포털|ko_kr-Home]]
* [[🗂️ 접을 수 있는 카테고리|ko_kr-Collapsible-Categories]]
* [[🧠 상태 지속성 및 JSON 설정|ko_kr-State-Persistence-and-Config]]
* [[🧩 아키텍처 및 Mixin 서브시스템|ko_kr-Architecture-and-Mixins]]
