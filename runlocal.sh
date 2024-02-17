#!/bin/bash
# Script para execução em ambiente LOCAL
# Parametros possiveis:
# -p : local, dev ou docker (ex: sh runlocal.sh -p docker). default = docker
# -b : true ou false (ex: sh runlocal.sh -b false). default = true
# -t : true ou false (ex: sh runlocal.sh -t false). default = true
# O parâmetro -p se refere ao Spring Profile que deseja subir a aplicação
# O parâmetro -b indica se é necessário compilar a aplicação ou não

CONTAINER_NAME="core-accounts-web-api"
CONTAINER_WIREMOCK="core-accounts-wiremock"

profile_list=(local docker dev)

while getopts p:b:t: flag
do
    case "${flag}" in
        p) profile=${OPTARG};;
        b) build=${OPTARG};;
        t) test=${OPTARG};;
    esac
done

if [[ -z "$profile" || ! ${profile_list[*]} =~ "$profile" ]]
then
    profile=docker
fi

if [[ -z "$build" || "$build" != "false" ]]
then
    build=true
fi

echo "Profile $profile"
echo "Build $build"

echo "---- Iniciando compilação do projeto ---- "
if [ "$build" == "true" ]
then
    if [ "$test" == "false" ]
    then
        echo "--- Skipping test ---"
        mvn clean install -Dmaven.test.skip=true
    else
        mvn clean install
    fi
fi

result=$?
if [ $result -ne 0 ] ; then
  echo Could not perform mvn clean install, exit code [$result]; exit $result
fi

echo "---- Parando os containers docker ----"
docker stop $CONTAINER_NAME
docker stop $CONTAINER_WIREMOCK


echo "---- Deletando os containers docker ----"
docker rm $CONTAINER_NAME
docker rm $CONTAINER_WIREMOCK


services_to_up=""
if [[ "$profile" != "docker" ]]
then
    services_to_up=web-api
fi


echo "---- Criando os containers docker ----"
docker-compose build --build-arg SPRING_PROFILE=$profile
docker-compose up $services_to_up