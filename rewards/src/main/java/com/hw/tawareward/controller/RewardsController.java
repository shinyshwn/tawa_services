package com.hw.tawareward.controller;

import com.hw.tawareward.model.Rewards;
import com.hw.tawareward.service.SingleOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class RewardsController {
    private final SingleOrderService rewardsService;
    Logger logger = LoggerFactory.getLogger("my logger");

    @Autowired
    public RewardsController(SingleOrderService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @GetMapping("/rewards")
    public List<Rewards> getRewards(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate time) {
        logger.info("rewards received");
        logger.info(time.toString());
        List<Rewards> res = rewardsService.getRewardsByTimeStamp(time);
        logger.info(String.valueOf(res.size()));
        return res;
    }


    @GetMapping("/rewards/by-user-month")
    public ResponseEntity<?> getRewardsByMonth(@RequestParam Integer id, @RequestParam Integer month) {
        return rewardsService.findRewardsByMonth(id, month);
    }

}
