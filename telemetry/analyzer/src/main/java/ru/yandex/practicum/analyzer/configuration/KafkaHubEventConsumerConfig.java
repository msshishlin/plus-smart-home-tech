package ru.yandex.practicum.analyzer.configuration;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Конфигурация потребителя топика, содержащего данные о событиях в хабе.
 */
@Configuration
@ConfigurationProperties("analyzer.kafka.hub-event-consumer")
@Getter
@Setter
@ToString
public class KafkaHubEventConsumerConfig {
    /**
     * Параметры потребителя данных.
     */
    private Properties properties;

    /**
     * Топик для получения данных.
     */
    private String topic;
}
