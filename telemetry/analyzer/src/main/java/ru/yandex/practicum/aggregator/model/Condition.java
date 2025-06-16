package ru.yandex.practicum.aggregator.model;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;

/**
 * Условие.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "conditions", schema = "public")
@ToString
public class Condition {
    /**
     * Идентификатор условия.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Тип условия.
     */
    @Column(name = "type", nullable = false)
    private ConditionTypeAvro type;

    /**
     * Операция над значением.
     */
    @Column(name = "operation", nullable = false)
    private ConditionOperationAvro operation;

    /**
     * Значение.
     */
    @Column(name = "value")
    private Integer value;
}
