package ex.data.silo.repository;

import ex.data.silo.domain.Message;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface MessageRepository extends R2dbcRepository<Message, Long> {

    Flux<Message> findByTo(Integer Long);
    Flux<Message> findByFrom(Integer Long);
}
