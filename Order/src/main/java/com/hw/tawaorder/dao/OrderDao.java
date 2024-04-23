package com.hw.tawaorder.dao;

import com.hw.tawaorder.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<Order,Integer> {

  @Query("select o from Order o where o.userId =?1")
  Page<Order> findByPage(Integer userId, Pageable pageable);

}
