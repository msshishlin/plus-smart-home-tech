package ru.yandex.practicum.collector.service.handler.hub;

import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.collector.kafka.producer.KafkaEventProducer;
import ru.yandex.practicum.collector.service.handler.HubEventHandler;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.time.Instant;

/**
 * Базовый обработчик данных события в хабе.
 *
 * @param <T> тип данных о событии в хабе в формате Avro.
 */
public abstract class BaseHubEventHandler<T extends SpecificRecordBase> implements HubEventHandler {
    /**
     * Издатель данных Kafka.
     */
    private final KafkaEventProducer kafkaEventProducer;

    /**
     * Конструктор.
     *
     * @param kafkaEventProducer издатель данных Kafka.
     */
    public BaseHubEventHandler(KafkaEventProducer kafkaEventProducer) {
        this.kafkaEventProducer = kafkaEventProducer;
    }

    @Override
    public void handle(HubEventProto hubEvent) {
        if (!hubEvent.getPayloadCase().equals(getHubEventType())) {
            throw new IllegalArgumentException("Неизвестный тип события: " + hubEvent.getPayloadCase());
        }

        T payload = mapToAvro(hubEvent);

        HubEventAvro hubEventAvro = HubEventAvro.newBuilder()
                .setHubId(hubEvent.getHubId())
                .setPayload(payload)
                .setTimestamp(Instant.ofEpochSecond(hubEvent.getTimestamp().getSeconds(), hubEvent.getTimestamp().getNanos()))
                .build();

        kafkaEventProducer.sendHubEvent(hubEventAvro);
    }

    /**
     * Преобразовать данные о событии в хабе в формат Avro.
     *
     * @param hubEvent данные о событии в хабе в формате Proto.
     * @return данные о событии в хабе в формате Avro.
     */
    protected abstract T mapToAvro(HubEventProto hubEvent);
}
