# Open Data – Inflazioa eta Inbertsioa (DCA) 📈💶

Proiektu akademikoa JavaFX erabiliz garatua, helburu didaktiko argi batekin: **inflazioak** denborarekin diruaren **erosahalmena** nola murrizten duen ulertzea, eta **inbertsio periodikoak (DCA)** indizeetan epe luzera nola lagundu dezakeen inflazioaren eragina **konpentsatzen** eta kapitala **hazten**.

Aplikazioa ikuspegi **bisual** eta **praktikoarekin** diseinatuta dago, klasean azaltzeko egokia izan dadin, grafiko argiak eta konparaketa zuzenak erabiliz.

---

## Edukia

- [Helburu didaktikoa](#helburu-didaktikoa)
- [Funtzionalitateak](#funtzionalitateak)
  - [Menu nagusia](#menu-nagusia)
  - [Inflazioa](#inflazioa)
  - [Inbertsioa (DCA)](#inbertsioa-dca)
- [Datuak eta Open Data](#datuak-eta-open-data)
- [Arkitektura eta egitura](#arkitektura-eta-egitura)

---

## Helburu didaktikoa

Proiektu honek bi kontzeptu ekonomiko nagusi lantzen ditu modu ulergarri eta bisualean:

### 1. Inflazioa
Inflazioak eragiten du denborarekin diruak **balioa galtzea**. Hau da, gaur egun dugun diru kopuru berarekin, etorkizunean produktu eta zerbitzu gutxiago eros daitezke.

Aplikazioak aukera ematen du:
- urte jakin batean dugun diru kopuru bat sartzeko,
- eta gaur egun kopuru horrek zenbat **erosahalmen** izango lukeen ikusteko.

### 2. Inbertsioa (DCA)
DCA (Dollar-Cost Averaging) estrategia erabiliz, hilean kopuru finko bat inbertitzen da indize batean. Estrategia honen bidez:
- merkatuaren gorabeherak leundu daitezke,
- eta epe luzera, inflazioaren gainetik errentagarritasuna lortu daiteke.

---

## Funtzionalitateak

### Menu nagusia

Aplikazioaren hasierako pantaila da, eta bertatik bi atal nagusietara sar daiteke:
- **Inflazioa**
- **Inbertsioa (DCA)**

Nabigazioa JavaFX-en `Scene` bera mantenduz egiten da, FXML fitxategiak aldatuz.

<img width="1196" height="628" alt="image" src="https://github.com/user-attachments/assets/ce8ae2c1-4048-471e-875c-61c89debe0b3" />

---

### Inflazioa

Atal honetan, erabiltzaileak inflazioaren eragina modu argian ikus dezake.

**Sarrerak:**
- Zenbatekoa (€)
- Hasierako urtea

**Emaitzak:**
- Gaur egungo balioa (€), inflazioa kontuan hartuta
- Balio-galera testu bidez azaldua

**Grafikoa:**
- Gaurko balio baliokidearen bilakaera
- Hasierako diruaren erosahalmenaren bilakaera

Kalkuluak inflazio-indizeen arteko erlazioan oinarritzen dira, eta datuak serie historiko gisa erakusten dira.

<img width="1199" height="626" alt="image" src="https://github.com/user-attachments/assets/8600e128-c83d-438f-83fa-5440f08e91f9" />

---

### Inbertsioa (DCA)

Atal honetan inbertsio periodiko baten simulazioa egiten da.

**Aukerak:**
- Inbertsio-indizea:
  - S&P 500
  - Nasdaq 100
  - Ibex 35
- Hilabeteko ekarpena (€)
- Hasierako urtea

**Konparaketa:**
- Aurreztea (inbertitu gabe), gaurko eurotan
- Inbertsioa (DCA), gaurko eurotan

Grafikoan argi ikusten da epe luzera inbertsioak aurrezte hutsarekin alderatuta izan dezakeen abantaila, betiere inflazioa kontuan hartuta.

<img width="1196" height="625" alt="image" src="https://github.com/user-attachments/assets/a52b7465-00f1-4751-bb92-8187fbd5dd64" />

---

## Datuak eta Open Data

Aplikazioak datu errealak erabiltzen ditu, `resources` karpetan CSV formatuan gordeta:

- **Inflazioa**: urteko inflazio-indizeak (1975–2024)
- **Indize bursatilak**:
  - S&P 500
  - Nasdaq 100
  - Ibex 35

> Datu hauek Open Data iturrietatik eskuratu dira, eta proiektuan **CSV moduan txertatu** dira aplikazioa offline exekutatu ahal izateko. Une honetan ez da API kontsumo zuzenik egiten.

---

## Arkitektura eta egitura

### Teknologiak
- Java
- JavaFX (FXML + Controls)
- Maven
- Proiektu modularra (`module-info.java`)

### Egitura nagusia


```
src/main/java/
└── opendata/
    ├── App.java
    ├── controller/
    ├── model/
    └── service/

src/main/resources/
└── opendata/
    ├── *.fxml
    ├── datuak/
    │   ├── inflazioa.csv
    │   ├── sp500.csv
    │   ├── nasdaq100.csv
    │   └── ibex35.csv
    └── Images/
```


