package ru.yandex.practicum.analyzer.kafka.deserializer;

import ru.yandex.practicum.kafka.serializer.BaseAvroDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public class HubEventAvroDeserializer extends BaseAvroDeserializer<HubEventAvro> {
    public HubEventAvroDeserializer() {
        super(HubEventAvro.getClassSchema());
    }
}
