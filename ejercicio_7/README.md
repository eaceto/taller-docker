# Ejercicio 7: PasswordAPI en Kubernetes


## Autor

* Ezequiel L. Aceto

## Descriptores

### Deployment

**deployment.yml** describe, entre otros parámetros:

1. A partir de que imagen se genera el Pod, y su nombre

```yml
    spec:
      containers:
      - name: password-api
        image: nicopaez/password-api
```

2. Los recursos máximos que puede consumir el Pod

```yml
        resources:
          limits:
            memory: "128Mi"
            cpu: "500m"
``` 

3. El puerto que hay que exponer en el Pod (Puerto de la aplicación)

```yml
        ports:
        - containerPort: 3000
```

4. Un request HTTP para hacer una prueba de vida del Pod

```yml
        livenessProbe:
          httpGet:
            path: /health
            port: 3000
          initialDelaySeconds: 5
          periodSeconds: 10
```

### Service

## Ejecución

1. Crear un **namespace** para alojar los pods y los servicios

```bash
$ kubectl create namespace password-api-namespace
namespace/password-api-namespace created
```

2. Aplicamos la configuración de deployment

```bash
$ kubectl apply -f deployment.yml -n password-api-namespace
deployment.apps/password-api created
```

3. Aplicacamos la configuración del servicio

```bash
$ kubectl apply -f service.yml -n password-api-namespace
service/password-api created
```

4. Verificamos que el servicio esté corriendo

```bash
$ kubectl get pods -n password-api-namespace
NAME                            READY   STATUS              RESTARTS   AGE
password-api-6c6fc67fd6-w5ltg   0/1     ContainerCreating   0          2m

$ kubectl get services -n password-api-namespace
NAME           TYPE       CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
password-api   NodePort   10.96.236.216   <none>        3000:31236/TCP   112s
```

5. Obtenemos la IP de nuestro cluster de minikube

```bash
$ minikube ip
192.168.49.2
```

6. Hacemos un cURL al health check para verificar que la app funciona


```bash
$ curl http://192.168.49.2:31236/health
{"host":"password-api-6c6fc67fd6-w5ltg","loadavg":[0.41552734375,0.47216796875,0.4521484375],"freemem":6157660160,"appversion":"1.0.0"}
```

Donde podemos verificar que el **host** corresponde con el único Pod en ejecución que vimos en el paso 4.


## Replicas

Cómo el ejercicio pedía tener 3 replicas (3 Pods) lo que vamos a hacer es agregar en el **deployment.yml** 

```yml
    replicas: 3
```

y volver a aplicar el **deployment.yml** en nuestro cluster. A lo que ahora el resultado de esta operación será:

```bash
$ kubectl apply -f deployment.yml -n password-api-namespace
deployment.apps/password-api configured

$ kubectl get pods -n password-api-namespace
NAME                            READY   STATUS    RESTARTS   AGE
password-api-6c6fc67fd6-2vnwc   1/1     Running   0          14s
password-api-6c6fc67fd6-w5ltg   1/1     Running   0          7m17s
password-api-6c6fc67fd6-z6trm   1/1     Running   0          14s
```

Donde se puede observar que tenemos 3 Pods, donde uno de ellos es más viejo, y recientemente se han agregado 2 más.

### Pruebas

Distintas llamadas al health check nos permitirán comprobar que ante cada ejecución el **servicio** está balanceando entre los distintos **pods**.

```bash
$ curl http://192.168.49.2:31236/health
{"host":"password-api-6c6fc67fd6-z6trm","loadavg":[0.54638671875,0.52001953125,0.47021484375],"freemem":6102089728,"appversion":"1.0.0"}⏎                                    kimi@Dell-XPS ~/W/

$ curl http://192.168.49.2:31236/health
{"host":"password-api-6c6fc67fd6-w5ltg","loadavg":[0.54638671875,0.52001953125,0.47021484375],"freemem":6095114240,"appversion":"1.0.0"}⏎                                    kimi@Dell-XPS ~/W/

$ curl http://192.168.49.2:31236/health
{"host":"password-api-6c6fc67fd6-2vnwc","loadavg":[0.50244140625,0.51123046875,0.46728515625],"freemem":6095380480,"appversion":"1.0.0"}⏎                                    kimi@Dell-XPS ~/W/

$ curl http://192.168.49.2:31236/health
{"host":"password-api-6c6fc67fd6-w5ltg","loadavg":[0.50244140625,0.51123046875,0.46728515625],"freemem":6098735104,"appversion":"1.0.0"}⏎                                    kimi@Dell-XPS ~/W/

$ curl http://192.168.49.2:31236/health
{"host":"password-api-6c6fc67fd6-w5ltg","loadavg":[0.50244140625,0.51123046875,0.46728515625],"freemem":6099267584,"appversion":"1.0.0"}⏎                                    kimi@Dell-XPS ~/W/

$ curl http://192.168.49.2:31236/health
{"host":"password-api-6c6fc67fd6-z6trm","loadavg":[0.50244140625,0.51123046875,0.46728515625],"freemem":6092558336,"appversion":"1.0.0"}⏎                                    kimi@Dell-XPS ~/W/

$ curl http://192.168.49.2:31236/health
{"host":"password-api-6c6fc67fd6-2vnwc","loadavg":[0.50244140625,0.51123046875,0.46728515625],"freemem":6095544320,"appversion":"1.0.0"}⏎                                    kimi@Dell-XPS ~/W/

$ curl http://192.168.49.2:31236/health
{"host":"password-api-6c6fc67fd6-2vnwc","loadavg":[0.50244140625,0.51123046875,0.46728515625],"freemem":6103896064,"appversion":"1.0.0"}⏎                                    kimi@Dell-XPS ~/W/

$ curl http://192.168.49.2:31236/health
{"host":"password-api-6c6fc67fd6-w5ltg","loadavg":[0.4619140625,0.50244140625,0.46435546875],"freemem":6095613952,"appversion":"1.0.0"}⏎                                     kimi@Dell-XPS ~/W/e/c/d/e/ejercicio_7 (main)> 
```
