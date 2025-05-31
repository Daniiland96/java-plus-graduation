package ru.practicum.ewm;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.kafka.KafkaClient;
import ru.practicum.ewm.service.AggregatorService;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AggregationStarter {

    private final Producer<Long, SpecificRecordBase> producer;
    private final Consumer<Long, SpecificRecordBase> consumer;
    private final AggregatorService aggregatorService;

    @Value("${kafka.topics.user-actions}")
    private String userActionsTopic;
    @Value("${kafka.topics.events-similarity}")
    private String eventsSimilarityTopic;
    private static final Duration CONSUME_ATTEMPT_TIMEOUT = Duration.ofMillis(1000);

    private static final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    public AggregationStarter(KafkaClient kafkaClient, AggregatorService aggregatorService) {
        this.producer = kafkaClient.getProducer();
        this.consumer = kafkaClient.getConsumer();
        this.aggregatorService = aggregatorService;
    }

    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));
        try {
            consumer.subscribe(List.of(userActionsTopic));
            while (true) {
                ConsumerRecords<Long, SpecificRecordBase> records = consumer.poll(CONSUME_ATTEMPT_TIMEOUT);
                if (!records.isEmpty()) {
                    int count = 0;
                    for (ConsumerRecord<Long, SpecificRecordBase> record : records) {
                        log.info("{}: Передаем сообщение для агрегации", AggregationStarter.class.getSimpleName());

                        List<EventSimilarityAvro> eventsSimilarity = aggregatorService.aggregationUserAction(record.value());
                        if (!eventsSimilarity.isEmpty()) {
                            sendInProducer(eventsSimilarity);
                        }

                        manageOffsets(record, count, consumer);
                        count++;
                    }
                    consumer.commitAsync();
                }
            }
        } catch (WakeupException ignored) {

        } catch (Exception e) {
            log.error("{}: Ошибка во время обработки событий от датчиков", AggregationStarter.class.getSimpleName(), e);
        } finally {
            try {
                producer.flush();
                consumer.commitSync();
            } finally {
                log.info("{}: Закрываем консьюмер", AggregationStarter.class.getSimpleName());
                consumer.close();
                log.info("{}: Закрываем продюсер", AggregationStarter.class.getSimpleName());
                producer.close(Duration.ofSeconds(5));
            }
        }
    }

    private void sendInProducer(List<EventSimilarityAvro> eventsSimilarity) {
        for (EventSimilarityAvro sim : eventsSimilarity) {
            log.info("Передаем через kafka-producer сообщение: {}", sim);
            producer.send(new ProducerRecord<>(
                    eventsSimilarityTopic,
                    null,
                    sim.getTimestamp().toEpochMilli(),
                    sim.getEventA(),
                    sim
            ));
        }
    }

    private static void manageOffsets(
            ConsumerRecord<Long, SpecificRecordBase> record,
            int count,
            Consumer<Long, SpecificRecordBase> consumer
    ) {
        // обновляем текущий оффсет для топика-партиции
        currentOffsets.put(
                new TopicPartition(record.topic(), record.partition()),
                new OffsetAndMetadata(record.offset() + 1)
        );

        if (count % 10 == 0) {
            consumer.commitAsync(currentOffsets, (offsets, exception) -> {
                if (exception != null) {
                    log.warn("Ошибка во время фиксации оффсетов: {}", offsets, exception);
                }
            });
        }
    }
}
