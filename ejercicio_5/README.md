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

### VOLUME



## Referencias

* https://docs.docker.com/engine/reference/builder/