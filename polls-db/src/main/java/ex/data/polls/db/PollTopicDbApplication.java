package ex.data.polls.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class PollTopicDbApplication {
	public static void main(String[] args) {
		SpringApplication.run(PollTopicDbApplication.class, args);
	}

}
