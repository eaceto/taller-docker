# Ejercicio 7: Creación de DockerCompose propio

## Autor

* Ezequiel L. Aceto

## Docker Compose

1. Levantar los containers con 

```bash
docker-compose up --build
```

Utilizar la opción **-d** para correrlo como daemon.

2. Abrir un navegador e ingresar a la URL

```
http://localhost:8080/
```

## Containers

El archivo docker-compose define 2 servicios

* redis
* app

## Health Check

El endpoint de health check se encuentra en:

```
http://localhost:8080/actuator/health
```

En el docker-compose se define para todos los servicios una politica de health check utilizando este endpoint.

```yml
    healthcheck:
      test: ["CMD-SHELL", "wget -O /dev/null http://localhost:8080/actuator/health || exit 1"]
      interval: 10s
      timeout: 10s
      start_period: 10s
      retries: 5
```

## Service endpoints

### Health check
```http
GET http://localhost:8080/actuator/health
Accept: application/json
```

### List existent users
```http
GET http://localhost:8080/users/
Accept: application/json
```

### Create a user
```http
POST http://localhost:8080/users/
Content-Type: application/json

{
  "userName": "eaceto", "displayName": "Ezequiel L. Aceto", "password": "12345678"
}
```

### Login
```http
POST http://localhost:8080/users/login
Content-Type: application/json

{
  "userName": "eaceto", "password": "12345678"
}
```
