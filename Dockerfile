# Use Maven to build the jar first
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Now use JDK to run the app
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
