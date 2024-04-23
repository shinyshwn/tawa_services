package com.hw.tawareward;

import com.hw.tawareward.model.Order;
import com.hw.tawareward.model.Rewards;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.hw.tawareward.dao.RewardsRepository;
import com.google.gson.Gson;
import com.hw.tawareward.service.SingleOrderService;


@Service
public class KafkaDBConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDBConsumer.class);

    @Autowired
    private RewardsRepository rewardsRepository;
    @Autowired
    private SingleOrderService singleOrderService;
    private Gson gson;

    public KafkaDBConsumer(RewardsRepository rewardsRepository) {
        this.rewardsRepository = rewardsRepository;
        this.gson = new Gson();
    }

    @KafkaListener(topics = "order",groupId = "99ranch")
    public void consume(String message) {
        LOGGER.info(String.format("Message received -> %s", message));

        Order order = gson.fromJson(message, Order.class);
        LOGGER.info(String.format("Received Order: " + order));

        Rewards rewards = new Rewards();
        rewards.setOrderId(order.getOrderId());
        rewards.setTimeStamp(order.getTimeStamp());
        rewards.setUserId(order.getUserId());
        // generate points for the current order
        Double orderPrice = order.getTotalPrice();
        Integer points = singleOrderService.generateRewards(orderPrice);
        LOGGER.info(String.format("Received Reward: " + order));
        rewards.setPoints(points);

        // update
        rewardsRepository.save(rewards);
    }
}
