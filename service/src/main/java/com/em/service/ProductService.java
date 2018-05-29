package com.em.service;

import com.em.model.Product;

import java.util.List;

/**
 * Created by guchong on 5/22/2018.
 */
public interface ProductService {
    Product findByProduct(String product);

    List<Product> findAll();

    Product save(Product product);

    void delete(Product product);
}
