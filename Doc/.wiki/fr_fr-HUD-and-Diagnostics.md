# 🖥️ HUD, Diagnostics & Rendu de l'Interface

| Paramètre | Spécification |
| :--- | :--- |
| **Moteur Graphique** | `net.minecraft.client.gui.GuiGraphicsExtractor` |
| **Contexte d'Écran** | `net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen` |
| **Couleur au Survol** | `0xFFFFFFAA` (Légère lueur jaune) |
| **Couleur Standard** | `0xFFFFFFFF` (Blanc franc) |
| **Cadre de Survol** | `0x22FFFFFF` (Rectangle blanc transparent) |
| **Ligne de Séparation** | `0x44AAAAAA` (Ligne de bordure) |
| **Couleur Bouton Actif** | `0x4400FF00` (Vert émeraude) |
| **Couleur Bouton Inactif** | `0x44FF0000` (Rouge rubis) |
| **Narration d'Accessibilité** | `NarratedElementType.TITLE` (`NarrationPriority.HOVERED`) |

---

## 📖 Vue d'Ensemble

Minecraft 26.2 a totalement repensé le pipeline de rendu du client, migrant le dessin des interfaces vers le système `GuiGraphicsExtractor`.

Collapsible Game Rules a été écrit sur mesure pour ce moteur, garantissant un rendu vectoriel sans ralentissement.

---

## 🎨 Palette de Couleurs

| Élément | Code Hex ARGB | Description | Application |
| :--- | :--- | :--- | :--- |
| **Fond au Survol** | `0x22FFFFFF` | Rectangle blanc avec 13% d'opacité. | En-tête des catégories et actions globales. |
| **Ligne de Bordure** | `0x44AAAAAA` | Trait gris clair de 1px avec 27% d'opacité. | Ligne basse des catégories et actions. |
| **Texte au Survol** | `0xFFFFFFAA` | Jaune doux éclatant. | Libellés des catégories et boutons survolés. |
| **Texte Normal** | `0xFFFFFFFF` | Blanc pur à 100%. | Titres réguliers et badges de comptage. |
| **Badge de Comptage** | `ChatFormatting.GRAY` | Gris Vanilla (` (N rules)`). | Suffixe accolé au nom des catégories. |
| **Bouton Activé** | `0x4400FF00` | Vert émeraude avec 27% d'opacité. | Fond de l'état `✔ ON` dans `BooleanToggleWidget`. |
| **Bouton Désactivé** | `0x44FF0000` | Rouge rubis avec 27% d'opacité. | Fond de l'état `✖ OFF` dans `BooleanToggleWidget`. |

---

## 💻 Implémentation du Rendu

```java
@Override
public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
    // 1. Premium Highlight on hover
    if (hovered) {
        graphics.fill(this.getX() - 2, this.getY(), this.getX() + this.getWidth() + 2, this.getY() + 24, 0x22FFFFFF);
    }

    // 2. Directional arrow, label, and child count badge
    String prefix = this.expanded ? "▼ " : "▶ ";
    Component countBadge = Component.literal(" (" + this.childCount + " rules)").withStyle(ChatFormatting.GRAY);
    Component display = Component.literal(prefix).append(this.label).append(countBadge);

    // 3. Centered text with dynamic hover tint
    graphics.centeredText(Minecraft.getInstance().font, display,
            this.getContentXMiddle(), this.getContentY() + 5, hovered ? 0xFFFFFFAA : 0xFFFFFFFF);
    
    // 4. Subtle separating line at the bottom
    graphics.fill(this.getX() + 10, this.getY() + 23, this.getX() + this.getWidth() - 10, this.getY() + 24, 0x44AAAAAA);
}
```

---

## 🔊 Effets Sonores et Retours Audio

* **Événement** : `SoundEvents.UI_BUTTON_CLICK`
* **Volume** : `1.0F`
* **Pitch** : `1.0F`
* **Déclencheurs** :
  - Clic gauche ou droit sur un en-tête.
  - Touches Espace / Entrée sur une catégorie sélectionnée.
  - Flèche gauche (fermer) ou Flèche droite (ouvrir).
  - Clics sur « Expand All » ou « Collapse All ».

---

## 🔗 Documentation Associée

* [[🏠 Aperçu & Accueil|fr_fr-Home]]
* [[🗂️ Catégories Repliables|fr_fr-Collapsible-Categories]]
* [[⌨️ Navigation au Clavier & Accessibilité|fr_fr-Keyboard-Navigation]]
* [[🧩 Architecture & Sous-système Mixin|fr_fr-Architecture-and-Mixins]]
