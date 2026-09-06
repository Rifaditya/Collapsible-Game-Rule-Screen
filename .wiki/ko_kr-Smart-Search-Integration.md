# 🔍 스마트 검색 통합

| 매개변수 | 세부 명세 |
| :--- | :--- |
| **인터셉트 대상 메서드** | `populateChildren(Ljava/lang/String;)V` |
| **인젝션 위치** | `@At("TAIL")` |
| **Mixin 클래스** | `AbstractGameRulesScreenRuleListMixin` |
| **검색어 정규화** | `filter.toLowerCase(java.util.Locale.ROOT)` |
| **검색 상태 판별 플래그**| `isSearching = !currentFilter.isEmpty()` |
| **펼침 평가 조건** | `isExpanded = isSearching || GameRuleStateConfig.isExpanded(key)` |

---

## 📖 개요

바닐라 검색창은 규칙 이름을 필터링합니다. 하지만 접이식 화면에서는 일치하는 규칙이 접혀 있는 카테고리 속에 숨어버려 보이지 않는 문제가 발생할 수 있습니다.

**스마트 검색 통합**은 플레이어의 검색어 입력을 실시간으로 감지하여, 일치하는 규칙이 포함된 카테고리를 **자동으로 임시 펼침 상태**로 전환합니다.

---

## ⚙️ 스마트 검색 파이프라인

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          SMART SEARCH PIPELINE                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   플레이어가 검색창에 검색어 입력 (예: "fire")                               │
│        │                                                                    │
│        ▼                                                                    │
│   바닐라 RuleList.populateChildren("fire")가 일치하는 규칙 필터링           │
│        │                                                                    │
│        ▼ (@Inject at TAIL)                                                  │
│   AbstractGameRulesScreenRuleListMixin이 필터링 결과 감지                    │
│        ├─ 정규화된 검색어 저장                                              │
│        ├─ 필터링된 전체 항목 리스트 보존                                    │
│        └─ updateVisibleEntries() 호출                                       │
│             │                                                               │
│             ▼                                                               │
│        isSearching = TRUE로 평가됨                                          │
│             │                                                               │
│             ▼                                                               │
│        일치하는 자식을 가진 카테고리가 강제로 isExpanded = TRUE로 전환       │
│        결과 규칙들이 화면에 즉시 노출됨!                                    │
│                                                                             │
│   플레이어가 검색창을 비움 ("")                                             │
│        │                                                                    │
│        ▼                                                                    │
│   isSearching = FALSE ──> 기존에 저장된 원래의 접힘 설정으로 즉시 복귀!     │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

검색창을 지우면 플레이어가 기존에 설정해둔 본래의 카테고리 접힘 상태로 매끄럽게 복귀합니다.

---

## 🔗 관련 문서

* [[🏠 개요 및 홈 포털|ko_kr-Home]]
* [[🗂️ 접을 수 있는 카테고리|ko_kr-Collapsible-Categories]]
* [[🧠 상태 지속성 및 JSON 설정|ko_kr-State-Persistence-and-Config]]
* [[🧩 아키텍처 및 Mixin 서브시스템|ko_kr-Architecture-and-Mixins]]
