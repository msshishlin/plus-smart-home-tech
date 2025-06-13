package sht.mapper;

import com.google.protobuf.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;

@Mapper
public interface SensorEventMapper {
    /**
     * Экземпляр маппера для моделей, содержащих информацию о событии датчика.
     */
    SensorEventMapper INSTANCE = Mappers.getMapper(SensorEventMapper.class);

    @Mapping(target = "payload", source = ".", qualifiedByName = "toAvroPayload")
    @Mapping(target = "timestamp", source = "timestamp", qualifiedByName = "toInstant")
    SensorEventAvro toSensorEventAvro(SensorEventProto event);

    ClimateSensorAvro toClimateSensorAvro(ClimateSensorProto event);

    LightSensorAvro toLightSensorAvro(LightSensorProto event);

    MotionSensorAvro toMotionSensorAvro(MotionSensorProto event);

    SwitchSensorAvro toSwitchSensorAvro(SwitchSensorProto event);

    TemperatureSensorAvro toTemperatureSensorAvro(TemperatureSensorProto event);

    @Named("toAvroPayload")
    static Object toAvroPayload(SensorEventProto event) {
        return switch (event.getPayloadCase()) {
            case CLIMATE_SENSOR_EVENT -> SensorEventMapper.INSTANCE.toClimateSensorAvro(event.getClimateSensorEvent());
            case LIGHT_SENSOR_EVENT -> SensorEventMapper.INSTANCE.toLightSensorAvro(event.getLightSensorEvent());
            case MOTION_SENSOR_EVENT -> SensorEventMapper.INSTANCE.toMotionSensorAvro(event.getMotionSensorEvent());
            case SWITCH_SENSOR_EVENT -> SensorEventMapper.INSTANCE.toSwitchSensorAvro(event.getSwitchSensorEvent());
            case TEMPERATURE_SENSOR_EVENT ->
                    SensorEventMapper.INSTANCE.toTemperatureSensorAvro(event.getTemperatureSensorEvent());
            case PAYLOAD_NOT_SET -> throw new RuntimeException("Can't map sensor event payload");
        };
    }

    @Named("toInstant")
    static Instant toInstant(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }
}
