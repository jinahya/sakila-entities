#!/bin/sh
docker run -p 33306:3306 -it --rm --name sakila-db sakila-db
