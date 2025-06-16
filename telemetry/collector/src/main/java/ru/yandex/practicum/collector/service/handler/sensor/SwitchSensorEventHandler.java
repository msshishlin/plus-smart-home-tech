package ru.yandex.practicum.collector.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.kafka.producer.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SwitchSensorProto;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;

/**
 * Обработчик данных события переключателя.
 */
@Component
public class SwitchSensorEventHandler extends BaseSensorEventHandler<SwitchSensorAvro> {
    /**
     * Конструктор.
     *
     * @param kafkaEventProducer издатель данных Kafka.
     */
    public SwitchSensorEventHandler(KafkaEventProducer kafkaEventProducer) {
        super(kafkaEventProducer);
    }

    @Override
    public SensorEventProto.PayloadCase getSensorEventType() {
        return SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT;
    }

    @Override
    protected SwitchSensorAvro mapToAvro(SensorEventProto sensorEvent) {
        SwitchSensorProto switchSensor = sensorEvent.getSwitchSensorEvent();

        return SwitchSensorAvro.newBuilder()
                .setState(switchSensor.getState())
                .build();
    }
}
