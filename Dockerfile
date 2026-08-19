# ---------- Estágio 1: build ----------
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build

# Copia só o pom primeiro — camada separada para cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Agora o código-fonte
COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------- Estágio 2: runtime ----------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Usuário sem privilégio — container não roda como root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]