package com.hw.tawaproduct.service;

import com.hw.tawaproduct.model.Product;
import com.hw.tawaproduct.model.ProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface productService {
    String createProduct(ProductRequest request);
    String updatePrice(Integer id, Double price);
    void deleteProduct(Integer id);
    String updateQuantity(Integer id, Integer quantity);
    List<Product> getProducts();
    Product getProduct(Integer id);
    Page<Product> getProductsByPage(Integer index, Integer pageSize);
}