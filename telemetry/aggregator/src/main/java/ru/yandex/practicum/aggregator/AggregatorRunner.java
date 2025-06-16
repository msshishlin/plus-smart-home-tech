package ru.yandex.practicum.aggregator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.aggregator.processor.AggregationProcessor;

@Component
@RequiredArgsConstructor
public class AggregatorRunner implements CommandLineRunner {
    private final AggregationProcessor aggregationProcessor;

    @Override
    public void run(String... args) throws Exception {
        aggregationProcessor.start();
    }
}
