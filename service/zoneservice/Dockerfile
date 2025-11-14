# Build stage
FROM gradle:8-jdk21-alpine AS build
WORKDIR /app
COPY settings.gradle build.gradle ./
COPY gradle ./gradle
COPY service/zoneservice/build.gradle ./service/zoneservice/
COPY service/zoneservice/src ./service/zoneservice/src
RUN gradle :service:zoneservice:bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
RUN apk add --no-cache curl
COPY --from=build /app/service/zoneservice/build/libs/*.jar zone-service.jar
ENTRYPOINT ["java", "-jar", "/zone-service.jar"]
