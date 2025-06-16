package ru.yandex.practicum.aggregator.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация Kafka.
 */
@Configuration
@ConfigurationProperties("aggregator.kafka")
@Getter
@Setter
@ToString
public class KafkaConfig {
    /**
     * Конфигурация потребителя данных.
     */
    public KafkaSensorConsumerConfig consumer;

    /**
     * Конфигурация издателя данных.
     */
    public KafkaSnapshotProducerConfig producer;
}
