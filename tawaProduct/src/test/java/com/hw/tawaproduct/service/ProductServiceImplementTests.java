package com.hw.tawaproduct.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hw.tawaproduct.dao.productDAO;
import com.hw.tawaproduct.model.Product;
import com.hw.tawaproduct.model.ProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplementTests {

    @Mock
    private productDAO productDAO;

    @InjectMocks
    private productServiceImplement productService;

    private Product product;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setName("Widget");
        product.setDescription("A widget");
        product.setUnitPrice(19.99);
        product.setQuantity(5);
        product.setProductId(1);

        productRequest = new ProductRequest();
        // Mimic the behavior of copying properties from productRequest to product
    }

    // Tests successful creation of a product using a product request
    @Test
    void createProduct_Success() {
        when(productDAO.save(any(Product.class))).thenReturn(product);
        String response = productService.createProduct(productRequest);
        assertEquals("product created successfully", response);
        verify(productDAO).save(any(Product.class));
    }

    // Tests successful updating of product price when product exists
    @Test
    void updatePrice_ProductExists() {
        when(productDAO.findById(anyInt())).thenReturn(Optional.of(product));
        String response = productService.updatePrice(product.getProductId(), 29.99);
        assertEquals("product's price updated successfully", response);
        verify(productDAO).save(product);
    }

    // Tests attempt to update price of a non-existent product
    @Test
    void updatePrice_ProductNotFound() {
        when(productDAO.findById(anyInt())).thenReturn(Optional.empty());
        String response = productService.updatePrice(2, 29.99);
        assertEquals("product not found", response);
        verify(productDAO, never()).save(any(Product.class));
    }

    // Tests successful updating of product quantity when product exists
    @Test
    void updateQuantity_ProductExists() {
        when(productDAO.findById(anyInt())).thenReturn(Optional.of(product));
        String response = productService.updateQuantity(product.getProductId(), 10);
        assertEquals("product's quantity updated successfully", response);
        verify(productDAO).save(product);
    }

    // Tests attempt to update quantity of a non-existent product
    @Test
    void updateQuantity_ProductNotFound() {
        when(productDAO.findById(anyInt())).thenReturn(Optional.empty());
        String response = productService.updateQuantity(2, 10);
        assertEquals("product not found", response);
        verify(productDAO, never()).save(any(Product.class));
    }

    // Tests deletion of a product by ID
    @Test
    void deleteProduct_Success() {
        doNothing().when(productDAO).deleteById(anyInt());
        productService.deleteProduct(product.getProductId());
        verify(productDAO).deleteById(product.getProductId());
    }

    // Tests retrieval of all products, expecting a non-empty list
    @Test
    void getProducts_Success() {
        when(productDAO.findAll()).thenReturn(Arrays.asList(new Product(), new Product()));
        List<Product> products = productService.getProducts();
        assertNotNull(products);
        assertEquals(2, products.size());
    }

    // Tests successful retrieval of a specific product by ID
    @Test
    void getProduct_Found() {
        when(productDAO.findById(anyInt())).thenReturn(Optional.of(new Product()));
        Product product = productService.getProduct(1);
        assertNotNull(product);
    }

    // Tests attempt to retrieve a non-existent product by ID
    @Test
    void getProduct_NotFound() {
        when(productDAO.findById(anyInt())).thenReturn(Optional.empty());
        Product product = productService.getProduct(1);
        assertNull(product);
    }

    // Tests pagination functionality to retrieve a specific page of products
    @Test
    void getProductsByPage_Success() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Product> pagedResponse = new PageImpl<>(Arrays.asList(new Product(), new Product()));
        when(productDAO.findAll(pageable)).thenReturn(pagedResponse);
        Page<Product> productsPage = productService.getProductsByPage(0, 1);
        assertNotNull(productsPage);
        assertEquals(2, productsPage.getTotalElements());
    }
}
