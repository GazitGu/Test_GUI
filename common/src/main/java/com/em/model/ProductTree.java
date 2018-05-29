package com.em.model;

/**
 * Created by guchong on 5/24/2018.
 */
public class ProductTree {
    private String product;
    private int prodcutId;

    public ProductTree(String product, int prodcutId) {
        this.product = product;
        this.prodcutId = prodcutId;
    }

    public String getProduct() {
        return product;
    }

    public int getProdcutId() {
        return prodcutId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductTree that = (ProductTree) o;

        return product != null ? product.equals(that.product) : that.product == null;
    }

    @Override
    public int hashCode() {
        return product != null ? product.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ProductTree{" +
                "product='" + product + '\'' +
                ", prodcutId=" + prodcutId +
                '}';
    }
}
