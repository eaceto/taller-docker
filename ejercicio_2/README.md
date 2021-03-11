# Ejercicio 2: Publicar una imagen

## Autor

* Ezequiel L. Aceto

## Pasos

1. Descargar la imagen nicopaez/pingapp:3.0.0

```sh
docker pull nicopaez/pingapp:3.0.0
```

2. Repositio en DockerHub

eaceto/pingapp

3. Login en DockerHub desde la terminal

```sh
docker login
```

4. Taggear la imagen nuevamente pero para el nuevo repositorio

```sh
docker tag nicopaez/pingapp:3.0.0 eaceto/pingapp:3.0.0
```

5. Publicar la imagen
```sh
docker push eaceto/pingapp:3.0.0
```

6. URL del repositorio con la imagen

https://hub.docker.com/repository/docker/eaceto/pingapp

