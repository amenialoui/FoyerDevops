# Use the OpenJDK 17 image as the base image
FROM openjdk:17
ENV SPRING_PROFILES_ACTIVE=dev

# Expose the port on which your Java application listens
EXPOSE 8089

# Set the working directory in the container
WORKDIR /app

# Argument for the JAR file


# Copy the JAR file into the container
COPY target/tp-foyer-5.0.0.jar /app/tp-foyer-5.0.0.jar

# Entry command to run the Java application
CMD ["java", "-jar", "tp-foyer-5.0.0.jar", "--spring.profiles.active=dev"]