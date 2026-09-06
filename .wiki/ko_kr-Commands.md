# 💬 Brigadier 명령어 및 범위 안내

| 매개변수 | 세부 명세 |
| :--- | :--- |
| **전용 커스텀 명령어** | `0` (설계상 등록하지 않음) |
| **명령어 아키텍처** | 순수 클라이언트 그래픽 인터페이스 (GUI) |
| **바닐라 명령어 호환성** | 바닐라 `/gamerule` 명령어와 `100%` 양방향 호환 |
| **인게임 UI 접근 경로** | 월드 생성 $\\to$ 게임 규칙 \\| 일시정지 메뉴 $\\to$ 게임 규칙 |

---

## 📖 명령어 미등록 정책

> [!NOTE]
> **서버 채팅 명령어 전무**: Instant Gratification(즉시 만족) 설계 철학에 따라, 본 모드는 불필요한 전용 명령어(`/cgr config` 등)를 등록하지 않습니다. 모든 설정은 그래픽 화면에서 즉시 시각적으로 확인하고 조작할 수 있습니다.

---

## 💻 바닐라 명령어와의 상호작용

모드가 바닐라 `AbstractGameRulesScreen` 위에서 동작하므로, 채팅 창에서 `/gamerule` 명령어로 값을 변경하면 해당 변경 사항이 인터페이스에 실시간으로 반영됩니다:

### 주요 바닐라 `/gamerule` 명령어

```bash
# 크리퍼 등 몬스터에 의한 블록 파괴 비활성화
/gamerule mobGriefing false

# 사망 시 인벤토리 유지
/gamerule keepInventory true

# 작물 성장 속도(랜덤 틱 속도) 변경
/gamerule randomTickSpeed 10

# 낮/밤 시간 흐름 정지
/gamerule doDaylightCycle false
```

명령어로 규칙을 수정한 뒤 게임 규칙 설정 화면을 열면, 해당 카테고리 내에서 수정된 최신 값을 바로 확인할 수 있습니다.

---

## 🔗 관련 문서

* [[🏠 개요 및 홈 포털|ko_kr-Home]]
* [[📜 게임 규칙 참조표|ko_kr-GameRules-Reference]]
* [[🎛️ 게임 규칙 프리셋 및 컨트롤|ko_kr-Game-Rule-Presets-and-Controls]]
