package com.em.service;

import com.em.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by guchong on 5/22/2018.
 */
public interface ProductRepository extends CrudRepository<Product, String> {

    Product findByProduct(String product);

    List<Product> findAll();

    Product save(Product product);

    void delete(Product product);
}
