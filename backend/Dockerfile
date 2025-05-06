# ---------- ФАЗА СБОРКИ ----------
FROM gradle:8.5-jdk21-alpine AS builder

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем Gradle файлы и зависимости заранее (для кеширования)
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle gradle
RUN gradle dependencies

# Копируем оставшиеся исходники
COPY . .

# Собираем Spring Boot fat JAR (или WAR)
RUN gradle bootJar --no-daemon

# ---------- ФАЗА ЗАПУСКА ----------
FROM amazoncorretto:21-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем только финальный JAR из builder
COPY --from=builder /app/build/libs/*.jar app.jar

# Открываем порт (если нужно)
EXPOSE 8080

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
