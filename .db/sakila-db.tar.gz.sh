#!/bin/sh
tar -xzvf ./sakila-db.tar.gz --strip-components 1 sakila-db/sakila-schema.sql sakila-db/sakila-data.sql
