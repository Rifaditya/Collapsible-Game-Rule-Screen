# 💬 Comandos Brigadier e Escopo

| Parâmetro | Especificação |
| :--- | :--- |
| **Comandos Brigadier Próprios** | `0` (Nenhum registrado por design) |
| **Arquitetura de Comandos** | Interface puramente gráfica do cliente (GUI) |
| **Conformidade com Vanilla** | `100%` Compatível com os comandos `/gamerule` nativos |
| **Acesso ao Menu no Jogo** | Criar Mundo $\to$ Regras de Jogo \| Menu de Pausa $\to$ Regras de Jogo |

---

## 📖 Mandato da Política de Ausência

> [!NOTE]
> **Zero Comandos de Servidor**: Em conformidade com a filosofia de resposta imediata (**Instant Gratification**), o **Collapsible Game Rules** não registra comandos no chat (como `/collapsiblegamerules reload` ou `/cgr config`). Todas as ações são realizadas diretamente na interface gráfica nativa de regras com resposta visual instantânea.

---

## 💻 Compatibilidade com Comandos Vanilla

Como o mod opera sobre a tela nativa `AbstractGameRulesScreen`, quaisquer modificações feitas via chat são sincronizadas de forma instantânea com o menu:

### Comandos `/gamerule` Comuns

```bash
# Desativar destruição de blocos por monstros (explosões de creeper)
/gamerule mobGriefing false

# Manter inventário ao morrer
/gamerule keepInventory true

# Velocidade de tiques aleatórios (crescimento de plantações)
/gamerule randomTickSpeed 10

# Pausar ciclo dia e noite
/gamerule doDaylightCycle false
```

Ao reabrir o menu de regras, todos os valores configurados via chat serão refletidos imediatamente em suas respectivas categorias dobráveis.

---

## 🔗 Documentação Relacionada

* [[🏠 Visão Geral & Página Inicial|pt_br-Home]]
* [[📜 Tabela de Referência de Regras de Jogo|pt_br-GameRules-Reference]]
* [[🎛️ Predefinições de Regras de Jogo & Controles|pt_br-Game-Rule-Presets-and-Controls]]
