# ⚒️ Collapsible Game Rules Wiki (Règles de Jeu Repliables)

<div align="center">

<img src="https://raw.githubusercontent.com/Rifaditya/Collapsible-Game-Rule-Screen/main/Images/2026-08-04_11.06.33.png" alt="Collapsible Game Rules Banner" width="800">

<p align="center">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge&logo=minecraft" alt="Minecraft 26.2+">
  <img src="https://img.shields.io/badge/Fabric-0.145.4+-blue?style=for-the-badge&logo=fabric" alt="Fabric">
  <img src="https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk" alt="Java 25">
  <img src="https://img.shields.io/badge/DasikLibrary-1.7.4-purple?style=for-the-badge" alt="DasikLibrary 1.7.4">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License GPLv3">
</p>

</div>

---

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 🧭 Bienvenue sur Collapsible Game Rules

**Collapsible Game Rules** réorganise l'écran standard des règles de jeu de Minecraft en une interface claire et repliable, dotée d'un dépliage intelligent lors de la recherche, d'une navigation intégrale au clavier, d'une persistance locale de l'état et de préréglages de jeu intégrés.

---

## 🌟 Fonctionnalités Principales & Index Rapide

| Fonctionnalité | Description | Guide de Référence |
| :--- | :--- | :--- |
| **🗂️ Catégories Repliables** | Remplace les en-têtes statiques par des widgets interactifs (`▼`/`▶`) arborant un badge de comptage. | [[🗂️ Catégories Repliables\|fr_fr-Collapsible-Categories]] |
| **🌎 Barre d'Actions Globales** | Boutons ancrés en haut pour « Tout Déplier » et « Tout Replier » en un seul clic. | [[🌎 Actions Globales & Bascules en Masse\|fr_fr-Global-Actions-and-Bulk-Toggles]] |
| **🔍 Recherche Intelligente** | Déplie automatiquement les catégories correspondantes dès que du texte est saisi dans la recherche. | [[🔍 Intégration de la Recherche Intelligente\|fr_fr-Smart-Search-Integration]] |
| **⌨️ Navigation au Clavier** | Prise en charge intégrale du clavier (Espace, Entrée, flèches) et narration d'accessibilité. | [[⌨️ Navigation au Clavier & Accessibilité\|fr_fr-Keyboard-Navigation]] |
| **🧠 Persistance de l'État** | Mémorise les catégories dépliées et repliées dans `config/collapsible-game-rules-state.json`. | [[🧠 Persistance de l'État & Configuration JSON\|fr_fr-State-Persistence-and-Config]] |
| **✨ Embellissement de Noms** | Formate dynamiquement les clés brutes non traduites des mods en titres clairs au format Title Case. | [[✨ Embellissement & Formatage des Catégories\|fr_fr-Category-Prettification-and-Naming]] |
| **🎛️ Préréglages & Curseurs** | Profils prêts à l'emploi (Bâtisseur, Jeu Rapide, Hardcore) et curseurs numériques continus. | [[🎛️ Préréglages de Règles de Jeu & Contrôles\|fr_fr-Game-Rule-Presets-and-Controls]] |
| **🧩 Intégration DasikLibrary** | Interroge les traductions générées par `DynamicGameRuleManager` pour la localisation des catégories. | [[📚 Intégration de l'API DasikLibrary\|fr_fr-API-and-Library-Integration]] |

---

## 🚀 Démarrage Rapide & Installation

1. Installez **[Fabric Loader](https://fabricmc.net/)** (`>=0.19.1`).
2. Téléchargez **[Fabric API](https://modrinth.com/mod/fabric-api)**.
3. Téléchargez **[DasikLibrary](https://modrinth.com/mod/dasik-library)** (`>=1.7.0`, **Dépendance Requise**).
4. Placez `collapsible-game-rules-1.0.9+26.2.jar` dans votre dossier `.minecraft/mods`.
5. Lancez Minecraft avec **Java 25+**.

---

## 📚 Index Complet de la Documentation

```
Racine du Wiki
├── 🧭 Matrice de Compatibilité des Versions ----> [[🧭 Matrice de Compatibilité des Versions|fr_fr-Version-Compatibility]]
├── 🎮 Mécaniques Principales de l'Interface
│   ├── Catégories Repliables ------------------> [[🗂️ Catégories Repliables|fr_fr-Collapsible-Categories]]
│   ├── Actions Globales & Bascules en Masse ---> [[🌎 Actions Globales & Bascules en Masse|fr_fr-Global-Actions-and-Bulk-Toggles]]
│   ├── Recherche Intelligente -----------------> [[🔍 Intégration de la Recherche Intelligente|fr_fr-Smart-Search-Integration]]
│   └── Navigation au Clavier & Accessibilité -> [[⌨️ Navigation au Clavier & Accessibilité|fr_fr-Keyboard-Navigation]]
├── ⚙️ Configuration & Préréglages
│   ├── Persistance de l'État & JSON -----------> [[🧠 Persistance de l'État & Configuration JSON|fr_fr-State-Persistence-and-Config]]
│   ├── Embellissement des Catégories ----------> [[✨ Embellissement & Formatage des Catégories|fr_fr-Category-Prettification-and-Naming]]
│   └── Préréglages & Contrôles ----------------> [[🎛️ Préréglages de Règles de Jeu & Contrôles|fr_fr-Game-Rule-Presets-and-Controls]]
├── 📋 Portée & Référence de Jeu
│   ├── Tableau de Référence des Règles --------> [[📜 Tableau de Référence des Règles de Jeu|fr_fr-GameRules-Reference]]
│   ├── Commandes Brigadier & Portée -----------> [[💬 Commandes Brigadier & Portée|fr_fr-Commands]]
│   └── Progrès & Portée -----------------------> [[🏆 Progrès & Portée|fr_fr-Advancements]]
└── 💻 Architecture Technique & Développement
    ├── HUD, Diagnostics & Rendu --------------> [[🖥️ HUD, Diagnostics & Rendu de l'Interface|fr_fr-HUD-and-Diagnostics]]
    ├── Configuration Développeur & Gradle -----> [[🛠️ Environnement Développeur & Builds Gradle|fr_fr-Developer-Setup-and-Building]]
    ├── Architecture & Sous-système Mixin ------> [[🧩 Architecture & Sous-système Mixin|fr_fr-Architecture-and-Mixins]]
    └── Intégration de l'API DasikLibrary ------> [[📚 Intégration de l'API DasikLibrary|fr_fr-API-and-Library-Integration]]
```

---

## 📜 Crédits & Licence

* **Auteur & Développeur Principal** : **Dasik (Rifaditya)**
* **Licence** : **GNU General Public License v3.0 (GPLv3)**
* **Dépôt GitHub** : [GitHub Repository](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen)
* **Projet Modrinth** : [Modrinth](https://modrinth.com/mod/collapsible-game-rules) (`lObgjyJl`)
* **Projet CurseForge** : [CurseForge](https://www.curseforge.com/minecraft/mc-mods/collapsible-game-rules) (`1468932`)
