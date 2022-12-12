package ex.data.silo;

import ex.data.silo.domain.Message;
import ex.data.silo.domain.PollTopic;
import ex.data.silo.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class SiloDomainTests {

	@Test
	void test_should_construct_poll_topic() {
		var poll = new PollTopic(1L, "test assured");

		Assertions
				.assertThat(poll)
				.isNotNull()
				.hasNoNullFieldsOrProperties()
				.hasFieldOrPropertyWithValue("id", 1L)
				.hasFieldOrPropertyWithValue("text", "test assured");
	}

	@Test
	void test_should_construct_user() {
		var user = new User(1L, "TestUser");

		Assertions
				.assertThat(user)
				.isNotNull()
				.hasNoNullFieldsOrProperties()
				.hasFieldOrPropertyWithValue("name", "TestUser")
				.hasFieldOrPropertyWithValue("id", 1L);
	}

	@Test
	void test_should_construct_message() {
		var message = new Message(123L, 1L, 2L, "Test!");

		Assertions
				.assertThat(message)
				.isNotNull()
				.hasNoNullFieldsOrProperties()
				.hasFieldOrPropertyWithValue("id", 123L)
				.hasFieldOrPropertyWithValue("from", 1L)
				.hasFieldOrPropertyWithValue("to", 2L)
				.hasFieldOrPropertyWithValue("text", "Test!");
	}
}
