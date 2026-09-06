# 🎛️ Predefinições de Regras de Jogo & Controles

| Parâmetro | Especificação |
| :--- | :--- |
| **Motor de Presets** | `net.instantgratification.collapsiblegamerules.preset.GameRulePresetEngine` |
| **Estrutura de Dados** | `record Preset(String id, Component displayName, Map<String, Object> ruleValues)` |
| **Widgets Interativos** | `IntegerSliderWidget`, `BooleanToggleWidget` |
| **Classe de Apoio ao Slider** | `net.instantgratification.collapsiblegamerules.util.GameRuleSliderHelper` |
| **Fundo Ativado** | `0x4400FF00` (Fundo verde esmeralda translúcido) |
| **Fundo Desativado** | `0x44FF0000` (Fundo vermelho rubi translúcido) |
| **Presets Integrados** | `builder` ("🏰 Modo Construtor"), `fast_play` ("⚡ Jogo Rápido"), `hardcore` ("💀 Realismo Hardcore") |

---

## 📖 Visão Geral

O Collapsible Game Rules oferece botões interativos e perfis predefinidos para aplicar ajustes complexos em um clique ou controlar números através de sliders em vez de digitar texto.

---

## 🏰 Matriz de Predefinições

O `GameRulePresetEngine` conta com três opções pré-configuradas:

| ID do Preset | Nome | Regra de Jogo | Valor Aplicado | Impacto no Jogo |
| :--- | :--- | :--- | :--- | :--- |
| `builder` | **🏰 Modo Construtor** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doMobSpawning`<br>`keepInventory`<br>`mobGriefing`<br>`doFireTick` | `false`<br>`false`<br>`false`<br>`true`<br>`false`<br>`false` | Ideal para construções: congela o tempo e o clima, desativa criaturas, destruição por creepers e fogo. |
| `fast_play` | **⚡ Jogo Rápido** | `randomTickSpeed`<br>`playersSleepingPercentage`<br>`keepInventory` | `10`<br>`0`<br>`true` | Sobrevivência dinâmica: cultivares $3\times$ mais rápidos, pula noite com um jogador e mantém inventário. |
| `hardcore` | **💀 Realismo Hardcore** | `naturalRegeneration`<br>`doInsomnia`<br>`playersSleepingPercentage` | `false`<br>`true`<br>`100` | Sobrevivência extrema: remove regeneração passiva de vida (exige maçãs/poções) e ativa fantasmas. |

---

## 🎚️ Widget de Slider Numérico (`IntegerSliderWidget`)

O `IntegerSliderWidget` substitui campos de texto numéricos por controles deslizantes suaves.

### Fórmulas de Normalização

Posição normalizada do slider a partir de um valor $v$:
$$\text{normalized} = \frac{\text{clamp}(v, \text{min}, \text{max}) - \text{min}}{\text{max} - \text{min}}$$

Valor inteiro calculado a partir da posição do slider $p \in [0.0, 1.0]$:
$$\text{calculatedInt} = \text{min} + \text{round}\left(p \times (\text{max} - \text{min})\right)$$

### Limites das Regras Vanilla (`GameRuleSliderHelper`)

| Chave da Regra | Mínimo ($	ext{min}$) | Máximo ($	ext{max}$) | Padrão Vanilla |
| :--- | :--- | :--- | :--- |
| `randomTickSpeed` | `0` | `100` | `3` |
| `spawnRadius` | `0` | `32` | `10` |
| `playersSleepingPercentage` | `0` | `100` | `100` |
| `maxEntityCramming` | `0` | `100` | `24` |
| `maxCommandChainLength` | `0` | `65536` | `65536` |
| `commandModificationBlockLimit` | `0` | `65536` | `32768` |

---

## 🔘 Widget de Alternância Booleana (`BooleanToggleWidget`)

O `BooleanToggleWidget` dá retorno visual imediato para opções binárias:

* **Estado: VERDADEIRO (`ON`)**: Mostra `✔ ON` com fonte verde sobre fundo verde (`0x4400FF00`).
* **Estado: FALSO (`OFF`)**: Mostra `✖ OFF` com fonte vermelha sobre fundo vermelho (`0x44FF0000`).
* **Clique com o Mouse**: Alterna o estado e dispara o callback `onToggle.accept(newState)`.

```
┌─────────────────────────┐     ┌─────────────────────────┐
│         ✔ ON            │     │         ✖ OFF           │
│   (Green Tint 0x4400FF00)│     │   (Red Tint 0x44FF0000) │
└─────────────────────────┘     └─────────────────────────┘
```

---

## 🔗 Documentação Relacionada

* [[🏠 Visão Geral & Página Inicial|pt_br-Home]]
* [[🗂️ Categorias Dobráveis|pt_br-Collapsible-Categories]]
* [[📜 Tabela de Referência de Regras de Jogo|pt_br-GameRules-Reference]]
* [[🧩 Arquitetura & Subsistema Mixin|pt_br-Architecture-and-Mixins]]
