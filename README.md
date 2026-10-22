# Paskolu Skaiciuokle

JavaFX desktop application for calculating and visualizing a home loan repayment plan.

## Features

- Calculate monthly payments using:
  - `Linijinis` (linear repayment)
  - `Anuiteto` (annuity repayment)
- Display full payment schedule in a table:
  - Date
  - Total payment
  - Principal part
  - Interest part
  - Remaining balance
- Visualize monthly payment trend in a line chart
- Apply payment deferral logic and recalculate schedule
- Filter payment rows by date range
- Export results to CSV (`PaskolosDuomenys.csv`)

## Tech Stack

- Java 25
- JavaFX 21.0.6 (`javafx-controls`, `javafx-fxml`)
- Maven + JavaFX Maven Plugin

## Project Structure

```text
Paskolu_Skaiciuokle/
  pom.xml
  mvnw
  mvnw.cmd
  src/main/java/org/skaiciuokle/paskolu_skaiciuokle/
    Controller.java
    Skaiciuokle.java
    Launcher.java
    Skaic.java
    MokejimoDuom.java
    Eksport.java
  src/main/resources/org/skaiciuokle/paskolu_skaiciuokle/
    Ekranas.fxml
```

## Requirements

- JDK 25 installed
- Internet connection on first run (Maven downloads dependencies)

## Run

Use Maven Wrapper (recommended):

```powershell
cd "C:\Users\bezdz\Documents\Java\Skaiciuokle\Paskolu_Skaiciuokle"
.\mvnw.cmd clean javafx:run
```

## Build

```powershell
cd "C:\Users\bezdz\Documents\Java\Skaiciuokle\Paskolu_Skaiciuokle"
.\mvnw.cmd clean package
```

## Entry Point

Configured JavaFX main class in `pom.xml`:

- `org.skaiciuokle.paskolu_skaiciuokle.Skaiciuokle`

A standard Java `main` launcher also exists:

- `org.skaiciuokle.paskolu_skaiciuokle.Launcher`

## CSV Export

Exported file name:

- `PaskolosDuomenys.csv`

CSV header:

```text
Data, Imokos suma, Kredito suma, Palukanu suma, Likus suma
```

## Notes

- UI labels are in Lithuanian.
- The app calculates schedule based on current date and selected loan end date.

