package sht.models.hub;

/**
 * Типы устройств, которые могут быть добавлены в систему.
 */
public enum DeviceType {
    /**
     * Климатический датчик.
     */
    CLIMATE_SENSOR,

    /**
     * Датчик освещенности.
     */
    LIGHT_SENSOR,

    /**
     * Датчик движения.
     */
    MOTION_SENSOR,

    /**
     * Переключатель.
     */
    SWITCH_SENSOR,

    /**
     * Датчик температуры.
     */
    TEMPERATURE_SENSOR
}
