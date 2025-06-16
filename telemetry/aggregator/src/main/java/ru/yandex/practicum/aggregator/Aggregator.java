package ru.yandex.practicum.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Главный класс сервиса Aggregator.
 */
@ConfigurationPropertiesScan
@SpringBootApplication
public class Aggregator {
    public static void main(String[] args) {
        SpringApplication.run(Aggregator.class, args);
    }
}
