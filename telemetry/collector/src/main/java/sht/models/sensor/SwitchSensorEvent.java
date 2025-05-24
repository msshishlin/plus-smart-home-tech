package sht.models.sensor;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие датчика переключателя, содержащее информацию о текущем состоянии переключателя.
 */
@Getter
@Setter
@ToString(callSuper = true)
public final class SwitchSensorEvent extends SensorEvent {
    /**
     * Текущее состояние переключателя.
     * <p>
     * true - включен, false - выключен.
     */
    @NotNull(message = "Текущее состояние переключателя не может быть равно null")
    private Boolean state;

    /**
     * Получить тип события датчика.
     *
     * @return тип события датчика.
     */
    @Override
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }
}
