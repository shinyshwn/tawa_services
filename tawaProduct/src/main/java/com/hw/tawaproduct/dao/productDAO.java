package com.hw.tawaproduct.dao;

import com.hw.tawaproduct.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productDAO extends JpaRepository<Product, Integer> {

}