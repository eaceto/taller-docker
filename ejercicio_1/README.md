# Ejercicio 1: Contenedor NGINX

## Autor

* Ezequiel L. Aceto

## Pasos

1. Descargar imagen de NGINX estable

```sh
docker pull nginx:stable-alpine
```

2. Crear página con HTML básica, con mi nombre en el title y body

```sh
echo "<!DOCTYPE html><html><head><title>Ejercicio 1</title></head><body>Ezequiel L. Aceto</body></html>" > index.html
```

3. Armar una imagen a partir de la imagin estable de NGINX y agregarle un layer que copie el index.html

```sh
echo "FROM nginx:stable-alpine
COPY index.html /usr/share/nginx/html" > Dockerfile
```

4. Compilar la imagen

```sh
docker build -t nginx-ej1 .
```

5. Correr la imagen recientemente compilada

```sh
docker run --name nginx-test-1 -p8080:80 -d nginx-ej1 
```

6. Abrir el navegador en http://localhost:8080

