package ex.data.silo;

import ex.data.silo.domain.Message;
import ex.data.silo.domain.PollTopic;
import ex.data.silo.domain.User;
import ex.data.silo.repository.MessageRepository;
import ex.data.silo.repository.PollTopicRepository;
import ex.data.silo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import reactor.test.StepVerifier;

@SpringBootTest
public class SiloRepositoryTests {

    @Autowired
    UserRepository users;

    @Autowired
    MessageRepository messages;

    @Autowired
    PollTopicRepository polls;

    @Test
    void test_should_save_find_poll_topic() {
        var poll = new PollTopic(null, "test assured");

        var flux = polls
                .save(poll)
                .thenMany(polls.findAll());

        StepVerifier
                .create(flux)
                .consumeNextWith(msg -> {
                    Assertions
                            .assertThat(msg)
                            .isNotNull()
                            .hasNoNullFieldsOrProperties();
                })
                .verifyComplete();
    }

    @Test
    void test_should_save_find_messages() {
        var message = new Message(null, 1L, 2L, "TEST");

        var flux = messages
                .save(message)
                .thenMany(messages.findAll());

        StepVerifier
                .create(flux)
                .consumeNextWith(msg -> {
                    Assertions
                            .assertThat(msg)
                            .isNotNull()
                            .hasNoNullFieldsOrProperties();
                })
                .verifyComplete();
    }

    @Test
    void test_should_save_find_users() {
        var user = new User(null, "L.Skywalker");

        var flux = users
                .save(user)
                .thenMany(users.findAll());

        StepVerifier
                .create(flux)
                .consumeNextWith(u -> {
                    Assertions
                            .assertThat(u)
                            .isNotNull()
                            .hasNoNullFieldsOrProperties();
                })
                .verifyComplete();
    }

    @Test
    void test_should_find_user_by_example() {
        var user = new User(null, "D.Vader");

        var example = Example.of(user, ExampleMatcher
                .matching().withIgnorePaths("id"));

        var flux = users
                .save(user)
                .thenMany(users.findOne(example));

        StepVerifier
                .create(flux)
                .consumeNextWith(u ->{
                    Assertions
                            .assertThat(u)
                            .isNotNull()
                            .hasNoNullFieldsOrProperties()
                            .hasFieldOrPropertyWithValue("name", "D.Vader");
                        })
                .verifyComplete();
    }
}