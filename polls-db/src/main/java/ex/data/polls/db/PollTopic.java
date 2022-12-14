package ex.data.polls.db;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "POLL_TOPICS")
public record PollTopic(@Id Long id, String description) {
}