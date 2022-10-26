package ex.data.silo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("messages")
public record Message(@Id Long id, @Column("fromId") Long from, @Column("toId") Long to, String text) {
}
