package ru.yandex.practicum.aggregator.kafka.deserializer;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.serializer.BaseAvroDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Component
public class SensorEventAvroDeserializer extends BaseAvroDeserializer<SensorEventAvro> {
    public SensorEventAvroDeserializer() {
        super(SensorEventAvro.getClassSchema());
    }
}
