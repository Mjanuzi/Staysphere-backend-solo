# Staysphere – Backend

##  Kort beskrivning
Staysphere är en plattform som kombinerar uthyrning och bokning av både privata boenden och hotell.  
I det här projektet har vi fokuserat på **refaktorering och optimering av befintlig backend-kod**, med målet att förbättra **läsbarhet, skalbarhet och framtidssäkerhet** snarare än att enbart bygga nya funktioner.  

---

##  Hur man bygger och kör projektet

### Förutsättningar
- Java 17+  
- Maven  
- MongoDB (lokalt eller i molnet)  

### Kör igång
1. Klona repot:
   ```bash
   git clone https://github.com/<StaySphere-Project>
   cd staysphere-backend
2. Starta projeket 
    ```bash
     (Hur man kör projektet)

### Principer & mönster
#### Vid refaktoreringen har vi tillämpat flera designprinciper och mönster för att uppnå bättre struktur:

- Single Responsibility Principle (SRP): Varje klass/service har ett tydligt och avgränsat ansvar.

- Template Method Pattern: För att kunna hantera olika typer av boenden (hotell, privata listings) på ett enhetligt men flexibelt sätt.

- Strategy Pattern: Möjliggör utbytbar logik, exempelvis för olika filter eller valideringsregler.

- Clean Architecture: Separering mellan affärslogik, dataåtkomst och API-lager för framtidssäkerhet.

  ### UML-diagram
  #### Vi har dokumenterat systemets struktur med UML för att visualisera design och refaktorering:

  - Klassdiagram: Översikt över systemets entiteter, deras relationer och ansvarsområden.
    
  - Sekvensdiagram: Visar flödet för centrala use cases som bokning och hantering av listings.
    
  - Use Case-diagram: Ger en övergripande bild av systemets aktörer och deras interaktioner.
    [Hoppa till UML-diagram](https://drive.google.com/file/d/1TB9EcJWsZpQ4blyUWf9_ynuVNCXWChM9/view?usp=sharing)
    (OBS Måste öppnas via Drawio)
