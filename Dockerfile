FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /run
COPY --from=build /app/target/*jar /run/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/run/app.jar"]