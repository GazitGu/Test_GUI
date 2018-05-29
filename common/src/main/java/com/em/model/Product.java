package com.em.model;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * Created by guchong on 5/22/2018.
 */
@Entity
@Table(name = "Product")
public class Product implements Serializable, Comparable<Product> {
    @Id
    @Column(length = 30)
    private String product;
    @Column(length = 30)
    private String tradingAccount;
    @Column(length = 100)
    private String password;
    @Column(length = 100)
    private String users;
    @Column(length = 100)
    private String riskControllers;
    @Column(length = 1000)
    private String description;

    @Transient
    private List<UserCfg> traders;
    @Transient
    private List<UserCfg> risks;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getTradingAccount() {
        return tradingAccount;
    }

    public void setTradingAccount(String tradingAccount) {
        this.tradingAccount = tradingAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getRiskControllers() {
        return riskControllers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRiskControllers(String riskControllers) {
        this.riskControllers = riskControllers;
    }

    public List<UserCfg> getTraders() {
        return traders;
    }

    public void setTraders(List<UserCfg> traders) {
        this.traders = traders;
    }

    public List<UserCfg> getRisks() {
        return risks;
    }

    public void setRisks(List<UserCfg> risks) {
        this.risks = risks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product1 = (Product) o;

        return product.equals(product1.product);
    }

    @Override
    public int hashCode() {
        return product.hashCode();
    }

    @Override
    public int compareTo(@NotNull Product o) {
        return this.product.compareTo(o.getProduct());
    }
}
