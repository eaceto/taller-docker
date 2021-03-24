# Ejercicio 6: Creación de cluster balanceado


## Autor

* Ezequiel L. Aceto

## Docker Compose

1. Levantar los containers con 

```bash
docker-compose up
```

Utilizar la opción **-d** para correrlo como daemon.

2. Abrir un navegador e ingresar a la URL

```
http://localhost:8080/
```

## Containers

El archivo docker-compose define 3 servicios

* balancer
* app1
* app2

### Balancer

El container **balancer** utiliza como imagen base un NGINX y obtiene la configuración del archivo **nginx/nginx.conf** (que se detalla a continuación). En el mismo se define un upstream con dos servidores (app1 y app2) y un proxy reverso desde el puerto entrante (80) al upstream *balancer*. 

Como no estamos definiendo un peso especifico para cada servidor en el upstream, el balanceo por defecto es equitativo.

nginx.conf
```
events { 
    worker_connections 1024; 
}

http {
    upstream balancer {
        server app1:3000;
        server app2:3000;
    }

    proxy_set_header   Host $host;
    proxy_set_header   X-Real-IP $remote_addr;
    proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header   X-Forwarded-Host $server_name;

    server {
        listen       80;       
        server_name  localhost;

        location / {
            proxy_pass http://balancer;
        }
    }
}
```


### App1 y App2

Los containers App1 y App2 son dos instancias de la imagen **nicopaez/password-api** las cuales exponen en el puerto 3000 una API de password.

Tambien cuentan con un health check en */health*.


## Health Check

El endpoint de health check se encuentra en:

```
http://localhost:8080/health
```

En el docker-compose se define para todos los servicios una politica de health check utilizando este endpoint.

```yml
    healthcheck:
      test: ["CMD-SHELL", "wget -O /dev/null http://localhost:3000/health || exit 1"]
      interval: 10s
      timeout: 10s
      start_period: 10s
      retries: 5
```
