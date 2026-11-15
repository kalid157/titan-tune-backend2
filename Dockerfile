# ==========================================
# ÉTAPE 1 : Construction (Build Stage)
# ==========================================
# On utilise une image officielle Maven avec Java 21 (Temurin)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# 1. On copie uniquement le pom.xml pour télécharger les dépendances.
# Cela permet à Docker de mettre en cache cette étape. Si vous ne changez pas le pom.xml,
# le build suivant sera beaucoup plus rapide.
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. On copie le reste du code source (dossier src)
COPY src ./src

# 3. On compile le projet et on crée le fichier .jar (en ignorant les tests pour aller plus vite)
RUN mvn clean package -DskipTests

# ==========================================
# ÉTAPE 2 : Exécution (Runtime Stage)
# ==========================================
# On utilise une image JRE (Java Runtime Environment) Alpine, très légère (moins de 200 Mo)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# On crée un utilisateur non-root pour des raisons de sécurité
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# On copie UNIQUEMENT le fichier .jar généré à l'étape 1
COPY --from=build /app/target/*.jar app.jar

# On expose le port (Render utilise la variable d'environnement PORT)
EXPOSE 8080

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-XX:TieredStopAtLevel=1", "-Dspring.main.lazy-initialization=true", "-jar", "app.jar"]