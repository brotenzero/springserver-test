# Build stage
FROM gradle:8-jdk21-alpine AS build
WORKDIR /app
COPY settings.gradle build.gradle ./
COPY gradle ./gradle
COPY service/soccerservice/build.gradle ./service/soccerservice/
COPY service/soccerservice/src ./service/soccerservice/src
RUN gradle :service:soccerservice:bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
RUN apk add --no-cache curl
COPY --from=build /app/service/soccerservice/build/libs/*.jar soccer-service.jar
ENTRYPOINT ["java", "-jar", "/soccer-service.jar"]
