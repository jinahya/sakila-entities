# sakila-entities
Defines entities for [Sakila Sample Database](https://dev.mysql.com/doc/sakila/en/).


## sakila-db

### additional rows inserted for testing

|table    |country_id|country |
|---------|----------|--------|
|`country`|`65535`   |`Asgard`|

### running sakila-db with Docker
We need a running database for testing our entities.

#### generate shellscripts
```bash
$ mvn -Dmysql.host=<your.docker.address> process-resources
```

#### build the image if you haven't done yet
```bash
$ sh ./.sakila-db.build.sh
```

#### run sakila-db
```bash
$ sh ./.sakila-db.run.sh
``` 

#### connect to sakila-db
```bash
$ sh ./.sakila-db.mysql.sh
```

##### connect to sakila-db from outside
```bash
$ mysql --host=127.0.0.1 --port=33306 --user=sakila --password=sakila --database=sakila
```

#### stop sakila-db container
```bash
$ sh ./.sakila-db.stop.sh
```
