package com.hw.tawaorder.service;

import com.hw.tawaorder.model.Order;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class KafkaService {


  Logger logger = LoggerFactory.getLogger("my logger");
  @Autowired
  private KafkaTemplate kafkaTemplate;

  public void addOrder(Order order){
    Gson gson = new Gson();
    String json = gson.toJson(order);
    logger.info("message send");
    kafkaTemplate.send("order",json);
  }

}
