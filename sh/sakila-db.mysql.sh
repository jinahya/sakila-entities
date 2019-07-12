### connect to the docker instance
#!/bin/sh
docker exec -it ${docker.container} mysql -u${mysql.user} -p${mysql.password} -D${mysql.database}
