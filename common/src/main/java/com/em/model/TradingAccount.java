package com.em.model;

/**
 * Created by guchong on 5/24/2018.
 */
public class TradingAccount {
    private String product;
    private String password;

    public TradingAccount() {
    }

    public TradingAccount(String product, String password) {
        this.product = product;
        this.password = password;
    }

    public String getProduct() {
        return product;
    }

    public String getPassword() {
        return password;
    }
}
