package com.hw.tawaproduct.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequest {
    private String name;

    private String description;

    private Double unitPrice;

    private Integer quantity;
}