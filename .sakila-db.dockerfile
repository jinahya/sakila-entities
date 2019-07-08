FROM mysql/mysql-server:latest

ENV MYSQL_ROOT_PASSWORD root
#ENV MYSQL_DATABASE sakila
#ENV MYSQL_USER sakila
#ENV MYSQL_PASSWORD sakila

COPY ./doc/0.sakila-schema.sql /docker-entrypoint-initdb.d/
COPY ./doc/1.sakila-data.sql /docker-entrypoint-initdb.d/
COPY ./doc/2.sakila-user.sql /docker-entrypoint-initdb.d/
