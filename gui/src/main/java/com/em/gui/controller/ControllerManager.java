package com.em.gui.controller;

import com.em.gui.property.OrderProperty;
import com.em.gui.restful.RestManagement;
import com.em.model.*;
import com.em.util.Encryptor;
import com.em.util.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by guchong on 5/24/2018.
 */
@Component
public class ControllerManager {
    private static final Logger logger = LoggerFactory.getLogger(ControllerManager.class);
    @Autowired
    private RestManagement restManagement;

    @Autowired
    private OrderManagementController orderManagementController;

    @Value("${service.host:}")
    private String serviceHost;

    private User loginUser;

    private final ObservableList<OrderProperty> orderPropertyObservableList = FXCollections.observableArrayList();
    private final Map<Integer,OrderProperty> ordersMap = new HashMap<>(100);

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public void start(){
        if(StringUtils.isEmpty(serviceHost)){
            serviceHost = "127.0.0.1:8080";
        }
        String[] arr = serviceHost.split(":");
        if(arr.length!=2){
            logger.warn("Host {} was not validate,format [host:port]",serviceHost);
            System.exit(-1);
        }
        restManagement.start(arr[0],Integer.parseInt(arr[1]));
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try{
                    getAllOrder();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        },3,3, TimeUnit.SECONDS);
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public User validateUser(String username, String password) throws ResourceAccessException{
        loginUser = restManagement.getUserRestClient().validateUser(username,password);
        return loginUser;
    }

    public RestResponse updateUser(String username, String macs){
        return restManagement.getUserRestClient().updateUser(username,macs);
    }

    public RestResponse updatePwd(String oldPwd,String password){
        if(loginUser==null){
            return new RestResponse(false,"用户未登录!");
        }else{
            if(!loginUser.getPassword().equalsIgnoreCase(Encryptor.encrypt(oldPwd))){
                return new RestResponse(false,"旧密码错误!");
            }else{
                return restManagement.getUserRestClient().updatePwd(loginUser.getUsername(),password);
            }
        }
    }

    public List<User> getAllUser(){
        return restManagement.getUserRestClient().getAll();
    }
    public RestResponse newUser(String userName,String password,int role,String description,String macs){
        return restManagement.getUserRestClient().newUser(userName,password,role,description,macs);
    }

    public RestResponse upsetRisk(Risk risk) {
        if(loginUser!=null){
            if(loginUser.getRole()==1){
                return restManagement.getRiskSettingRestClient().upsetRisk(1,risk);
            }else if(loginUser.getRole()==2){
                RestResponse ret = checkLocalRiskSetting(risk);
                if(ret.isSuccess()){
                    return restManagement.getRiskSettingRestClient().upsetRisk(2,risk);
                }else{
                    return new RestResponse(false,ret.getResult());
                }
            }else{
                return new RestResponse(false,"您没有权限设置风控!");
            }
        }
        return new RestResponse(false,"用户未登录!");
    }

    private RestResponse checkLocalRiskSetting(Risk risk){
        Risk globalRisk = restManagement.getRiskSettingRestClient().getRisk(1);
        if(globalRisk==null){
            globalRisk = Utils.getDefaultRisk();
        }
        if(risk.getFlowControl()>globalRisk.getFlowControl()){
            return new RestResponse(false,"流量设置大于券商管理员参数设置!");
        }
        if(risk.getTotalNotional()>globalRisk.getTotalNotional()){
            return new RestResponse(false,"净买入金额设置大于券商管理员参数设置!");
        }
        if(risk.getOrdersTotal()>globalRisk.getOrdersTotal()){
            return new RestResponse(false,"帐户总委托数设置大于券商管理员参数设置!");
        }

        if(risk.getCancelRate()>globalRisk.getCancelRate()){
            return new RestResponse(false,"撤单比大于券商管理员参数设置!");
        }

        if(risk.getTradeRate()<globalRisk.getTradeRate()){
            return new RestResponse(false,"成交比小于券商管理员参数设置!");
        }

        if(risk.getRejectRate()>globalRisk.getRejectRate()){
            return new RestResponse(false,"废单比大于券商管理员参数设置!");
        }
        return new RestResponse(true,"风控设置成功!");
    }



    public RestResponse startStrategy() {
        return restManagement.getStrategyRestClient().start(1);
    }

    public RestResponse stopStrategy() {
        return restManagement.getStrategyRestClient().stop(1);
    }

    public List<Product> getAllProducts() {
        return restManagement.getProductRestClient().getAllProduct();
    }

    public RestResponse upsetProduct(Product product) {
        return restManagement.getProductRestClient().upsetProduct(product);
    }

    public RestResponse updateTradingAccount(String product,String password) {
        return restManagement.getProductRestClient().updateTradingAccountPwd(product,password);
    }

    public RestResponse newOrder(Order order) {
        return restManagement.getOrderRestClient().newOrder(order);
    }

    public void getAllOrder(){
        List<Order> orders = restManagement.getOrderRestClient().getAllOrder();
        if(orders==null || orders.size()==0){
            return ;
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for(Order order : orders){
                    OrderProperty orderProperty = null;
                    if(ordersMap.containsKey(order.getOrderId())){
                        orderProperty = ordersMap.get(order.getOrderId());
                    }else{
                        orderProperty = new OrderProperty();
                        orderProperty.setOrderId(order.getOrderId());
                        orderProperty.setSymbol(order.getSymbol());
                        orderProperty.setQuantity(order.getQuantity());
                        orderProperty.setPrice(order.getPrice());
                        orderProperty.setSide(order.getSide());
                        orderProperty.setProduct(order.getProduct());
                        orderProperty.setOrderType(order.getOrderType());
                        orderProperty.setPositionEffect(order.getPositionEffect());
                        ordersMap.put(order.getOrderId(),orderProperty);
                        orderPropertyObservableList.add(0,orderProperty);
                    }
                    orderProperty.setOrdStatus(order.getOrdStatus());
                    orderProperty.setCumQty(order.getCumQty());
                    orderProperty.setAvgPx(order.getAvgPx());
                }
            }
        });


    }

    public ObservableList<OrderProperty> getOrderPropertyObservableList() {
        return orderPropertyObservableList;
    }

    public void cancelOrder(int orderId) {
        restManagement.getOrderRestClient().cancelOrder(orderId);
    }
}
