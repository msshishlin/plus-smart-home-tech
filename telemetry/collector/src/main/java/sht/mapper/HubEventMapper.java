package sht.mapper;

import com.google.protobuf.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;
import java.util.List;

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

    @Mapping(target = "actions", source = "actionList", qualifiedByName = "toDeviceActionList")
    @Mapping(target = "conditions", source = "conditionList", qualifiedByName = "toScenarioConditionList")
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

    @Named("toDeviceActionList")
    static List<DeviceActionAvro> toDeviceActionList(List<DeviceActionProto> actions) {
        return actions.stream().map((action) -> DeviceActionAvro.newBuilder()
                .setSensorId(action.getSensorId())
                .setType(switch(action.getType()) {
                    case ACTIVATE -> ActionTypeAvro.ACTIVATE;
                    case DEACTIVATE -> ActionTypeAvro.DEACTIVATE;
                    case INVERSE -> ActionTypeAvro.INVERSE;
                    case SET_VALUE -> ActionTypeAvro.SET_VALUE;
                    case UNRECOGNIZED -> throw new RuntimeException("Can't map device action type");
                })
                .setValue(action.getValue())
                .build()
        ).toList();
    }

    @Named("toScenarioConditionList")
    static List<ScenarioConditionAvro> toScenarioConditionList(List<ScenarioConditionProto> conditions) {
        return conditions.stream().map((condition) -> ScenarioConditionAvro.newBuilder()
                .setSensorId(condition.getSensorId())
                .setType(switch (condition.getType()) {
                    case CO2LEVEL -> ConditionTypeAvro.CO2LEVEL;
                    case HUMIDITY -> ConditionTypeAvro.HUMIDITY;
                    case LUMINOSITY -> ConditionTypeAvro.LUMINOSITY;
                    case MOTION -> ConditionTypeAvro.MOTION;
                    case TEMPERATURE -> ConditionTypeAvro.TEMPERATURE;
                    case SWITCH -> ConditionTypeAvro.SWITCH;
                    case UNRECOGNIZED -> throw new RuntimeException("Can't map scenario condition type");
                })
                .setOperation(switch (condition.getOperation()) {
                    case EQUALS -> ConditionOperationAvro.EQUALS;
                    case GREATER_THAN -> ConditionOperationAvro.GREATER_THAN;
                    case LOWER_THAN -> ConditionOperationAvro.LOWER_THAN;
                    case UNRECOGNIZED -> throw new RuntimeException("Can't map scenario condition operation");
                })
                .setValue(switch (condition.getValueCase()) {
                    case BOOL_VALUE -> condition.getBoolValue();
                    case INT_VALUE -> condition.getIntValue();
                    case VALUE_NOT_SET -> null;
                })
                .build()
        ).toList();
    }
}
