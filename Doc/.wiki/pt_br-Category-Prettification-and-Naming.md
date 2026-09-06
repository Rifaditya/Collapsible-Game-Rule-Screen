# ✨ Embelezamento & Nomeação de Categorias

| Parámetro | Especificação |
| :--- | :--- |
| **Classe Utilitária** | `net.instantgratification.collapsiblegamerules.util.CategoryPrettifier` |
| **Fachada de Metadados** | `net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper` |
| **Condição de Formatação** | `!Language.getInstance().has(key)` |
| **Remoção de Prefixo** | Remove `"gamerule.category."` |
| **Tratamento de Separadores**| Ponto `.` (namespace) e regex `[_-]` (palavras) |
| **Provedor de Metadados** | `DynamicGameRuleManager.getGeneratedTranslations()` |

---

## 📖 Visão Geral

Muitos mods registram categorias com chaves brutas de tradução (como `gamerule.category.better-bats.better_bats` ou `gamerule.category.item_clumps`), esquecendo de adicionar as strings no arquivo `lang/en_us.json`. No Minecraft original, isso exibe nomes brutos ilegíveis na tela.

O **Category Prettification** limpa e formata dinamicamente essas chaves, transformando-as em títulos organizados no formato Title Case.

---

## ⚙️ Pipeline do Algoritmo de Formatação

`CategoryPrettifier.prettifyCategoryKey(String key)` executa as seguintes etapas:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       CATEGORY PRETTIFICATION PIPELINE                      │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Input Key: "gamerule.category.better-bats.better_bats"                    │
│        │                                                                    │
│        ▼ [Step 1: Prefix Stripping]                                         │
│   Strip "gamerule.category." ──> "better-bats.better_bats"                  │
│        │                                                                    │
│        ▼ [Step 2: Namespace & Path Separation]                              │
│   Separate namespace "better-bats" and path "better_bats"                   │
│        │                                                                    │
│        ▼ [Step 3: Redundancy Normalization]                                 │
│   Compare normalized strings: "betterbats" == "betterbats"                  │
│   Deduplicate to single segment: "better_bats"                              │
│        │                                                                    │
│        ▼ [Step 4: Delimiter Splitting & Capitalization]                     │
│   Split by "[_-]" ──> ["better", "bats"]                                    │
│   Capitalize words ──> ["Better", "Bats"]                                   │
│        │                                                                    │
│        ▼ [Step 5: String Join]                                              │
│   Output Display Title: "Better Bats"                                       │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📊 Exemplos de Transformação

| Chave Bruta da Categoria | Rótulo Formatado | Observações |
| :--- | :--- | :--- |
| `gamerule.category.better-bats.better_bats` | **Better Bats** | Remove redundâncias entre namespace e path. |
| `gamerule.category.minecraft.spawning` | **Spawning** | Descarta o namespace padrão `minecraft`. |
| `gamerule.category.social_mobs.wolf_pack` | **Social Mobs Wolf Pack** | Combina partes distintas de namespace e caminho. |
| `gamerule.category.custom_rules` | **Custom Rules** | Troca underscores por espaços e aplica maiúsculas. |
| `gamerule.category.instant-gratification.ore-multiplier` | **Instant Gratification Ore Multiplier** | Separa hífens e capitaliza cada termo. |

---

## 💻 Código Fonte da Implementação

```java
public static String prettifyCategoryKey(String key) {
    if (key == null) {
        return "";
    }
    String name = key;
    if (name.startsWith("gamerule.category.")) {
        name = name.substring("gamerule.category.".length());
    }

    // Split namespace and path if dot is present
    int dotIndex = name.indexOf('.');
    if (dotIndex != -1) {
        String ns = name.substring(0, dotIndex);
        String path = name.substring(dotIndex + 1);
        
        // If the namespace is "minecraft", just drop it
        if (ns.equals("minecraft")) {
            name = path;
        } else {
            // Normalize for comparison
            String normNs = ns.replace("-", "").replace("_", "").toLowerCase(Locale.ROOT);
            String normPath = path.replace("-", "").replace("_", "").toLowerCase(Locale.ROOT);
            if (normPath.contains(normNs) || normNs.contains(normPath)) {
                name = path; // Use the path part since it's more specific or includes namespace
            } else {
                name = ns + " " + path;
            }
        }
    }

    // Split by underscore or dash
    String[] parts = name.split("[_-]");
    List<String> words = new ArrayList<>();
    for (String part : parts) {
        if (part.isEmpty()) {
            continue;
        }
        String capitalized = part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1);
        words.add(capitalized);
    }
    return String.join(" ", words);
}
```

---

## 🔗 Documentação Relacionada

* [[🏠 Visão Geral & Página Inicial|pt_br-Home]]
* [[🗂️ Categorias Dobráveis|pt_br-Collapsible-Categories]]
* [[📚 Integração com a API do DasikLibrary|pt_br-API-and-Library-Integration]]
