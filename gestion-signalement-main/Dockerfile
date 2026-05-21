# ─────────────────────────────────────────────
# Stage 1 : Build avec Maven
# ─────────────────────────────────────────────
FROM maven:3.8.4-openjdk-8 AS builder

WORKDIR /app

# Copier pom.xml en premier pour profiter du cache des dépendances
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copier le code source et compiler
COPY src ./src
RUN mvn package -DskipTests -B

# ─────────────────────────────────────────────
# Stage 2 : Image runtime légère
# ─────────────────────────────────────────────
FROM eclipse-temurin:8-jre

WORKDIR /app

# Créer un utilisateur non-root (bonne pratique sécurité)
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

# Copier uniquement le JAR depuis le stage builder
COPY --from=builder /app/target/gestion-signalement.jar app.jar

RUN chown appuser:appgroup app.jar

USER appuser

# Port Spring Boot
EXPOSE 8080

# Variables d'environnement par défaut
ENV SPRING_PROFILES_ACTIVE=production
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Health check Docker natif
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/.urandom","-jar","/app.jar"]