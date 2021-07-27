#/bin/sh
POD_NAME=petq-apiserver
NAME=$(kubectl get pod | grep -E $POD_NAME | awk '{print $1}')

kubectl logs -f $NAME
