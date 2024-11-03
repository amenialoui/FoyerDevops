FROM openjdk:17-alpine
RUN apk add --no-cache curl
EXPOSE 8082
RUN curl -o app.jar http://admin:nexus@192.168.50.4:8081/repository/maven-releases/tn/esprit/tpfoyer/1.0.0/foyer-1.0.0.jar
WORKDIR /
ENV SPRINGPROFILES=prod
ENTRYPOINT ["java", "-jar", "app.jar","-Dspring.profiles.active=${SPRINGPROFILES}","-jar", "-Dserver.port=8082", "-Dserver.address=0.0.0.0"]