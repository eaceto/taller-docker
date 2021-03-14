# Ejercicio 5: Explorando Dockerfile commands

## Autor

* Ezequiel L. Aceto

## Comandos

### HEALTHCHECK

Nos permite verificar la salud del proceso que corre dentro del contenedor, mediante la ejecución a intervalos regulares, de un comando que definimos nosotros.

```dockerfile
HEALTHCHECK --start-period=30s --interval=30s --timeout=3s --retries=5  CMD curl -f http://localhost:8080/ || exit 1
```

Las variables que toma esta sintaxis nos permiten determinar

* **start-period** Tiempo que espera antes del 1er test. (0s por defecto)
* **interval** Intervalo de tiempo entre prueba y prueba. (30s por defecto)
* **timeout** Tiempo de esperar hasta considerar fallida una prueba. (30s por defecto)
* **retries** Reintentos hasta considerar que el contenedor no está saludable. (3 por defecto)

Solo se puede tener **un** solo healthcheck en nuestro Dockerfile. Si tenes más de uno, solo el último será el que se utilice.

### ONBUILD

La instrucción **ONBUILD** nos permite poner puntos de ejecución en un imagen, que serán utilizados cuando alguien use nuestra imagen como base (**FROM** ...). Es una instrucción muy útil para generar *templates* de Dockerfile. 

Por ejemplo, supongamos que tenemos una imagen de Node.js que queremos desarrollar para aplicaciones React.js con Typescript. Nuestra imagen va a partir de alguna versión de Node.js, luego correr TypeScript para transpilar a JavaScript, y hacer incluse un minificado del código.

Ahora, ese Dockerfile lo vamos a querer utilizar en nuestras innumerables aplicaciones, y no podemor copiar y pegar su contenido cada vez que lo querramos usar. Pero el código de nuestras aplicaciones no lo tenemos disponible al momento de hacer **build** de este Dockerfile. 

Tenemos que poder poner una marca, donde operemos sobre la imagen, sabiendo que alguno de los argumentos estarán disponibles solo cuando alguien utilice nuestra imagen como base (en la imagen de la aplicación en concreto).

**ONBUILD** nos permite hacer todo esto. Poniendo instrucciónes que se ejecutarán cuando nuestra imagen sea utilizada como base de una nueva imagen. Por ejemplo:

```Dockerfile
ONBUILD RUN tsc -p tsconfig.js
```

Esto deja una marca para, que cuando usen nuestra imagen como base, se ejecute en ese punto un RUN con el comando *tsc -p tsconfig.js* donde *tsconfig.js* es un archivo que está disponible en la nueva imagen que estamos creando, pero no en la imagen base.

### VOLUME

La instrucción **VOLUME** nos permite especificar un volumen, como lo haríamos con el *flag -v* en tiempo de ejecución del contenedor, pero en tiempo de definición de la imagen.

Cuando especificamos un volumen a través de esta instrucción, el directorio es mappeado automáticamente a la carpeta */var/lib/docker/vfs* del Host.

Por ejemplo, si tenemos una imagen con MySQL y queremos en el Dockerfile indicar el volumen donde se almacena la configuración y la información, por ejemplo:

```dockerfile
VOLUME /var/lib/mysql
```  

Tenemos que tener en cuenta que el *flag -v* tiene precedencia sobre la instrucción **VOLUME**
## Referencias

* https://docs.docker.com/engine/reference/builder/