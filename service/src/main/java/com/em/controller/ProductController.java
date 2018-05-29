package com.em.controller;

import com.em.model.*;
import com.em.service.ProductService;
import com.em.service.RiskSettingService;
import com.em.service.UserService;
import com.em.util.Encryptor;
import com.em.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guchong on 5/22/2018.
 */
@RestController
@RequestMapping(path = "/product/")
public class ProductController {
    @Value("${system.configPath:}")
    private String configPath;
    @Autowired
    private ProductService productService;

    @Autowired
    private RiskSettingService riskSettingService;

    @Autowired
    private UserService userService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(path = "upsetProduct", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public RestResponse upsetProduct(@RequestBody Product product) {
        if(product!=null && StringUtils.isNotEmpty(product.getProduct())){
            Product product1 = productService.findByProduct(product.getProduct());
            if(product1!=null){
                product.setPassword(product1.getPassword());
                productService.save(product);
                return new RestResponse(true,"");
            }
            productService.save(product);
            return new RestResponse(true,"");
        }
        return new RestResponse(false,"产品不存在!");
    }

    @RequestMapping(path = "updateProduct", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public RestResponse updateProduct(@RequestBody Product product) {
        if(product!=null){
            Product product1 = productService.findByProduct(product.getProduct());
            product.setPassword(product1.getPassword());
            productService.save(product);
            return new RestResponse(true,"");
        }
        return new RestResponse(false,"产品不存在!");
    }

    @RequestMapping(path = "updateTradingAccountPwd", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public RestResponse updateTradingAccuntPwd(@RequestBody TradingAccount account) {
        if(account!=null){
            List<Product> products = getAllProduct();
            if(products!=null){
                for(Product product : products){
                    if(product.getProduct().equalsIgnoreCase(account.getProduct())){
                        product.setPassword(Encryptor.encrypt(account.getPassword()));
                        productService.save(product);

                        break;
                    }
                }
                RiskSetting riskSetting = riskSettingService.findByRiskId(2);
                if(riskSetting==null || StringUtils.isEmpty(riskSetting.getContent())){
                    riskSetting = riskSettingService.findByRiskId(1);
                }
                Risk risk = null;
                if(riskSetting!=null && StringUtils.isNotEmpty(riskSetting.getContent())){
                    try {
                        risk = mapper.readValue(riskSetting.getContent(),Risk.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(risk==null){
                    risk = Utils.getDefaultRisk();
                }
                List<User> users = userService.findAllUser();
                for(Product product : products){
                    String[] traders = product.getUsers().split(",");
                    String[] risks = product.getRiskControllers().split(",");
                    List<UserCfg> userCfgs = new ArrayList<>();
                    List<UserCfg> riskCfgs = new ArrayList<>();
                    for(String user : traders){
                        for(User user1 : users){
                            if(user.equals(user1.getUsername())){
                                UserCfg userCfg = new UserCfg();
                                userCfg.setUsername(user);
                                userCfg.setMacs(user1.getMacs());
                                userCfgs.add(userCfg);
                                break;
                            }
                        }
                    }
                    for(String user : risks){
                        for(User user1 : users){
                            if(user.equals(user1.getUsername())){
                                UserCfg userCfg = new UserCfg();
                                userCfg.setUsername(user);
                                userCfg.setMacs(user1.getMacs());
                                riskCfgs.add(userCfg);
                                break;
                            }
                        }
                    }
                    product.setTraders(userCfgs);
                    product.setRisks(riskCfgs);
                }
                Utils.flush(configPath+ File.separator+"strategy.cfg",risk,products);
            }
            return new RestResponse(true,"交易帐号更新成功!");
        }
        return new RestResponse(false,"交易帐号更新失败!");
    }

    @GetMapping(path = "getAll/", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Product> getAllProduct() {
        return productService.findAll();
    }
}
