package sht.models.sensor;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие датчика движения.
 */
@Getter
@Setter
@ToString(callSuper = true)
public final class MotionSensorEvent extends SensorEvent {
    /**
     * Качество связи.
     */
    @NotNull(message = "Качество связи не может быть равно null")
    private Integer linkQuality;

    /**
     * Наличие/отсутствие движения.
     */
    @NotNull(message = "Наличие/отсутствие движения не может быть равно null")
    private Boolean motion;

    /**
     * Напряжение.
     */
    @NotNull(message = "Напряжение не может быть равно null")
    private Integer voltage;

    /**
     * Получить тип события датчика.
     *
     * @return тип события датчика.
     */
    @Override
    public SensorEventType getType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }
}
