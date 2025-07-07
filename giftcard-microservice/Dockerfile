# Uses an OpenJDK image for Java
FROM openjdk:17-alpine

# Sets the working directory
WORKDIR /app

# Copy the JAR file built in the container
COPY target/giftcard-microservice-0.0.1-SNAPSHOT.jar app.jar

# Copy the properties file to the working directory
COPY src/main/resources/application.properties application.properties

# Establece la zona horaria a Ecuador
RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/America/Guayaquil /etc/localtime \
    && echo "America/Guayaquil" > /etc/timezone \

# Expone el puerto en el que la aplicación se ejecutará
EXPOSE 8812

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
