package sht.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;

/**
 * Контракт клиента Kafka.
 */
public interface CollectorKafkaClient {
    /**
     * Получить издателя данных.
     *
     * @return издатель данных.
     */
    Producer<String, SpecificRecordBase> getProducer();

    /**
     * Остановить работу клиента Kafka.
     */
    void stop();
}
