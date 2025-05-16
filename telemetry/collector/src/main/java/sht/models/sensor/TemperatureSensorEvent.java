package sht.models.sensor;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие датчика температуры, содержащее информацию о температуре в градусах Цельсия и Фаренгейта.
 */
@Getter
@Setter
@ToString(callSuper = true)
public final class TemperatureSensorEvent extends SensorEvent {
    /**
     * Температура в градусах Цельсия.
     */
    @NotNull(message = "Температура в градусах Цельсия не может быть равна null")
    private Integer temperatureC;

    /**
     * Температура в градусах Фаренгейта.
     */
    @NotNull(message = "Температура в градусах Фаренгейта не может быть равна null")
    private Integer temperatureF;

    /**
     * Получить тип события датчика.
     *
     * @return тип события датчика.
     */
    @Override
    public SensorEventType getType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }
}
