package ex.data.silo.repository;

import ex.data.silo.domain.PollTopic;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PollTopicRepository extends R2dbcRepository<PollTopic, Long> { }