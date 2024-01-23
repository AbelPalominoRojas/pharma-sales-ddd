FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/pharma-sales-1.0.0-SNAPSHOT.jar pharma-sales.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pharma-sales.jar"]