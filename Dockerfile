FROM gradle:9.3.1-jdk17-corretto AS dependencies
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
RUN gradle dependencies --no-daemon

FROM dependencies AS builder
WORKDIR /app
COPY src src
RUN gradle bootJar --no-daemon

FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]