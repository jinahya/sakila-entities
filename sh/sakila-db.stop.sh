#!/bin/sh
exists=$(docker ps --filter "name=^/${docker.container}$" --format '{{.Names}}')
if [[ "$exists" = ${docker.container} ]];
then
    echo ${docker.container} exists.
    running=$(docker inspect -f '{{.State.Running}}' ${docker.container})
    if [[ "$running" = "true" ]];
    then
        echo ${docker.container} is currently running. stopping...
    else
        echo ${docker.container} is not currently running. exiting...
        exit 1
    fi
else
    echo no name for ${docker.container}. exiting...
    exit 0;
fi
docker stop ${docker.container}
