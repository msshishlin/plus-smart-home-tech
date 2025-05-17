package sht.models.hub;

/**
 * Типы условий, которые могут использоваться в сценариях.
 */
public enum ScenarioConditionType {
    /**
     * Движение.
     */
    MOTION,

    /**
     * Освещенность.
     */
    LUMINOSITY,

    /**
     * Переключение.
     */
    SWITCH,

    /**
     * Температура.
     */
    TEMPERATURE,

    /**
     * Уровень углекислого газа.
     */
    CO2LEVEL,

    /**
     * Влажность.
     */
    HUMIDITY
}
