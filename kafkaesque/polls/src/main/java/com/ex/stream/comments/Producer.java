package com.ex.stream.comments;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Producer {

    public static void main(String... args) {

        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        List<KeyValue<Long, String>> polls = Arrays.asList(
                new KeyValue<>(1L, "Java"),
                new KeyValue<>(2L, "Scala"),
                new KeyValue<>(3L, "Kotlin")
        );


        DefaultKafkaProducerFactory<Long, String> pf = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<Long, String> template = new KafkaTemplate<>(pf, true);
        template.setDefaultTopic("polls");

        for (KeyValue<Long, String> keyValue : polls) {
            System.out.println("Sending " + keyValue.value);
            template.sendDefault(keyValue.key, keyValue.value);
        }

    }
}
