package com.em.model;

import org.jetbrains.annotations.NotNull;

/**
 * Created by guchong on 5/26/2018.
 */
public class Order implements Comparable<Order>{
    private int orderId;
    private String side;
    private String symbol;
    private double quantity;
    private double price;
    private String orderType;
    private String product;
    private String positionEffect;
    private volatile double avgPx;
    private volatile double cumQty;
    private volatile String ordStatus;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPositionEffect() {
        return positionEffect;
    }

    public void setPositionEffect(String positionEffect) {
        this.positionEffect = positionEffect;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public double getAvgPx() {
        return avgPx;
    }

    public void setAvgPx(double avgPx) {
        this.avgPx = avgPx;
    }

    public double getCumQty() {
        return cumQty;
    }

    public void setCumQty(double cumQty) {
        this.cumQty = cumQty;
    }

    public String getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(String ordStatus) {
        this.ordStatus = ordStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return orderId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", side='" + side + '\'' +
                ", symbol='" + symbol + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", orderType='" + orderType + '\'' +
                ", product='" + product + '\'' +
                ", positionEffect='" + positionEffect + '\'' +
                ", avgPx=" + avgPx +
                ", cumQty=" + cumQty +
                ", ordStatus='" + ordStatus + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NotNull Order o) {
        return o.getOrderId()>orderId?-1:1;
    }
}
