package com.ex.stream.comments;

import org.apache.kafka.common.serialization.Serdes;
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

    // The following errors:
    // https://issues.apache.org/jira/browse/KAFKA-14270

    @Profile("count")
    @Bean
    public Function<KStream<Long, PollVote>, KTable<Long, Long>> countVotes() {
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

    @Profile("results")
    @Bean
    public BiFunction<KTable<Long, Long>, KTable<Long, PollChoice>, KTable<Long, PollResult>> outputResults() {
        return (resultsTable, choiceTable) ->
                resultsTable
                        .leftJoin(choiceTable,
                                (cnt, choice) -> new PollResult(" " + choice.text(), cnt))
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