package sht.models.sensor;

/**
 * Типы событий датчиков.
 */
public enum SensorEventType {
    /**
     * Событие датчика движения.
     */
    MOTION_SENSOR_EVENT,

    /**
     * Событие датчика температуры.
     */
    TEMPERATURE_SENSOR_EVENT,

    /**
     * Событие датчика освещенности.
     */
    LIGHT_SENSOR_EVENT,

    /**
     * Событие климатического датчика.
     */
    CLIMATE_SENSOR_EVENT,

    /**
     * Событие датчика переключателя.
     */
    SWITCH_SENSOR_EVENT
}
