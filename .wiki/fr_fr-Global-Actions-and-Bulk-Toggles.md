# 🌎 Actions Globales & Bascules en Masse

| Paramètre | Spécification |
| :--- | :--- |
| **Classe du Composant** | `net.instantgratification.collapsiblegamerules.ui.GlobalActionsRuleEntry` |
| **Position dans la Liste** | Index `0` (Fixé en haut de `RuleList`) |
| **Bouton Gauche** | `[ Expand All ]` (`gui.collapsible-game-rules.expand_all`) |
| **Bouton Droit** | `[ Collapse All ]` (`gui.collapsible-game-rules.collapse_all`) |
| **Centre du Bouton Gauche** | `this.getX() + this.getWidth() / 4` |
| **Centre du Bouton Droit** | `this.getX() + 3 * this.getWidth() / 4` |
| **Couleur au Survol** | `0x22FFFFFF` (Appliquée sur la moitié survolée) |
| **Ligne de Séparation Basse** | `0x44AAAAAA` |
| **Son de Clic** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 Vue d'Ensemble

Quand un modpack comprend des centaines de règles, ouvrir ou fermer chaque dossier à la main est contraignant.

La **Barre d'Actions Globales** est ancrée à l'**Index 0**, permettant de tout déplier ou tout replier instantanément d'un seul clic.

---

## 🎨 Disposition Partagée

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ◄──────────────── Left Half ───────────────►◄────────────── Right Half ───► │
│               [ Expand All ]                               [ Collapse All ] │
│ ─────────────────────────────────────────────────────────────────────────── │
└─────────────────────────────────────────────────────────────────────────────┘
```

* **Zone Gauche (`mouseX < getX() + getWidth() / 2`)** : Lance `expandAll`.
* **Zone Droite (`mouseX >= getX() + getWidth() / 2`)** : Lance `collapseAll`.
* **Survol** : Éclaire la moitié active avec `0x22FFFFFF` et passe la police en `0xFFFFFFAA`.

---

## ⚙️ Mécanismes Techniques

### 1. Insertion Fixée à l'Index 0
Dans `AbstractGameRulesScreenRuleListMixin` :

```java
if (!this.collapsible_game_rules$allEntries.isEmpty()) {
    this.addEntry(new GlobalActionsRuleEntry(
        () -> {
            List<String> allKeys = this.collapsible_game_rules$allEntries.stream()
                .filter(e -> e instanceof AbstractGameRulesScreen.CategoryRuleEntry)
                .map(e -> {
                    Component lbl = ((CategoryRuleEntryAccessor) e).collapsible_game_rules$getLabel();
                    if (lbl.getContents() instanceof TranslatableContents translatable) {
                        return translatable.getKey();
                    }
                    return lbl.getString();
                })
                .toList();
            GameRuleStateConfig.expandAll(allKeys);
            this.collapsible_game_rules$updateVisibleEntries();
        },
        () -> {
            GameRuleStateConfig.collapseAll();
            this.collapsible_game_rules$updateVisibleEntries();
        }
    ));
}
```

### 2. Extraction des Clés de Catégorie
1. Isole les entrées `CategoryRuleEntry`.
2. Récupère le libellé avec `CategoryRuleEntryAccessor`.
3. Extrait la clé (`gamerule.category.spawning`) si traduite.
4. Utilise le texte brut sinon.
5. Regroupe en liste immuable Java 25 (`.toList()`) et transmet à `GameRuleStateConfig.expandAll(allKeys)`.

### 3. Répartition des Clics
Dans `GlobalActionsRuleEntry.mouseClicked(MouseButtonEvent event, boolean doubleClick)` :
```java
if (event.button() == 0 || event.button() == 1) {
    double mouseX = event.x();
    if (mouseX < this.getX() + this.getWidth() / 2.0) {
        this.expandAll.run();
    } else {
        this.collapseAll.run();
    }
    Minecraft.getInstance().getSoundManager().play(
        SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)
    );
    return true;
}
```

---

## 🌐 Clés de Localisation

```json
{
  "gui.collapsible-game-rules.expand_all": "Expand All",
  "gui.collapsible-game-rules.collapse_all": "Collapse All"
}
```

---

## 🔗 Documentation Associée

* [[🏠 Aperçu & Accueil|fr_fr-Home]]
* [[🗂️ Catégories Repliables|fr_fr-Collapsible-Categories]]
* [[🧠 Persistance de l'État & Configuration JSON|fr_fr-State-Persistence-and-Config]]
* [[🧩 Architecture & Sous-système Mixin|fr_fr-Architecture-and-Mixins]]
