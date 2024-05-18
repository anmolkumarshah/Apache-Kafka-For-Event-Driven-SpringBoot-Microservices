package com.appsdeveloperblog.ws.products.service;

import com.appsdeveloperblog.ws.core.ProductCreatedEvent;
import com.appsdeveloperblog.ws.products.model.CreateProductRestModel;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements  ProductService {

    @Autowired
    KafkaTemplate kafkaTemplate;


    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public String createProduct(CreateProductRestModel restModel) throws Exception {

        String productId = UUID.randomUUID().toString();

        //TODO: persist product details

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,
                restModel.getTitle(),restModel.getPrice(), restModel.getQuantity());

//        CompletableFuture<SendResult<String,ProductCreatedEvent>> future =
//                kafkaTemplate.send("product-created-event-topic", productId, productCreatedEvent);

        /*future.whenComplete((result, exception) -> {
            if(exception != null){
                LOGGER.error("**********Error in saving the event");
            }else{
                LOGGER.info("***********Product event saved successfully "+productId);
            }
        });*/

//        future.join();

        LOGGER.info("********* Before Sending event");
        ProducerRecord<String,ProductCreatedEvent> producerRecord = new ProducerRecord<>(
                "product-created-event-topic2", productId, productCreatedEvent
        );
        producerRecord.headers().add("messageId",UUID.randomUUID().toString().getBytes());
        // for testing
//        producerRecord.headers().add("messageId","123".getBytes());


        SendResult<String,ProductCreatedEvent> result =
                  (SendResult<String, ProductCreatedEvent>) kafkaTemplate.send(producerRecord).get();

        LOGGER.info("Partition "+result.getRecordMetadata().partition());
        LOGGER.info("Topic "+result.getRecordMetadata().topic());
        LOGGER.info("Offset "+result.getRecordMetadata().offset());

        LOGGER.info("*********** Returning Product Id");
        return productId;
    }
}
