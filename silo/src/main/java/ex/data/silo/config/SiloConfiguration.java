package ex.data.silo.config;

import ex.data.silo.domain.PollTopic;
import ex.data.silo.domain.User;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

@Configuration
public class SiloConfiguration {

    @Bean
    BeforeConvertCallback<User> userIdGeneratingCallback(DatabaseClient databaseClient) {

        return (user, sqlIdentifier) -> {
            if (user.id() == null) {
                return databaseClient.sql("SELECT NEXT VALUE FOR primary_key")
                        .map(row -> row.get(0, Long.class))
                        .first()
                        .map(id -> new User(id, user.name()));
            }
            return Mono.just(user);
        };
    }

    @Bean
    BeforeConvertCallback<PollTopic> pollIdGeneratingCallback(DatabaseClient databaseClient) {

        return (poll, sqlIdentifier) -> {
            if (poll.id() == null) {
                return databaseClient.sql("SELECT NEXT VALUE FOR primary_key")
                        .map(row -> row.get(0, Long.class))
                        .first()
                        .map(id -> new PollTopic(id, poll.text()));
            }
            return Mono.just(poll);
        };
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
//        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ByteArrayResource(("CREATE SEQUENCE primary_key;"
//                + "DROP TABLE IF EXISTS user;"
////                + "DROP TABLE IF EXISTS message;"
//                + "CREATE TABLE user (id INT PRIMARY KEY, name VARCHAR(100) NOT NULL);"
//        )//                + "CREATE TABLE message (id INT PRIMARY KEY, from INT NOT NULL, to INT NOT NULL, text VARCHAR(255));")
//                .getBytes())));

        return initializer;
    }
}
