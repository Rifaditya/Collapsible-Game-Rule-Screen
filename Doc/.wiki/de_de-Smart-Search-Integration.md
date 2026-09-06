# 🔍 Intelligente Suchintegration

| Parameter | Spezifikation |
| :--- | :--- |
| **Abgefangene Methode** | `populateChildren(Ljava/lang/String;)V` |
| **Injektionspunkt** | `@At("TAIL")` |
| **Mixin-Klasse** | `AbstractGameRulesScreenRuleListMixin` |
| **Anfrage-Normalisierung** | `filter.toLowerCase(java.util.Locale.ROOT)` |
| **Suchzustands-Flag** | `isSearching = !currentFilter.isEmpty()` |
| **Ausklappbedingung** | `isExpanded = isSearching || GameRuleStateConfig.isExpanded(key)` |
| **Neuaufruf-Methode** | `collapsible_game_rules$updateVisibleEntries()` |

---

## 📖 Übersicht

In Vanilla filtert die Suchleiste nach Regelnamen oder Beschreibungen. In einer einklappbaren Ansicht würden Suchtreffer bei geschlossenen Kategorien verborgen bleiben.

Die **Intelligente Suche** beobachtet das Suchfeld: Sobald Text eingetippt wird, klappen alle Kategorien mit Treffern automatisch auf, sodass Ergebnisse sofort sichtbar sind.

---

## ⚙️ Ablauf der intelligenten Suche

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

## 💻 Technische Details

### 1. Injektionspunkt `@Inject`
Der Mixin hängt sich an das Ende der Vanilla-Methode `populateChildren`:

```java
@Inject(method = "populateChildren(Ljava/lang/String;)V", at = @At("TAIL"))
private void collapsible_game_rules$onPopulateChildren(String filter, CallbackInfo ci) {
    this.collapsible_game_rules$currentFilter = (filter != null) ? filter.toLowerCase(java.util.Locale.ROOT) : "";
    // Save the currently generated list of all entries
    this.collapsible_game_rules$allEntries = new ArrayList<>(this.children());
    this.collapsible_game_rules$updateVisibleEntries();
}
```

### 2. Dynamische Zustandsauswertung
In `updateVisibleEntries()`:

```java
boolean isSearching = !this.collapsible_game_rules$currentFilter.isEmpty();
final String finalPersistenceKey = persistenceKey;
boolean isExpanded = isSearching || GameRuleStateConfig.isExpanded(finalPersistenceKey);
```

### 3. Zerstörungsfreie Zustandswiederherstellung
Da `isSearching` nur während aktiver Suche gilt, stellt das Leeren des Suchfeldes die in `GameRuleStateConfig` hinterlegten Einstellungen sofort wieder her, ohne die gespeicherte Konfiguration zu überschreiben.

---

## 🔗 Weiterführende Dokumentation

* [[🏠 Übersicht & Startseite|de_de-Home]]
* [[🗂️ Einklappbare Kategorien|de_de-Collapsible-Categories]]
* [[🧠 Zustandsspeicherung & JSON-Konfiguration|de_de-State-Persistence-and-Config]]
* [[🧩 Architektur & Mixin-Subsystem|de_de-Architecture-and-Mixins]]
