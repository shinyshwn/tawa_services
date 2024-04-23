package com.hw.tawaproduct.service;

import com.hw.tawaproduct.dao.productDAO;
import com.hw.tawaproduct.model.Product;
import com.hw.tawaproduct.model.ProductRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class productServiceImplement implements productService {

    private final productDAO productDAO;

    @Autowired
    public productServiceImplement(productDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public String createProduct(ProductRequest request) {
        Product product = new Product();
        BeanUtils.copyProperties(request, product);
        productDAO.save(product);
        return "product created successfully";
    }

    @Override
    public String updatePrice(Integer id, Double price) {
        Optional<Product> product = productDAO.findById(id);
        if (product.isPresent()) {
            product.get().setUnitPrice(price);
            productDAO.save(product.get());
            return "product's price updated successfully";
        } else {
            return "product not found";
        }
    }

    @Override
    public String updateQuantity(Integer id, Integer quantity) {
        Optional<Product> product = productDAO.findById(id);
        if (product.isPresent()) {
            Integer current_quantity = product.get().getQuantity();
            product.get().setQuantity(quantity);
            productDAO.save(product.get());
            return "product's quantity updated successfully";
        } else {
            return "product not found";
        }
    }

    @Override
    public void deleteProduct(Integer id) {
        productDAO.deleteById(id);
    }


    @Override
    public List<Product> getProducts() {
        return productDAO.findAll();
    }

    @Override
    public Product getProduct(Integer id) {
        Optional<Product> product = productDAO.findById(id);
        return product.orElse(null);
    }

    @Override
    public Page<Product> getProductsByPage(Integer index, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(index, pageSize);
        return productDAO.findAll(pageRequest);
    }
}