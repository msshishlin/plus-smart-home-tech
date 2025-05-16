package sht.models.hub;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие, сигнализирующее о добавлении нового устройства в систему.
 */
@Getter
@Setter
@ToString(callSuper = true)
public final class DeviceAddedEvent extends HubEvent {
    /**
     * Идентификатор добавленного устройства.
     */
    @NotEmpty(message = "Идентификатор добавленного устройства не может быть пустым")
    private String id;

    /**
     * Тип устройства.
     */
    @NotNull(message = "Тип устройства не может быть равен null")
    private DeviceType deviceType;

    /**
     * Получить тип события, происходящего в хабе.
     *
     * @return тип события, происходящего в хабе.
     */
    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}
