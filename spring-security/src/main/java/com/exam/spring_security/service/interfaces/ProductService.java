package com.exam.spring_security.service.interfaces;

import com.exam.spring_security.model.Product;

import java.util.List;

public interface ProductService {
    void delete(Long id);
    Product get(Long id);
    Product save(Product product);
    List<Product> getAll();
}
