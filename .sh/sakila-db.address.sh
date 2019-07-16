#!/bin/sh
exists=$(docker ps --filter "name=^/${docker.container}$" --format '{{.Names}}')
if [[ "$exists" = ${docker.container} ]];
then
    echo ${docker.container} exists.
    address=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' ${docker.container})
    echo ${docker.container} is currently running at $address.
else
    echo no name for ${docker.container}. exiting...
    exit 0;
fi
