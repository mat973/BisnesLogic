package com.example.bisneslogic.config;



import com.example.bisneslogic.models.StringValue;
import com.example.bisneslogic.services.UserDetailsServiceImpl;
import com.fasterxml.jackson.core.JsonGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.juli.logging.Log;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {


    public final String topicName;

    private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);


    public KafkaConfig(@Value("${kafka.emailTopic}") String topicName) {
        this.topicName = topicName;
    }

    // Настройки для Producer

    @Bean
    public ObjectMapper objectMapper(){
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public ProducerFactory<String, StringValue> producerFactory(
            KafkaProperties kafkaProperties, ObjectMapper mapper){
        var props = kafkaProperties.buildProducerProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, StringValue>(props);
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(mapper));
        return kafkaProducerFactory;
    }

    @Bean
    public  KafkaTemplate<String, StringValue> kafkaTemplate(
            ProducerFactory<String, StringValue> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(topicName).partitions(1).replicas(1).build();
    }

    @Bean
    public DataSender dataSender(NewTopic topic, KafkaTemplate<String, StringValue> kafkaTemplate){
        return new DataSenderKafka(topic.name(),
                kafkaTemplate,
                stringValue -> logger.info("asked, value:" + stringValue));
    }

    @Bean
    public StringValueSourse stringValueSourse(DataSender dataSender){return new StringValueSourse(dataSender);}
}