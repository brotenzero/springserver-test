# Build stage
FROM gradle:8-jdk21-alpine AS build
WORKDIR /app
COPY settings.gradle build.gradle ./
COPY gradle ./gradle
COPY server/configserver/build.gradle ./server/configserver/
COPY server/configserver/src ./server/configserver/src
RUN gradle :server:configserver:bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
RUN apk add --no-cache curl
COPY --from=build /app/server/configserver/build/libs/*.jar config-server.jar
ENTRYPOINT ["java", "-jar", "/config-server.jar"]