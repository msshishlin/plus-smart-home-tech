package ru.yandex.practicum.analyzer.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Конфигурация потребителя топика, содержащего данные о снимках состояний датчиков.
 */
@Configuration
@ConfigurationProperties("analyzer.kafka.sensors-snapshot-consumer")
@Getter
@Setter
@ToString
public class KafkaSnapshotConsumerConfig {
    /**
     * Параметры потребителя данных.
     */
    private Properties properties;

    /**
     * Топик для получения данных.
     */
    private String topic;
}
