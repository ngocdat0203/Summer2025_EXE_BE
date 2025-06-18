# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
COPY .env .




RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jdk

WORKDIR /app
COPY --from=builder /app/target/LoveHavenStopSystem-0.0.1-SNAPSHOT.war app.war
COPY --from=builder /app/.env .

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.war"]
