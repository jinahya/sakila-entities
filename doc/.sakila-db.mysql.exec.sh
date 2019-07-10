### connect to the docker instance
#!/bin/sh
docker exec -it sakila-db mysql -usakila -psakila -Dsakila
