# 🎛️ 게임 규칙 프리셋 및 컨트롤

| 매개변수 | 세부 명세 |
| :--- | :--- |
| **프리셋 엔진** | `net.instantgratification.collapsiblegamerules.preset.GameRulePresetEngine` |
| **프리셋 데이터 구조** | `record Preset(String id, Component displayName, Map<String, Object> ruleValues)` |
| **조작 위젯** | `IntegerSliderWidget`, `BooleanToggleWidget` |
| **슬라이더 헬퍼** | `net.instantgratification.collapsiblegamerules.util.GameRuleSliderHelper` |
| **토글 ON 배경색** | `0x4400FF00` (에메랄드 그린) |
| **토글 OFF 배경색** | `0x44FF0000` (루비 레드) |
| **내장 프리셋** | `builder` ("🏰 건축가 모드"), `fast_play` ("⚡ 빠른 플레이"), `hardcore` ("💀 하드코어") |

---

## 📖 개요

직관적인 조작을 돕기 위해 숫자 직접 입력 방식을 슬라이더 바 위젯으로 대체하였으며, 플레이 스타일에 맞춘 규칙 구성을 원클릭으로 적용할 수 있는 프리셋 시스템을 지원합니다.

---

## 🏰 기본 내장 프리셋 목록

| ID | 프리셋 이름 | 대상 게임 규칙 | 적용 값 | 게임플레이 효과 |
| :--- | :--- | :--- | :--- | :--- |
| `builder` | **🏰 건축가 모드** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doMobSpawning`<br>`keepInventory`<br>`mobGriefing`<br>`doFireTick` | `false`<br>`false`<br>`false`<br>`true`<br>`false`<br>`false` | 건축 최적화: 낮/밤과 날씨를 고정하고 몬스터 스폰, 크리퍼 지형 파괴, 불 번짐을 차단. |
| `fast_play` | **⚡ 빠른 플레이** | `randomTickSpeed`<br>`playersSleepingPercentage`<br>`keepInventory` | `10`<br>`0`<br>`true` | 빠른 진행: 농작물 성장 속도를 3배로 증가시키고 1명만 자도 밤을 스킵. |
| `hardcore` | **💀 하드코어** | `naturalRegeneration`<br>`doInsomnia`<br>`playersSleepingPercentage` | `false`<br>`true`<br>`100` | 극한 생존: 자연 치유 비활성화 (황금 사과/포션 필수), 팬텀 출현 활성화. |

---

## 🎚️ 숫자 슬라이더 (`IntegerSliderWidget`)

직접 숫자를 타이핑할 필요 없이 마우스 드래그로 손쉽게 값을 조절할 수 있습니다.

바닐라 규칙 표준 범위:
* `randomTickSpeed`: 0 ~ 100
* `spawnRadius`: 0 ~ 32
* `playersSleepingPercentage`: 0 ~ 100
* `maxEntityCramming`: 0 ~ 100

---

## 🔘 부울 값 토글 스위치 (`BooleanToggleWidget`)

* **ON (활성화)**: 에메랄드 그린 배경과 `✔ ON` 표시.
* **OFF (비활성화)**: 루비 레드 배경과 `✖ OFF` 표시.

---

## 🔗 관련 문서

* [[🏠 개요 및 홈 포털|ko_kr-Home]]
* [[🗂️ 접을 수 있는 카테고리|ko_kr-Collapsible-Categories]]
* [[📜 게임 규칙 참조표|ko_kr-GameRules-Reference]]
* [[🧩 아키텍처 및 Mixin 서브시스템|ko_kr-Architecture-and-Mixins]]
