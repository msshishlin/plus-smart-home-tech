package sht.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.Producer;

public interface AggregatorKafkaClient {
    /**
     * Получить потребителя данных.
     *
     * @return потребитель данных.
     */
    Consumer<String, SpecificRecordBase> getConsumer();

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
