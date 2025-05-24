package sht.models.hub;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие, сигнализирующее об удалении устройства из системы.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class DeviceRemovedEvent extends HubEvent {
    /**
     * Идентификатор удаленного устройства.
     */
    @NotEmpty(message = "Идентификатор удаленного устройства не может быть пустым")
    private String id;

    /**
     * Получить тип события, происходящего в хабе.
     *
     * @return тип события, происходящего в хабе.
     */
    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED;
    }
}
