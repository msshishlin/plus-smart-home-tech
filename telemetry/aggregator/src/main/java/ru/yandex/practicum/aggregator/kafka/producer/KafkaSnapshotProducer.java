package ru.yandex.practicum.aggregator.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.aggregator.configuration.KafkaConfig;
import ru.yandex.practicum.aggregator.configuration.KafkaSnapshotProducerConfig;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

@Component
@Slf4j
public class KafkaSnapshotProducer {
    /**
     * Конфигурация издателя данных.
     */
    private final KafkaSnapshotProducerConfig config;

    /**
     * Издатель данных.
     */
    private final KafkaProducer<String, SpecificRecordBase> producer;

    /**
     * Конструктор.
     *
     * @param kafkaConfig конфигурация Kafka.
     */
    public KafkaSnapshotProducer(KafkaConfig kafkaConfig) {
        this.config = kafkaConfig.producer;
        this.producer = new KafkaProducer<>(this.config.getProperties());
    }

    /**
     * Отправить снапшот состояния датчиков в хабе в Kafka.
     * @param sensorsSnapshotAvro снапшот состояния датчиков в хабе.
     */
    public void sendSensorSnapshot(SensorsSnapshotAvro sensorsSnapshotAvro) {
        try {
            String topic = config.getTopic();
            ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(topic, sensorsSnapshotAvro);

            producer.send(record);
        }
        catch(Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
    }

    public void stop() {
        try {
            producer.flush();
        } finally {
            producer.close();
        }
    }
}
