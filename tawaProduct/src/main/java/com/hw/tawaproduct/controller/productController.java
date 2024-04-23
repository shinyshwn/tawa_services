package com.hw.tawaproduct.controller;
import com.hw.tawaproduct.model.Product;
import com.hw.tawaproduct.model.ProductRequest;
import com.hw.tawaproduct.service.productService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class productController {
    private final productService service;

    @Autowired
    public productController(productService service) {
        this.service = service;
    }

    @PostMapping(value = "/create")
    public String create(@RequestBody ProductRequest request) {
        return service.createProduct(request);
    }

    @PutMapping(value = "/quantity/{id}")
    public String updateQuantity(@PathVariable("id") Integer id, @RequestParam("quantity") Integer quantity) {
        return service.updateQuantity(id, quantity);
    }

    @PutMapping(value = "/price/{id}")
    public String updatePrice(@PathVariable("id") Integer id, @RequestParam("price") Double price) {
        return service.updatePrice(id, price);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        service.deleteProduct(id);
    }

    @GetMapping(value = "/{id}")
    public Product findById(@PathVariable("id") Integer id) {
        return service.getProduct(id);
    }

    @GetMapping(value = "/all")
    public List<Product> findAll() {
        return service.getProducts();
    }

    @GetMapping(value = "/page")
    public Page<Product> findByPage(@RequestParam Integer index, @RequestParam Integer size) {
        return service.getProductsByPage(index, size);
    }
}
