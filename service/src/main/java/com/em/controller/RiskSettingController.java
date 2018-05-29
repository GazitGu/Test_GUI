package com.em.controller;

import com.em.model.RestResponse;
import com.em.model.Risk;
import com.em.model.RiskSetting;
import com.em.model.User;
import com.em.service.ProductService;
import com.em.service.RiskSettingService;
import com.em.service.UserService;
import com.em.util.Encryptor;
import com.em.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by guchong on 5/22/2018.
 */
@RestController
@RequestMapping(path = "/risk/")
public class RiskSettingController {
    private static final Logger logger = LoggerFactory.getLogger(RiskSettingController.class);

    @Value("${system.configPath:}")
    private String configPath;

    @Autowired
    private RiskSettingService riskSettingService;

    @Autowired
    private ProductService productService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(path = "upsetRisk", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public RestResponse upsetRisk(@RequestBody RiskSetting riskSetting) {
        riskSettingService.save(riskSetting);
        Risk risk = null;
        if(riskSetting.getRiskId()==1){
            String riskStr = getRisk(2);
            if(StringUtils.isNotEmpty(riskStr)){
                try {
                    risk = mapper.readValue(riskStr,Risk.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        if(risk==null){
            try {
                risk = mapper.readValue(riskSetting.getContent(),Risk.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(risk==null){
            risk = Utils.getDefaultRisk();
        }
        Utils.flush(configPath+ File.separator+"strategy.cfg",risk,productService.findAll());
        return new RestResponse(true, "");
    }

    @GetMapping(path = "getRisk/{key}/", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getRisk(@PathVariable int key) {
        RiskSetting riskSetting = riskSettingService.findByRiskId(key);
        if(riskSetting!=null){
            return riskSetting.getContent();
        }
        return "";
    }
}
