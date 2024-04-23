package com.hw.tawaorder;

import static org.mockito.ArgumentMatchers.eq;
import com.google.gson.Gson;
import com.hw.tawaorder.model.Order;
import com.hw.tawaorder.service.KafkaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TawaorderApplicationTests {

	@MockBean
	private KafkaTemplate<String, String> kafkaTemplate;

	@InjectMocks
	private KafkaService realKafkaService = new KafkaService();


	// Verifies if an order is correctly serialized and sent to Kafka topic
	@Test
	public void whenAddOrderIsCalled_thenKafkaTemplateShouldSendOrder() {

		Order mockOrder = new Order();
		Gson gson = new Gson();
		String json = gson.toJson(mockOrder);
		realKafkaService.addOrder(mockOrder);
		Mockito.verify(kafkaTemplate).send(eq("order"), eq(json));
	}

}