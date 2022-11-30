package com.ex.stream.comments;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.BiFunction;
import java.util.function.Function;

@SpringBootApplication
public class PollsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollsApplication.class, args);
    }

    // Take Tuple (UserName, CID), and Produce
    //  CID : Count
    @Bean
    public Function<KStream<String, Long>, KTable<Long, Long>> voting() {
        return pollVoteStream ->
                pollVoteStream
                        .map((voter, cid) -> KeyValue.pair(cid, voter))
                        .groupByKey(Grouped.with(Serdes.Long(), Serdes.String()))
                        .count(Materialized.as("vote-count-2"))
                ;
    }
}


record Poll(String name, String text) {
}

record PollChoice(String pollName, Long choiceId, String choiceText) {
}

record PollVote(String voter, Long choiceId) {}

record PollVoteTotal(Long choiceId, Long count) {
} // KV choiceId, count

record PollResult(String choiceText, Long count) {
}