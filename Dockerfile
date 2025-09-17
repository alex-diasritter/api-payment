# Build stage
FROM maven:3.9.4-openjdk-17 AS build
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY src src

# Build application
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Create non-root user for security
RUN useradd -r -s /bin/false appuser
USER appuser

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]