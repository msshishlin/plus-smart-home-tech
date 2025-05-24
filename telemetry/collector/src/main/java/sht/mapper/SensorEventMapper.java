package sht.mapper;

import org.apache.avro.specific.SpecificRecordBase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.kafka.telemetry.event.*;
import sht.models.sensor.*;

@Mapper
public interface SensorEventMapper {
    /**
     * Экземпляр маппера для моделей, содержащих информацию о событии датчика.
     */
    SensorEventMapper INSTANCE = Mappers.getMapper(SensorEventMapper.class);

    @Mapping(target = "payload", source = ".", qualifiedByName = "getPayload")
    SensorEventAvro toSensorEventAvro(SensorEvent event);

    ClimateSensorAvro toClimateSensorAvro(ClimateSensorEvent event);

    LightSensorAvro toLightSensorAvro(LightSensorEvent event);

    MotionSensorAvro toMotionSensorAvro(MotionSensorEvent event);

    SwitchSensorAvro toSwitchSensorAvro(SwitchSensorEvent event);

    TemperatureSensorAvro toTemperatureSensorAvro(TemperatureSensorEvent event);

    @Named("getPayload")
    static Object getPayload(SensorEvent event) {
        if (event instanceof ClimateSensorEvent climateSensorEvent) {
            return SensorEventMapper.INSTANCE.toClimateSensorAvro(climateSensorEvent);
        }

        if (event instanceof LightSensorEvent lightSensorEvent) {
            return SensorEventMapper.INSTANCE.toLightSensorAvro(lightSensorEvent);
        }

        if (event instanceof MotionSensorEvent motionSensorEvent) {
            return SensorEventMapper.INSTANCE.toMotionSensorAvro(motionSensorEvent);
        }

        if (event instanceof SwitchSensorEvent switchSensorEvent) {
            return SensorEventMapper.INSTANCE.toSwitchSensorAvro(switchSensorEvent);
        }

        if (event instanceof TemperatureSensorEvent temperatureSensorEvent) {
            return SensorEventMapper.INSTANCE.toTemperatureSensorAvro(temperatureSensorEvent);
        }

        throw new RuntimeException("Не удалось выполнить преобразование типа " + event.getClass().getName() + " в тип " + SpecificRecordBase.class.getName());
    }
}
