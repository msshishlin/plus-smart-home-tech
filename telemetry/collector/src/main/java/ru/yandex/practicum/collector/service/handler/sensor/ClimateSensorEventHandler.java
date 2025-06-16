package ru.yandex.practicum.collector.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.kafka.producer.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.ClimateSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;

/**
 * Обработчик данных события климатического датчика.
 */
@Component
public class ClimateSensorEventHandler extends BaseSensorEventHandler<ClimateSensorAvro> {
    /**
     * Конструктор.
     *
     * @param kafkaEventProducer издатель данных Kafka.
     */
    public ClimateSensorEventHandler(KafkaEventProducer kafkaEventProducer) {
        super(kafkaEventProducer);
    }

    @Override
    public SensorEventProto.PayloadCase getSensorEventType() {
        return SensorEventProto.PayloadCase.CLIMATE_SENSOR_EVENT;
    }

    @Override
    protected ClimateSensorAvro mapToAvro(SensorEventProto sensorEvent) {
        ClimateSensorProto climateSensor = sensorEvent.getClimateSensorEvent();

        return ClimateSensorAvro.newBuilder()
                .setCo2Level(climateSensor.getCo2Level())
                .setHumidity(climateSensor.getHumidity())
                .setTemperatureC(climateSensor.getTemperatureC())
                .build();
    }
}
