# ✨ 카테고리 이름 정리 및 서식

| 매개변수 | 세부 명세 |
| :--- | :--- |
| **유틸리티 클래스** | `net.instantgratification.collapsiblegamerules.util.CategoryPrettifier` |
| **메타데이터 헬퍼** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **서식 적용 조건** | `!Language.getInstance().has(key)` (번역이 없을 때) |
| **접두사 제거** | `"gamerule.category."` 접두사 자동 제거 |
| **처리 구분자** | 네임스페이스 점 `.` 및 단어 구분자 `[_-]` 파싱 |
| **동적 번역 소스** | `DynamicGameRuleManager.getGeneratedTranslations()` |

---

## 📖 개요

일부 서드파티 모드는 언어 파일(`lang/en_us.json`)에 카테고리 번역을 제공하지 않아 원시 키(예: `gamerule.category.better-bats.better_bats`)가 그대로 화면에 노출되는 경우가 있습니다.

**Category Prettifier**는 이러한 번역되지 않은 키를 실시간으로 분석하여, 읽기 편한 깔끔한 Title Case(예: `Better Bats`)로 변환합니다.

---

## 📊 변환 예시 표

| 원시 카테고리 키 | 정리된 표시 이름 | 처리 내용 |
| :--- | :--- | :--- |
| `gamerule.category.better-bats.better_bats` | **Better Bats** | 중복되는 네임스페이스와 경로를 하나로 병합. |
| `gamerule.category.minecraft.spawning` | **Spawning** | 바닐라 `minecraft` 네임스페이스 접두사 생략. |
| `gamerule.category.social_mobs.wolf_pack` | **Social Mobs Wolf Pack** | 밑줄을 공백으로 바꾸고 각 단어 첫 글자 대문자화. |
| `gamerule.category.custom_rules` | **Custom Rules** | 각 단어 첫 글자를 대문자로 변환. |

---

## 🔗 관련 문서

* [[🏠 개요 및 홈 포털|ko_kr-Home]]
* [[🗂️ 접을 수 있는 카테고리|ko_kr-Collapsible-Categories]]
* [[📚 DasikLibrary API 통합|ko_kr-API-and-Library-Integration]]
