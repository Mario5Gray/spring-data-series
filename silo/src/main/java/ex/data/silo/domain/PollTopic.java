package ex.data.silo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("poll_topics")
public record PollTopic(@Id Long id, @Column("text") String text) {
}
