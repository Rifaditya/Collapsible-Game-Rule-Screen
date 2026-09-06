# 🎛️ Préréglages de Règles de Jeu & Contrôles

| Paramètre | Spécification |
| :--- | :--- |
| **Moteur de Préréglages** | `net.instantgratification.collapsiblegamerules.preset.GameRulePresetEngine` |
| **Format du Préréglage** | `record Preset(String id, Component displayName, Map<String, Object> ruleValues)` |
| **Widgets Interactifs** | `IntegerSliderWidget`, `BooleanToggleWidget` |
| **Aide au Curseur** | `net.instantgratification.collapsiblegamerules.util.GameRuleSliderHelper` |
| **Fond Actif** | `0x4400FF00` (Vert émeraude translucide) |
| **Fond Inactif** | `0x44FF0000` (Rouge rubis translucide) |
| **Préréglages d'Usine** | `builder` ("🏰 Mode Bâtisseur"), `fast_play` ("⚡ Jeu Rapide"), `hardcore` ("💀 Réalisme Hardcore") |

---

## 📖 Vue d'Ensemble

Collapsible Game Rules fournit des boutons et curseurs intuitifs pour appliquer des configurations en un clic ou glisser des curseurs sans taper de chiffres.

---

## 🏰 Matrice des Préréglages Intégrés

`GameRulePresetEngine` propose trois profils prêts à l'emploi :

| Identifiant | Nom | Règle de Jeu | Valeur Appliquée | Impact sur la Partie |
| :--- | :--- | :--- | :--- | :--- |
| `builder` | **🏰 Mode Bâtisseur** | `doDaylightCycle`<br>`doWeatherCycle`<br>`doMobSpawning`<br>`keepInventory`<br>`mobGriefing`<br>`doFireTick` | `false`<br>`false`<br>`false`<br>`true`<br>`false`<br>`false` | Idéal pour bâtir : gèle le temps et la météo, coupe les créatures, les creepers et le feu. |
| `fast_play` | **⚡ Jeu Rapide** | `randomTickSpeed`<br>`playersSleepingPercentage`<br>`keepInventory` | `10`<br>`0`<br>`true` | Survie dynamique : cultures accélérées ($3\times$), nuit passée avec 1 joueur, inventaire gardé. |
| `hardcore` | **💀 Réalisme Hardcore** | `naturalRegeneration`<br>`doInsomnia`<br>`playersSleepingPercentage` | `false`<br>`true`<br>`100` | Défi extrême : retire la régénération passive (pommes dorées/potions requises) et active les phantoms. |

---

## 🎚️ Curseur Numérique (`IntegerSliderWidget`)

Remplace les cases de texte numériques par des curseurs glissants.

### Formules Mathématiques de Normalisation

Position du curseur pour un entier $v$ :
$$\text{normalized} = \frac{\text{clamp}(v, \text{min}, \text{max}) - \text{min}}{\text{max} - \text{min}}$$

Valeur entière calculée d'après la position $p \in [0.0, 1.0]$ :
$$\text{calculatedInt} = \text{min} + \text{round}\left(p \times (\text{max} - \text{min})\right)$$

### Limites des Règles Vanilla (`GameRuleSliderHelper`)

| Clé de la Règle | Minimum ($	ext{min}$) | Maximum ($	ext{max}$) | Valeur Vanilla |
| :--- | :--- | :--- | :--- |
| `randomTickSpeed` | `0` | `100` | `3` |
| `spawnRadius` | `0` | `32` | `10` |
| `playersSleepingPercentage` | `0` | `100` | `100` |
| `maxEntityCramming` | `0` | `100` | `24` |
| `maxCommandChainLength` | `0` | `65536` | `65536` |
| `commandModificationBlockLimit` | `0` | `65536` | `32768` |

---

## 🔘 Interrupteur Booléen (`BooleanToggleWidget`)

`BooleanToggleWidget` procure un retour visuel clair :

* **État : VRAI (`ON`)** : Affiche `✔ ON` en vert sur fond émeraude (`0x4400FF00`).
* **État : FAUX (`OFF`)** : Affiche `✖ OFF` en rouge sur fond rubis (`0x44FF0000`).
* **Clic de Souris** : Change la valeur et exécute `onToggle.accept(newState)`.

```
┌─────────────────────────┐     ┌─────────────────────────┐
│         ✔ ON            │     │         ✖ OFF           │
│   (Green Tint 0x4400FF00)│     │   (Red Tint 0x44FF0000) │
└─────────────────────────┘     └─────────────────────────┘
```

---

## 🔗 Documentation Associée

* [[🏠 Aperçu & Accueil|fr_fr-Home]]
* [[🗂️ Catégories Repliables|fr_fr-Collapsible-Categories]]
* [[📜 Tableau de Référence des Règles de Jeu|fr_fr-GameRules-Reference]]
* [[🧩 Architecture & Sous-système Mixin|fr_fr-Architecture-and-Mixins]]
