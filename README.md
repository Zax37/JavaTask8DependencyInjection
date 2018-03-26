# Java w zastosowaniach produkcyjnych

## Zadanie 8 - Generator transakcji - DI

"Generator transakcji" z poprzednich zajęć należy podzielić na komponenty (beany) / moduły w sensie DI.

Mają istnieć przynajmniej 3 moduły:

- generujący zawartość na podstawie danych,

- czytający dane wejściowe,

- zapisujący wygenerowane dane.

Moduł zapisujący wygenerowane dane powinien mieć 3 implementacje:

- JSON (jak w zadaniu z wykładu 2),

- XML,

- yaml.

Dodatkowy parametr do wyboru formatu: -format=[xml|json|yaml], domyślnie: json

Przykładowe wywołanie:

- java -jar transaction-generator.jar -customerIds 1:20 -dateRange "2018-03-08T00:00:00.000-0100":"2018-03-08T23:59:59.999-0100" -itemsFile items.csv -itemsCount 5:15 -itemsQuantity 1:30 -eventsCount 1000 <br> -outDir ./output -format xml

Parametry:

- customerIds: zakres, z jakiego będą generowane wartości do pola "customer_id". Domyślny 1:20

- dateRange: zakres dat, z jakiego będzie generowane pole "timestamp". Domyślny dzień uruchomienia, cała doba

- itemsFile: nazwa pliku csv zawierającego spis produktów. Przykładowy plik (items.csv) dołączony do zadania.

- itemsCount: zakres ilości elementów generowanych w tablicy "items". Domyślny 1:5

- itemsQuantity: zakres z jakiego będzie generowana ilość kupionych produktów danego typu (pole "quantity"). Domyślnie 1:5

- eventsCount: ilość transakcji (pojedynczych plików) do wygenerowania. Domyślnie 100.

- outDir: katalog, do którego mają być zapisane pliki. Domyślnie aktualny katalog roboczy.

- format: format generowanych plików. Do wyboru xml, json i yaml.
