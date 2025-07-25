# Proyecto Prueba Técnica - Monolito Modular con Microservicios y Frontend

Este proyecto está estructurado como un **monorepo** que integra varios microservicios de backend en SpringBoot y un frontend Angular para la gestión de usuarios y gift cards. Incluye toda la lógica para pruebas y despliegue local rápido, usando Docker Compose para la infraestructura principal.

## Respuesta a planteamiento de prueba técnica

para el almacenamiento de Archivo tipo S3, se me ocurre desacoplar el almacenamiento de archivos del resto del sistema, implementando un microservicio de almaceaniminto. Este microservicio podria encargarse de usar un proveedor en la nube para almacenamiento de objetos de cualquier tipo. 
Una alternativa seria implementar el servicio on-premise, usando librerias como MinIO. En este caso, cada vez que se recibe un archivo, el microservicio lo sube al storage y retorna la URL publica, los metdadatos pueden guardarse en una base de datos NoSQL como MongoDB, facilitando busquedas rapidas. 
Tambien se podria gaurdar directamente archivos binarios directamente en MongoDB usando base64.

Para el envío de correo electrónico, se puede dsacoplar el proceso usando una cola de mensajes como RabbitMQ, que ya se utiliza en este sistema. Cada vez que una tarjeta es usada/emititda, el microservicio publica un evento en cola, podria existir un microservicio notivicaciones suscrito a estos eventos, y es el que se encarga de enviar el correo electronico usando proveedor SMTP como SendGrid o Amazon SES. Esto permite que el sistema sea más escalable y desacoplado, ya que el microservicio de notificaciones puede ser modificado o reemplazado sin afectar al resto del sistema.

## Estructura del Repositorio

- `discovery-microservicio/` - Microservicio de descubrimiento (Eureka)
- `gateway-microservicio/` - API Gateway (Spring Cloud Gateway)
- `user-microservicio/` - Microservicio de usuarios (Spring Boot)
- `giftcard-microservicio/` - Microservicio de gift cards (Spring Boot)
- `frontend-prueba-tecnica/` - Aplicación frontend (Angular)
- `docker-compose.yml` - Orquestación de contenedores backend, base de datos y RabbitMQ
- `init-db/` - Scripts de inicialización de la base de datos
- `postman-collection/` - Colección de Postman para pruebas de APIs

## Arquitectura General

La solución se compone de los siguientes elementos:

| Componente                | Descripción                                                          |
|---------------------------|----------------------------------------------------------------------|
| **discovery-microservicio** | Service Discovery (Eureka), registro/detección de microservicios    |
| **gateway-microservicio**   | API Gateway, enruta y expone todos los endpoints del sistema        |
| **user-microservicio**      | CRUD de usuarios, integración con base de datos PostgreSQL          |
| **giftcard-microservicio**  | Gestión de gift cards, integración con PostgreSQL y RabbitMQ        |
| **frontend-prueba-tecnica** | Aplicación Angular para la gestión desde interfaz gráfica           |
| **PostgreSQL**              | Base de datos relacional para los microservicios                    |
| **RabbitMQ**                | Mensajería para comunicación entre microservicios                   |

> **Nota:** El frontend NO está incluido dentro del `docker-compose`. Se debe levantar manualmente.

---

## Requisitos Previos

- [Docker](https://www.docker.com/) y [Docker Compose](https://docs.docker.com/compose/) instalados
- Node.js y npm (para frontend)
- Java 17+ y Maven/Gradle (si deseas construir los microservicios localmente)

---

## Levantar el Backend y la Infraestructura

1. **Configura las variables de entorno en un archivo `.env`**  
   Crea el archivo `.env` en la raíz, por ejemplo:

    ```env
    POSTGRES_USER=postgres
    POSTGRES_PASSWORD=PruebaTecnicaExito1
    
    SPRING_R2DBC_URL=r2dbc:postgresql://localhost:5432/giftcard_db
    SPRING_R2DBC_USERNAME=postgres
    SPRING_R2DBC_PASSWORD=PruebaTecnicaExito1
    
    SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432
    SPRING_DATASOURCE_USERNAME=postgres
    SPRING_DATASOURCE_PASSWORD=PruebaTecnicaExito1
    
    MICROSERVICES_DISCOVERY=discovery-microservice:8790
    
    RABBITMQ_HOST=rabbitmq
    RABBITMQ_PORT=5672
    RABBITMQ_USERNAME=guest
    RABBITMQ_PASSWORD=guest
    
    SECRET=pY9Vsg4NqZz7wBxtK2dHr8Tf9kLmEq5J
    EXP_TIME=864000000
    
    JWT_SECRET=pY9Vsg4NqZz7wBxtK2dHr8Tf9kLmEq5J
    JWT_EXPIRED_TIME=864000000
    ```

2. **Construye y levanta los contenedores del backend:**  
   Desde la raíz del proyecto, ejecuta:

    ```sh
    docker-compose up --build -d
    ```

   Esto levantará:
    - PostgreSQL en `localhost:5432`
    - RabbitMQ en `localhost:15672` (UI)
    - Eureka en `localhost:8790`
    - Gateway en `localhost:8810`
    - User Service en `localhost:8811`
    - Gift Card Service en `localhost:8812`

3. **Verifica el estado de los servicios:**
    - **Eureka (Discovery):** http://localhost:8790
    - **RabbitMQ Admin:** http://localhost:15672 (user/pass: guest/guest)
    - **Gateway:** http://localhost:8810

---

## Levantar el Frontend

El frontend se desarrolla y ejecuta de forma independiente.

1. Entra en la carpeta:

    ```sh
    cd frontend-prueba-tecnica
    ```

2. Instala las dependencias:

    ```sh
    npm install
    ```

3. Ejecuta la app en modo desarrollo:

    ```sh
    npm start
    # o
    ng serve
    ```

   Por defecto, corre en http://localhost:4200

---

## Flujos de acceso al sistema
### 1. Inicio del sistema
   Al abrir el navegador en http://localhost:4200, el sistema te redirigirá automáticamente a la página de inicio de sesión.

### 2. Registro de usuario
   Si no tienes una cuenta, haz clic en Registrarse.
   En la página de registro deberás ingresar:

- Nombre de usuario

- Correo electrónico válido

- Contraseña

### 3. Verificación y correo de confirmación
   Al enviar el formulario de registro:

- El sistema validará que la información sea correcta y que el correo no esté registrado.

- Si todo es correcto, recibirás un correo electrónico con una contraseña aleatoria generada por el sistema.

- Esta contraseña es temporal y deberás utilizarla para tu primer inicio de sesión.

### 4. Inicio de sesión
   Después de recibir el correo, vuelve a la página de inicio de sesión.
   Ingresa tu nombre de usuario y la contraseña enviada por email para acceder al sistema.

#### _Notas importantes_:

- _Debes tener acceso al correo electrónico que registraste, ya que ahí recibirás la contraseña temporal._
- _Por razones de seguridad, se recomienda cambiar la contraseña después del primer ingreso._

## Colección de Postman

- Dentro de la carpeta `postman-collection/` encontrarás una colección para probar todos los endpoints de los microservicios.
- Importa la colección en Postman y usa los ejemplos para testear el backend.

---

## Resumen de Endpoints

| Servicio         | Puerto   | Ejemplo Endpoint                       |
|------------------|----------|----------------------------------------|
| Eureka           | 8790     | `http://localhost:8790`                |
| Gateway          | 8810     | `http://localhost:8810/api/usuarios`   |
| User Service     | 8811     | `http://localhost:8811/api/usuarios`   |
| Gift Card        | 8812     | `http://localhost:8812/api/giftcards`  |
| RabbitMQ Admin   | 15672    | `http://localhost:15672`               |
| Frontend         | 4200     | `http://localhost:4200`                |

---

## Notas

- **Base de datos:** PostgreSQL se inicializa con los scripts en `init-db/`.
- **RabbitMQ:** usado para comunicación asíncrona entre microservicios.
- **Frontend:** consumir los endpoints a través del Gateway.
- **Variables sensibles** van en `.env` (no se sube al repo).

---
