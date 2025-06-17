package ru.yandex.practicum.analyzer.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Сценарий.
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "scenarios", schema = "public")
@ToString
public class Scenario {
    /**
     * Идентификатор сценария.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Идентификатор хаба, к которому относится сценарий.
     */
    @Column(name = "hub_id", nullable = false)
    private String hubId;

    /**
     * Название сценария.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Коллекция условий для выполнения сценария.
     */
    @JoinTable(name = "scenario_conditions", joinColumns = @JoinColumn(name = "scenario_id"), inverseJoinColumns = @JoinColumn(name = "condition_id"))
    @MapKeyColumn(table = "scenario_conditions", name = "sensor_id")
    @OneToMany(fetch = FetchType.EAGER)
    private Map<String, Condition> conditions = new HashMap<>();

    /**
     * Коллекция действий, которые необходимо выполнить в рамках сценария.
     */
    @JoinTable(name = "scenario_actions", joinColumns = @JoinColumn(name = "scenario_id"), inverseJoinColumns = @JoinColumn(name = "action_id"))
    @MapKeyColumn(table = "scenario_actions", name = "sensor_id")
    @OneToMany(fetch = FetchType.EAGER)
    private Map<String, Action> actions = new HashMap<>();
}
