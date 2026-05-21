# Dockerfile simple — prend le JAR déjà compilé par Maven dans le pipeline
# Le JAR est copié dans le dossier drop par le stage Build

FROM eclipse-temurin:8-jre

WORKDIR /app

# Créer un utilisateur non-root
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

# Copier le JAR depuis le contexte de build (dossier drop)
COPY gestion-signalement.jar app.jar

RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=production
ENV JAVA_OPTS="-Xms256m -Xmx512m"

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/.urandom -jar app.jar"]