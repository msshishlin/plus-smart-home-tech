package ru.yandex.practicum.aggregator.configuration;

import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

/**
 * Конфигурация издателя данных Kafka.
 */
@Getter
@Setter
public class KafkaSnapshotProducerConfig {
    /**
     * Параметры потребителя данных.
     */
    private Properties properties;

    /**
     * Топик для получения данных.
     */
    private String topic;
}
