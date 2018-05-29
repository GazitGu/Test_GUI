package com.em.client;

import com.em.model.Order;
import com.em.model.Product;
import com.em.model.RestResponse;
import com.em.model.TradingAccount;
import com.em.util.Encryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by guchong on 5/22/2018.
 */
@Component
public class OrderRestClient {
    @Value("${service.host:127.0.0.1}")
    private String serviceHost;

    @Value("${service.port:8080}")
    private int servicePort;

    private RestTemplate restTemplate;

    private String context = "order";

    public OrderRestClient() {
        restTemplate = new RestTemplate();
    }

    public OrderRestClient(String host, int port) {
        this.serviceHost = host;
        this.servicePort = port;
        restTemplate = new RestTemplate();
    }

    public RestResponse newOrder(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/newOrder");
        String address = sb.toString();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        HttpEntity<Order> request = new HttpEntity<>(order, headers);
        RestResponse ret = restTemplate.postForObject(address, request, RestResponse.class);
        return ret;
    }

    public RestResponse cancelOrder(int orderId) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/cancelOrder/");
        sb.append(orderId).append("/");
        String address = sb.toString();
        RestResponse ret = restTemplate.getForObject(address, RestResponse.class);
        return ret;
    }

    public List<Order> getAllOrder(){
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/getAllOrders/");
        String address = sb.toString();
        ResponseEntity<Collection<Order>> rateResponse = restTemplate.exchange(address, HttpMethod.GET, null,
                new ParameterizedTypeReference<Collection<Order>>() {});
        Collection<Order> ret = rateResponse.getBody();
        if(ret==null){
            return Collections.emptyList();
        }
        List<Order> orders = new ArrayList<>(ret.size());
        orders.addAll(ret);
        Collections.sort(orders);
        return orders;
    }

}
