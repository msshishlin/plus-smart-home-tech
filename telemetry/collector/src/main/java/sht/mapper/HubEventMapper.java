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
public interface HubEventMapper {
    /**
     * Экземпляр маппера для моделей, содержащих информацию о событии, происходящем в хабе.
     */
    HubEventMapper INSTANCE = Mappers.getMapper(HubEventMapper.class);

    @Mapping(target = "payload", source = ".", qualifiedByName = "toAvroPayload")
    @Mapping(target = "timestamp", source = "timestamp", qualifiedByName = "toInstant")
    HubEventAvro toHubEventAvro(HubEventProto event);

    @Mapping(target = "type", source = "type", qualifiedByName = "toDeviceTypeAvro")
    DeviceAddedEventAvro toDeviceAddedEventAvro(DeviceAddedEventProto event);

    DeviceRemovedEventAvro toDeviceRemovedEventAvro(DeviceRemovedEventProto event);

    ScenarioAddedEventAvro toScenarioAddedEventAvro(ScenarioAddedEventProto event);

    ScenarioRemovedEventAvro toScenarioRemovedEventAvro(ScenarioRemovedEventProto event);

    @Named("toAvroPayload")
    static Object toAvroPayload(HubEventProto event) {
        return switch (event.getPayloadCase()) {
            case DEVICE_ADDED -> HubEventMapper.INSTANCE.toDeviceAddedEventAvro(event.getDeviceAdded());
            case DEVICE_REMOVED -> HubEventMapper.INSTANCE.toDeviceRemovedEventAvro(event.getDeviceRemoved());
            case SCENARIO_ADDED -> HubEventMapper.INSTANCE.toScenarioAddedEventAvro(event.getScenarioAdded());
            case SCENARIO_REMOVED -> HubEventMapper.INSTANCE.toScenarioRemovedEventAvro(event.getScenarioRemoved());
            case PAYLOAD_NOT_SET -> throw new RuntimeException("Can't map hub event payload");
        };
    }

    @Named("toInstant")
    static Instant toInstant(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }

    @Named("toDeviceTypeAvro")
    static DeviceTypeAvro toDeviceTypeAvro(DeviceTypeProto type) {
        return switch (type) {
            case CLIMATE_SENSOR -> DeviceTypeAvro.CLIMATE_SENSOR;
            case LIGHT_SENSOR -> DeviceTypeAvro.LIGHT_SENSOR;
            case MOTION_SENSOR -> DeviceTypeAvro.MOTION_SENSOR;
            case SWITCH_SENSOR -> DeviceTypeAvro.SWITCH_SENSOR;
            case TEMPERATURE_SENSOR -> DeviceTypeAvro.TEMPERATURE_SENSOR;
            case UNRECOGNIZED -> throw new RuntimeException("Can't map device type");
        };
    }
}
