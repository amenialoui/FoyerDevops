# Utilisez l'image OpenJDK 17 comme image de base
FROM openjdk:17

# Exposez le port sur lequel votre application Java écoute
EXPOSE 8089

# Définissez le répertoire de travail dans le conteneur
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# Commande d'entrée pour exécuter l'application Java
CMD ["java", "-jar", "app.jar"]