package com.em.model;

import java.io.Serializable;

/**
 * Created by guchong on 5/24/2018.
 */
public class Risk implements Serializable{
    private int flowControl;
    private int ordersTotal;
    private double cancelRate;
    private double tradeRate;
    private double rejectRate;
    private double totalNotional;
    private int threshold;
    private String var1;
    private String var2;
    private String var3;
    private String var4;
    private String var5;

    public int getFlowControl() {
        return flowControl;
    }

    public void setFlowControl(int flowControl) {
        this.flowControl = flowControl;
    }

    public int getOrdersTotal() {
        return ordersTotal;
    }

    public void setOrdersTotal(int ordersTotal) {
        this.ordersTotal = ordersTotal;
    }

    public double getCancelRate() {
        return cancelRate;
    }

    public void setCancelRate(double cancelRate) {
        this.cancelRate = cancelRate;
    }

    public double getTradeRate() {
        return tradeRate;
    }

    public void setTradeRate(double tradeRate) {
        this.tradeRate = tradeRate;
    }

    public double getRejectRate() {
        return rejectRate;
    }

    public void setRejectRate(double rejectRate) {
        this.rejectRate = rejectRate;
    }

    public double getTotalNotional() {
        return totalNotional;
    }

    public void setTotalNotional(double totalNotional) {
        this.totalNotional = totalNotional;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getVar1() {
        return var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public String getVar2() {
        return var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }

    public String getVar3() {
        return var3;
    }

    public void setVar3(String var3) {
        this.var3 = var3;
    }

    public String getVar4() {
        return var4;
    }

    public void setVar4(String var4) {
        this.var4 = var4;
    }

    public String getVar5() {
        return var5;
    }

    public void setVar5(String var5) {
        this.var5 = var5;
    }

    @Override
    public String toString() {
        return "Risk{" +
                "flowControl=" + flowControl +
                ", ordersTotal=" + ordersTotal +
                ", cancelRate=" + cancelRate +
                ", tradeRate=" + tradeRate +
                ", rejectRate=" + rejectRate +
                ", totalNotional=" + totalNotional +
                ", threshold=" + threshold +
                ", var1='" + var1 + '\'' +
                ", var2='" + var2 + '\'' +
                ", var3='" + var3 + '\'' +
                ", var4='" + var4 + '\'' +
                ", var5='" + var5 + '\'' +
                '}';
    }
}
