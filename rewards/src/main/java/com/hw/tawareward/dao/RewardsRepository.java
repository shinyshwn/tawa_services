package com.hw.tawareward.dao;

import com.hw.tawareward.model.Rewards;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface RewardsRepository extends JpaRepository<Rewards, Integer> {

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private java.lang.Integer rewardId;

    private java.lang.Integer userId;

    private java.lang.Integer orderId;

    private java.lang.Integer points;

    @Column(name = "timestamp")
    private Date timeStamp;
    */
    // Above is the DAO of reward
    // Write the two queries below for me

    // Qeury 1:
    // Find by Month + ID
    // SUM of rewardPoints for past 1 month from TABLE group by UserId
    // Just Return an Integer

    // Put SQL query here

    @Query("SELECT r FROM Rewards r WHERE r.timeStamp >= :timestamp")
    List<Rewards> findByTimestampAfter(LocalDateTime timestamp);


    @Query("")
    List<Rewards> findByUserId(Integer ID);

}