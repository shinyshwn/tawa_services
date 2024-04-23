package com.hw.tawaorder.service;


import com.hw.tawaorder.model.Order;
import com.hw.tawaorder.model.request.OrderRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

  Integer createOrder(OrderRequest orderRequest);
  void deleteById(Integer id);

  Order getById(Integer id);
  List<Order> getAll();
  Page<Order> getByPage(Integer userId, Integer pageNumber, Integer pageSize,List<String> sorts);

}
