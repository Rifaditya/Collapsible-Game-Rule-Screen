# ⌨️ Navigation au Clavier & Accessibilité

| Paramètre | Spécification |
| :--- | :--- |
| **Classe du Composant** | `CollapsibleCategoryRuleEntry` |
| **Interface d'Accessibilité** | `net.minecraft.client.gui.narration.NarratableEntry` |
| **Priorité de Narration** | `NarrationPriority.HOVERED` |
| **Type d'Élément Narré** | `NarratedElementType.TITLE` |
| **Touches de Basculement** | `GLFW_KEY_SPACE`, `GLFW_KEY_ENTER`, `GLFW_KEY_KP_ENTER` |
| **Touche pour Replier** | `GLFW_KEY_LEFT` (Uniquement si déplié) |
| **Touche pour Déplier** | `GLFW_KEY_RIGHT` (Uniquement si replié) |
| **Retour Sonore** | `SoundEvents.UI_BUTTON_CLICK` (`1.0F`) |

---

## 📖 Vue d'Ensemble

Collapsible Game Rules prend en charge la navigation complète au clavier et la synthèse vocale, permettant de parcourir et modifier toutes les règles sans utiliser la souris.

---

## ⌨️ Raccourcis Clavier

Lorsqu'un en-tête est sélectionné dans `RuleList`, `keyPressed(KeyEvent event)` gère les touches suivantes :

| Touche | Constante GLFW | Action | Condition | Son |
| :--- | :--- | :--- | :--- | :--- |
| **Espace** | `GLFW_KEY_SPACE` | Basculer l'état | Toujours | `UI_BUTTON_CLICK` |
| **Entrée** | `GLFW_KEY_ENTER` | Basculer l'état | Toujours | `UI_BUTTON_CLICK` |
| **Entrée Pavé Numérique** | `GLFW_KEY_KP_ENTER` | Basculer l'état | Toujours | `UI_BUTTON_CLICK` |
| **Flèche Gauche (←)** | `GLFW_KEY_LEFT` | **Replier la Catégorie** | Seulement si `expanded == true` | `UI_BUTTON_CLICK` |
| **Flèche Droite (→)** | `GLFW_KEY_RIGHT` | **Déplier la Catégorie** | Seulement si `expanded == false`| `UI_BUTTON_CLICK` |

---

## ⚙️ Implémentation Technique

### 1. Gestion des Flèches Directionnelles
Adopte le comportement habituel des arbres de répertoires des systèmes d'exploitation :

```java
@Override
public boolean keyPressed(KeyEvent event) {
    int keyCode = event.key();
    if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER || keyCode == GLFW.GLFW_KEY_SPACE) {
        this.toggleAction.run();
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        return true;
    } else if (keyCode == GLFW.GLFW_KEY_LEFT && this.expanded) {
        this.toggleAction.run();
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        return true;
    } else if (keyCode == GLFW.GLFW_KEY_RIGHT && !this.expanded) {
        this.toggleAction.run();
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        return true;
    }
    return super.keyPressed(event);
}
```

### 2. Synthèse Vocale (`updateNarration`)

```java
@Override
public NarrationPriority narrationPriority() {
    return NarrationPriority.HOVERED;
}

@Override
public void updateNarration(NarrationElementOutput output) {
    output.add(NarratedElementType.TITLE, this.label);
}
```

---

## 🔗 Documentation Associée

* [[🏠 Aperçu & Accueil|fr_fr-Home]]
* [[🗂️ Catégories Repliables|fr_fr-Collapsible-Categories]]
* [[🖥️ HUD, Diagnostics & Rendu de l'Interface|fr_fr-HUD-and-Diagnostics]]
* [[🧩 Architecture & Sous-système Mixin|fr_fr-Architecture-and-Mixins]]
