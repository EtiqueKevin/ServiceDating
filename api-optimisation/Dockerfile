# Utiliser une image de base avec OpenJDK 23
FROM openjdk:23-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR de l'application dans le conteneur
COPY target/api-otpimisation-*.jar app.jar

# Exposer le port de l'application
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
