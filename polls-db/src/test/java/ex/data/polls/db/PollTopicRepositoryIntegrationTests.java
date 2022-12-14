package ex.data.polls.db;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class PollTopicRepositoryIntegrationTests {

    @Autowired
    CrudRepository<PollTopic, Long> repo;

    @Container
    static MySQLContainer<?> db = new MySQLContainer<>("mysql");

    @DynamicPropertySource
    static void registerPostgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> db.getJdbcUrl());
        registry.add("spring.datasource.username", () -> db.getUsername());
        registry.add("spring.datasource.password", () -> db.getPassword());
    }


    @Test
    public void testShouldSave() {
        var poll = new PollTopic(null, "TEST");

        var savedPoll = repo.save(poll);

        Assertions
                .assertThat(savedPoll)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("description", "TEST");
    }
}
