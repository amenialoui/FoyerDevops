FROM openjdk:17-alpine

# Install curl
RUN apk add --no-cache curl

# Set the working directory
WORKDIR /

# Expose the desired port
EXPOSE 8082

# Download the application JAR from Nexus
RUN curl -o app.jar http://admin:nexus@192.168.50.4:8081/repository/maven-releases/tn/esprit/tpfoyer/1.0.0/foyer-1.0.0.jar

# Set environment variables
ENV SPRING_PROFILES=prod

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar", "-Dspring.profiles.active=prod", "-Dserver.port=8082", "-Dserver.address=0.0.0.0"]
