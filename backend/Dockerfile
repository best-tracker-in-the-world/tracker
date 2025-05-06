FROM gradle:8.5-jdk21-alpine AS builder

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle gradle
RUN gradle dependencies

COPY . .

RUN gradle bootJar --no-daemon

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
