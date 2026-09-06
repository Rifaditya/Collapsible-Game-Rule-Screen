# 🔍 Integración de Búsqueda Inteligente

| Parámetro | Especificación |
| :--- | :--- |
| **Método Interceptado** | `populateChildren(Ljava/lang/String;)V` |
| **Punto de Inyección** | `@At("TAIL")` |
| **Clase Mixin** | `AbstractGameRulesScreenRuleListMixin` |
| **Normalización de Consulta** | `filter.toLowerCase(java.util.Locale.ROOT)` |
| **Estado de Búsqueda Activa** | `isSearching = !currentFilter.isEmpty()` |
| **Condición de Despliegue** | `isExpanded = isSearching || GameRuleStateConfig.isExpanded(key)` |
| **Método de Reconstrucción** | `collapsible_game_rules$updateVisibleEntries()` |

---

## 📖 Visión General

En Minecraft Vanilla, el campo de búsqueda filtra reglas por nombre o descripción. En una interfaz con carpetas plegables, una implementación ingenua correría el riesgo de ocultar resultados dentro de categorías cerradas.

**Búsqueda Inteligente** soluciona esto monitorizando las pulsaciones en la caja de búsqueda en tiempo real: cuando hay una consulta activa, cualquier categoría que contenga una coincidencia se expande automáticamente, mostrando los resultados de inmediato sin requerir clics adicionales.

---

## ⚙️ Flujo de la Búsqueda Inteligente

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          SMART SEARCH PIPELINE                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Player types query into Search Bar (e.g. "fire")                          │
│        │                                                                    │
│        ▼                                                                    │
│   Vanilla AbstractGameRulesScreen.RuleList.populateChildren("fire")         │
│   (Filters the internal list to matching rules & their category headers)    │
│        │                                                                    │
│        ▼ (@Inject at TAIL)                                                  │
│   AbstractGameRulesScreenRuleListMixin.collapsible_game_rules$onPopulate... │
│        ├─ Stores normalized query: filter.toLowerCase(Locale.ROOT)          │
│        ├─ Captures filtered list: allEntries = new ArrayList<>(children())  │
│        └─ Calls updateVisibleEntries()                                      │
│             │                                                               │
│             ▼                                                               │
│        isSearching = !currentFilter.isEmpty() (Evaluates to TRUE)           │
│             │                                                               │
│             ▼                                                               │
│        Every present category header is forced isExpanded = TRUE            │
│        All matched child rules render immediately!                          │
│                                                                             │
│   Player clears Search Bar ("")                                             │
│        │                                                                    │
│        ▼                                                                    │
│   isSearching = FALSE ──> Reverts to persistent GameRuleStateConfig states! │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 💻 Detalles Técnicos de Implementación

### 1. Inyección `@Inject`
El mixin intercepta el final del método `populateChildren` de Vanilla:

```java
@Inject(method = "populateChildren(Ljava/lang/String;)V", at = @At("TAIL"))
private void collapsible_game_rules$onPopulateChildren(String filter, CallbackInfo ci) {
    this.collapsible_game_rules$currentFilter = (filter != null) ? filter.toLowerCase(java.util.Locale.ROOT) : "";
    // Save the currently generated list of all entries
    this.collapsible_game_rules$allEntries = new ArrayList<>(this.children());
    this.collapsible_game_rules$updateVisibleEntries();
}
```

### 2. Evaluación Dinámica del Estado
En `updateVisibleEntries()`:

```java
boolean isSearching = !this.collapsible_game_rules$currentFilter.isEmpty();
final String finalPersistenceKey = persistenceKey;
boolean isExpanded = isSearching || GameRuleStateConfig.isExpanded(finalPersistenceKey);
```

### 3. Restauración de Estado No Destructiva
Dado que `isSearching` es una condición temporal que sólo actúa durante el filtrado activo, borrar el texto de búsqueda restablece instantáneamente el estado de despliegue manual guardado en `GameRuleStateConfig`, sin sobreescribir las preferencias en el disco.

---

## 🔗 Documentación Relacionada

* [[🏠 Resumen y Portal Principal|es_es-Home]]
* [[🗂️ Categorías Desplegables|es_es-Collapsible-Categories]]
* [[🧠 Persistencia de Estado y Configuración JSON|es_es-State-Persistence-and-Config]]
* [[🧩 Arquitectura y Subsistema Mixin|es_es-Architecture-and-Mixins]]
