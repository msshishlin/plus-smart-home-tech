package ru.yandex.practicum.collector.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.configuration.KafkaProducerConfig;
import ru.yandex.practicum.collector.configuration.KafkaTopic;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Component
@Slf4j
public class KafkaEventProducer {
    /**
     * Конфигурация издателя данных.
     */
    private final KafkaProducerConfig config;

    /**
     * Издатель данных.
     */
    private final KafkaProducer<String, SpecificRecordBase> producer;

    /**
     * Конструктор.
     *
     * @param config конфигурация Kafka.
     */
    public KafkaEventProducer(KafkaProducerConfig config) {
        this.config = config;
        this.producer = new KafkaProducer<>(this.config.getProperties());
    }

    /**
     * Отправить в Kafka данные о событии в хабе.
     *
     * @param hubEventAvro данные о событии в хабе.
     */
    public void sendHubEvent(HubEventAvro hubEventAvro) {
        try {
            String topic = config.getTopics().get(KafkaTopic.HUBS_EVENTS);
            ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(topic, hubEventAvro);

            producer.send(record);
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
    }

    /**
     * Отправить в Kafka данные о событии датчика.
     *
     * @param sensorEventAvro данные о событии датчика.
     */
    public void sendSensorEvent(SensorEventAvro sensorEventAvro) {
        try {
            String topic = config.getTopics().get(KafkaTopic.SENSORS_EVENTS);
            ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(topic, sensorEventAvro);

            producer.send(record);
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
    }
}
