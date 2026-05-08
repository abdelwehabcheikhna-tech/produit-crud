FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copier Maven wrapper
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Rendre mvnw exécutable
RUN chmod +x mvnw

# Télécharger les dépendances
RUN ./mvnw dependency:go-offline

# Copier le code source
COPY src src

# Compiler
RUN ./mvnw clean package -DskipTests

# Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]