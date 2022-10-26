package ex.data.producer;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.function.context.PollableBean;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

@EnableScheduling
@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Bean
    public Consumer<TextMessage> log() {
        return message -> {
            System.out.printf("From: %s | To: %s | Body: %s \n", message.from(), message.to(), message.message());
        };
    }

	@PollableBean
    public Supplier<TextMessage> messages() {
        return () -> new TextMessage("Mario", "Binary", "101011111100");
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
		streamBridge.send("log-in-0", new TextMessage("Mario", "Mr. Ed", "HeeeHaawww"));
	}
}