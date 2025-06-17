package ru.yandex.practicum.aggregator.model;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;

/**
 * Действие.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "actions", schema = "public")
@ToString
public class Action {
    /**
     * Идентификатор действия.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Тип действия.
     */
    @Column(name = "type", nullable = false)
    private ActionTypeAvro type;

    /**
     * Опорное значение для сравнения со значениями в условиях.
     */
    @Column(name = "value", nullable = false)
    private int value;
}
