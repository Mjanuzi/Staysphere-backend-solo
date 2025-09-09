## Funktionella krav ##
### Listing skapande och bokningshantering

FK-001: Systemet ska tillåta värdar att skapa, redigera och ta bort boendelistingar.

FK-001.1 Systemet ska tillåta admin att skapa, redigera och ta bort hotell listings.

FK-002: Systemet ska stödja uppladdning av bilder och beskrivningar för varje boende.

FK-002.1: Systemet ska stödja att admin ska kunna ladda upp bilder och beskrivningar av hotell.

FK-003: Gäster ska kunna skicka en bokningsförfrågan till en tillgänglig listing.

FK-003.1 Gäster ska kunna boka hotell för eget val av datum.

FK-004: Värdar ska kunna acceptera eller neka inkommande bokningsförfrågningar.

FK-004.1 Gäst ska kunna avbryta en bokningsförfrågan.

FK-005: Systemet ska skapa en bokningsbekräftelse till både gäst och värd.

### Tillgänglighet och hantering av datum

FK-006: Värdar ska kunna lägga till tillgängliga datum för boende.

FK-006.1: Värdar ska kunna ta bort redan tillgängliga datum.

FK-007: Systemet ska automatiskt förhindra dubbelbokningar.

FK-008: Gäster ska kunna söka efter boenden baserat på specifika datum.

FK-009: Bokningssystemet ska beräkna totalpriset baserat på valda datum och eventuella avgifter.

### Användarhantering

FK-010: Användare ska kunna registrera konto som gäst.

FK-010.1: Användare ska kunna logga in.

FK-010.2: Gäst ska kunna ansöka om att bli värd.

FK-011: Användare ska kunna logga in och logga ut.

FK-012: Värdar ska kunna se en lista över sina publicerade listings och bokningar.

FK-013: Gäster ska kunna se sina tidigare och kommande bokningar.

### Sökfunktioner och filtrering
FK-014: Användare ska kunna filtrera mellan hotell och boende.

FK-015: Gäst ska kunna filtrera mellan pris, plats, bekvämligheter, antal reviews

### Recensioner och betyg

FK-017: Gäster ska kunna lämna recensioner och betyg på värdar/boenden.

### Icke-funktionella krav

IK-001: Systemet ska ha en responstid på under 3 sekunder vid sökning efter boenden.

IK-002: Användardata ska lagras säkert enligt GDPR.

IK-003: Applikationen ska kunna hantera minst 200 samtidiga användare utan prestandaförsämring.

IK-004: Språkstöd för svenska och engelska ska finnas vid lansering.
