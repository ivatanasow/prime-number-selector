# Stage 1: Build the JAR file
FROM gradle:8.8.0-alpine AS build
RUN addgroup -S consumerservicegroup && adduser -S consumerserviceuser -G consumerservicegroup
WORKDIR /app
RUN chown -R consumerserviceuser:consumerservicegroup /app
USER consumerserviceuser
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build -x test --no-daemon

# Stage 2: Create the runtime image
FROM amazoncorretto:21-alpine3.20
RUN addgroup -S consumerservicegroup && adduser -S consumerserviceuser -G consumerservicegroup
WORKDIR /app
RUN chown -R consumerserviceuser:consumerservicegroup /app
USER consumerserviceuser
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
