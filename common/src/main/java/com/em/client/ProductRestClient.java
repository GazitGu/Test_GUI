package com.em.client;

import com.em.model.Product;
import com.em.model.RestResponse;
import com.em.model.TradingAccount;
import com.em.model.User;
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

import java.util.Collections;
import java.util.List;

/**
 * Created by guchong on 5/22/2018.
 */
@Component
public class ProductRestClient {
    @Value("${service.host:127.0.0.1}")
    private String serviceHost;

    @Value("${service.port:8080}")
    private int servicePort;

    private RestTemplate restTemplate;

    private String context = "product";

    public ProductRestClient() {
        restTemplate = new RestTemplate();
    }

    public ProductRestClient(String host, int port) {
        this.serviceHost = host;
        this.servicePort = port;
        restTemplate = new RestTemplate();
    }

    public RestResponse upsetProduct(Product product) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/upsetProduct");
        String address = sb.toString();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        HttpEntity<Product> request = new HttpEntity<>(product, headers);
        RestResponse ret = restTemplate.postForObject(address, request, RestResponse.class);
        return ret;
    }

    public RestResponse updateProduct(Product product) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/updateProduct");
        String address = sb.toString();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        HttpEntity<Product> request = new HttpEntity<>(product, headers);
        RestResponse ret = restTemplate.postForObject(address, request, RestResponse.class);
        return ret;
    }

    public RestResponse updateTradingAccountPwd(String product,String password) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/updateTradingAccountPwd");
        String address = sb.toString();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        TradingAccount tradingAccount = new TradingAccount(product,Encryptor.encrypt(password));
        HttpEntity<TradingAccount> request = new HttpEntity<>(tradingAccount, headers);
        RestResponse ret = restTemplate.postForObject(address, request, RestResponse.class);
        return ret;
    }

    public List<Product> getAllProduct(){
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/getAll/");
        String address = sb.toString();
        ResponseEntity<List<Product>> rateResponse = restTemplate.exchange(address, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {});
        List<Product> ret = rateResponse.getBody();
        if(ret==null){
            return Collections.emptyList();
        }
        return ret;
    }

}
