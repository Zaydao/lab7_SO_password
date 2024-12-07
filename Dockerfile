# Folosim imaginea de bază OpenJDK
FROM openjdk:17-jdk-slim AS builder

# Instalăm Maven
RUN apt-get update && apt-get install -y maven git

# Setăm directorul de lucru în container
WORKDIR /app

# Copiem fișierele POM pentru a gestiona dependințele
COPY pom.xml .

# Descărcăm dependințele aplicației fără a construi aplicația
RUN mvn dependency:go-offline

# Copiem codul sursă al aplicației
COPY src /app/src

# Construim aplicația
RUN mvn clean package

# Folosim o imagine de bază pentru rularea aplicației
FROM openjdk:17-jdk-slim

# Setăm directorul de lucru în container
WORKDIR /app

# Copiem aplicația construită din imaginea anterioară
COPY --from=builder /app/target/*.jar /app/app.jar

# Expunem portul pe care rulează aplicația
EXPOSE 8080

# Rulăm aplicația
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
