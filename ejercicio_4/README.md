# Ejercicio 4: creación de imagen PasswordAPI

## Autor

* Ezequiel L. Aceto

## Publicación de la imagen

### Compilación

```sh
docker build -t eaceto/passwordapp:4.0.1 . --build-arg JAR_FILE=./passwordapi.jar --build-arg JAR_SHA1=da5e6a74ce69074a2075575a2a4cb4c5709cb74a
```
### Publicación

```sh
docker push eaceto/passwordapp:4.0.1
```

### Tagear la imagen como 'latest'

```sh
docker tag eaceto/passwordapp:4.0.1 eaceto/passwordapp:latest
```

### Pushear los tags, incluido latest

```sh
docker push -a eaceto/passwordapp
```

## Ejecución de la imagen

### Descargar el tag latest

```sh
docker push eaceto/passwordapp
```

### Correr el contenedor

```sh
docker run --rm -d -p 8080:8080 eaceto/passwordapp
```

## Links

* [Imagen en Docker Hub](https://hub.docker.com/repository/docker/eaceto/passwordapp)
* [Dockerfile](https://github.com/eaceto/taller-docker/blob/main/ejercicio_4/Dockerfile)
