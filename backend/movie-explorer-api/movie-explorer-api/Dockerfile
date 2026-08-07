# Stage 1: Build using Maven
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy pom.xml and src
# Paths are relative to Render's build context (Root Directory = backend/)
COPY movie-explorer-api/movie-explorer-api/pom.xml .
COPY movie-explorer-api/movie-explorer-api/src ./src

# Build the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Create runtime image (JRE for a leaner image)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]