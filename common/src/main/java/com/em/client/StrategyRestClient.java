package com.em.client;

import com.em.model.RestResponse;
import com.em.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Created by guchong on 5/22/2018.
 */
@Component
public class StrategyRestClient {

    private static final Logger logger = LoggerFactory.getLogger(StrategyRestClient.class);

    @Value("${service.host:127.0.0.1}")
    private String serviceHost;

    @Value("${service.port:8080}")
    private int servicePort;

    private RestTemplate restTemplate;

    private String context = "strategy";

    public StrategyRestClient(){
        restTemplate = new RestTemplate();
    }

    public StrategyRestClient(String host, int port) {
        this.serviceHost = host;
        this.servicePort = port;
        restTemplate = new RestTemplate();
    }

    public RestResponse start(int strategyId){
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/start");
        sb.append("/");
        String address = sb.toString();
        try{
            logger.info("Start strategy {}",address);
            RestResponse ret = restTemplate.getForObject(address, RestResponse.class);
            return ret;
        }catch(Exception e){
            e.printStackTrace();
            return new RestResponse(false,"请检查后端服务是否正常!");
        }
    }

    public RestResponse stop(int strategyId) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/stop");
        sb.append("/");
        String address = sb.toString();
        try{
            logger.info("Stop strategy {}",address);
            RestResponse ret = restTemplate.getForObject(address, RestResponse.class);
            return ret;
        }catch(Exception e){
            e.printStackTrace();
            return new RestResponse(false,"请检查后端服务是否正常!");
        }
    }
}
