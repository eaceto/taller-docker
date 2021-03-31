# Ejercicio 7: TelegramBot


## Autor

* Ezequiel L. Aceto


## Descriptores

### Namespace

1. Crear un namespace para esta aplicación

```bash
$ kubectl create namespace k8s-bot
namespace/k8s-bot created
```

### Secrets

1. Aplicar primero el descriptor de secrets. Previamente completar en ese archivo el valor **A_COMPLETAR** con la representación en Base64 del API Token del bot de Telegram

```bash
$ kubectl apply -f secrets.yml -n k8s-bot
secret/k8s-bot-telegram-token created
```

### Deployment

1. Aplicar el descriptor del deployment.

```bash
$ kubectl apply -f deployment.yml -n k8s-bot
deployment.apps/k8s-bot created
```

### Service

En este caso no es necesario exponer un **service** dado que el bot corre como standalone, y no expone un puerto de entrada.

## Verificación

Además de poder chatear con nuestro bot, otra cosa que podemos realizar es ejecutar un comando en el Pod. Por ejemplo, un **ps**

```bash
kubectl exec <pod id> -n <namespace> -- <command>
```

```bash
$ kubectl exec k8s-bot-586b68db6c-n5bsq -n k8s-bot -- ps
PID TTY          TIME CMD 
1   ?        00:00:00 ruby
32  ?        00:00:00 ps
```

Donde se puede ver que está corriendo el proceso **Ruby** del Bot.
