# Using Debezium

```shell
docker compose up
```

Wait for everything to stand up.

## Verify Kafka

```shell
curl -H "Accept:application/json" localhost:8083/
```

Should return a JSON object similar to:

```json
{"version":"3.2.0","commit":"38103ffaa962ef50""kafka_cluster_id":"Lh57gSpNTCeZ6hM9V6ahoQ"}
```

Then check the default connectors (there are none):

```shell
curl -H "Accept:application/json" localhost:8083/connectors/
```

should return `[]`.

## Verify MySQL

Post this bit of JSON to the connector:

```json
{
  "name": "inventory-connector",  
  "config": {  
    "connector.class": "io.debezium.connector.mysql.MySqlConnector",
    "tasks.max": "1",  
    "database.hostname": "mysql",  
    "database.port": "3306",
    "database.user": "debezium",
    "database.password": "dbz",
    "database.server.id": "184054",  
    "database.server.name": "dbserver1",  
    "database.include.list": "inventory",  
    "database.history.kafka.bootstrap.servers": "kafka:9092",  
    "database.history.kafka.topic": "schema-changes.inventory"  
  }
}
```

By using the following command:

```bash
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ --data @filename.json
```

Hit the connectors status:

```bash
curl -H "Accept:application/json" localhost:8083/connectors/
```

should return `["inventory-connector"]`


Check in on connector tasks:

```bash
curl -i -X GET -H "Accept:application/json" localhost:8083/connectors/inventory-connector
```

Should return something like:

```json
{
  "name": "inventory-connector",
  "config": {
    "connector.class": "io.debezium.connector.mysql.MySqlConnector",
    "database.user": "debezium",
    "database.server.id": "184054",
    "tasks.max": "1",
    "database.hostname": "mysql",
    "database.password": "dbz",
    "database.history.kafka.bootstrap.servers": "kafka:9092",
    "database.history.kafka.topic": "dbhistory.inventory",
    "name": "inventory-connector",
    "database.server.name": "dbserver1",
    "database.port": "3306",
    "database.include.list": "inventory"
  },
  "tasks": [
    {
      "connector": "inventory-connector",
      "task": 0
    }
  ],
  "type": "source"
}
```

## Kafka topic activity

By viewing the dbserver1.inventory.customers topic, you can see how the MySQL connector captured create events in the inventory database. In this case, the create events capture new customers being added to the database.


```bash
docker-compose -f docker-compose.yml exec kafka /kafka/bin/kafka-console-consumer.sh \
    --bootstrap-server kafka:9092 \
    --from-beginning \
    --property print.key=true \
    --topic dbserver1.inventory.customers
```


With that, we can then execute some mysql commands to watch the consumer logs increase:

```bash
docker-compose -f docker-compose.yml exec mysql bash -c 'mysql -u $MYSQL_USER -p$MYSQL_PASSWORD inventory'
```

You'll be given a mysql shell to operate with.