FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copier les fichiers avec un encodage correct
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code source et compiler avec l'encodage UTF-8
COPY src ./src
RUN mvn clean package -DskipTests -Dfile.encoding=UTF-8

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]