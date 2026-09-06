# 📚 DasikLibrary API 통합

| 매개변수 | 세부 명세 |
| :--- | :--- |
| **필수 선행 라이브러리** | `net.dasik.social:dasik-library` |
| **버전 제약 조건** | `dasik-library: >=1.7.0` (권장: `1.7.4`) |
| **격리 헬퍼 클래스** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **사용 API 메서드** | `DynamicGameRuleManager.getGeneratedTranslations()` |
| **시작 시 검증** | `CollapsibleGameRulesFabric.onInitialize()`에서 필수 검증 |

---

## 📖 개요

Collapsible Game Rules는 **DasikLibrary**와 유기적으로 연동되어, 모던 모드 생태계에서 동적으로 생성된 게임 규칙의 카테고리 메타데이터와 번역을 실시간으로 가져옵니다.

---

## 🔒 클래스로딩 격리 패턴

선행 라이브러리가 없는 환경에서 클래스 로딩 충돌(`NoClassDefFoundError`)이 발생하는 것을 방지하기 위해, `DasikMetadataHelper`를 통한 지연 로딩(Lazy Loading) 구조를 채택했습니다.

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      CLASSLOADING ISOLATION PATTERN                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   AbstractGameRulesScreenRuleListMixin                                      │
│        │                                                                    │
│        ▼ (FabricLoader.isModLoaded("dasik-library") 확인)                   │
│   ┌─────────────────────────────────────────────────────────────────────┐   │
│   │ if (FabricLoader.getInstance().isModLoaded("dasik-library")) {      │   │
│   │     categoryKey = DasikMetadataHelper.getCategoryTranslation(...);  │   │
│   │ }                                                                   │   │
│   └─────────────────────────────────────────────────────────────────────┘   │
│        │                                                                    │
│        ▼ (라이브러리 로드가 확인되었을 때만 안전하게 호출)                  │
│   DasikMetadataHelper ──> net.dasik.social.api.gamerule.DynamicGameRuleManager│
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 🛡️ 모드 시작 시 필수 선행 모드 검증

```java
public class CollapsibleGameRulesFabric implements ModInitializer {
    public static final String MOD_ID = "collapsible-game-rules";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
        LOGGER.info("Initializing Collapsible Game Rules [Core Align 2.1]");

        // 필수 모드 로드 검사
        if (!FabricLoader.getInstance().isModLoaded("dasik-library")) {
            throw new RuntimeException("Collapsible Game Rules requires DasikLibrary to function. Please install it.");
        }
    }
}
```

---

## 🔗 관련 문서

* [[🏠 개요 및 홈 포털|ko_kr-Home]]
* [[🧭 버전 호환성 매트릭스|ko_kr-Version-Compatibility]]
* [[✨ 카테고리 이름 정리 및 서식|ko_kr-Category-Prettification-and-Naming]]
* [[🧩 아키텍처 및 Mixin 서브시스템|ko_kr-Architecture-and-Mixins]]
