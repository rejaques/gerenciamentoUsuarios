FROM maven:3.8-openjdk-17 AS builder
LABEL authors="renan"

WORKDIR /app
#build pra rodar local
COPY . .

RUN mvn clean install -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "/app/target/gerenciamento.usuarios.war"]
