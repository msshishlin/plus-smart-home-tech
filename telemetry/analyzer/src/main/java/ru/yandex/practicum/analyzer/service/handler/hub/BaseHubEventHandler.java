package ru.yandex.practicum.analyzer.service.handler.hub;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.service.handler.HubEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.lang.reflect.ParameterizedType;

/**
 * Обработчик данных события в хабе.
 */
@Component
@Getter
public abstract class BaseHubEventHandler<T> implements HubEventHandler {
    private final Class<T> hubEventType;

    protected BaseHubEventHandler() {
        this.hubEventType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Обработать данные события в хабе.
     *
     * @param hubEvent данные события в хабе.
     */
    public void handle(HubEventAvro hubEvent) {
        if (!this.hubEventType.isInstance(hubEvent.getPayload())) {
            throw new IllegalArgumentException("Неизвестный тип события: " + hubEvent.getPayload().getClass());
        }

        handle(hubEvent.getHubId(), (T) (hubEvent.getPayload()));
    }

    protected abstract void handle(String hubId, T hubEvent);
}
