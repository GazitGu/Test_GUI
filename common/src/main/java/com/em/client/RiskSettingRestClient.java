package com.em.client;

import com.em.model.*;
import com.em.util.Encryptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by guchong on 5/22/2018.
 */
@Component
public class RiskSettingRestClient {

    private static final Logger logger = LoggerFactory.getLogger(RiskSettingRestClient.class);

    @Value("${service.host:127.0.0.1}")
    private String serviceHost;

    @Value("${service.port:8080}")
    private int servicePort;

    private RestTemplate restTemplate;

    private String context = "risk";

    private ObjectMapper mapper = new ObjectMapper();

    public RiskSettingRestClient(){
        restTemplate = new RestTemplate();
    }

    public RiskSettingRestClient(String host, int port) {
        this.serviceHost = host;
        this.servicePort = port;
        restTemplate = new RestTemplate();
    }

    public RestResponse upsetRisk(int key, Risk risk) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/upsetRisk");
        sb.append("/");
        String address = sb.toString();
        RiskSetting riskSetting = new RiskSetting();
        riskSetting.setRiskId(key);
        try {
            riskSetting.setContent(mapper.writeValueAsString(risk));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.info("Risk:{}",riskSetting);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        HttpEntity<RiskSetting> request = new HttpEntity<>(riskSetting, headers);
        RestResponse ret = restTemplate.postForObject(address, request, RestResponse.class);
        return ret;
    }

    public Risk getRisk(int key){
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/getRisk");
        sb.append("/");
        sb.append(key);
        sb.append("/");
        String address = sb.toString();
        String ret = restTemplate.getForObject(address, String.class);
        if(!StringUtils.isEmpty(ret)){
            try {
                Risk risk = mapper.readValue(ret,Risk.class);
                return risk;
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return null;
    }
}
