# 🖥️ HUD, 진단 및 UI 렌더링

| 매개변수 | 세부 명세 |
| :--- | :--- |
| **그래픽 렌더링 엔진** | `net.minecraft.client.gui.GuiGraphicsExtractor` |
| **적용 화면** | `AbstractGameRulesScreen` |
| **호버 텍스트 색상** | `0xFFFFFFAA` (은은한 골드/옐로우) |
| **일반 텍스트 색상** | `0xFFFFFFFF` (선명한 화이트) |
| **호버 배경 박스** | `0x22FFFFFF` (반투명 화이트) |
| **하단 테두리선** | `0x44AAAAAA` (차분한 회색) |
| **토글 활성 색상** | `0x4400FF00` (에메랄드 그린) |
| **토글 비활성 색상** | `0x44FF0000` (루비 레드) |

---

## 📖 개요

Minecraft 26.2에서 전면 도입된 현대식 `GuiGraphicsExtractor` 렌더링 파이프라인에 완벽하게 대응하여, 부드럽고 가벼운 최신 바닐라 스타일 그래픽을 선보입니다.

---

## 🎨 색상 구성표

| 요소 | ARGB 코드 | 설명 |
| :--- | :--- | :--- |
| **호버 배경** | `0x22FFFFFF` | 카테고리 헤더 및 전체 작업 버튼 마우스 호버 효과. |
| **구분선** | `0x44AAAAAA` | 각 카테고리 하단에 배치되는 1px 얇은 구분선. |
| **호버 텍스트** | `0xFFFFFFAA` | 커서가 닿았을 때 활성화되는 밝은 텍스트. |
| **일반 텍스트** | `0xFFFFFFFF` | 높은 가독성을 제공하는 순백색 텍스트. |
| **규칙 수 배지** | `ChatFormatting.GRAY` | 카테고리 이름 옆에 표시되는 규칙 건수 배지. |

---

## 🔗 관련 문서

* [[🏠 개요 및 홈 포털|ko_kr-Home]]
* [[🗂️ 접을 수 있는 카테고리|ko_kr-Collapsible-Categories]]
* [[⌨️ 키보드 탐색 및 접근성|ko_kr-Keyboard-Navigation]]
* [[🧩 아키텍처 및 Mixin 서브시스템|ko_kr-Architecture-and-Mixins]]
