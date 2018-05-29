package com.em.gui.property;

import javafx.beans.property.*;

/**
 * Created by guchong on 5/26/2018.
 */
public class OrderProperty {
    private IntegerProperty orderId = new SimpleIntegerProperty();
    private StringProperty symbol = new SimpleStringProperty();
    private StringProperty side = new SimpleStringProperty();
    private DoubleProperty quantity = new SimpleDoubleProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private StringProperty orderType = new SimpleStringProperty();
    private StringProperty positionEffect = new SimpleStringProperty();
    private DoubleProperty avgPx = new SimpleDoubleProperty();
    private StringProperty ordStatus = new SimpleStringProperty();
    private DoubleProperty cumQty = new SimpleDoubleProperty();
    private StringProperty product = new SimpleStringProperty();

    public int getOrderId() {
        return orderId.get();
    }

    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public String getSymbol() {
        return symbol.get();
    }

    public StringProperty symbolProperty() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol.set(symbol);
    }

    public String getSide() {
        return side.get();
    }

    public StringProperty sideProperty() {
        return side;
    }

    public void setSide(String side) {
        this.side.set(side);
    }

    public double getQuantity() {
        return quantity.get();
    }

    public DoubleProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity.set(quantity);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getOrderType() {
        return orderType.get();
    }

    public StringProperty orderTypeProperty() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType.set(orderType);
    }

    public String getPositionEffect() {
        return positionEffect.get();
    }

    public StringProperty positionEffectProperty() {
        return positionEffect;
    }

    public void setPositionEffect(String positionEffect) {
        this.positionEffect.set(positionEffect);
    }

    public double getAvgPx() {
        return avgPx.get();
    }

    public DoubleProperty avgPxProperty() {
        return avgPx;
    }

    public void setAvgPx(double avgPx) {
        this.avgPx.set(avgPx);
    }

    public String getOrdStatus() {
        return ordStatus.get();
    }

    public StringProperty ordStatusProperty() {
        return ordStatus;
    }

    public void setOrdStatus(String ordStatus) {
        this.ordStatus.set(ordStatus);
    }

    public double getCumQty() {
        return cumQty.get();
    }

    public DoubleProperty cumQtyProperty() {
        return cumQty;
    }

    public void setCumQty(double cumQty) {
        this.cumQty.set(cumQty);
    }

    public String getProduct() {
        return product.get();
    }

    public StringProperty productProperty() {
        return product;
    }

    public void setProduct(String product) {
        this.product.set(product);
    }
}
