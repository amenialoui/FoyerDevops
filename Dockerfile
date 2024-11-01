# Étape de construction - Utiliser l'image Maven comme image de base
FROM maven:3.8.4-openjdk-11 AS build
# Étape d'exécution - Utiliser une image JDK pour exécuter l'application
FROM openjdk:11-jre-slim



# Exposer le port sur lequel l'application écoute
EXPOSE 8089

COPY target/FoyerDevops-5.0.0.jar FoyerDevops-5.0.0.jar

# Définir le point d'entrée de l'application
ENTRYPOINT ["java", "-jar", "FoyerDevops-5.0.0.jar"]
