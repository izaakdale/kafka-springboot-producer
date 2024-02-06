package com.appsdeveloperblog.ws.products.service;

import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.ws.core.ProductCreatedEvent;
import com.appsdeveloperblog.ws.products.rest.CreateProductRequestModel;

@Service
public class ProductServiceImpl implements ProductService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

  public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public String createProduct(CreateProductRequestModel productRestModel)
      throws Exception {

    String productID = UUID.randomUUID().toString();
    // persist data to some data here, before publishing event.

    ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productID,
        productRestModel.getTitle(), productRestModel.getPrice(), productRestModel.getQuantity());

    logger.info("publishing product created event");
    SendResult<String, ProductCreatedEvent> result = kafkaTemplate.send("product-created-events",
        productID, productCreatedEvent).get();

    logger.info("partition: " + result.getRecordMetadata().partition());
    logger.info("topic: " + result.getRecordMetadata().topic());

    logger.info("********** returning product ID");
    return productID;
  }
}
