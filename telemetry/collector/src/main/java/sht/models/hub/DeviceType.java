package sht.models.hub;

/**
 * Типы устройств, которые могут быть добавлены в систему.
 */
public enum DeviceType {
    /**
     * Датчик движения.
     */
    MOTION_SENSOR,

    /**
     * Датчик температуры.
     */
    TEMPERATURE_SENSOR,

    /**
     * Датчик освещенности.
     */
    LIGHT_SENSOR,

    /**
     * Климатический датчик.
     */
    CLIMATE_SENSOR,

    /**
     * Переключатель.
     */
    SWITCH_SENSOR
}
