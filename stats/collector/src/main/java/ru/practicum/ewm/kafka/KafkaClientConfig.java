package ru.practicum.ewm.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.Instant;
import java.util.Properties;
import java.util.concurrent.Future;

@Slf4j
@Configuration
public class KafkaClientConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka.producer.properties")
    public Properties kafkaProducerProperties() {
        return new Properties();
    }

    @Bean
    Producer<Long, SpecificRecordBase> kafkaProducer(Properties kafkaProducerProperties) {
        log.info("Создаем для kafka {}", Producer.class.getSimpleName());
        return new KafkaProducer<>(kafkaProducerProperties);
    }

    @Bean
    KafkaClient getKafkaClient(Producer<Long, SpecificRecordBase> kafkaProducer) {
        return new KafkaClient() {

            @Override
            public void send(String topic, Instant timestamp, Long eventId, SpecificRecordBase event) {
                ProducerRecord<Long, SpecificRecordBase> record =
                        new ProducerRecord<>(topic, null, timestamp.toEpochMilli(), eventId, event);
                log.info("Отправляем в kafka-topic {} сообщение: {}", topic, event);
                Future<RecordMetadata> recordMetadataFuture = kafkaProducer.send(record);
            }

            @Override
            public void close() {
                kafkaProducer.flush();
                log.info("Закрываем {}", Producer.class.getSimpleName());
                kafkaProducer.close(Duration.ofSeconds(10));
            }
        };
    }
}
