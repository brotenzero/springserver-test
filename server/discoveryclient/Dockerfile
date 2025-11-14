# Build stage
FROM gradle:8-jdk21-alpine AS build
WORKDIR /app
COPY settings.gradle build.gradle ./
COPY gradle ./gradle
COPY server/discoveryclient/build.gradle ./server/discoveryclient/
COPY server/discoveryclient/src ./server/discoveryclient/src
RUN gradle :server:discoveryclient:bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
RUN apk add --no-cache curl
COPY --from=build /app/server/discoveryclient/build/libs/*.jar discovery-client.jar
ENTRYPOINT ["java", "-jar", "/discovery-client.jar"]
