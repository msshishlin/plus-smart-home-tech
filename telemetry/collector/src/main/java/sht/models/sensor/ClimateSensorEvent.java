package sht.models.sensor;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие климатического датчика, содержащее информацию о температуре, влажности и уровне CO2.
 */
@Getter
@Setter
@ToString(callSuper = true)
public final class ClimateSensorEvent extends SensorEvent {
    /**
     * Уровень температуры по шкале Цельсия.
     */
    @NotNull(message = "Уровень температуры по шкале Цельсия не может быть равным null")
    private Integer temperatureC;

    /**
     * Влажность.
     */
    @NotNull(message = "Влажность не может быть равным null")
    private Integer humidity;

    /**
     * Уровень CO2.
     */
    @NotNull(message = "Уровень углекислого газа не может быть равным null")
    private Integer co2Level;

    /**
     * Получить тип события датчика.
     *
     * @return тип события датчика.
     */
    @Override
    public SensorEventType getType() {
        return SensorEventType.CLIMATE_SENSOR_EVENT;
    }
}
