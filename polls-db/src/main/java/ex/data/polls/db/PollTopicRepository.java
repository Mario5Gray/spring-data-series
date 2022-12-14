package ex.data.polls.db;

import org.springframework.data.repository.CrudRepository;

public interface PollTopicRepository extends CrudRepository<PollTopic, Long> {
}