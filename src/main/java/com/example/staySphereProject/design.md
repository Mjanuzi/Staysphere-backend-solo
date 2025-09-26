# Design
###  Beskrivning

Vår huvudfokus har varit att göra en refactor på vår kod då vi anser att den befintliga koden i bookingService och ListingService inte var strukturerad, god-klasser och att den strider emot SRP vilket gör koden väldigt svårhanterad och svårläst.

### Vad ingår 
BookingService och ListingService är god klasser som behövdes ses över. Extrahering av logik var prioriterat för Single Responsibility. 

### Vad ingår inte
Reviews och Users var något vi inte la någon tid på men som vi ändå ser är viktiga att se över senare då vi anser att Reviews höjer användarupplevelsen.
För Users är tanken att vi ska ha en Admin panel för maintainance i helhet och skapande av hotell för hotellkedjor. 


### Utgångsläga - Före - Problem

1. Våra serviceklasser var godklasser som hanterade allt.
2. Ingen struktur i koden med varken designmönster eller principer. 
3. Våra methoder i Listingservice och BookingService hade dublicerade rader av kod där det fanns methoder som användes på samma sätt.
4. Vi hade överkomplex datumlogik för functionen Avaliable.
5. Vi hade även tight coupling med kod 
6. BookingService manipulerade hela listingAvalibiliting
7. Det var endast designat för att skapa ett typ av boende.
8. Vi bröt mot framtidssäkrad utveckling. Det var väldigt svårt att förstå sig på kod om man inte redan var insatt.


### Utgångsläga - Efter
1. Vi extraherade logik från vår BookingService och ListingService för att stödja SRP.
2. Implementation av designmönster och principer för enklare förståelse och utvecklade av framtid kod.
3. Skapande av en klass för att hantera exempelvis AddAvailablity som förr var duplicerad kod i båda klasserna. Nu återanvänds koden i båda services. 
4. Vi delade upp den komplexa datumhanteringen i mindre delar. 
5. Vi delade upp kod i fler klasser och implementerade abstrakt listing för att säkerställa så att framtid implemetering av andra boendetyper är lättare.
6. Eftersom designmöster och principer implementerades i projektet gjorde det lättare att förstå koden för nya utvecklare. 

## Designval (Principer/Mönster)
### Template Method Pattern
Eftersom vår grundidé var att implementera fler boendetyper så som hotell ville vi hitta ett sätt för att säkerställa att inte duplicera fält i de olika modellerna. Därav hjälper TMP oss att simplificera skapandet av en ny boendetyp med gemensamma fält för alla typer.
I den gamla ListingService var exempelvis createListing en väldigt stor metod med valideringar och objektskapande av objektet Listing.
Eftersom vi nu använde oss av TMP så gjorde det att skapande av ListingModellen blev abstrakt och objektskapandet av Listing omöjlig i createListing.
Istället för byta ut _Listing listing = new Listing ();_ till _Residence residence = new Residence ();_ i createListing ville vi framtidssäkra att andra boendetyper skulle också hanteras.
TMP hjälpte då oss att skapa en template för alla typer av objekt som då hanteras i deras respektive processor modulärt.

### Builder pattern

Då vår idé är att hosts ska ges möjligheten till att erbjuda kunder fler typer av services i samband med bokningar så som extra städavgifter pga att man tar med sig ett husdjur, cykeluthyrning mm 
hjälper Builder Pattern oss att enkelt implementera dessa funktioner. 

### Single Respinsibility Principle
Vi hade som tidigare nämnt God klasser som gjorde allt. Vi ville dela upp koden i ansvarsområden för att enkelt kunna felhantera kod vid eventuella fel. 
Detta gör även att det blir enklare att bygga ut koden med nya funktioner när man hittat rätt ansvarsområden. Nya utvecklare har lättare att sätta sig in i koden med hjälp av SRP.


### DRY 
Efter att ha gått igenom koden så ville vi ta bort duplicerad kod så som AddAvailablity(); för att istället använda gemensam kod mer modulärt. 

### Open/Closed Principle
Nu när vi använder oss av Template Method Pattern skapar vi en parent klass som inte förändras. Då använder vi oss av parent och child classer där av att parent blir oberörd och inte förendrars när vi vidar utvecklar listingmodellen,
då skapar vi en child klass som ökar våra alternativ till olika typer av boenden. Detta gör vi i både ListingMetoden och i ListingProcessorn. För ListingMethoden använder vi för att skapa olika boendetyper, för ListingProcessorn använder vi för valideringar och funktioner i de olika boendetyperna.

## Analys och konsekvenser


### Hur förändringar förbättrade koden:
- Lättläst kod  
- Mer strukturerat
- Bättre förutsättning för vidareutveckling
- Fördelat ansvarsområden noggrant


### Nackdelar:
- Vi har skapat fler klasser än vad vi hade tidigare. Detta kan anses vara en nackdel men vi behövde göra dessa förändringar för Single Responsibility. För nya utvecklare blir det fler klasser att gå in i och läsa på men i det långa loppen så tror vi att detta är en fördel för förståelse av kod. 
- Koden har blivit mer komplex om man tänker på klasserna som är skapade. Det är abstrakta modeller och processor som man måste ha överseende för och vara väldigt varsam med att inte ändra dessa då hela process vägen för exemepelvis en ny listing är baserat på de abstrakta klasserna.



