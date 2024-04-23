package com.hw.tawareward.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "REWARDS")
@Getter
@Setter
public class Rewards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REWARDID")
    private Integer rewardId;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "ORDER_ID")
    private Integer orderId;

    @Column(name = "POINTS")
    private Integer points;

    @Column(name = "TIME_STAMP")
    private Date timeStamp;
}
