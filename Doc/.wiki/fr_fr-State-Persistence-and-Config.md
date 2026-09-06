# 🧠 Persistance de l'État & Configuration JSON

| Paramètre | Spécification |
| :--- | :--- |
| **Classe de Configuration** | `net.instantgratification.collapsiblegamerules.GameRuleStateConfig` |
| **Emplacement du Fichier** | `.minecraft/config/collapsible-game-rules-state.json` |
| **Structure en Mémoire** | `Set<String> expandedCategories = new HashSet<>()` |
| **Moteur de Sérialisation** | `com.google.gson.Gson` (Pretty-Printing activé) |
| **Indicateur de Temporisation**| `private static boolean isDirty = false` |
| **Point de Sauvegarde** | `ScreenMixin` intercepte `Screen.removed()` (`@At("HEAD")`) |
| **Clé de Sauvegarde** | Clé de traduction (`TranslatableContents.getKey()`) ou chaîne brute |

---

## 📖 Vue d'Ensemble

Le mod dispose d'un mécanisme de sauvegarde asynchrone régulé. Les dossiers ouverts ou fermés restent enregistrés d'une partie à l'autre sans jamais se réinitialiser intempestivement.

---

## 📄 Format du Fichier JSON

Le fichier `.minecraft/config/collapsible-game-rules-state.json` contient un tableau direct :

```json
[
  "gamerule.category.spawning",
  "gamerule.category.mobs",
  "gamerule.category.updates"
]
```

* **Présent dans le tableau** : La catégorie est **DÉPLIÉE**.
* **Absent du tableau** : La catégorie est **REPLIÉE** (comportement par défaut).

---

## ⚡ Régulation Haute Performance des Écritures

Écrire sur le disque à chaque clic provoquerait des saccades. Pour maintenir **un affichage parfait sans perte de FPS**, `GameRuleStateConfig` s'appuie sur le flag `isDirty` :

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

## 💻 Référence des Méthodes de l'API

### Méthodes Publiques de `GameRuleStateConfig`

| Signature | Type de Retour | Description |
| :--- | :--- | :--- |
| `load()` | `void` | Charge `collapsible-game-rules-state.json` au démarrage. |
| `save()` | `void` | Écrit `expandedCategories` sur le disque via `Files.newBufferedWriter`. |
| `saveIfDirty()` | `void` | Sauvegarde uniquement si `isDirty == true`, puis remet le flag à zéro. |
| `isExpanded(String categoryKey)` | `boolean` | Indique si la clé est présente dans `expandedCategories`. |
| `setExpanded(String categoryKey, boolean expanded)` | `void` | Modifie la clé et passe `isDirty` à `true`. |
| `expandAll(Iterable<String> allKeys)` | `void` | Ajoute toutes les clés en lot et active `isDirty = true`. |
| `collapseAll()` | `void` | Vide le tableau et active `isDirty = true`. |

---

## 🔒 Sauvegarde lors de la Fermeture (`ScreenMixin`)

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

Cela garantit la sauvegarde des préférences quel que soit le bouton utilisé (**Terminé**, **Annuler** ou la touche **Échap**).

---

## 🔗 Documentation Associée

* [[🏠 Aperçu & Accueil|fr_fr-Home]]
* [[🌎 Actions Globales & Bascules en Masse|fr_fr-Global-Actions-and-Bulk-Toggles]]
* [[🧩 Architecture & Sous-système Mixin|fr_fr-Architecture-and-Mixins]]
