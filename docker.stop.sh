#!/bin/sh
exists=$(docker ps --filter "name=^/sakila-db$" --format '{{.Names}}')
if [[ "$exists" = sakila-db ]];
then
    echo sakila-db exists.
    running=$(docker inspect -f '{{.State.Running}}' sakila-db)
    if [[ "$running" = "true" ]];
    then
        echo sakila-db is currently running. stopping...
    else
        echo sakila-db is not currently running. exiting...
        exit 1
    fi
else
    echo no name for sakila-db. exiting...
    exit 0;
fi
docker stop sakila-db
