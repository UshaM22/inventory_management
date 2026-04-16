# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the pom and src so Maven can actually build the project
COPY pom.xml .
COPY src ./src

# Now build the JAR (This creates the 'target' folder inside Docker)
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the JAR we just built in the 'build' stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]