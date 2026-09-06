# 🗂️ Catégories Repliables

| Paramètre | Spécification |
| :--- | :--- |
| **Composant Système** | `CollapsibleCategoryRuleEntry` (Classe interne) |
| **Mixin Englobant** | `AbstractGameRulesScreenRuleListMixin` |
| **Classe Cible** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen.RuleList` |
| **Icônes d'État** | Déplié : `▼ ` \| Replié : `▶ ` |
| **Format du Badge de Nombre** | ` (N rules)` (`ChatFormatting.GRAY`) |
| **Couleur au Survol** | `0x22FFFFFF` (25% rectangle blanc translucide) |
| **Ligne de Séparation Basse** | `0x44AAAAAA` (Ligne discrète) |
| **Couleur du Texte** | Survolé : `0xFFFFFFAA` \| Standard : `0xFFFFFFFF` |
| **Son de Clic** | `net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK` (Volume : `1.0F`) |
| **Type de Narration** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 Vue d'Ensemble

Dans Minecraft Vanilla, les noms de catégories sont des étiquettes statiques (`CategoryRuleEntry`), listant toutes les règles dans un long volet défilant. Avec de nombreux mods, la liste devient difficile à lire.

**Collapsible Categories** remplace ces étiquettes par des widgets interactifs `CollapsibleCategoryRuleEntry` qui s'ouvrent et se ferment à la demande.

---

## 🎨 Disposition Visuelle et Hiérarchie

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ [ Expand All ]                                             [ Collapse All ] │ ◄── GlobalActionsRuleEntry (Index 0)
├─────────────────────────────────────────────────────────────────────────────┤
│ ▼ ⚔️ Mobs (14 rules)                                                        │ ◄── CollapsibleCategoryRuleEntry (Expanded)
│ ─────────────────────────────────────────────────────────────────────────── │
│   mobGriefing                                                     [ ON ]    │ ◄── Child RuleEntry
│   doMobSpawning                                                   [ ON ]    │ ◄── Child RuleEntry
│   doMobLoot                                                       [ ON ]    │ ◄── Child RuleEntry
├─────────────────────────────────────────────────────────────────────────────┤
│ ▶ 👤 Player (8 rules)                                                       │ ◄── CollapsibleCategoryRuleEntry (Collapsed)
│ ─────────────────────────────────────────────────────────────────────────── │
│ ▼ 🌧️ Updates (6 rules)                                                      │ ◄── CollapsibleCategoryRuleEntry (Expanded)
│ ─────────────────────────────────────────────────────────────────────────── │
│   doFireTick                                                      [ ON ]    │ ◄── Child RuleEntry
│   randomTickSpeed                                                 [ 3  ]    │ ◄── Child RuleEntry
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## ⚙️ Mécanismes Techniques

### 1. Algorithme de Comptage des Règles
Dans `updateVisibleEntries()`, le mod compte les règles jusqu'à la prochaine catégorie :

```java
int childCount = 0;
for (int j = i + 1; j < this.collapsible_game_rules$allEntries.size(); j++) {
    if (this.collapsible_game_rules$allEntries.get(j) instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
        break;
    }
    childCount++;
}
```

### 2. Pipeline de Rendu (`extractContent`)
S'appuie sur `GuiGraphicsExtractor` :
1. **Encadré au survol** : Trace un fond `0x22FFFFFF` entre `[getX() - 2, getY()]` et `[getX() + getWidth() + 2, getY() + 24]`.
2. **Flèche et Texte** : Préfixe (`▼ ` ou `▶ `), nom de la catégorie et badge gris (` (N rules)`).
3. **Centrage** : Centré horizontalement sur `getContentXMiddle()` avec décalage vertical `getContentY() + 5`.
4. **Séparateur Inférieur** : Une ligne `0x44AAAAAA` à `getY() + 23` délimite chaque catégorie.

### 3. Gestion des Clics
Dans `mouseClicked(MouseButtonEvent event, boolean doubleClick)` :
* **Clic Gauche (`event.button() == 0`)** ou **Clic Droit (`event.button() == 1`)** :
  1. Lance `toggleAction.run()`.
  2. Enregistre l'état dans `GameRuleStateConfig`.
  3. Joue le son Vanilla : `SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)`.
  4. Déclenche `updateVisibleEntries()` pour adapter la liste.
  5. Met à jour les dimensions avec `updateSizeAndPosition(...)`.

### 4. Accessibilité et Narration d'Écran
Implémente `NarratableEntry` :
* **Priorité** : `NarrationPriority.HOVERED`
* **Sortie** : Transmet le titre en tant que `NarratedElementType.TITLE` aux lecteurs d'écran.

---

## 🔗 Documentation Associée

* [[🏠 Aperçu & Accueil|fr_fr-Home]]
* [[🌎 Actions Globales & Bascules en Masse|fr_fr-Global-Actions-and-Bulk-Toggles]]
* [[⌨️ Navigation au Clavier & Accessibilité|fr_fr-Keyboard-Navigation]]
* [[🧩 Architecture & Sous-système Mixin|fr_fr-Architecture-and-Mixins]]
