# Multi-stage Dockerfile for Spring Boot application with local Gradle distribution support
# Stage 1: Dependency Caching and Build
FROM openjdk:21-slim AS builder
# Install necessary tools
RUN apt-get update && apt-get install -y unzip wget
# Set working directory
WORKDIR /app
# Create Gradle cache directory
RUN mkdir -p /gradle-cache
ENV GRADLE_USER_HOME=/gradle-cache
# Copy all necessary files
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src
# Attempt to copy local Gradle distribution, download if not present
RUN if [ ! -f gradle/wrapper/gradle-8.11-bin.zip ]; then \
        wget https://services.gradle.org/distributions/gradle-8.11-bin.zip -O gradle/wrapper/gradle-8.11-bin.zip; \
    fi
# Copy Gradle distribution to cache directory
RUN cp gradle/wrapper/gradle-8.11-bin.zip /gradle-cache/
# Unzip Gradle distribution
RUN unzip /gradle-cache/gradle-8.11-bin.zip -d /gradle-cache
# Make Gradle wrapper executable
RUN chmod +x ./gradlew
# Verify Gradle distribution
RUN ls -l /gradle-cache
# Update Gradle wrapper properties to use local distribution
RUN sed -i "s|distributionUrl=.*$|distributionUrl=file:///gradle-cache/gradle-8.11-bin.zip|" gradle/wrapper/gradle-wrapper.properties
# Set Gradle options
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false -Dorg.gradle.offline.mode=false"
# Build the application
RUN ./gradlew clean bootJar --no-daemon
# Stage 2: Create lightweight runtime image
FROM openjdk:21-slim
# Set working directory
WORKDIR /app
# Copy the built jar from the builder stage
COPY --from=builder /app/build/libs/*.jar /app/movie-ticketing-system-backend.jar
# Expose the application port (adjust as needed)
EXPOSE 8080
# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/movie-ticketing-system-backend.jar"]
