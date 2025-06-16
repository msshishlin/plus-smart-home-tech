package ru.yandex.practicum.collector.configuration;

/**
 * Топик Kafka.
 */
public enum KafkaTopic {
    /**
     * Топик, содержащий данные о событиях, связанных с хабами.
     */
    HUBS_EVENTS,

    /**
     * Топик, содержащий данные о событиях, связанных с датчиками.
     */
    SENSORS_EVENTS;

    /**
     * Проверить содержит ли перечисление полученное значение.
     *
     * @param value значение.
     * @return признак содержит ли перечисление полученное значение.
     */
    public static boolean contains(String value) {
        for (KafkaTopic kafkaTopic : values()) {
            if (kafkaTopic.name().equals(value)) {
                return true;
            }
        }

        return false;
    }
}
