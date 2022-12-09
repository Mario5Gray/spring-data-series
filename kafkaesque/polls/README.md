# Spring Cloud Stream w/ Kafka Streams

This application is a Stream processor that utilizes SCS support for Kafka Streams
to implement a 'polls' service. To get started, stand up the Streams and Kafka infrastructure
by executing the scripts in the `kafkaesque/infra` directory.

## App layout

The application takes in votes and polls over HTTP, then emits those results to a 
websocket sink. The intermediary steps between source and sink are composed of Kafka streams
which process votes, polls, and results.

Here is the application layout:

```asciidoc
+--------------+                    +------------------+
|    /votes    |     * votes        |  KAFKA Stream    |
|              +------------------->|                  |
|  HTTP SOURCE |                    |  count votes     |
|              |                    |                  |
+--------------+                    +---------+--------+
                                              |
                                              | * counts
+--------------+                    +---------v--------+
|    /polls    |                    |  KAFKA Stream    |
|              |     * polls        |                  |
|  HTTP SOURCE +------------------->|  process results |
|              |                    |                  |
+--------------+                    +---------+--------+
                                              |
                                              | * results
               *=Kafka topic name   +---------v--------+
                                    |   /websocket     |
                                    |                  |
                                    |  websocket sink  |
                                    |                  |
                                    +------------------+
```

## Build the stream components

To get started, you will need to build the Docker image for streams processors in the main part of the app called `polls`.

From the root of the repository:

```shell
$ cd kafkaesque/polls
$ mvn clean spring-boot:build-image
```
The image that pops out will be called from our docker compose file.

## Deploy the Infra

This 'docker-compose.yml' file starts instances of Kafka, Zookeeper, polls, websocket-sink, http-polls and http-votes. The later 2 containers are HTTP endpoints we will send app specific data.
These applications are the Kafka-bound [HTTP Source Stream](https://github.com/spring-cloud/stream-applications/blob/main/applications/source/http-source/README.adoc) variety that let us send JSON data into the streams via HTTP and emit those payloads into a kafka topic.

Be sure to have the following ports open: 9092, 9090, 9091, 9095, 9096.

From the root of the repository:
```bash
$ cd kafkaesque/infra
$ docker compose up
```

Once the logs finish scrolling, you are then ready to interact with the application.
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

Next, we can listen to the output of the `results` topic by subscribing to the websocket-sink.
In this example, we will use [websocat](https://github.com/vi/websocat) to connect and listen to outbound results data.

```bash
$ websocat http://localhost:9095/websocket
```

Finally, we have some vote data to send to the `http-votes` endpoint by executing `send-votes.sh' command:

```shell
infra $ ./send-votes.sh
```

At this point, you may watch the `websocat` websocket listener emit the final vote counts:

```text
websocat ws://localhost:9095/websocket
{"choiceText":"Kotlin","count":1}
{"choiceText":"Scala","count":4}
{"choiceText":"Java","count":1}
```

This is the culmination of all stream processors.