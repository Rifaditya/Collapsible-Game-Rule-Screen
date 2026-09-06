# 🧩 아키텍처 및 Mixin 서브시스템

| 매개변수 | 세부 명세 |
| :--- | :--- |
| **Mixin 설정 파일** | `src/main/resources/collapsible-game-rules.mixins.json` |
| **Refmap 파일명** | `collapsible-game-rules-refmap.json` |
| **호환성 레벨** | `JAVA_25` |
| **루트 패키지** | `net.instantgratification.collapsiblegamerules` |
| **Mixin 패키지** | `net.instantgratification.collapsiblegamerules.mixin` |
| **등록된 Mixin 수** | 클라이언트 전용 `4`개 (인젝터 3개 + 인터페이스 접근자 1개) |

---

## 📖 패키지 및 클래스 구조

```
net.instantgratification.collapsiblegamerules
├── CollapsibleGameRulesFabric.java           ──> 모드 초기화, 버전 가드 및 의존성 검사
├── CollapsibleGameRulesFabricClient.java     ──> 클라이언트 초기화 (설정 로드)
├── GameRuleStateConfig.java                  ──> JSON 지속성 관리자 (Set<String>)
├── mixin
│   ├── AbstractGameRulesScreenRuleListMixin.java ──> 목록 가로채기 및 접이식 카테고리 렌더링
│   ├── CategoryRuleEntryAccessor.java        ──> 카테고리 컴포넌트 라벨 접근 인터페이스
│   ├── IntegerRuleEntryMixin.java            ──> 숫자 규칙 래퍼
│   └── ScreenMixin.java                      ──> 화면 종료 시 자동 저장 훅
├── preset
│   └── GameRulePresetEngine.java             ──> 내장 프리셋 프로필 정의
├── ui
│   ├── BooleanToggleWidget.java              ──> ON/OFF 토글 위젯
│   ├── GlobalActionsRuleEntry.java           ──> 최상단 일괄 작업 헤더 (인덱스 0)
│   └── IntegerSliderWidget.java              ──> 정수 슬라이더 조작 위젯
└── util
    ├── CategoryPrettifier.java               ──> 미번역 원시 키 정리 엔진
    ├── DasikMetadataHelper.java              ──> DasikLibrary 격리 호출 레이어
    ├── GameRuleSliderHelper.java             ──> 바닐라 규칙 기본값 및 범위 정의
    └── ModVersionGuard.java                  ──> 런타임 클래스로더 무결성 검증
```

---

## 🔗 관련 문서

* [[🏠 개요 및 홈 포털|ko_kr-Home]]
* [[🗂️ 접을 수 있는 카테고리|ko_kr-Collapsible-Categories]]
* [[🧠 상태 지속성 및 JSON 설정|ko_kr-State-Persistence-and-Config]]
* [[📚 DasikLibrary API 통합|ko_kr-API-and-Library-Integration]]
