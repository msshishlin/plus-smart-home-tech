package ru.yandex.practicum.analyzer.kafka.consumer;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.configuration.KafkaHubEventConsumerConfig;

import java.time.Duration;
import java.util.List;

@Component
public class KafkaHubEventConsumer {
    /**
     * Конфигурация потребителя данных.
     */
    private final KafkaHubEventConsumerConfig config;

    /**
     * Потребиль данных.
     */
    private final org.apache.kafka.clients.consumer.KafkaConsumer<String, SpecificRecordBase> consumer;

    /**
     * Конструктор.
     *
     * @param config конфигурация потребителя данных.
     */
    public KafkaHubEventConsumer(KafkaHubEventConsumerConfig config) {
        this.config = config;
        this.consumer = new KafkaConsumer<>(this.config.getProperties());

        Runtime.getRuntime().addShutdownHook(new Thread(this.consumer::wakeup));
    }

    public void subscribe() {
        consumer.subscribe(List.of(config.getTopic()));
    }

    public ConsumerRecords<String, SpecificRecordBase> poll() {
        return consumer.poll(Duration.ofMillis(100));
    }

    public void stop() {
        try {
            consumer.commitSync();
        } finally {
            consumer.close();
        }
    }
}
