# ⚒️ Collapsible Game Rules Wiki (접을 수 있는 게임 규칙)

<div align="center">

<img src="https://raw.githubusercontent.com/Rifaditya/Collapsible-Game-Rule-Screen/main/Images/2026-08-04_11.06.33.png" alt="Collapsible Game Rules Banner" width="800">

<p align="center">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge&logo=minecraft" alt="Minecraft 26.2+">
  <img src="https://img.shields.io/badge/Fabric-0.145.4+-blue?style=for-the-badge&logo=fabric" alt="Fabric">
  <img src="https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk" alt="Java 25">
  <img src="https://img.shields.io/badge/DasikLibrary-1.7.4-purple?style=for-the-badge" alt="DasikLibrary 1.7.4">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License GPLv3">
</p>

</div>

---

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 🧭 Collapsible Game Rules에 오신 것을 환영합니다

**Collapsible Game Rules**는 마인크래프트의 기본 게임 규칙 설정 화면을 카테고리별로 깔끔하게 접을 수 있는 반응형 인터페이스로 개편합니다. 실시간 검색 자동 확장, 풀 키보드 조작, 로컬 상태 지속성, 직관적인 게임 규칙 프리셋을 제공합니다.

---

## 🌟 주요 기능 및 빠른 인덱스

| 기능 | 설명 | 참조 가이드 |
| :--- | :--- | :--- |
| **🗂️ 접을 수 있는 카테고리** | 정적 텍스트 헤더를 규칙 개수 배지가 포함된 인터랙티브 위젯(`▼`/`▶`)으로 대체합니다. | [[🗂️ 접을 수 있는 카테고리\|ko_kr-Collapsible-Categories]] |
| **🌎 전체 작업 바** | 화면 최상단에 고정된 "모두 펼치기" 및 "모두 접기" 버튼으로 원클릭 일괄 제어. | [[🌎 전체 작업 및 일괄 토글\|ko_kr-Global-Actions-and-Bulk-Toggles]] |
| **🔍 스마트 검색 통합** | 검색창에 텍스트를 입력하면 일치하는 규칙이 포함된 카테고리가 자동으로 펼쳐집니다. | [[🔍 스마트 검색 통합\|ko_kr-Smart-Search-Integration]] |
| **⌨️ 키보드 탐색 및 접근성** | 스페이스바, 엔터, 화살표 키를 통한 완벽한 키보드 조작 및 스크린 리더 내레이션을 지원합니다. | [[⌨️ 키보드 탐색 및 접근성\|ko_kr-Keyboard-Navigation]] |
| **🧠 상태 지속성** | 카테고리의 펼침/접힘 상태를 `config/collapsible-game-rules-state.json`에 영구 기억합니다. | [[🧠 상태 지속성 및 JSON 설정\|ko_kr-State-Persistence-and-Config]] |
| **✨ 카테고리 이름 정리** | 모드의 번역되지 않은 원시 키를 읽기 쉬운 단정한 Title Case 제목으로 동적 변환합니다. | [[✨ 카테고리 이름 정리 및 서식\|ko_kr-Category-Prettification-and-Naming]] |
| **🎛️ 프리셋 및 컨트롤** | 원클릭 게임 모드 프로필(건축가, 빠른 플레이, 하드코어) 및 연속형 숫자 슬라이더. | [[🎛️ 게임 규칙 프리셋 및 컨트롤\|ko_kr-Game-Rule-Presets-and-Controls]] |
| **🧩 DasikLibrary API 통합** | `DynamicGameRuleManager`로부터 동적 번역을 조회하여 모드 카테고리 현지화를 돕습니다. | [[📚 DasikLibrary API 통합\|ko_kr-API-and-Library-Integration]] |

---

## 🚀 빠른 시작 및 설치 안내

1. **[Fabric Loader](https://fabricmc.net/)** (`>=0.19.1`)를 설치합니다.
2. **[Fabric API](https://modrinth.com/mod/fabric-api)**를 다운로드합니다.
3. **[DasikLibrary](https://modrinth.com/mod/dasik-library)** (`>=1.7.0`, **필수 선행 모드**)를 설치합니다.
4. `collapsible-game-rules-1.0.9+26.2.jar` 파일을 `.minecraft/mods` 폴더에 넣습니다.
5. **Java 25+** 환경에서 마인크래프트를 실행합니다.

---

## 📚 문서 전체 목차

```
Wiki 루트
├── 🧭 버전 호환성 매트릭스 ------------> [[🧭 버전 호환성 매트릭스|ko_kr-Version-Compatibility]]
├── 🎮 핵심 UI 조작 및 인터페이스
│   ├── 접을 수 있는 카테고리 ----------> [[🗂️ 접을 수 있는 카테고리|ko_kr-Collapsible-Categories]]
│   ├── 전체 작업 및 일괄 토글 --------> [[🌎 전체 작업 및 일괄 토글|ko_kr-Global-Actions-and-Bulk-Toggles]]
│   ├── 스마트 검색 통합 --------------> [[🔍 스마트 검색 통합|ko_kr-Smart-Search-Integration]]
│   └── 키보드 탐색 및 접근성 ----------> [[⌨️ 키보드 탐색 및 접근성|ko_kr-Keyboard-Navigation]]
├── ⚙️ 설정 및 프리셋 시스템
│   ├── 상태 지속성 및 JSON 설정 -------> [[🧠 상태 지속성 및 JSON 설정|ko_kr-State-Persistence-and-Config]]
│   ├── 카테고리 이름 정리 및 서식 ----> [[✨ 카테고리 이름 정리 및 서식|ko_kr-Category-Prettification-and-Naming]]
│   └── 게임 규칙 프리셋 및 컨트롤 ----> [[🎛️ 게임 규칙 프리셋 및 컨트롤|ko_kr-Game-Rule-Presets-and-Controls]]
├── 📋 게임플레이 범위 및 참조
│   ├── 게임 규칙 참조표 --------------> [[📜 게임 규칙 참조표|ko_kr-GameRules-Reference]]
│   ├── Brigadier 명령어 및 범위 안내 --> [[💬 Brigadier 명령어 및 범위 안내|ko_kr-Commands]]
│   └── 발전 과제 및 범위 안내 --------> [[🏆 발전 과제 및 범위 안내|ko_kr-Advancements]]
└── 💻 기술 아키텍처 및 개발
    ├── HUD, 진단 및 UI 렌더링 ---------> [[🖥️ HUD, 진단 및 UI 렌더링|ko_kr-HUD-and-Diagnostics]]
    ├── 개발자 환경 설정 및 Gradle 빌드 -> [[🛠️ 개발자 환경 설정 및 Gradle 빌드|ko_kr-Developer-Setup-and-Building]]
    ├── 아키텍처 및 Mixin 서브시스템 ---> [[🧩 아키텍처 및 Mixin 서브시스템|ko_kr-Architecture-and-Mixins]]
    └── DasikLibrary API 통합 -----------> [[📚 DasikLibrary API 통합|ko_kr-API-and-Library-Integration]]
```

---

## 📜 크레딧 및 라이선스

* **원작자 및 수석 개발자**: **Dasik (Rifaditya)**
* **라이선스**: **GNU General Public License v3.0 (GPLv3)**
* **GitHub 저장소**: [GitHub Repository](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen)
* **Modrinth 페이지**: [Modrinth](https://modrinth.com/mod/collapsible-game-rules) (`lObgjyJl`)
* **CurseForge 페이지**: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/collapsible-game-rules) (`1468932`)
