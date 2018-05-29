package com.em.gui.restful;

import com.em.client.*;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.stereotype.Component;

/**
 * Created by guchong on 5/23/2018.
 */
@Component
public class RestManagement {
    private UserRestClient userRestClient;
    private RiskSettingRestClient riskSettingRestClient;
    private ProductRestClient productRestClient;
    private StrategyRestClient strategyRestClient;
    private OrderRestClient orderRestClient;

    public void start(String host, int port) {
        userRestClient = new UserRestClient(host, port);
        riskSettingRestClient = new RiskSettingRestClient(host, port);
        productRestClient = new ProductRestClient(host, port);
        strategyRestClient = new StrategyRestClient(host,port);
        orderRestClient = new OrderRestClient(host,port);
    }

    public UserRestClient getUserRestClient() {
        return userRestClient;
    }

    public RiskSettingRestClient getRiskSettingRestClient() {
        return riskSettingRestClient;
    }

    public ProductRestClient getProductRestClient() {
        return productRestClient;
    }

    public StrategyRestClient getStrategyRestClient() {
        return strategyRestClient;
    }

    public OrderRestClient getOrderRestClient() {
        return orderRestClient;
    }
}
