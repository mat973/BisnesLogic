package com.example.bisneslogic.config;



import com.example.bisneslogic.models.StringValue;
import com.example.bisneslogic.models.StringValueSource;
import com.example.bisneslogic.services.*;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;


import static org.springframework.kafka.support.serializer.JsonDeserializer.TYPE_MAPPINGS;

@Configuration
@EnableKafka
public class KafkaConfig {


    @Autowired
    private EmailService emailService;
    public final String topicName;

    private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);


    public KafkaConfig(@Value("${kafka.emailTopic}") String topicName) {
        this.topicName = topicName;
    }

    // Настройки для Producer

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public ProducerFactory<String, StringValue> producerFactory(
            KafkaProperties kafkaProperties, ObjectMapper mapper) {
        var props = kafkaProperties.buildProducerProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, StringValue>(props);
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(mapper));
        return kafkaProducerFactory;
    }


    @Bean
    public KafkaTemplate<String, StringValue> kafkaTemplate(
            ProducerFactory<String, StringValue> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topicName).partitions(1).replicas(1).build();
    }

    @Bean
    public DataSender dataSender(NewTopic topic, KafkaTemplate<String, StringValue> kafkaTemplate) {
        return new DataSenderKafka(topic.name(),
                kafkaTemplate,
                stringValue -> logger.info("asked, value:" + stringValue));
    }

    @Bean
    public StringValueSource stringValueSourse(DataSender dataSender) {
        return new StringValueSource(dataSender);
    }


    @Bean
    public ConsumerFactory<String, StringValue> consumerFactory(
            KafkaProperties kafkaProperties, ObjectMapper mapper) {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(TYPE_MAPPINGS, "com.example.bisneslogic.models.StringValue:com.example.bisneslogic.models.StringValue");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 20_000);

        var kafkaConsumerFactory = new DefaultKafkaConsumerFactory<String, StringValue>(props);
        kafkaConsumerFactory.setValueDeserializer(new JsonDeserializer<>(mapper));
        return kafkaConsumerFactory;
    }

    @Bean("listenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, StringValue>>
    listenerContainerFactory(ConsumerFactory<String, StringValue> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, StringValue>();
        factory.setConsumerFactory(consumerFactory);
//        factory.setBatchListener(true);
        factory.setConcurrency(1);
        factory.getContainerProperties().setIdleBetweenPolls(1_000);
        factory.getContainerProperties().setPollTimeout(1_000);

        var executor = new SimpleAsyncTaskExecutor("k-consumer-");
        executor.setConcurrencyLimit(10);
        var listenerTaskExecutor = new ConcurrentTaskExecutor(executor);
        factory.getContainerProperties().setListenerTaskExecutor(listenerTaskExecutor);
        return factory;
    }
//    @Bean
//    public NewTopic topic() {
//        return TopicBuilder.name(topicName).partitions(1).replicas(1).build();
//    }

    @Bean
    public StringValueConsumer stringValueConsumerLogger() {
        return new StringValueConsumerLogger(emailService);
    }

    @Bean
    public KafkaClient stringValueConsumer(StringValueConsumer stringValueConsumer) {
        return new KafkaClient(stringValueConsumer);
    }

    public static class KafkaClient {
        private final StringValueConsumer stringValueConsumer;

        public KafkaClient(StringValueConsumer stringValueConsumer) {
            this.stringValueConsumer = stringValueConsumer;
        }

        @KafkaListener(
                topics = "${kafka.emailTopic}",
                containerFactory = "listenerContainerFactory")
        public void listen(@Payload StringValue values) {
            logger.info("values, values.size:" + values);
            stringValueConsumer.accept(values);
        }
    }

}
//gepite8027@otemdi.com