package sht.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Properties;

/**
 * Конфигурация клиента Kafka.
 */
@Configuration
public class CollectorKafkaClientConfiguration {
    /**
     * Получить клиента Kafka.
     *
     * @return клиент Kafka.
     */
    @Bean
    @Scope("prototype")
    CollectorKafkaClient getClient() {
        return new CollectorKafkaClient() {
            /**
             * Издатель данных.
             */
            private Producer<String, SpecificRecordBase> producer;

            /**
             * Получить издателя данных.
             *
             * @return издатель данных.
             */
            @Override
            public Producer<String, SpecificRecordBase> getProducer() {
                if (producer == null) {
                    initProducer();
                }

                return producer;
            }

            /**
             * Инициировать издателя данных.
             */
            private void initProducer() {
                Properties config = new Properties();
                config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
                config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
                config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CollectorKafkaAvroSerializer.class);

                producer = new KafkaProducer<>(config);
            }

            @Override
            public void stop() {
                if (producer != null) {
                    producer.close();
                }
            }
        };
    }
}
