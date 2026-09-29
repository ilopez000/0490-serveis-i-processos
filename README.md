# MP0490 Programació de serveis i processos · DAM2 · PratFP

Exemples de classe del mòdul **0490 Programació de serveis i processos** (CFGS DAM, 2n curs).
Tots formen part del projecte **ServiHub**, una plataforma de serveis distribuïts que creix
versió a versió: v1 llançador multiprocés · v2 processador multifil · v3 servidor de sockets ·
v4 servei REST · v5 rols i TLS.

## Com fer-ho servir

1. Clona el repositori:
   ```
   git clone https://github.com/ilopez000/0490-serveis-i-processos.git
   ```
2. Obre la carpeta amb **IntelliJ IDEA** (és un projecte Maven, Java 21).
3. Obre la classe que vulguis i prem el triangle verd ▶ al costat del `main`.

## Exemples per sessió

Cada sessió té el seu paquet dins de `src/main/java/cat/pratfp/servihub/`.

| Sessió | Paquet | Tema | Classes |
|---|---|---|---|
| 1 | `sessio1` | ServiHub v0: el primer procés fill | `App` |
| 2 | `sessio2` | Llançar i controlar subprocessos amb ProcessBuilder | `Ex1ProcessosDelSistema` · `Ex2LlancaIEspera` · `Ex3SortidaIError` · `Ex4TempsMaxim` · `Ex5EntornIFitxers` · `Ex6TasquesEnParallel` · auxiliars `Jvm`, `Tasca`, `Mostrador` |

## Estructura

```
0490-serveis-i-processos/
├── pom.xml
└── src/main/java/cat/pratfp/servihub/
    ├── sessio1/           ← un paquet per sessió
    └── sessio2/
```

La carpeta `sortida/` la crea l'exemple 5 en executar-se i no es puja al repositori.

---
Docent: Ignasi López Aylagas · PratFP
