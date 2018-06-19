# Java w zastosowaniach produkcyjnych

## Zadanie 8 - Generator transakcji - DI

(do commitu ecf192fdaca6b0bc3f3795a2e4e30b1bfcc153da - Changed way of supplying TransactionWriter.)

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

## Zadanie 10 - Generator transakcji w Dockerze

(od commitu 284a4702ba21646f521b3be77e32eee610dc18e9 - Dockerized it.)

Utworzony obraz po uruchomieniu powinien odczytać parametry z pliku: /storage/generator.properties (przykład w załączniku), i wygenerować pliki z transakcjami do katalogu: /storage, po czym zakończyć swoje działanie.

docker build --tag generator-transakcji .

## Zadanie 12 - Broker transakcji

(najnowsze commity od e41c80cab76ad975315b1acb0308e2769a08158d - Queued it.)

Dodatkowe parametry do linii komend:

- broker tcp://localhost:616161

- queue nazwa-kolejki

- topic nazwa-tematu

Dodatkowe uwagi: 

- jeśli jest podany 'broker', to należy podać 'queue', albo 'topic', albo - jednocześnie 'queue' i 'topic'.

- jeśli jest podany 'broker', to 'outDir' jest opcjonalny; może, ale nie musi zostać podany.

Przykładowy obraz dockera z ActiveMQ: webcenter/activemq 
Komenda do uruchomienia obrazu dokerowego: 

- docker run --name='activemq' -it --rm -e 'ACTIVEMQ_CONFIG_MINMEMORY=512' -e 'ACTIVEMQ_CONFIG_MAXMEMORY=2048' -P webcenter/activemq:latest

Po uruchomieniu maszyn zmienną broker należy ustawić w pliku /storage/generator.properties kontenera.

Sam projekt uruchomić można za pomocą docker-compose.
