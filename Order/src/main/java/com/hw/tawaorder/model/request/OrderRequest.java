package com.hw.tawaorder.model.request;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OrderRequest {
  private Integer userId;
  private Integer productId;
  private Integer quantity;
}
