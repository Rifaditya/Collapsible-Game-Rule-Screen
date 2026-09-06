# 💬 Comandos Brigadier y Alcance

| Parámetro | Especificación |
| :--- | :--- |
| **Comandos Brigadier Propios** | `0` (Ninguno registrado por diseño) |
| **Arquitectura de Comandos** | Interfaz puramente gráfica del cliente (GUI) |
| **Compatibilidad con Vanilla** | `100%` Compatible con el comando nativo `/gamerule` |
| **Rutas de Acceso en el Juego** | Crear mundo $\to$ Reglas de juego \| Menú de pausa $\to$ Reglas de juego |

---

## 📖 Mandato de Política de Ausencia

> [!NOTE]
> **Cero Comandos de Servidor**: En conformidad con la filosofía de gratificación instantánea (**Instant Gratification**), **Collapsible Game Rules** no registra comandos en el chat (como `/collapsiblegamerules reload` o `/cgr config`). Toda la gestión se realiza directamente desde la pantalla de reglas con respuesta visual instantánea.

---

## 💻 Compatibilidad con Comandos de Vanilla

Dado que el mod opera sobre el `AbstractGameRulesScreen` nativo, cualquier cambio efectuado a través del chat se sincroniza inmediatamente con la pantalla:

### Comandos Comunes de `/gamerule`

```bash
# Desactivar destrucción por mobs (explosiones de creeper)
/gamerule mobGriefing false

# Mantener inventario al morir
/gamerule keepInventory true

# Velocidad de tics aleatorios (crecimiento de cultivos)
/gamerule randomTickSpeed 10

# Detener ciclo de luz diurna
/gamerule doDaylightCycle false
```

Al reabrir la pantalla de reglas, todos los valores modificados se reflejarán instantáneamente dentro de sus categorías desplegables.

---

## 🔗 Documentación Relacionada

* [[🏠 Resumen y Portal Principal|es_es-Home]]
* [[📜 Tabla de Referencia de GameRules|es_es-GameRules-Reference]]
* [[🎛️ Ajustes Preestablecidos y Controles de Game Rules|es_es-Game-Rule-Presets-and-Controls]]
