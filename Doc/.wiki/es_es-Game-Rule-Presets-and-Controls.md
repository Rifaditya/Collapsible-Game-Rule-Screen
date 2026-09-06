# 🎛️ Ajustes Preestablecidos y Controles de Game Rules

| Parámetro | Especificación |
| :--- | :--- |
| **Motor de Presets** | `net.instantgratification.collapsiblegamerules.preset.GameRulePresetEngine` |
| **Estructura de Datos** | `record Preset(String id, Component displayName, Map<String, Object> ruleValues)` |
| **Widgets Interactivos** | `IntegerSliderWidget`, `BooleanToggleWidget` |
| **Ayudante Deslizante** | `net.instantgratification.collapsiblegamerules.util.GameRuleSliderHelper` |
| **Fondo de Activo** | `0x4400FF00` (Verde esmeralda translúcido) |
| **Fondo de Inactivo** | `0x44FF0000` (Rojo rubí translúcido) |
| **Presets de Fábrica** | `builder` ("🏰 Modo Constructor"), `fast_play` ("⚡ Partida Rápida"), `hardcore` ("💀 Realismo Extremo") |

---

## 📖 Visión General

Collapsible Game Rules incluye controles interactivos y presets listos para usar, permitiendo aplicar configuraciones completas de mundo con un solo clic o deslizar valores de forma táctil en lugar de escribir números a mano.

---

## 🏰 Matriz de Presets Preconfigurados

`GameRulePresetEngine` proporciona tres perfiles predeterminados:

| ID del Preset | Título | Regla de Juego | Valor Asignado | Efecto en la Partida |
| :--- | :--- | :--- | :--- | :--- |
| `builder` | **🏰 Modo Constructor** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doMobSpawning`<br>`keepInventory`<br>`mobGriefing`<br>`doFireTick` | `false`<br>`false`<br>`false`<br>`true`<br>`false`<br>`false` | Ideal para construcción creativa: congela el clima/hora, desactiva criaturas, creepers y fuego. |
| `fast_play` | **⚡ Partida Rápida** | `randomTickSpeed`<br>`playersSleepingPercentage`<br>`keepInventory` | `10`<br>`0`<br>`true` | Supervivencia ágil: aceleración de cultivos ($3\times$), saltar noche con un solo jugador y conservar inventario. |
| `hardcore` | **💀 Realismo Extremo** | `naturalRegeneration`<br>`doInsomnia`<br>`playersSleepingPercentage` | `false`<br>`true`<br>`100` | Desafío extremo: elimina la curación natural pasiva (se requieren manzanas/pociones) y genera fantasmas. |

---

## 🎚️ Control Deslizante Interactivo (`IntegerSliderWidget`)

`IntegerSliderWidget` sustituye los campos de texto por deslizadores continuos.

### Fórmulas Matemáticas de Normalización

Calcular la posición normalizada del deslizador desde un entero $v$:
$$\text{normalized} = \frac{\text{clamp}(v, \text{min}, \text{max}) - \text{min}}{\text{max} - \text{min}}$$

Obtener el entero a partir de la posición del deslizador $p \in [0.0, 1.0]$:
$$\text{calculatedInt} = \text{min} + \text{round}\left(p \times (\text{max} - \text{min})\right)$$

### Límites de Reglas Vanilla (`GameRuleSliderHelper`)

| Clave de Regla | Mínimo ($	ext{min}$) | Máximo ($	ext{max}$) | Valor Vanilla |
| :--- | :--- | :--- | :--- |
| `randomTickSpeed` | `0` | `100` | `3` |
| `spawnRadius` | `0` | `32` | `10` |
| `playersSleepingPercentage` | `0` | `100` | `100` |
| `maxEntityCramming` | `0` | `100` | `24` |
| `maxCommandChainLength` | `0` | `65536` | `65536` |
| `commandModificationBlockLimit` | `0` | `65536` | `32768` |

---

## 🔘 Botón Conmutador Booleano (`BooleanToggleWidget`)

`BooleanToggleWidget` ofrece respuesta visual inmediata para reglas binarias:

* **Estado: VERDADERO (`ON`)**: Muestra `✔ ON` en color verde sobre fondo verde (`0x4400FF00`).
* **Estado: FALSO (`OFF`)**: Muestra `✖ OFF` en color rojo sobre fondo rojo (`0x44FF0000`).
* **Clic con el Ratón**: Alterna el valor y llama al consumidor `onToggle.accept(newState)`.

```
┌─────────────────────────┐     ┌─────────────────────────┐
│         ✔ ON            │     │         ✖ OFF           │
│   (Green Tint 0x4400FF00)│     │   (Red Tint 0x44FF0000) │
└─────────────────────────┘     └─────────────────────────┘
```

---

## 🔗 Documentación Relacionada

* [[🏠 Resumen y Portal Principal|es_es-Home]]
* [[🗂️ Categorías Desplegables|es_es-Collapsible-Categories]]
* [[📜 Tabla de Referencia de GameRules|es_es-GameRules-Reference]]
* [[🧩 Arquitectura y Subsistema Mixin|es_es-Architecture-and-Mixins]]
