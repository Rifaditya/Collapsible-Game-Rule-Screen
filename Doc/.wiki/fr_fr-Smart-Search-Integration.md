# 🔍 Intégration de la Recherche Intelligente

| Paramètre | Spécification |
| :--- | :--- |
| **Méthode Interceptée** | `populateChildren(Ljava/lang/String;)V` |
| **Point d'Injection** | `@At("TAIL")` |
| **Classe Mixin** | `AbstractGameRulesScreenRuleListMixin` |
| **Normalisation du Texte** | `filter.toLowerCase(java.util.Locale.ROOT)` |
| **Indicateur de Recherche** | `isSearching = !currentFilter.isEmpty()` |
| **Condition de Dépliage** | `isExpanded = isSearching || GameRuleStateConfig.isExpanded(key)` |
| **Méthode d'Actualisation** | `collapsible_game_rules$updateVisibleEntries()` |

---

## 📖 Vue d'Ensemble

En Vanilla, le champ de recherche filtre les règles selon leur nom ou description. Dans un menu repliable, un filtrage classique cacherait les résultats dans les dossiers fermés.

La **Recherche Intelligente** surveille les saisies en temps réel : dès qu'un mot-clé est saisi, toutes les catégories contenant une règle correspondante se déplient automatiquement, révélant les résultats sans intervention supplémentaire.

---

## ⚙️ Schéma de la Recherche Intelligente

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

## 💻 Détails Techniques d'Implémentation

### 1. Crochet `@Inject`
Le mixin intercepte la fin de la méthode `populateChildren` de Vanilla :

```java
@Inject(method = "populateChildren(Ljava/lang/String;)V", at = @At("TAIL"))
private void collapsible_game_rules$onPopulateChildren(String filter, CallbackInfo ci) {
    this.collapsible_game_rules$currentFilter = (filter != null) ? filter.toLowerCase(java.util.Locale.ROOT) : "";
    // Save the currently generated list of all entries
    this.collapsible_game_rules$allEntries = new ArrayList<>(this.children());
    this.collapsible_game_rules$updateVisibleEntries();
}
```

### 2. Évaluation de l'État de Dépliage
Dans `updateVisibleEntries()` :

```java
boolean isSearching = !this.collapsible_game_rules$currentFilter.isEmpty();
final String finalPersistenceKey = persistenceKey;
boolean isExpanded = isSearching || GameRuleStateConfig.isExpanded(finalPersistenceKey);
```

### 3. Restauration Non Destructive
Puisque `isSearching` n'agit que pendant la recherche, effacer le champ restaure aussitôt les dossiers ouverts ou fermés enregistrés dans `GameRuleStateConfig`, sans écraser le fichier de configuration.

---

## 🔗 Documentation Associée

* [[🏠 Aperçu & Accueil|fr_fr-Home]]
* [[🗂️ Catégories Repliables|fr_fr-Collapsible-Categories]]
* [[🧠 Persistance de l'État & Configuration JSON|fr_fr-State-Persistence-and-Config]]
* [[🧩 Architecture & Sous-système Mixin|fr_fr-Architecture-and-Mixins]]
