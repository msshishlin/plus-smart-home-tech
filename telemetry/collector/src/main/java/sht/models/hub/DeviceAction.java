package sht.models.hub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Действие, которое должно быть выполнено устройством.
 */
@Getter
@Setter
@ToString
public final class DeviceAction {
    /**
     * Идентификатор датчика, связанного с действием.
     */
    private String sensorId;

    /**
     * Тип действия при срабатывании условия активации сценария.
     */
    private DeviceActionType type;

    /**
     * Необязательное значение, связанное с действием.
     */
    private Integer value;
}
