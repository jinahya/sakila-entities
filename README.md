# sakila-entities

Defines entities for [Sakila Sample Database](https://dev.mysql.com/doc/sakila/en/).

## docker

We need a running database for integration-testing our entities.

### build image

Let's build the image.

```bash
$ docker build -t jinahya/sakila-db .
```

### run container

```bash
$ docker run --rm -d --name sakila-db -p 33306:3306 jinahya/sakila-db
``` 

### connect to the container

```bash
$ docker exec -it sakila-db mysql -usakila -psakila -Dsakila
```

### stop the container

```bash
$ docker stop sakila-db
```
