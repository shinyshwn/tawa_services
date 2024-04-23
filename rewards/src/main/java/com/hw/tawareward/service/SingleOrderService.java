package com.hw.tawareward.service;

import com.hw.tawareward.dao.RewardsRepository;
import com.hw.tawareward.model.Rewards;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import java.time.*;
import java.util.*;


@Service
public class SingleOrderService {

    @Autowired
    private RewardsRepository repository;
    public Integer generateRewards(Double price) {
        Integer rewards = 0;
        if (price <= 50) {
            return 0;
        } else if (price > 50 && price <= 100) {
            rewards = (int) (price - 50);
        } else {
            rewards = (int)(price - 100) * 2 + 50;
        }
        return rewards;
    }


    public List<Rewards> getRewardsByTimeStamp(LocalDate timestamp) {
        return repository.findByTimestampAfter(timestamp.atStartOfDay());
    }


    public ResponseEntity<?> findRewardsByMonth(Integer userId, Integer month) {
        try {
            List<Rewards> rewardsForUser = repository.findByUserId(userId);

            if (rewardsForUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            if (month == 0) {
                int totalPoints = rewardsForUser.stream().mapToInt(Rewards::getPoints).sum();
                return ResponseEntity.ok(totalPoints);
            }

            int rewardsPerMonth = rewardsForUser.stream()
                    .filter(reward -> {
                        LocalDate localDate = reward.getTimeStamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        return localDate.getMonthValue() == month;
                    })
                    .mapToInt(Rewards::getPoints)
                    .sum();

            return ResponseEntity.ok(rewardsPerMonth);

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred while processing your request.");
        }
    }


}
