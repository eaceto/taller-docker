# Ejercicio 3: Jugando con contenedores

## Autor

Ezequiel L. Aceto (mail)[mailto:ezequiel.aceto@gmail.com]

## Pasos

1. Descargar imagenes

```sh
docker pull nicopaez/passwordapi-java:java8-alpine
docker pull nicopaez/passwordapi-java:java8-fabric
```

2. Inspeccionar las imagenes con *dive* y extraer los layers

### java8-alpine

```Docker image
FROM 3b2f672a71b0c0b069511fdcab2f5f340b87c6e863696ef38475cca0e2e842b9            

{   echo '#!/bin/sh';   echo 'set -e';   echo;   echo 'dirname "$(dirname "$(readlink -f "$(which javac || which java)")")"';  } > /usr/local/bin/docker-java-home  && chmod +x /usr/local/bin/docker-java-home

set -x  && apk add --no-cache   openjdk8-jre="$JAVA_ALPINE_VERSION"  && [ "$JAVA_HOME" = "$(docker-java-home)" ]

COPY file:a6e9b80e7469da50e389566bffb8669b6e40df0caeaf327465fb3d0162788101 in /app.jar
```
       
### java8-fabric

```Docker image
FROM 3b2f672a71b0c0b069511fdcab2f5f340b87c6e863696ef38475cca0e2e842b9   

apk add --update     curl     openjdk8=8.171.11-r0  && rm /var/cache/apk/*  && echo "securerandom.source=file:/dev/urandom" >> /usr/lib/jvm/default-jvm/jre/lib/security/java.security

ADD file:0e4d47c8ceb53292ec2698ca6f70c3f2954e747263a46f1b6b1fa70f71d9d9d4 in /opt/run-java-options

mkdir -p /opt/agent-bond  && curl http://central.maven.org/maven2/io/fabric8/agent-bond-agent/1.2.0/agent-bond-agent-1.2.0.jar -o /opt/agent-bond/agent-bond.jar && chmod 444 /opt/agent-bond/agent-bond.jar  && chmod 755 /opt/run-java-options

ADD file:19cf38fb1c34ab0ebb2252bb70ed3ea973b69d12e67d2ef36785bb6efa0eb9f4 in /opt/agent-bond/

COPY file:9664759840f7d23e79c24ae404fe981e8256b6c5145da410f07b11767f9366e3 in /deployments/

chmod 755 /deployments/run-java.sh

COPY file:a6e9b80e7469da50e389566bffb8669b6e40df0caeaf327465fb3d0162788101 in /deployments/app.jar
```           
           
                                                                          
3. Diferencias y similitudes

* Ambas parten de la misma imagen, eso lo vemos en la primer capa (from 3b2f672a71b0c0b).
* Ambas instalan OpenJDK 8, una obtiene la versión estable para alpile, la 2da tiene la versión fija (8.171.11-r0)
* Ambas instalan PasswordAPI
* La version de Alpine corre directamente un JAR con la aplicación, la versión con Fabric utiliza Agent Bond lo cual permite hacer profiling e instrumentación de una app Java
* La versión de Fabric tiene muchas mas capas (layers) que la de Alpine (8 contra 4).

