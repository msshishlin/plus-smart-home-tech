package ru.yandex.practicum.analyzer.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Датчик.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "sensors", schema = "public")
@ToString
public class Sensor {
    /**
     * Идентификатор датчика.
     */
    @Id
    private String id;

    /**
     * Идентификатор хаба, к которому относится датчик.
     */
    @Column(name = "hub_id", nullable = false)
    private String hubId;
}
