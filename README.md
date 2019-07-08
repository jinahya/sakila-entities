# sakila-entities
Defines entities for [Sakila Sample Database](https://dev.mysql.com/doc/sakila/en/).


## Running sakila-db with Docker
We need a running database for testing our entities.

### Build the image if you haven't done yet
```bash
$ sh ./.sakila-db.build.sh
```

### Run sakila-db
```bash
$ sh ./.sakila-db.run.sh
``` 

### Connect to sakila-db
```bash
$ sh ./.sakila-db.mysql.sh
```

#### Connect to sakila-db from outside
```bash
$ mysql --host=127.0.0.1 --port=33306 --user=sakila --password=sakila --database=sakila
```

### Stop sakila-db container
```bash
$ sh ./.sakila-db.stop.sh
```