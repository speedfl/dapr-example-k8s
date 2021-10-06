#!/bin/bash

service=dapr-consumer-notification

function getVersion() {
    cat pom.xml | grep "<version>" | head -1 | sed -En "s/(.*<version>)(.*)(<\/version>)/\2/p"
}

function watchDeploy() {
    watch -n 1 "kubectl get pods | grep ${service}"
}

function copyDocker() {
    echo "-- Importing Image"
    k3d image import speedfl/${service}:$(getVersion)
}

function helmPackage() {
    echo "-- Packaging with Helm"
    helm package helm -d helm
}

function getHelm() {
   ls helm/${service}*.tgz
}

function deploy() {
    copyDocker
    helmPackage
    echo "-- Installing and wait "
    helm install ${service} $(getHelm) --wait
}

function redeploy() {
    copyDocker
    helmPackage
    echo "-- Upgrading and wait "
    helm upgrade ${service} $(getHelm) --wait
}

function build() {
    mvn clean install -DskipTests
}

function undeploy() {
    helm uninstall ${service}
}

function getPod() {
    kubectl get pods | grep ${service} | cut -d " " -f 1
}

function logs() {
    kubectl logs $(getPod) -c ${service} --follow
}

function connect() {
    kubectl exec -it $(getPod) -c ${service} bash
}

function debug() {
    kubectl port-forward $(getPod) 5000
}

if [ "$1" == "deploy" ]; then
    deploy 
elif [ "$1" == "redeploy" ]; then
    redeploy
elif [ "$1" == "build" ]; then
    build
elif [ "$1" == "undeploy" ]; then
    undeploy
elif [ "$1" == "logs" ]; then
    logs
elif [ "$1" == "connect" ]; then
    connect
elif [ "$1" == "debug" ]; then
    debug
elif [ "$1" == "watch" ]; then
    watchDeploy
else
    echo "No command found"
fi