# 🧠 상태 지속성 및 JSON 설정

| 매개변수 | 세부 명세 |
| :--- | :--- |
| **설정 관리 클래스** | `net.instantgratification.collapsiblegamerules.GameRuleStateConfig` |
| **저장 파일 경로** | `.minecraft/config/collapsible-game-rules-state.json` |
| **메모리 내 데이터 구조** | `Set<String> expandedCategories = new HashSet<>()` |
| **직렬화 라이브러리** | `com.google.gson.Gson` (들여쓰기 서식 적용) |
| **지연 쓰기 플래그** | `private static boolean isDirty = false` |
| **저장 트리거** | `ScreenMixin`이 `Screen.removed()` 가로챔 (`@At("HEAD")`) |

---

## 📖 개요

카테고리의 펼침 및 접힘 상태는 로컬 JSON 파일에 자동 저장되어, 월드를 재접속하거나 게임을 재시작해도 플레이어가 정돈해둔 인터페이스 상태가 그대로 유지됩니다.

---

## 📄 JSON 파일 구조

`.minecraft/config/collapsible-game-rules-state.json`:

```json
[
  "gamerule.category.spawning",
  "gamerule.category.mobs",
  "gamerule.category.updates"
]
```

* **배열에 키가 존재함**: 해당 카테고리가 **펼쳐진 상태**입니다.
* **배열에 키가 존재하지 않음**: 해당 카테고리가 **접힌 상태**입니다 (기본값).

---

## ⚡ 스터터링 방지 지연 저장 (Throttled I/O)

카테고리를 클릭할 때마다 디스크에 파일을 쓰면 프레임 드랍이 발생할 수 있습니다. 본 모드는 메모리 내의 `isDirty` 플래그를 활용하여, 게임 규칙 화면을 닫을 때 한 번만 디스크에 안전하게 저장합니다.

---

## 🔗 관련 문서

* [[🏠 개요 및 홈 포털|ko_kr-Home]]
* [[🌎 전체 작업 및 일괄 토글|ko_kr-Global-Actions-and-Bulk-Toggles]]
* [[🧩 아키텍처 및 Mixin 서브시스템|ko_kr-Architecture-and-Mixins]]
