# Streaming Microservices with Spring Cloud Stream

This repository contains several examples for implementing Streaming, R2DBC and CDC strategies.

This repository uses [Git LFS](https://git-lfs.github.com) to store some larger assets.
 
## Kafkaesque

Kafka Streams applications:

`polls`: This application will take votes from an HTTP source, and produce results into a topic `counting`. The `counting` topic is joined with `polls` topic, which adds descriptions to the results. These results are then sent to a websocket sink for downstream consumers (maybe cached, single page generation, etc...).
## Silo

Rather than publishing every datapoint (users, polls, choices, etc..) to streams, this app takes the approach of an existing RDBMS that contains low-cardinality entries, with Change Data Capture to support the streaming backends.

`poll-topics`: This repository takes CDC updates from a RDBMS then pushes new poll-choices to the 'polls' stream for reference during the results procedure.
### Links

[Spring Cloud Stream reference](https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream.html#spring-cloud-stream-reference)

[SCS Applications](https://github.com/spring-cloud/stream-applications)

Deployable [SCS apps bound to specific broker](https://repo.maven.apache.org/maven2/org/springframework/cloud/stream/app/)

[Kafka Streams Binder Docs](https://docs.spring.io/spring-cloud-stream-binder-kafka/docs/current/reference/html/spring-cloud-stream-binder-kafka.html)

[Spring Kafka Docs useful when configuring kafka connectivity](https://docs.spring.io/spring-kafka/reference/html/#tips-n-tricks)

[SpEL Docs](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#expressions)

[Websocat tool](https://github.com/vi/websocat)
