package ru.yandex.practicum.aggregator.service.handler.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.aggregator.repository.ScenarioRepository;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@Component
@RequiredArgsConstructor
public class ScenarioRemovedEventHandler extends BaseHubEventHandler<ScenarioRemovedEventAvro> {
    /**
     * Хранилище сценариев.
     */
    private final ScenarioRepository scenarioRepository;

    @Override
    protected void handle(String hubId, ScenarioRemovedEventAvro hubEvent) {
        scenarioRepository.findByNameAndHubId(hubId, hubEvent.getName()).ifPresent(scenarioRepository::delete);
    }
}
