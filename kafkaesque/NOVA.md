# Spring Cloud Kafka Streams

This guide reflects the method sused to produce a streaming application with [Spring Coud Stream](https://spring.io/projects/spring-cloud-stream) and 
Spring Cloud Kafka. Spring Cloud Stream is a [Streaming Microservices](https://tanzu.vmware.com/event-streaming) API that requires a queue/messaging 
system beneath your applications for things to work. SCS supports many systems such as [RabbitMQ](), [Apache RocketMQ](), [Microsoft Queueing][], [Apache Kafka]() and more. Streaming microservies are a powerful way to
produce applications that require scalibility, monitoring, and low-impact abstraction for things like sending
messages between services, and reporting erronous actions (such as [Dead Letter Queue]()). This is advantageous
over plain TCP services using HTTP or RSocket, since we get to capture and store application messages - as opposed to monitoring for bad input/output.

## Spring Cloud Stream

Starting with Spring Cloud Stream, we will select an underlaying messaging infrastructure. This guide uses Kafka, so will focus mainly on configuration of this system. However, unlike changing RPC protocols, this will only require chaning a few binding parameters.



* Review Spring Cloud Stream
 * How to import it
 * How to configure it
* Review Spring Kafka Binder.   ].  <-- difference between the 2
 * How to import it
 * How to configure it
* Review Kafka Streams Binder.  ]
 * import & Configuration
* Re-review Kafka Streams on top of SCS
 * Sample Streams API usage
 * with Cloud Stream outputs
* Stream Applications (http input, websocket output)
