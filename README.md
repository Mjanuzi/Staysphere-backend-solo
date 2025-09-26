# Staysphere – Backend

##  Kort beskrivning
Staysphere är en plattform som kombinerar uthyrning och bokning av både privata boenden och hotell.  
I det här projektet har vi fokuserat på **refaktorering och optimering av befintlig backend-kod**, med målet att förbättra **läsbarhet, skalbarhet och framtidssäkerhet** snarare än att enbart bygga nya funktioner.  

---

##  Hur man bygger och kör projektet

### Tech stack
- Java 17+  
- Maven  
- MongoDB  

### Kör igång
1. Klona repot:
   ```bash
   git clone https://github.com/<StaySphere-Project>
2. Öppna Projektet i IntelliJ
3. Starta projeket 
    ```bash
     Run the 'StaySphereProjectApplication'(Main) file 

### Principer & mönster
#### Vid refaktoreringen har vi tillämpat flera designprinciper och mönster för att uppnå bättre struktur:

- **Single Responsibility Principle (SRP)**: Varje klass/service har ett tydligt och avgränsat ansvar.

- **Template Method Pattern**: För att kunna hantera olika typer av boenden (hotell, privata listings) på ett enhetligt men flexibelt sätt.

- **Builder Pattern**: Används för att möjliggöra att hosts kan lägga till extra tjänster i samband med bokningar, t.ex. städavgift för husdjur eller cykeluthyrning.  

- **Open/Closed Principle**: Används tillsammans med Template Method Pattern för att skapa en parent-klass som inte förändras.  
  Nya boendetyper och funktioner implementeras istället genom child-klasser, vilket gör systemet utbyggbart utan att ändra befintlig kod.  
  Detta gäller både i `ListingMethod` (olika boendetyper) och i `ListingProcessor` (valideringar och funktioner).  

### UML-diagram

#### Vi har dokumenterat systemets struktur med UML för att visualisera design och refaktorering:

  - Klassdiagram: Översikt över systemets entiteter, deras relationer och ansvarsområden.
    
  - Sekvensdiagram: Visar flödet för centrala use cases av bokning processen.
    
  - Use Case-diagram: Ger en övergripande bild av systemets aktörer och deras interaktioner.
    [Hoppa till UML-diagram](https://app.diagrams.net/#G1TB9EcJWsZpQ4blyUWf9_ynuVNCXWChM9#%7B%22pageId%22%3A%22fZKKRFcfot3JoDX8hNPE%22%7D)
    
 ---

##  Postman-samling

För att enklare kunna testa och förstå API:et har vi skapat en **Postman-samling** med färdiga requests.  
Samlingen innehåller exempel för autentisering, CRUD-operationer på listings samt bokningar.  

### Öppna 
- [Postman-dokumentation](https://documenter.getpostman.com/view/40894272/2sAYkHnHkB)  


