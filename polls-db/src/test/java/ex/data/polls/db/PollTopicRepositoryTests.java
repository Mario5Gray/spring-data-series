package ex.data.polls.db;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.repository.CrudRepository;

@DataJdbcTest
public class PollTopicRepositoryTests {

    @Autowired
    CrudRepository<PollTopic, Long> repo;

    @Test
    public void testShouldSaveFind() {
        var poll = new PollTopic(null, "TEST");

        var savedPoll = repo.save(poll);

        Assertions
                .assertThat(savedPoll)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("description", "TEST");
    }
}
