java -jar http-source-kafka-3.2.1.jar -Dlogging.level.org.springframework=DEBUG -Dlogging.level.org.springframework.integration=DEBUG --spring.cloud.stream.bindings.output.destination=votes --server.port=9090 --management.wavefront.metrics.export.enabled=false --management.datadog.metrics.export.enabled=false --http.path-pattern=/vote --http.cors.allowed-headers=kafka_messageKey --http.mapped-request-headers=kafka_messageKey --spring.cloud.stream.bindings.output.producer.messageKeyExpression="headers['kafka_messageKey']"
