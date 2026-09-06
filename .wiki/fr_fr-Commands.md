# 💬 Commandes Brigadier & Portée

| Paramètre | Spécification |
| :--- | :--- |
| **Commandes Brigadier Dédiées** | `0` (Aucune enregistrée par conception) |
| **Architecture des Commandes** | Interface purement graphique côté client (GUI) |
| **Parité avec les Commandes Vanilla**| `100%` Compatible avec la commande native `/gamerule` |
| **Accès à l'Interface en Jeu** | Créer un monde $\to$ Règles de jeu \| Menu pause $\to$ Règles de jeu |

---

## 📖 Mandat de la Politique d'Absence

> [!NOTE]
> **Zéro Commande Serveur** : Conformément à la philosophie de satisfaction immédiate (**Instant Gratification**), **Collapsible Game Rules** n'enregistre aucune commande de tchat (comme `/collapsiblegamerules reload` ou `/cgr config`). Tous les réglages s'effectuent directement depuis l'interface avec retour visuel immédiat.

---

## 💻 Compatibilité avec les Commandes Vanilla

Le mod opérant au-dessus du `AbstractGameRulesScreen` natif, toute modification via le tchat est répercutée en direct dans l'interface :

### Commandes `/gamerule` Courantes

```bash
# Désactiver les dégâts causés par les monstres (explosions de creeper)
/gamerule mobGriefing false

# Conserver l'inventaire à la mort
/gamerule keepInventory true

# Vitesse des tics aléatoires (croissance des plantes)
/gamerule randomTickSpeed 10

# Bloquer le cycle jour/nuit
/gamerule doDaylightCycle false
```

En rouvrant l'écran des règles, toutes les valeurs modifiées par commande apparaîtront instantanément dans leurs catégories repliables respectives.

---

## 🔗 Documentation Associée

* [[🏠 Aperçu & Accueil|fr_fr-Home]]
* [[📜 Tableau de Référence des Règles de Jeu|fr_fr-GameRules-Reference]]
* [[🎛️ Préréglages de Règles de Jeu & Contrôles|fr_fr-Game-Rule-Presets-and-Controls]]
