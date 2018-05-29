package com.em.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guchong on 5/25/2018.
 */
public class StrategyConfiguration implements Serializable{
    private Risk global;
    private List<Product> products;

    public StrategyConfiguration(Risk global, List<Product> products) {
        this.global = global;
        this.products = products;
    }

    public Risk getGlobal() {
        return global;
    }

    public void setGlobal(Risk global) {
        this.global = global;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
