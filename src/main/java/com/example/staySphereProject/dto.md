# DTO
Tänk på DTOs som specialdesignade transportbehållare för data.
När du skickar ett paket med posten använder en låda på 2x2 meter...
- du väljer en lämplig förpackning för just den specifika försändelsen.

- På samma sätt är DTOs skräddarsydda objekt för att transportera precis
  den data som behövs mellan olika lager i din applikation.

## I vårt exempel har vi två DTOs:
1. RegisterRequest - som tar emot data från klienten när någon vill registrera sig
2. RegisterResponse - som skickar tillbaka precis den information klienten behöver veta om registreringen

När det kommer till MongoDB och referenser blir DTOs extra viktiga av flera anledningar:

### Hantering av cirkulära referenser
När du har modeller som refererar till varandra
(tänk en User som har Posts, och varje Post har en referens tillbaka till User)
kan du hamna i oändliga serialiseringsloops. Med DTOs kan du bryta dessa cirklar genom
att välja exakt vilken data som ska skickas.

## Prestandaoptimering
MongoDB-dokument kan bli väldigt stora med många inbäddade dokument och referenser.
Med DTOs kan du välja att bara hämta och skicka den data som faktiskt behövs för en specifik operation.

### Versionshantering och API-stabilitet
DTOs ger dig ett extra lager av abstraktion mellan din databasmodell och ditt API.
Om du behöver ändra hur data lagras i MongoDB (till exempel splitta ett fält i två)
kan du behålla samma DTO-struktur utåt så att dina API-klienter inte påverkas.

### Validering och säkerhet
I din RegisterRequest använder du @NotBlank-annotationen för validering.
Detta är ett bra exempel på hur DTOs kan innehålla valideringslogik som är specifik för
just det användningsfallet. Du vill kanske inte ha samma strikta validering på alla
ställen där ett användarnamn förekommer.

# Fördelarna med DTO är:
- Vi undviker att skicka onödig information om författaren (som telefonnummer och adress) när vi skapar en ny bok
- Vi undviker cirkulära referenser genom att bara använda författarens ID
- Vi kan validera inkommande data på ett tydligt sätt
- Koden blir mycket mer lättförståelig och lättare att underhålla
- Bättre kontroll över vilken data som exponeras
- Mer effektiv dataöverföring
- Tydligare API-kontrakt
- Bättre säkerhet genom att känslig data aldrig av misstag läcker ut

Detta är särskilt viktigt i MongoDB-baserade system där datastrukturen ofta är mer flexibel och komplex än i traditionella relationsdatabaser.
