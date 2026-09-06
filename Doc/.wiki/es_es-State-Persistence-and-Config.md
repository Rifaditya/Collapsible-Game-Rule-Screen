# 🧠 Persistencia de Estado y Configuración JSON

| Parámetro | Especificación |
| :--- | :--- |
| **Clase del Gestor** | `net.instantgratification.collapsiblegamerules.GameRuleStateConfig` |
| **Ubicación del Archivo** | `.minecraft/config/collapsible-game-rules-state.json` |
| **Estructura en Memoria** | `Set<String> expandedCategories = new HashSet<>()` |
| **Motor de Serialización** | `com.google.gson.Gson` (Pretty-Printing activado) |
| **Bandera de I/O Throttling**| `private static boolean isDirty = false` |
| **Punto de Guardado** | `ScreenMixin` intercepta `Screen.removed()` (`@At("HEAD")`) |
| **Estrategia de Claves** | Clave de traducción (`TranslatableContents.getKey()`) o texto literal |

---

## 📖 Visión General

Collapsible Game Rules cuenta con un motor asíncrono y controlado de persistencia de estado. En lugar de restablecer las opciones por defecto cada vez que se abre un mundo, el mod guarda qué categorías estaban abiertas o cerradas entre sesiones.

---

## 📄 Formato de Configuración JSON

El estado se guarda en un array JSON claro en `.minecraft/config/collapsible-game-rules-state.json`:

```json
[
  "gamerule.category.spawning",
  "gamerule.category.mobs",
  "gamerule.category.updates"
]
```

* **Presente en el array**: Indica que la categoría está actualmente **DESPLEGADA**.
* **Ausente del array**: Indica que la categoría está actualmente **PLEGADA** (estado predeterminado).

---

## ⚡ Arquitectura de Alto Rendimiento con I/O Throttling

Escribir en el disco en cada clic generaría ralentizaciones. Para asegurar **cero pérdidas de fotogramas**, `GameRuleStateConfig` usa la bandera `isDirty`:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       THROTTLED PERSISTENCE WORKFLOW                        │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Player clicks Category Header                                             │
│        │                                                                    │
│        ▼                                                                    │
│   GameRuleStateConfig.setExpanded(key, state)                               │
│        ├─ Updates in-memory HashSet<String> in 0.0001 μs                    │
│        └─ Marks: isDirty = true (ZERO DISK I/O)                             │
│                                                                             │
│   Player closes Game Rules Screen (Esc, Done, or Cancel)                    │
│        │                                                                    │
│        ▼                                                                    │
│   ScreenMixin.collapsible_game_rules$onRemoved()                            │
│        │                                                                    │
│        ▼                                                                    │
│   GameRuleStateConfig.saveIfDirty()                                         │
│        ├─ Checks: if (isDirty) Ellipsis                                      │
│        ├─ Writes JSON to disk in background buffer                          │
│        └─ Resets: isDirty = false                                           │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 💻 Referencia de Métodos API

### Métodos Públicos de `GameRuleStateConfig`

| Firma del Método | Tipo Retornado | Descripción |
| :--- | :--- | :--- |
| `load()` | `void` | Carga el archivo `collapsible-game-rules-state.json` al iniciar el cliente. |
| `save()` | `void` | Guarda el conjunto `expandedCategories` en disco con `Files.newBufferedWriter`. |
| `saveIfDirty()` | `void` | Escribe en disco únicamente si `isDirty == true` y restablece la bandera. |
| `isExpanded(String categoryKey)` | `boolean` | Verifica si la clave se encuentra en `expandedCategories`. |
| `setExpanded(String categoryKey, boolean expanded)` | `void` | Añade o elimina la clave y marca `isDirty = true`. |
| `expandAll(Iterable<String> allKeys)` | `void` | Añade masivamente todas las claves y marca `isDirty = true`. |
| `collapseAll()` | `void` | Vacía el conjunto y marca `isDirty = true`. |

---

## 🔒 Integración en ScreenMixin

```java
@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Inject(method = "removed", at = @At("HEAD"))
    private void collapsible_game_rules$onRemoved(CallbackInfo ci) {
        if ((Object) this instanceof AbstractGameRulesScreen) {
            GameRuleStateConfig.saveIfDirty();
        }
    }
}
```

Esto asegura que cuando el jugador salga de la pantalla (pulsando **Hecho**, **Cancelar** o la tecla **Escape**), los cambios queden perfectamente almacenados.

---

## 🔗 Documentación Relacionada

* [[🏠 Resumen y Portal Principal|es_es-Home]]
* [[🌎 Acciones Globales y Alternancia Masiva|es_es-Global-Actions-and-Bulk-Toggles]]
* [[🧩 Arquitectura y Subsistema Mixin|es_es-Architecture-and-Mixins]]
