package com.em.client;

import com.em.model.Product;
import com.em.model.RestResponse;
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
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Created by guchong on 5/22/2018.
 */

@Component
public class UserRestClient {
    @Value("${service.host:127.0.0.1}")
    private String serviceHost;

    @Value("${service.port:8080}")
    private int servicePort;

    private RestTemplate restTemplate;

    private String context = "user";

    public UserRestClient(){
        restTemplate = new RestTemplate();
    }

    public UserRestClient(String host, int port) {
        this.serviceHost = host;
        this.servicePort = port;
        restTemplate = new RestTemplate();
    }

    public User validateUser(String userName, String password) throws ResourceAccessException{
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/validateUser");
        sb.append("/");
        sb.append(userName);
        sb.append("/");
        sb.append(password);
        sb.append("/");
        String address = sb.toString();
        try{
            User ret = restTemplate.getForObject(address, User.class);
            return ret;
        }catch(Exception e){
            throw new ResourceAccessException("");
        }
    }

    //newUser/{username}/{pwd}/{description}/{role}/{macs}/
    public RestResponse newUser(String userName, String password,int role, String description,String macs) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/newUser");
        sb.append("/");
        String address = sb.toString();
        User user = new User();
        user.setUsername(userName);
        user.setPassword(Encryptor.encrypt(password));
        user.setRole(role);
        user.setDescription(description);
        user.setMacs(macs);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        RestResponse ret = restTemplate.postForObject(address, request, RestResponse.class);
        return ret;
    }

    //updateUser/{username}/{macs}/
    public RestResponse updateUser(String userName, String macs) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/updateUser");
        sb.append("/");
        sb.append(userName);
        sb.append("/");
        sb.append(macs);
        sb.append("/");
        String address = sb.toString();
        RestResponse ret = restTemplate.getForObject(address, RestResponse.class);
        return ret;
    }

    //updatePwd/{username}/{pwd}/
    public RestResponse updatePwd(String userName, String pwd) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/updatePwd");
        sb.append("/");
        sb.append(userName);
        sb.append("/");
        sb.append(pwd);
        sb.append("/");
        String address = sb.toString();
        RestResponse ret = restTemplate.getForObject(address, RestResponse.class);
        return ret;
    }

    //deleteUser/{username}/
    public RestResponse deleteUser(String userName) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/deleteUser");
        sb.append("/");
        sb.append(userName);
        sb.append("/");
        String address = sb.toString();
        RestResponse ret = restTemplate.getForObject(address, RestResponse.class);
        return ret;
    }

    //getAll/
    public List<User> getAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(serviceHost);
        sb.append(":");
        sb.append(servicePort);
        sb.append("/");
        sb.append(context);
        sb.append("/getAll");
        sb.append("/");
        String address = sb.toString();
        ResponseEntity<List<User>> rateResponse = restTemplate.exchange(address, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {});
        List<User> ret = rateResponse.getBody();
        if(ret==null){
            return Collections.emptyList();
        }
        return ret;
    }
}
