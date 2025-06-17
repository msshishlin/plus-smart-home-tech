package ru.yandex.practicum.analyzer.kafka.deserializer;

import ru.yandex.practicum.kafka.serializer.BaseAvroDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public class SensorsSnapshotAvroDeserializer extends BaseAvroDeserializer<SensorsSnapshotAvro> {
    public SensorsSnapshotAvroDeserializer() {
        super(SensorsSnapshotAvro.getClassSchema());
    }
}
