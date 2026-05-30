# ! ETAPA 1: BUILD (LA CONSTRUCCION DEL .JAR DE JAVA)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ! ETAPA 2: RUNTIME (PRODUCTO FINAL)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
