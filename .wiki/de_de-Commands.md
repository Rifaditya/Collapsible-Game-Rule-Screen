# 💬 Brigadier-Befehle & Abgrenzung

| Parameter | Spezifikation |
| :--- | :--- |
| **Eigene Brigadier-Befehle** | `0` (Aus architektonischen Gründen nicht registriert) |
| **Befehlsarchitektur** | Reine grafische Benutzeroberfläche (GUI) im Client |
| **Vanilla-Befehlsparität** | `100%` Vollständig kompatibel mit `/gamerule` |
| **Zugriff im Spiel** | Welt erstellen $\to$ Spielregeln \| Pausenmenü $\to$ Spielregeln |

---

## 📖 Mandat der Abgrenzungsrichtlinie

> [!NOTE]
> **Keine Server-Befehle**: Gemäß der Philosophie der sofortigen Rückmeldung (**Instant Gratification**) registriert **Collapsible Game Rules** keine Chat-Befehle (wie `/collapsiblegamerules reload` oder `/cgr config`). Sämtliche Konfiguration erfolgt interaktiv direkt im Menü mit sofortiger visueller Rückmeldung.

---

## 💻 Kompatibilität mit Vanilla-Befehlen

Da die Mod auf dem nativen `AbstractGameRulesScreen` aufbaut, werden Änderungen über den Chat synchron übernommen:

### Typische `/gamerule`-Befehle

```bash
# Zerstörung durch Mobs deaktivieren (Creeper-Explosionen)
/gamerule mobGriefing false

# Inventar beim Tod behalten
/gamerule keepInventory true

# Zufällige Tick-Geschwindigkeit (Pflanzenwachstum)
/gamerule randomTickSpeed 10

# Tag-Nacht-Zyklus anhalten
/gamerule doDaylightCycle false
```

Beim erneuten Öffnen der Spielregeln werden alle geänderten Werte direkt in ihren jeweiligen einklappbaren Kategorien aktualisiert dargestellt.

---

## 🔗 Weiterführende Dokumentation

* [[🏠 Übersicht & Startseite|de_de-Home]]
* [[📜 Spielregeln-Referenztabelle|de_de-GameRules-Reference]]
* [[🎛️ Spielregel-Voreinstellungen & Steuerelemente|de_de-Game-Rule-Presets-and-Controls]]
