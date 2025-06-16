package ru.yandex.practicum.aggregator.kafka.consumer;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.aggregator.configuration.KafkaConfig;
import ru.yandex.practicum.aggregator.configuration.KafkaSensorConsumerConfig;

import java.time.Duration;
import java.util.List;

@Component
public class KafkaSensorConsumer {
    /**
     * Конфигурация kafka.
     */
    private final KafkaSensorConsumerConfig config;

    /**
     * Потребитель данных.
     */
    private final KafkaConsumer<String, SpecificRecordBase> consumer;

    /**
     * Конструктор.
     *
     * @param kafkaConfig конфигурация Kafka.
     */
    public KafkaSensorConsumer(KafkaConfig kafkaConfig) {
        this.config = kafkaConfig.consumer;
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
