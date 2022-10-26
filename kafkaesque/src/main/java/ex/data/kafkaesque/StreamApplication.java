package ex.data.kafkaesque;

import ex.data.kafkaesque.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.util.StringUtils;

import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
@Slf4j
public class StreamApplication {


    public static void main(String[] args) {
        SpringApplication.run(StreamApplication.class, args);
    }

//	@Bean
//	public KafkaStreamsConfiguration streamsConfiguration() {
//		return new KafkaStreamsConfiguration(Map.of(
//				APPLICATION_ID_CONFIG, "messageStreams",
//				BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
//				DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName(),
//				DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName(),
//				DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class
//		));
//	}

    @Bean
    public Function<KStream<Long, Message>, KStream<Long, Long>> kstream(StreamsBuilder kstreamBuilder) {
        return kStream -> kStream
                .filter((k, msg) -> StringUtils.hasText(msg.text()))
                .map((k, msg) -> new KeyValue<>(msg.from(), 1L))
                .groupByKey(Grouped.with(Serdes.Long(), Serdes.Long()))
                .count(Materialized.as("msgCnt"))
                .toStream();
    }

    @Bean
    Consumer<KTable<Long, Long>> messageCounts() {
        return counts -> counts
                .toStream()
                .foreach((k, v) -> log.info(
                        String.format("user: %d - messages: %d", k, v)));
    }
}
