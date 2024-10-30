FROM ubuntu:latest
LABEL authors="mestiri"

ENTRYPOINT ["top", "-b"]
# Utiliser une image Maven comme image de base pour construire l'application
FROM maven:3.8.4-openjdk-11 AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier POM et les sources dans le conteneur
COPY pom.xml .
COPY src ./src

# Construire l'application
RUN mvn clean package -DskipTests

# Utiliser une image JDK pour exécuter l'application
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Définir la commande de démarrage
CMD ["java", "-jar", "app.jar"]
