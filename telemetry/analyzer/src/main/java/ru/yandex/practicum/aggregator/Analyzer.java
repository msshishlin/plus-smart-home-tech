package ru.yandex.practicum.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class Analyzer {
    public static void main(String[] args) {
        SpringApplication.run(Analyzer.class, args);
    }
}
