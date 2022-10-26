package ex.data.silo;

import ex.data.silo.domain.Message;
import ex.data.silo.domain.User;
import ex.data.silo.repository.MessageRepository;
import ex.data.silo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
public class SiloRepositoryTests {

    @Autowired
    UserRepository users;

    @Autowired
    MessageRepository messages;

    @Test
    void test_should_save_find_messages() {
        var message = new Message(null, 1L, 2L, "TEST");

        var stream = messages.save(message)
                .then(messages.findById(1L));

        StepVerifier
                .create(stream)
                .assertNext(msg -> {
                    Assertions
                            .assertThat(msg)
                            .isNotNull()
                            .hasNoNullFieldsOrProperties()
                            .hasFieldOrPropertyWithValue("id", 1L);   // HINT
                })
                .verifyComplete();
    }
    @Test
    void test_should_save_find_users() {
        var user = new User(null, "L.Skywalker");

        StepVerifier
                .create(users.save(user))
                .assertNext(u -> {
                    Assertions
                            .assertThat(u)
                            .isNotNull()
                            .hasNoNullFieldsOrProperties();
                })
                .verifyComplete();
    }
}
