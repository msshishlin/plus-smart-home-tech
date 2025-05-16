package sht.models.sensor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие датчика освещенности, содержащее информацию о качестве связи и уровне освещенности.
 */
@Getter
@Setter
@ToString(callSuper = true)
public final class LightSensorEvent extends SensorEvent {
    /**
     * Качество связи.
     */
    private Integer linkQuality;

    /**
     * Уровень освещенности.
     */
    private Integer luminosity;

    /**
     * Получить тип события датчика.
     *
     * @return тип события датчика.
     */
    @Override
    public SensorEventType getType() {
        return SensorEventType.LIGHT_SENSOR_EVENT;
    }
}
