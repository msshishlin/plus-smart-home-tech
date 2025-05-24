package sht.models.hub;

/**
 * Типы действий при срабатывании условия активации сценария.
 */
public enum DeviceActionType {
    /**
     * Активация.
     */
    ACTIVATE,

    /**
     * Деактивация.
     */
    DEACTIVATE,

    /**
     * Инверсия значения.
     */
    INVERSE,

    /**
     * Установка значения.
     */
    SET_VALUE
}
