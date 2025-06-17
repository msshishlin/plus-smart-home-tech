package ru.yandex.practicum.analyzer.kafka.consumer;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.configuration.KafkaSnapshotConsumerConfig;

import java.time.Duration;
import java.util.List;

@Component
public class KafkaSnapshotConsumer {
    /**
     * Конфигурация потребителя данных.
     */
    private final KafkaSnapshotConsumerConfig config;

    /**
     * Потребиль данных.
     */
    private final KafkaConsumer<String, SpecificRecordBase> consumer;

    /**
     * Конструктор.
     *
     * @param config конфигурация потребителя данных.
     */
    public KafkaSnapshotConsumer(KafkaSnapshotConsumerConfig config) {
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
