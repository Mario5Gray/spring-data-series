package ex.data.producer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.function.context.PollableBean;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

// AIO function composition example
@Slf4j
@EnableScheduling
@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Bean
    public Consumer<TextMessage> logMessage() {
        return message -> {
            log.info(String.format("From: %s | To: %s | Body: %s \n", message.from(), message.to(), message.message()));
        };
    }

    @Bean
    public Consumer<String> logString() {
        return str -> {
            log.info(str);
        };
    }

    @PollableBean
    public Supplier<TextMessage> sourceMessage() {
        return () -> new TextMessage("Mario", "Binary", Long.toBinaryString(new Random().nextInt(0xFFFFFF)));
    }

    @Bean
    public Function<TextMessage, String> stringifyMessage() {
        return (message) -> String.format("Msg From: %s | To: %s | Body: %s \n", message.from(), message.to(), message.message());
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> runner(StreamBridge streamBridge) {
        return event -> Executors.newScheduledThreadPool(1).schedule(() -> {
            for (var i = 0; i < 100; i++) {
                bridgeSend(streamBridge);
            }
        }, 2, TimeUnit.SECONDS);
    }

    @SneakyThrows
    private static void bridgeSend(StreamBridge streamBridge) {
        streamBridge.send("logMessage-in-0", new TextMessage("Mario", "Mr. Ed", "HeeeHaawww"));
    }
}