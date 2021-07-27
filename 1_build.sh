#/bin/sh
ID=codejune
REPOSITORY=petq-apiserver
VERSION=v0.1

docker build -t $ID/$REPOSITORY:$VERSION .
docker push $ID/$REPOSITORY:$VERSION
