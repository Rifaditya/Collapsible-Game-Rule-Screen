# 📚 Integração com a API do DasikLibrary

| Parâmetro | Especificação |
| :--- | :--- |
| **Dependência Central** | `net.dasik.social:dasik-library` |
| **Restrição de Versão** | `dasik-library: >=1.7.0` (Compilação: `1.7.4`) |
| **Classe de Isolamento** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **Método Consultado** | `DynamicGameRuleManager.getGeneratedTranslations()` |
| **Validação no Boot** | Verificação rígida em `CollapsibleGameRulesFabric.onInitialize()` |

---

## 📖 Visão Geral

O Collapsible Game Rules integra-se à **DasikLibrary** para obter traduções dinâmicas e metadados estruturados de categorias em mods modernos do Minecraft.

Para assegurar a integridade da JVM, referências diretas a classes da `DasikLibrary` ficam encapsuladas na classe auxiliar `DasikMetadataHelper`.

---

## 🔒 Arquitetura de Isolamento de ClassLoading

Na JVM moderna, chamar diretamente classes externas em classes quentes (como Mixins) faz a máquina virtual carregar a biblioteca de imediato. Se ela estiver ausente, ocorre um `NoClassDefFoundError` fatal antes do mod conseguir avisar o jogador.

Isolando as chamadas de `DynamicGameRuleManager` no `DasikMetadataHelper`, a JVM só executa o carregamento quando seguro:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      CLASSLOADING ISOLATION PATTERN                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   AbstractGameRulesScreenRuleListMixin                                      │
│        │                                                                    │
│        ▼ (Checks FabricLoader.isModLoaded("dasik-library"))                 │
│   ┌─────────────────────────────────────────────────────────────────────┐   │
│   │ if (FabricLoader.getInstance().isModLoaded("dasik-library")) {      │   │
│   │     categoryKey = DasikMetadataHelper.getCategoryTranslation(...);  │   │
│   │ }                                                                   │   │
│   └─────────────────────────────────────────────────────────────────────┘   │
│        │                                                                    │
│        ▼ (Only loads DasikMetadataHelper when confirmed safe)               │
│   DasikMetadataHelper ──> net.dasik.social.api.gamerule.DynamicGameRuleManager│
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 💻 Implementação da Busca de Metadados

```java
public final class DasikMetadataHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger("collapsible-game-rules");

    private DasikMetadataHelper() {}

    /**
     * Retrieves the localized name for a category from DasikLibrary metadata.
     * Enforced Hard Dependency: This method makes direct calls to DasikLibrary.
     */
    public static String getCategoryTranslation(String categoryLabel) {
        Map<String, String> translations =
                DynamicGameRuleManager.getGeneratedTranslations();

        return translations.getOrDefault(
                "gamerule.category." + categoryLabel.toLowerCase(Locale.ROOT),
                categoryLabel
        );
    }
}
```

---

## 🛡️ Validação Estrita de Dependência

```java
public class CollapsibleGameRulesFabric implements ModInitializer {
    public static final String MOD_ID = "collapsible-game-rules";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
        LOGGER.info("Initializing Collapsible Game Rules [Core Align 2.1]");

        // Hard Dependency Enforcement
        if (!FabricLoader.getInstance().isModLoaded("dasik-library")) {
            throw new RuntimeException("Collapsible Game Rules requires DasikLibrary to function. Please install it.");
        }
    }
}
```

---

## 🔗 Documentação Relacionada

* [[🏠 Visão Geral & Página Inicial|pt_br-Home]]
* [[🧭 Matriz de Compatibilidade de Versões|pt_br-Version-Compatibility]]
* [[✨ Embelezamento & Nomeação de Categorias|pt_br-Category-Prettification-and-Naming]]
* [[🧩 Arquitetura & Subsistema Mixin|pt_br-Architecture-and-Mixins]]
