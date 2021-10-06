#!/bin/bash

function buildItem() {
    cd $1
    ./lifecycle.sh build
    cd ..
}

function build() {
    buildItem consumer-billing
    buildItem consumer-notification
    buildItem producer
}

function deployItem() {
    cd $1
    ./lifecycle.sh deploy
    cd ..
}

function deployDapr() {
    kubectl create -f dapr-resources/dapr-pubsub-order-$1.yaml
    kubectl create -f dapr-resources/dapr-subscription-order-created.yaml
}

function deploy() {
    if [ ! -z "$1" ]; then
        deployDapr "$1"
    fi
    deployItem producer
    deployItem consumer-billing
    deployItem consumer-notification
}

function redeployItem() {
    cd $1
    ./lifecycle.sh redeploy
    cd ..
}

function redeploy() {
    redeployItem producer
    redeployItem consumer-billing
    redeployItem consumer-notification
}

function undeployItem() {
    cd $1
    ./lifecycle.sh undeploy
    cd ..
}

function undeploy() {
    undeployItem producer
    undeployItem consumer-billing
    undeployItem consumer-notification   
}

function clean() {
    undeploy
    kubectl delete component.dapr.io/order --ignore-not-found
    kubectl delete subscription.dapr.io/order-created-subscription --ignore-not-found
}

if [ "$1" == "deploy" ]; then
    deploy "$2"
elif [ "$1" == "redeploy" ]; then
    redeploy
elif [ "$1" == "build" ]; then
    build
elif [ "$1" == "undeploy" ]; then
    undeploy
elif [ "$1" == "clean" ]; then
    clean
else
    echo "No command found"
fi