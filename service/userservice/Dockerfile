# Build stage
FROM gradle:8-jdk21-alpine AS build
WORKDIR /app
COPY settings.gradle build.gradle ./
COPY gradle ./gradle
COPY service/userservice/build.gradle ./service/userservice/
COPY service/userservice/src ./service/userservice/src
RUN gradle :service:userservice:bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
RUN apk add --no-cache curl
COPY --from=build /app/service/userservice/build/libs/*.jar user-service.jar
ENTRYPOINT ["java", "-jar", "/user-service.jar"]
