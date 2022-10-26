package ex.data.kafkaesque.domain;

public record Message(Long id, Long from, Long to, String text) {
}