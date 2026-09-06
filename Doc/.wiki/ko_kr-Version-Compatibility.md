# 🧭 버전 호환성 매트릭스

| 매개변수 | 세부 명세 |
| :--- | :--- |
| **현재 대상 버전** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **모드 버전** | `1.0.9+26.2` |
| **지원 게임 환경** | Minecraft 26.2+ (Modern Sovereign 시대) |
| **Java 요구 사항** | **Java 25+** (`release = 25`, toolchain Java 25) |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` (`*`) |
| **필수 라이브러리** | **DasikLibrary** `>=1.7.0` (현재: `1.7.4`) |
| **빌드 도구** | Loom `1.15.2` / Gradle `9.3+` (`--no-daemon`) |
| **실행 환경** | **클라이언트 전용** (`"environment": "client"`) |
| **라이선스** | **GPL-3.0-or-later** |

---

> 📌 **저장소 소스 코드 고지 사항**: 이 Wiki의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근에 커밋된 미출시 커밋이나 개발 중인 기능이 포함될 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

---

## 📊 세대별 런타임 수명 주기 매트릭스

Collapsible Game Rules는 모던 세대(`MC 26.2+`)를 위해 최적화 개발되었으며, `GuiGraphicsExtractor` 렌더링 파이프라인, Java 25 Stream API (`.toList()`), 난독화 없는 런타임 환경을 활용합니다.

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       MULTI-ERA RUNTIME LIFECYCLE MATRIX                    │
├───────────────────┬─────────────┬──────────────┬───────────────┬────────────┤
│ Minecraft Anchor  │ Java Level  │ Loader Bound │ DasikLibrary  │ 지원 상태  │
├───────────────────┼─────────────┼──────────────┼───────────────┼────────────┤
│ **MC 26.2+**      │ **Java 25** │ `>=0.19.1`   │ `>=1.7.0`     │ 🟢 활성    │
│ MC 26.1.2         │ Java 25     │ `>=0.18.4`   │ `>=1.7.0`     │ 🟡 전방호환│
│ MC 1.21.x         │ Java 21     │ N/A          │ N/A           │ ⚪ 미지원  │
│ MC 1.20.1         │ Java 17     │ N/A          │ N/A           │ ⚪ 미지원  │
└───────────────────┴─────────────┴──────────────┴───────────────┴────────────┘
```

---

## 🔒 버전 가드 및 클래스로더 보호

호환되지 않는 환경에서 월드가 손상되는 것을 방지하기 위해, 모드 내에 `net.instantgratification.collapsiblegamerules.util.ModVersionGuard`가 통합되어 있습니다.

### 검증 메커니즘
`ModInitializer.onInitialize()` 실행 시:
```java
ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
```

---

## 📦 의존성 선언

`src/main/resources/fabric.mod.json`:

```json
{
  "schemaVersion": 1,
  "id": "collapsible-game-rules",
  "version": "${version}",
  "name": "Collapsible Game Rules",
  "description": "Makes the GameRules UI screens collapsible by category.",
  "authors": [
    "Dasik (Rifaditya)"
  ],
  "license": "GPL-3.0-or-later",
  "environment": "client",
  "entrypoints": {
    "main": [
      "net.instantgratification.collapsiblegamerules.CollapsibleGameRulesFabric"
    ],
    "client": [
      "net.instantgratification.collapsiblegamerules.CollapsibleGameRulesFabricClient"
    ]
  },
  "mixins": [
    "collapsible-game-rules.mixins.json"
  ],
  "depends": {
    "fabricloader": ">=0.19.1",
    "minecraft": ">=26.2-",
    "java": ">=25",
    "fabric-api": "*",
    "dasik-library": ">=1.7.0"
  }
}
```

---

## 🔗 관련 문서

* [[🏠 개요 및 홈 포털|ko_kr-Home]]
* [[🛠️ 개발자 환경 설정 및 Gradle 빌드|ko_kr-Developer-Setup-and-Building]]
* [[📚 DasikLibrary API 통합|ko_kr-API-and-Library-Integration]]
