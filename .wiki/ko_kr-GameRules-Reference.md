# 📜 게임 규칙 참조표

| 매개변수 | 세부 명세 |
| :--- | :--- |
| **실행 환경** | **클라이언트 전용** (`"environment": "client"`) |
| **모드 전용 서버 게임 규칙** | `0` (UI 인터페이스 레벨에서만 동작) |
| **바닐라 카테고리 지원율** | `100%` (`Player`, `Mobs`, `Spawning`, `Drops`, `Updates`, `Chat`, `Misc`) |
| **모드 카테고리 지원** | `DasikMetadataHelper` 및 `CategoryPrettifier`를 통한 자동 지원 |
| **상태 저장 방식** | 로컬 설정 파일 `config/collapsible-game-rules-state.json` |

---

## 📖 UI 동작 원칙

> [!NOTE]
> **순수 클라이언트 UI 모드**: Collapsible Game Rules는 설정 화면의 시각적 구성과 조작 편의성을 개선하는 모드입니다. 서버의 규칙 연산이나 월드 틱에는 관여하지 않으며, 표시되는 모든 규칙은 바닐라나 다른 모드로부터 제공됩니다.

---

## 🗂️ 표준 바닐라 카테고리 및 포함 규칙

화면을 열면 Minecraft 26.2의 게임 규칙들이 다음 카테고리로 자동 정리됩니다:

| 카테고리 | 포함된 대표 규칙 | 일반적인 사용 용도 |
| :--- | :--- | :--- |
| **👤 플레이어 (Player)** | `keepInventory`<br>`naturalRegeneration`<br>`playersSleepingPercentage`<br>`fallDamage`<br>`drowningDamage`<br>`fireDamage`<br>`freezeDamage` | 사망 시 인벤토리 보존, 밤을 넘기기 위한 침대 수면 비율, 낙하/익사/화염 피해 설정. |
| **⚔️ 몬스터 (Mobs)** | `mobGriefing`<br>`doMobSpawning`<br>`doMobLoot`<br>`universalAnger`<br>`forgiveDeadPlayers` | 크리퍼 폭발 블록 파괴 차단, 몬스터 아이템 드롭 제어, 분노 상태 공유 제어. |
| **🌱 스폰 (Spawning)** | `doMobSpawning`<br>`doPatrolSpawning`<br>`doTraderSpawning`<br>`doWardenSpawning`<br>`spawnRadius` | 몬스터, 방랑 상인, 순찰대 스폰 설정 및 초기 스폰 반경 범위. |
| **📦 드롭 (Drops)** | `doEntityDrops`<br>`doTileDrops`<br>`doMobLoot`<br>`globalSoundEvents` | 블록 파괴 시 아이템 드롭, 몹 전리품, 글로벌 사운드 재생 설정. |
| **🌧️ 업데이트 (Updates)** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doFireTick`<br>`randomTickSpeed`<br>`maxCommandChainLength` | 낮/밤 및 날씨 흐름 고정, 불 확산 방지, 농작물 성장 속도(랜덤 틱) 제어. |
| **💬 채팅 (Chat)** | `showDeathMessages`<br>`sendCommandFeedback`<br>`logAdminCommands`<br>`reducedDebugInfo` | 사망 메시지 출력 여부, 명령어 실행 결과 안내, F3 디버그 화면 좌표 숨김. |
| **⚙️ 기타 (Misc)** | `commandBlockOutput`<br>`maxEntityCramming`<br>`disableElytraMovementCheck` | 엔티티 한 칸 밀집 한계치, 커맨드 블록 출력, 겉날개 비행 속도 검사. |

---

## 🔗 관련 문서

* [[🏠 개요 및 홈 포털|ko_kr-Home]]
* [[🗂️ 접을 수 있는 카테고리|ko_kr-Collapsible-Categories]]
* [[🎛️ 게임 규칙 프리셋 및 컨트롤|ko_kr-Game-Rule-Presets-and-Controls]]
* [[📚 DasikLibrary API 통합|ko_kr-API-and-Library-Integration]]
