package ex.data.silo.config;

import ex.data.silo.domain.Message;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Component
public class MessageIdGeneratingCallback implements BeforeConvertCallback<Message> {

    private final DatabaseClient databaseClient;

    @Override
    public Publisher<Message> onBeforeConvert(Message msg, SqlIdentifier table) {
        if (msg.id() == null) {

            return databaseClient.sql("SELECT NEXT VALUE FOR primary_key") //
                    .map(row -> row.get(0, Long.class)) //
                    .first() //
                    .map(id -> new Message(id, msg.from(), msg.to(), msg.text()));
        }

        return Mono.just(msg);
    }
}
