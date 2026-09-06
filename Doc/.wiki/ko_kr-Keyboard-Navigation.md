# ⌨️ 키보드 탐색 및 접근성

| 매개변수 | 세부 명세 |
| :--- | :--- |
| **컴포넌트 대상** | `CollapsibleCategoryRuleEntry` |
| **접근성 인터페이스** | `net.minecraft.client.gui.narration.NarratableEntry` |
| **내레이션 우선순위** | `NarrationPriority.HOVERED` |
| **내레이션 요소** | `NarratedElementType.TITLE` |
| **토글 키** | `Space`, `Enter`, `숫자패드 Enter` |
| **접기 전용 키** | `왼쪽 화살표 (←)` (펼쳐진 상태일 때만) |
| **펼치기 전용 키** | `오른쪽 화살표 (→)` (접힌 상태일 때만) |
| **사운드 피드백** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 개요

Collapsible Game Rules는 완벽한 키보드 조작 체계와 스크린 리더 음성 지원을 갖추고 있어, 마우스 없이도 모든 게임 규칙을 탐색하고 조작할 수 있습니다.

---

## ⌨️ 단축키 안내

목록에서 카테고리 헤더에 포커스가 위치할 때 사용할 수 있는 키는 다음과 같습니다:

| 키 | GLFW 상수 | 동작 | 조건 | 사운드 |
| :--- | :--- | :--- | :--- | :--- |
| **Space** | `GLFW_KEY_SPACE` | 펼침/접힘 상태 반전 | 상시 동작 | `UI_BUTTON_CLICK` |
| **Enter** | `GLFW_KEY_ENTER` | 펼침/접힘 상태 반전 | 상시 동작 | `UI_BUTTON_CLICK` |
| **숫자패드 Enter**| `GLFW_KEY_KP_ENTER` | 펼침/접힘 상태 반전 | 상시 동작 | `UI_BUTTON_CLICK` |
| **왼쪽 화살표 (←)**| `GLFW_KEY_LEFT` | **카테고리 접기** | `expanded == true` | `UI_BUTTON_CLICK` |
| **오른쪽 화살표 (→)**| `GLFW_KEY_RIGHT`| **카테고리 펼치기** | `expanded == false` | `UI_BUTTON_CLICK` |

운영체제의 표준 파일 탐색기 트리 구조와 동일한 조작 감각을 제공합니다.

---

## 🔗 관련 문서

* [[🏠 개요 및 홈 포털|ko_kr-Home]]
* [[🗂️ 접을 수 있는 카테고리|ko_kr-Collapsible-Categories]]
* [[🖥️ HUD, 진단 및 UI 렌더링|ko_kr-HUD-and-Diagnostics]]
* [[🧩 아키텍처 및 Mixin 서브시스템|ko_kr-Architecture-and-Mixins]]
