package ru.yandex.practicum.collector.service.handler.sensor;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.collector.kafka.producer.KafkaEventProducer;
import ru.yandex.practicum.collector.service.handler.SensorEventHandler;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.time.Instant;

/**
 * Базовый обработчик данных о событии датчика.
 *
 * @param <T> тип данных о событии датчика в формате Avro.
 */
@Slf4j
public abstract class BaseSensorEventHandler<T extends SpecificRecordBase> implements SensorEventHandler {
    /**
     * Издатель данных Kafka.
     */
    private final KafkaEventProducer kafkaEventProducer;

    /**
     * Конструктор.
     *
     * @param kafkaEventProducer издатель данных Kafka.
     */
    public BaseSensorEventHandler(KafkaEventProducer kafkaEventProducer) {
        this.kafkaEventProducer = kafkaEventProducer;
    }

    @Override
    public void handle(SensorEventProto sensorEvent) {
        if (!sensorEvent.getPayloadCase().equals(getSensorEventType())) {
            throw new IllegalArgumentException("Неизвестный тип события: " + sensorEvent.getPayloadCase());
        }

        T payload = mapToAvro(sensorEvent);

        SensorEventAvro sensorEventAvro = SensorEventAvro.newBuilder()
                .setHubId(sensorEvent.getHubId())
                .setId(sensorEvent.getId())
                .setTimestamp(Instant.ofEpochSecond(sensorEvent.getTimestamp().getSeconds(), sensorEvent.getTimestamp().getNanos()))
                .setPayload(payload)
                .build();

        kafkaEventProducer.sendSensorEvent(sensorEventAvro);
    }

    /**
     * Преобразовать данные о событии датчика в формат Avro.
     *
     * @param sensorEvent данные о событии датчика в формате Proto.
     * @return данные о событии датчика в формате Avro.
     */
    protected abstract T mapToAvro(SensorEventProto sensorEvent);
}