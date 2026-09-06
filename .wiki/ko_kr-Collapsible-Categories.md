# 🗂️ 접을 수 있는 카테고리

| 매개변수 | 세부 명세 |
| :--- | :--- |
| **시스템 컴포넌트** | `CollapsibleCategoryRuleEntry` (내부 클래스) |
| **핵심 Mixin** | `AbstractGameRulesScreenRuleListMixin` |
| **대상 클래스** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList` |
| **상태 표시 아이콘** | 펼쳐짐: `▼ ` \\| 접힘: `▶ ` |
| **규칙 수 배지 서식** | ` (N rules)` (`ChatFormatting.GRAY`) |
| **마우스 호버 배경색** | `0x22FFFFFF` (반투명 화이트) |
| **하단 구분선** | `0x44AAAAAA` (은은한 회색 라인) |
| **텍스트 색상** | 호버 시: `0xFFFFFFAA` \\| 평상시: `0xFFFFFFFF` |
| **클릭 사운드** | `net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK` (볼륨: `1.0F`) |
| **스크린 리더 우선순위**| `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 개요

바닐라 마인크래프트에서는 카테고리 이름이 단순한 정적 라벨(`CategoryRuleEntry`)로만 표시되어, 수많은 규칙이 하나의 긴 스크롤 목록에 무분별하게 나열됩니다.

**Collapsible Categories**는 이 라벨들을 반응형 `CollapsibleCategoryRuleEntry` 위젯으로 교체하여, 클릭이나 키보드 조작으로 자유롭게 카테고리를 펼치고 접을 수 있도록 개선합니다.

---

## 🎨 화면 레이아웃 구성

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ [ 모두 펼치기 ]                                             [ 모두 접기 ]   │ ◄── GlobalActionsRuleEntry (인덱스 0)
├─────────────────────────────────────────────────────────────────────────────┤
│ ▼ ⚔️ Mobs (14 rules)                                                        │ ◄── CollapsibleCategoryRuleEntry (펼쳐짐)
│ ─────────────────────────────────────────────────────────────────────────── │
│   mobGriefing                                                     [ ON ]    │ ◄── 자식 RuleEntry
│   doMobSpawning                                                   [ ON ]    │ ◄── 자식 RuleEntry
│   doMobLoot                                                       [ ON ]    │ ◄── 자식 RuleEntry
├─────────────────────────────────────────────────────────────────────────────┤
│ ▶ 👤 Player (8 rules)                                                       │ ◄── CollapsibleCategoryRuleEntry (접힘)
│ ─────────────────────────────────────────────────────────────────────────── │
│ ▼ 🌧️ Updates (6 rules)                                                      │ ◄── CollapsibleCategoryRuleEntry (펼쳐짐)
│ ─────────────────────────────────────────────────────────────────────────── │
│   doFireTick                                                      [ ON ]    │ ◄── 자식 RuleEntry
│   randomTickSpeed                                                 [ 3  ]    │ ◄── 자식 RuleEntry
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## ⚙️ 세부 구현 원리

### 1. 자식 규칙 개수 계산 알고리즘
`updateVisibleEntries()` 실행 시, 다음 카테고리 헤더가 나타나기 전까지의 자식 항목 수를 집계합니다:

```java
int childCount = 0;
for (int j = i + 1; j < this.collapsible_game_rules$allEntries.size(); j++) {
    if (this.collapsible_game_rules$allEntries.get(j) instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
        break;
    }
    childCount++;
}
```

### 2. 렌더링 파이프라인 (`extractContent`)
`GuiGraphicsExtractor` 활용:
1. **호버 하이라이트**: 커서가 닿았을 때 `0x22FFFFFF` 배경 박스 렌더링.
2. **화살표 및 배지**: 접힘 상태 화살표(`▼ ` 또는 `▶ `), 카테고리 이름, 회색 규칙 개수 배지(` (N rules)`).
3. **중앙 정렬**: `getContentXMiddle()`을 기준으로 중앙 정렬.
4. **하단 구분선**: `getY() + 23` 위치에 `0x44AAAAAA` 가로선을 그어 시각적 구분감 제공.

### 3. 클릭 이벤트 처리
마우스 좌클릭 또는 우클릭 시 접힘 상태를 반전시키고, `GameRuleStateConfig`에 저장한 후 `SoundEvents.UI_BUTTON_CLICK` 사운드를 재생합니다.

---

## 🔗 관련 문서

* [[🏠 개요 및 홈 포털|ko_kr-Home]]
* [[🌎 전체 작업 및 일괄 토글|ko_kr-Global-Actions-and-Bulk-Toggles]]
* [[⌨️ 키보드 탐색 및 접근성|ko_kr-Keyboard-Navigation]]
* [[🧩 아키텍처 및 Mixin 서브시스템|ko_kr-Architecture-and-Mixins]]
