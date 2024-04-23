package com.hw.tawareward;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.hw.tawareward.dao.RewardsRepository;
import com.hw.tawareward.service.SingleOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TawarewardsApplicationTests {

	@Mock
	private RewardsRepository repository;

	@InjectMocks
	private SingleOrderService singleOrderService;

	//Test when price is at or below 50, rewards should be 0
	@Test
	public void whenPriceIsAtOrBelow50_thenRewardsShouldBeZero() {

		Double price = 50.0;
		Integer rewards = singleOrderService.generateRewards(price);
		assertEquals(0, rewards);
	}

	//Test when price is above 50 and below 100, rewards should be price-50
	@Test
	public void whenPriceIsAbove50AndAtOrBelow100_thenRewardsShouldBePriceMinus50() {

		Double price = 75.0;
		Integer rewards = singleOrderService.generateRewards(price);
		assertEquals(25, rewards);
	}

	//Test when price is above 100, rewards should be double for the part over 100 then plus 50
	@Test
	public void whenPriceIsAbove100_thenRewardsShouldBeDoubleOver100Plus50() {

		Double price = 150.0;
		Integer rewards = singleOrderService.generateRewards(price);
		assertEquals(150, rewards);
	}
}
