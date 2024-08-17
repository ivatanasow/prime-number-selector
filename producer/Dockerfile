# Stage 1: Build the JAR file
FROM gradle:8.8.0-alpine AS build
RUN addgroup -S producerservicegroup && adduser -S producerserviceuser -G producerservicegroup
WORKDIR /app
RUN chown -R producerserviceuser:producerservicegroup /app
USER producerserviceuser
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build -x test --no-daemon

# Stage 2: Create the runtime image
FROM amazoncorretto:21-alpine3.20
RUN addgroup -S producerservicegroup && adduser -S producerserviceuser -G producerservicegroup
WORKDIR /app
RUN chown -R producerserviceuser:producerservicegroup /app
USER producerserviceuser
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
