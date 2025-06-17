package ru.yandex.practicum.analyzer.service.handler.snapshot;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.model.Action;
import ru.yandex.practicum.analyzer.model.Condition;
import ru.yandex.practicum.analyzer.model.Scenario;
import ru.yandex.practicum.analyzer.repository.ScenarioRepository;
import ru.yandex.practicum.analyzer.service.handler.SensorsSnapshotHandler;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.List;
import java.util.Map;

@Component
public class SensorsSnapshotHandlerImpl implements SensorsSnapshotHandler {
    /**
     * Хранилище сценариев.
     */
    private final ScenarioRepository scenarioRepository;

    /**
     * Клиент hub-router'а.
     */

    private final HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterClient;

    /**
     * Конструктор.
     *
     * @param scenarioRepository хранилище сценариев.
     * @param hubRouterClient    клиент hub-router'а.
     */
    public SensorsSnapshotHandlerImpl(ScenarioRepository scenarioRepository,
                                      @GrpcClient("hub-router") HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterClient) {
        this.scenarioRepository = scenarioRepository;
        this.hubRouterClient = hubRouterClient;
    }

    @Override
    public void handle(SensorsSnapshotAvro sensorsSnapshot) {
        Map<String, SensorStateAvro> sensorsState = sensorsSnapshot.getSensorsState();

        List<Scenario> scenarios = scenarioRepository.findByHubId(sensorsSnapshot.getHubId());
        for (Scenario scenario : scenarios) {
            if (checkScenario(scenario, sensorsState)) {
                for (Map.Entry<String, Action> action : scenario.getActions().entrySet()) {
                    DeviceActionProto deviceAction = DeviceActionProto.newBuilder()
                            .setSensorId(action.getKey())
                            .setType(switch (action.getValue().getType()) {
                                case ACTIVATE -> ActionTypeProto.ACTIVATE;
                                case DEACTIVATE -> ActionTypeProto.DEACTIVATE;
                                case SET_VALUE -> ActionTypeProto.SET_VALUE;
                                case INVERSE -> ActionTypeProto.INVERSE;
                            })
                            .setValue(action.getValue().getValue())
                            .build();

                    hubRouterClient.handleDeviceAction(DeviceActionRequest.newBuilder()
                            .setHubId(sensorsSnapshot.getHubId())
                            .setScenarioName(scenario.getName())
                            .setAction(deviceAction)
                            .build());
                }
            }
        }
    }

    private boolean checkScenario(Scenario scenario, Map<String, SensorStateAvro> sensorsState) {
        return scenario.getConditions().entrySet().stream().allMatch((entry) -> {
            SensorStateAvro sensorState = sensorsState.get(entry.getKey());
            if (sensorState == null) {
                return false;
            }

            return switch (sensorState.getData()) {
                case ClimateSensorAvro climateSensor -> checkClimateSensor(climateSensor, entry.getValue());
                case LightSensorAvro lightSensor -> checkLightSensor(lightSensor, entry.getValue());
                case MotionSensorAvro motionSensor -> checkMotionSensor(motionSensor, entry.getValue());
                case SwitchSensorAvro switchSensor -> checkSwitchSensor(switchSensor, entry.getValue());
                case TemperatureSensorAvro temperatureSensor ->
                        checkTemperatureSensor(temperatureSensor, entry.getValue());
                default -> false;
            };
        });
    }

    private boolean checkClimateSensor(ClimateSensorAvro climateSensor, Condition condition) {
        return switch (condition.getType()) {
            case CO2LEVEL ->
                    handleConditionOperation(condition.getOperation(), condition.getValue(), climateSensor.getCo2Level());
            case HUMIDITY ->
                    handleConditionOperation(condition.getOperation(), condition.getValue(), climateSensor.getHumidity());
            case TEMPERATURE ->
                    handleConditionOperation(condition.getOperation(), condition.getValue(), climateSensor.getTemperatureC());
            case null, default -> false;
        };
    }

    private boolean checkLightSensor(LightSensorAvro lightSensor, Condition condition) {
        return switch (condition.getType()) {
            case LUMINOSITY ->
                    handleConditionOperation(condition.getOperation(), condition.getValue(), lightSensor.getLuminosity());
            case null, default -> false;
        };
    }

    private boolean checkMotionSensor(MotionSensorAvro motionSensor, Condition condition) {
        return switch (condition.getType()) {
            case MOTION ->
                    handleConditionOperation(condition.getOperation(), condition.getValue(), motionSensor.getMotion() ? 1 : 0);
            case null, default -> false;
        };
    }

    private boolean checkSwitchSensor(SwitchSensorAvro switchSensor, Condition condition) {
        return switch (condition.getType()) {
            case SWITCH ->
                    handleConditionOperation(condition.getOperation(), condition.getValue(), switchSensor.getState() ? 1 : 0);
            case null, default -> false;
        };
    }

    private boolean checkTemperatureSensor(TemperatureSensorAvro temperatureSensor, Condition condition) {
        return switch (condition.getType()) {
            case TEMPERATURE ->
                    handleConditionOperation(condition.getOperation(), condition.getValue(), temperatureSensor.getTemperatureC());
            case null, default -> false;
        };
    }

    private boolean handleConditionOperation(ConditionOperationAvro conditionOperation, Integer expectedValue, Integer actualValue) {
        return switch (conditionOperation) {
            case EQUALS -> actualValue.equals(expectedValue);
            case GREATER_THAN -> actualValue > expectedValue;
            case LOWER_THAN -> actualValue < expectedValue;
        };
    }
}