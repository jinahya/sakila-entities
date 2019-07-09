FROM mysql/mysql-server:latest

ENV MYSQL_ROOT_PASSWORD root
#ENV MYSQL_DATABASE sakila
#ENV MYSQL_USER sakila
#ENV MYSQL_PASSWORD sakila

COPY sakila-schema.sql /docker-entrypoint-initdb.d/0.sakila-schema.sql
COPY sakila-data.sql /docker-entrypoint-initdb.d/1.sakila-data.sql
COPY sakila-user.sql /docker-entrypoint-initdb.d/2.sakila-user.sql
