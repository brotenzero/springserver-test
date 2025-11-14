# Build stage
FROM gradle:8-jdk21-alpine AS build
WORKDIR /app
COPY settings.gradle build.gradle ./
COPY gradle ./gradle
COPY service/common/build.gradle ./service/common/
COPY service/common/src ./service/common/src
RUN gradle :service:common:bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
RUN apk add --no-cache curl
COPY --from=build /app/service/common/build/libs/*.jar common-service.jar
ENTRYPOINT ["java", "-jar", "/common-service.jar"]
