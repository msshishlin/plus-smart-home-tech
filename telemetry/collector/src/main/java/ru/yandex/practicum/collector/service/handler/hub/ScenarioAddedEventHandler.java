package ru.yandex.practicum.collector.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.kafka.producer.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.List;

/**
 * Обработчик данных события добавления в хаб нового сценария.
 */
@Component
public class ScenarioAddedEventHandler extends BaseHubEventHandler<ScenarioAddedEventAvro> {
    /**
     * Конструктор.
     *
     * @param kafkaEventProducer издатель данных Kafka.
     */
    public ScenarioAddedEventHandler(KafkaEventProducer kafkaEventProducer) {
        super(kafkaEventProducer);
    }

    @Override
    public HubEventProto.PayloadCase getHubEventType() {
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }

    @Override
    protected ScenarioAddedEventAvro mapToAvro(HubEventProto hubEvent) {
        ScenarioAddedEventProto scenarioAddedEvent = hubEvent.getScenarioAdded();

        return ScenarioAddedEventAvro.newBuilder()
                .setActions(mapToDeviceActionAvroList(scenarioAddedEvent.getActionList()))
                .setConditions(mapToScenarioConditionAvroList(scenarioAddedEvent.getConditionList()))
                .setName(scenarioAddedEvent.getName())
                .build();
    }

    private static List<DeviceActionAvro> mapToDeviceActionAvroList(List<DeviceActionProto> deviceActionList) {
        return deviceActionList.stream().map(ScenarioAddedEventHandler::mapToDeviceActionAvro).toList();
    }

    private static DeviceActionAvro mapToDeviceActionAvro(DeviceActionProto deviceAction) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(deviceAction.getSensorId())
                .setType(mapToActionTypeAvro(deviceAction.getType()))
                .setValue(deviceAction.getValue())
                .build();
    }

    private static ActionTypeAvro mapToActionTypeAvro(ActionTypeProto actionType) {
        return switch (actionType) {
            case ACTIVATE -> ActionTypeAvro.ACTIVATE;
            case DEACTIVATE -> ActionTypeAvro.DEACTIVATE;
            case INVERSE -> ActionTypeAvro.INVERSE;
            case SET_VALUE -> ActionTypeAvro.SET_VALUE;
            case UNRECOGNIZED -> throw new RuntimeException("Can't map device action type");
        };
    }

    private static List<ScenarioConditionAvro> mapToScenarioConditionAvroList(List<ScenarioConditionProto> scenarioConditionList) {
        return scenarioConditionList.stream().map(ScenarioAddedEventHandler::mapToScenarioConditionAvro).toList();
    }

    private static ScenarioConditionAvro mapToScenarioConditionAvro(ScenarioConditionProto scenarioCondition) {
        return ScenarioConditionAvro.newBuilder()
                .setOperation(mapToConditionOperationAvro(scenarioCondition.getOperation()))
                .setSensorId(scenarioCondition.getSensorId())
                .setType(mapToConditionTypeAvro(scenarioCondition.getType()))
                .setValue(switch (scenarioCondition.getValueCase()) {
                    case BOOL_VALUE -> scenarioCondition.getBoolValue();
                    case INT_VALUE -> scenarioCondition.getIntValue();
                    case VALUE_NOT_SET -> null;
                })
                .build();
    }

    private static ConditionOperationAvro mapToConditionOperationAvro(ConditionOperationProto conditionOperation) {
        return switch (conditionOperation) {
            case EQUALS -> ConditionOperationAvro.EQUALS;
            case GREATER_THAN -> ConditionOperationAvro.GREATER_THAN;
            case LOWER_THAN -> ConditionOperationAvro.LOWER_THAN;
            case UNRECOGNIZED -> throw new RuntimeException("Can't map scenario condition operation");
        };
    }

    private static ConditionTypeAvro mapToConditionTypeAvro(ConditionTypeProto conditionType) {
        return switch (conditionType) {
            case CO2LEVEL -> ConditionTypeAvro.CO2LEVEL;
            case HUMIDITY -> ConditionTypeAvro.HUMIDITY;
            case LUMINOSITY -> ConditionTypeAvro.LUMINOSITY;
            case MOTION -> ConditionTypeAvro.MOTION;
            case TEMPERATURE -> ConditionTypeAvro.TEMPERATURE;
            case SWITCH -> ConditionTypeAvro.SWITCH;
            case UNRECOGNIZED -> throw new RuntimeException("Can't map scenario condition type");
        };
    }
}
