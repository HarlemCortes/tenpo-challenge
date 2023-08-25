# API REST Tenpo Challenge - Documentación

Bienvenido a la documentación de la API REST desarrollada en Spring Boot que ofrece funcionalidades de suma y aplicación de porcentaje a números, además de la gestión de historial de llamadas. A continuación, encontrarás información sobre cómo ejecutar el proyecto, las tecnologías utilizadas y cómo interactuar con la API.

## Tecnologías Utilizadas

- Java 17
- Spring Boot 3.1.2
- Spring Boot Data JPA
- Spring Boot Security
- Spring Boot Web
- Spring Boot AOP (Aspect-Oriented Programming)
- Spring Boot Cache
- Caffeine (Caching Library)
- Spring Cloud Circuit Breaker - Resilience4j
- PostgreSQL (Base de datos)
- Jackson Databind (Procesamiento de JSON)
- Lombok (Biblioteca de anotaciones)
- JWT (JSON Web Tokens)
- Springdoc OpenAPI - Swagger (Documentación de la API)
- Spring Boot Test
- Spring Security Test

# Instrucciones de Ejecución

A continuación, se detallan los pasos para ejecutar la API en tu entorno local. Asegúrate de tener **Docker** instalado en tu sistema antes de continuar.

## Pasos

1. **Clonar el Repositorio**:

   Clona el repositorio desde GitHub:

   ```bash
   git clone https://github.com/HarlemCortes/tenpo-challenge.git
   ```

2. **Ejecutar Docker Compose**:

   Desde la raíz del proyecto, donde se encuentra el archivo `docker-compose.yml`, ejecuta el siguiente comando para crear y ejecutar los contenedores de la base de datos PostgreSQL y la API:

   ```bash
   docker compose up -d
   ```

3. **Acceder a la Documentación**:

   Una vez que los contenedores estén en ejecución, podrás acceder a la documentación interactiva Swagger de la API en:

   ```
   http://localhost:8081/swagger-ui.html
   ```

## Pruebas Unitarias

El proyecto incluye pruebas unitarias para garantizar la calidad del código. Puedes ejecutar las pruebas con **Maven** ejecutando:

```bash
./mvn test
```

## Repositorio Público

El código fuente de la API se encuentra en un repositorio público en GitHub: [Enlace al Repositorio](https://github.com/HarlemCortes/tenpo-challenge)

## Descripción de los Servicios

A continuación, se proporciona una descripción de los servicios disponibles en la API:

### Sumar Números

**Descripción**: Suma dos números y aplica un porcentaje al resultado.

- **URL**: `POST /api/tenpo/sum`
- **Roles Requeridos**: Autenticado (cualquier rol)

### Historial de Llamadas

**Descripción**: Obtiene el historial de todas las llamadas realizadas.

- **URL**: `GET /api/tenpo/sum/history`
- **Roles Requeridos**: Autenticado (rol ADMIN)

### Registrar Usuario

**Descripción**: Registra un nuevo usuario en el sistema.

- **URL**: `POST /api/tenpo/auth/register`
- **Roles Requeridos**: Ninguno

### Refrescar Token

**Descripción**: Refresca el token de acceso.

- **URL**: `POST /api/tenpo/auth/refresh-token`
- **Roles Requeridos**: Autenticado (cualquier rol)

### Autenticar Usuario

**Descripción**: Autentica a un usuario y proporciona tokens de acceso y actualización.

- **URL**: `POST /api/tenpo/auth/authenticate`
- **Roles Requeridos**: Ninguno


### Notas Adicionales

Si deseas ejecutar la API en un contenedor Docker separado, puedes descargar la imagen de la API utilizando el siguiente comando:

```bash
docker pull harlem90/sum-service:latest
