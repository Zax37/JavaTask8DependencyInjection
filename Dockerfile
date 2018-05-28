FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD build/libs/transaction-generator.jar app.jar
ADD storage/generator.properties /storage/generator.properties
ADD storage/items.csv /storage/items.csv
CMD java -Dserver.port=$PORT -jar app.jar