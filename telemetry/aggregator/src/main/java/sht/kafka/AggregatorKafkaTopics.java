package sht.kafka;

public class AggregatorKafkaTopics {
    /**
     * Данные, связанные с показаниями датчиков событиями устройств.
     */
    public final static String TELEMETRY_SENSORS_V1 = "telemetry.sensors.v1";

    /**
     * Данные, связанные со снэпшотами состояний датчиков.
     */
    public final static String TELEMETRY_SNAPSHOTS_V1 = "telemetry.snapshots.v1";
}
