# Utiliser une image de base avec OpenJDK 23
FROM openjdk:23-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# install maven
RUN apt-get update && apt-get install -y maven

# Exposer le port de l'application
EXPOSE 46247
