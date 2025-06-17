package ru.yandex.practicum.aggregator.service.handler.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.aggregator.model.Action;
import ru.yandex.practicum.aggregator.model.Condition;
import ru.yandex.practicum.aggregator.model.Scenario;
import ru.yandex.practicum.aggregator.repository.ActionRepository;
import ru.yandex.practicum.aggregator.repository.ConditionRepository;
import ru.yandex.practicum.aggregator.repository.ScenarioRepository;
import ru.yandex.practicum.aggregator.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScenarioAddedEventHandler extends BaseHubEventHandler<ScenarioAddedEventAvro> {
    /**
     * Хранилище действий над устройствами.
     */
    private final ActionRepository actionRepository;

    /**
     * Хранилище условий для выполнения сценария.
     */
    private final ConditionRepository conditionRepository;

    /**
     * Хранилище сценариев.
     */
    private final ScenarioRepository scenarioRepository;

    /**
     * Хранилище датчиков.
     */
    private final SensorRepository sensorRepository;

    @Override
    protected void handle(String hubId, ScenarioAddedEventAvro hubEvent) {
        // Получаем идентификаторы датчиков, указанных в условиях срабатывания сценария, а также задействованных при срабатывании сценария.
        Set<String> deviceIds = new HashSet<>();
        deviceIds.addAll(hubEvent.getConditions().stream().map(ScenarioConditionAvro::getSensorId).collect(Collectors.toSet()));
        deviceIds.addAll(hubEvent.getActions().stream().map(DeviceActionAvro::getSensorId).collect(Collectors.toSet()));

        // Проверяем все ли датчики зарегистрированы в хабе.
        if (!sensorRepository.existsByIdInAndHubId(deviceIds, hubId)) {
            return;
        }

        // Сохраняем условия выполнения сценария.
        Map<String, Condition> conditions = new HashMap<>();
        hubEvent.getConditions().forEach(condition -> conditions.put(condition.getSensorId(), Condition.builder()
                .type(condition.getType())
                .operation(condition.getOperation())
                .value(switch (condition.getValue()) {
                    case Boolean booleanValue -> booleanValue ? 1 : 0;
                    case Integer integerValue -> integerValue;
                    case null, default -> null;
                })
                .build()));
        conditionRepository.saveAll(conditions.values());

        // Сохраняем набор действий сценария.
        Map<String, Action> actions = new HashMap<>();
        hubEvent.getActions().forEach(action -> actions.put(action.getSensorId(), Action.builder()
                .type(action.getType())
                .value(action.getValue())
                .build()));
        actionRepository.saveAll(actions.values());

        // Сохраняем сам сценарий.
        Scenario scenario = Scenario.builder()
                .hubId(hubId)
                .name(hubEvent.getName())
                .conditions(conditions)
                .actions(actions)
                .build();
        scenarioRepository.save(scenario);
    }
}