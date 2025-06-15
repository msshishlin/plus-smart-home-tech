package sht.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Properties;
import java.util.UUID;

/**
 * Конфигурация клиента Kafka.
 */
@Configuration
public class AggregatorKafkaClientConfiguration {
    /**
     * Получить клиента Kafka.
     *
     * @return клиент Kafka.
     */
    @Bean
    @Scope("prototype")
    AggregatorKafkaClient getClient() {
        return new AggregatorKafkaClient() {
            /**
             * Потребитель данных.
             */
            private Consumer<String, SpecificRecordBase> consumer;

            /**
             * Издатель данных.
             */
            private Producer<String, SpecificRecordBase> producer;

            /**
             * Получить потребителя данных.
             *
             * @return потребиль данных.
             */
            @Override
            public Consumer<String, SpecificRecordBase> getConsumer() {
                if (consumer == null) {
                    initConsumer();
                }

                return consumer;
            }

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
            private void initConsumer() {
                Properties config = new Properties();
                config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
                config.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
                config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
                config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AggregatorKafkaAvroDeserializer.class);

                consumer = new KafkaConsumer<>(config);
                Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));
            }

            /**
             * Инициировать издателя данных.
             */
            private void initProducer() {
                Properties config = new Properties();
                config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
                config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
                config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AggregatorKafkaAvroSerializer.class);

                producer = new KafkaProducer<>(config);
            }

            @Override
            public void stop() {
                if (consumer != null) {
                    try {
                        consumer.commitSync();
                    } finally {
                        consumer.close();
                    }
                }

                if (producer != null) {
                    try {
                        producer.flush();
                    } finally {
                        producer.close();
                    }
                }
            }
        };
    }
}
