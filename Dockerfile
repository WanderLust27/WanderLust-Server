# Stage 1: Build with Maven
FROM maven:3.9-eclipse-temurin-17 as builder
WORKDIR /workspace/app

# Cache Maven dependencies first
COPY pom.xml .
RUN mvn dependency:go-offline

# Build application
COPY src src
RUN mvn clean package -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built JAR directly (simpler approach)
COPY --from=builder /workspace/app/target/wanderlust-app-0.0.1-SNAPSHOT.jar /app.jar

# JVM tuning
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+HeapDumpOnOutOfMemoryError -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

# Security
RUN addgroup --system spring && \
    adduser --system --ingroup spring spring && \
    chown -R spring:spring /app
USER spring:spring

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]