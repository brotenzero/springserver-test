# Build stage
FROM gradle:8-jdk21-alpine AS build
WORKDIR /app
COPY settings.gradle build.gradle ./
COPY gradle ./gradle
COPY server/eurekaserver/build.gradle ./server/eurekaserver/
COPY server/eurekaserver/src ./server/eurekaserver/src
RUN gradle :server:eurekaserver:bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
RUN apk add --no-cache curl
COPY --from=build /app/server/eurekaserver/build/libs/*.jar eureka-server.jar
ENTRYPOINT ["java", "-jar", "/eureka-server.jar"]
