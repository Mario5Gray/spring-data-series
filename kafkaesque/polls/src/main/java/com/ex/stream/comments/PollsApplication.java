package com.ex.stream.comments;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.Duration;
import java.util.function.BiFunction;
import java.util.function.Function;

@SpringBootApplication
public class PollsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollsApplication.class, args);
    }

    @Profile("count")
    @Bean
    public Function<KStream<Bytes, PollVote>, KTable<Long, Long>> countVotes() {
        return pollVoteStream ->
                pollVoteStream
                        .map((voter, pollVote) -> KeyValue.pair(Long.valueOf(pollVote.choiceId()), pollVote.voter()))
                        .groupByKey(Grouped.with(Serdes.Long(), Serdes.String()))
                        .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(2)))
                        .count(Materialized.as("vote-count-2"))
                        .toStream((id, cnt) -> id.key())
                        .toTable()
                ;
    }

    // Take Tuple (UserName, CID), and Produce
    //  CID : Count
    @Profile("oldcount")
    @Bean
    public Function<KStream<String, String>, KTable<Long, Long>> oldCountVotes() {
        return pollVoteStream ->
                pollVoteStream
                        .map((voter, cid) -> KeyValue.pair(Long.valueOf(cid), voter))
                        .groupByKey(Grouped.with(Serdes.Long(), Serdes.String()))
                        .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(2)))
                        .count(Materialized.as("vote-count-2"))
                        .toStream((id, cnt) -> id.key())
                        .toTable()
                ;
    }

    @Profile("results")
    @Bean
    public BiFunction<KTable<Long, Long>, KTable<Long, String>, KStream<Long, String>> outputResults() {
        return (resultsTable, choiceTable) ->
                resultsTable
                        .leftJoin(choiceTable,
                                (cnt, choice) -> new PollResult(choice, cnt).toString())
                .toStream()
                ;
    }
}


record Poll(String id, String name) {
}

record PollChoice(Long choiceId, String text) {
}

record PollVote(String voter, Long choiceId) {
}

record PollVoteTotal(Long choiceId, Long count) {
} // KV choiceId, count

record PollResult(String choiceText, Long count) {
}