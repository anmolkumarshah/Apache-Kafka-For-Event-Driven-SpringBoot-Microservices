package com.appsdeveloperblog.ws.products;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    NewTopic createNewTopic(){
        return TopicBuilder.name("product-created-event-topic2")
                .partitions(3)
                .replicas(2)
                .configs(Map.of("min.insync.replicas","2"))
                .build();
    }

}
