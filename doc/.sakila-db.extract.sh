#!/bin/sh
tar -xzvf sakila-db.tar.gz --strip-components 1 sakila-db/sakila-schema.sql sakila-db/sakila-data.sql
#mv -v ./sakila-schema.sql ./.copy/0.sakila-schema.sql
#mv -v ./sakila-data.sql ./.copy/1.sakila-data.sql
