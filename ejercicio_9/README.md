# Ejercicio 9: Jobvacancy


## Autor

* Ezequiel L. Aceto

## Plataforma

El proyecto se desplegará en [Okteto](https://okteto.com) en el namespace **jobvacancy-eaceto**.

## Aplicación

La aplicación a ejecutar es [JobVacancy](https://hub.docker.com/r/nicopaez/jobvacancy-ruby). La versión a ejecutar será la 1.3.0 cuyas [capas de la imagen](https://hub.docker.com/layers/nicopaez/jobvacancy-ruby/1.3.0/images/sha256-d260c20eff996376132d21a2a9055b59f3eca36d2bec1c9b8ee761cc038c1514?context=explore) nos indican que:

1. Es una aplicación Ruby
    1. La versión de Ruby es 2.5.7
    2. La versión de RubyGems es 3.0.3
2. Expone un servicio en el puerto 3000 (TCP)

## Manifiestos (Descriptores)

## Despliegue

EL despliegue se realizará en el namespace **jobvacancy** en la plataforma [Okteto](http://okteto.com)


```bash
$ kubectl apply -f configs_development.yml -n jobvacancy-eaceto
configmap/jobvacancy created
```

```bash
$ kubectl apply -f secrets_development.yml -n jobvacancy-eaceto
secret/jobvacancysecret created
```

```bash
$ kubectl apply -f db_development.yml -n jobvacancy-eaceto
deployment.apps/jobvacancy created
```
