# Multistage Dockerfile: build with Maven, run on JRE 17

# Builder stage
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace
# copy only files needed for dependency resolution first (caching)
COPY pom.xml mvnw .
COPY .mvn .mvn
RUN mvn -q -B dependency:go-offline

# copy source and build
COPY src src
RUN mvn -q -B -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
# non-root user
RUN groupadd --system spring && useradd --system --gid spring spring
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY --from=builder /workspace/target/*.jar ./app.jar
RUN chown spring:spring /app/app.jar
USER spring
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
