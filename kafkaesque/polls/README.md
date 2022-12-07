# Spring Cloud Stream w/ Kafka Streams

This application is a Stream processor that utilizes SCS support for Kafka Streams
to implement a 'polls' service. To get started, stand up the Streams and Kafka infrastructure
by executing the scripts in the `kafkaesque/infra` directory.

## App layout

The application takes in votes on potential poll choices, then emits those results to a 
dashboard like Grafana.

Here is the application layout:

```asciidoc
+--------------+                    +------------------+
|    /votes    |     * votes        |  KAFKA Stream    |
|              +------------------->|                  |
|  HTTP SOURCE |                    |  count votes     |
|              |                    |                  |
+--------------+                    +---------+--------+
                                              | (left join)
                                              | * counts
+--------------+                    +---------v--------+
|    /polls    |                    |  KAFKA Stream    |
|              |     * polls        |                  |
|  HTTP SOURCE +------------------->|  process results |
|              |                    |                  |
+--------------+                    +---------+--------+
                                              |
                                              |
                  *=Kafka Topic Names         |
                                              |               +-------------------+
                                              |* results      |                   |
                                              |               |                   |
                                              +-------------->|    Dashboard      |
                                                              |                   |
                                                              +-------------------+
```

## Build the app

To get started, you will need to build the Docker image for streams processors in the main part of the app.

From the root of the repository:

```shell
$ cd kafkaesque/polls
$ mvn clean spring-boot:build-image
```
The image that pops out will be called from our docker compose file.

## Deploy the Infra

This 'docker-compose.yml' file starts instances of Kafka, Zookeeper, polls, http-polls and http-votes. 
The later 2 containers are HTTP endpoints we will talk to in order to furnish data for the application.
These applications are the Kafka-bound [HTTP Source Stream](https://github.com/spring-cloud/stream-applications/blob/main/applications/source/http-source/README.adoc) variety
that let us send JSON data into the streams via HTTP.

From the root of the repository:
```bash
$ cd kafkaesque/infra
$ docker compose up
```

In addition to Kafka infrastructure, a couple of  are 
started. 

## Feed the data

The Poll questionnaire is an arrangement of choices that are composed of the following:

Here are the choices in 'data/polls.json':

```json
{"key":"1","value":"Java"}
{"key":"2","value":"Scala"}
{"key":"3","value":"Kotlin"}
```

You can send this data to the `http-polls` endpoint by executing:

```shell
infra $ ./send-polls.sh
```

Currently, we have some vote data to send but plan to provide a WEB interface. For now,
send the votes to the `http-votes` endpoint by executing `send-votes.sh' command:

```shell
infra $ ./send-votes.sh
```

