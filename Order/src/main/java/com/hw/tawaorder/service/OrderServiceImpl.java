package com.hw.tawaorder.service;

import com.hw.tawaorder.dao.OrderDao;
import com.hw.tawaorder.model.Order;
import com.hw.tawaorder.model.Product;
import com.hw.tawaorder.model.request.OrderRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

  @Autowired
  private OrderDao orderDao;

  @Autowired
  private CheckInventoryService checkInventoryService;

  @Autowired
  KafkaService kafkaService;

  @Override
  public Integer createOrder(OrderRequest orderRequest) {
    Order order =  new Order();
    BeanUtils.copyProperties(orderRequest,order);
    Logger logger = LoggerFactory.getLogger("my logger");
//    System.out.println("order1: "+order);
    try {
      Product product=  checkInventoryService.checkInventory(orderRequest.getProductId());
//      System.out.println(product);
      if(orderRequest.getQuantity()<=product.getQuantity()){
        order.setTotalPrice(product.getUnitPrice()*orderRequest.getQuantity());
        order.setQuantity(order.getQuantity());
        order.setUnitPrice(product.getUnitPrice());
        order.setTimeStamp(new Date());
//        System.out.println("order2: "+order);
        orderDao.save(order);
        checkInventoryService.updateInventory(orderRequest.getProductId(),product.getQuantity()-order.getQuantity());
        logger.info(order.toString());
        kafkaService.addOrder(order);
      }else{
        throw new RuntimeException("Inventory shortage.");
      }

    }catch (Exception e){
      throw new RuntimeException(e.getMessage(), e);
    }

    return order.getUserId();
  }



  @Override
  public void deleteById(Integer id) {
    orderDao.deleteById(id);
  }

  @Override
  public Order getById(Integer id) {
    Optional<Order> optionalOrder  = orderDao.findById(id);
    return optionalOrder.orElse(null);
  }

  @Override
  public List<Order> getAll() {
    return orderDao.findAll();
  }

  @Override
  public Page<Order> getByPage(Integer userId, Integer pageNumber, Integer pageSize, List<String> sorts) {
    if(sorts != null && !sorts.isEmpty()){
      List<Sort.Order> orders = sorts.stream().map(s->Sort.Order.by(s)).collect(Collectors.toList());
      PageRequest pageRequest = PageRequest.of(pageNumber,pageSize, Sort.by(orders));
      return orderDao.findAll(pageRequest);
    }
    PageRequest pageRequest = PageRequest.of(pageNumber,pageSize);
    return orderDao.findAll(pageRequest);
  }
}
