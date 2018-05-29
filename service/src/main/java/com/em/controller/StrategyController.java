package com.em.controller;

import com.em.model.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by guchong on 5/24/2018.
 */
@RestController
@RequestMapping(path = "/strategy/")
public class StrategyController {
    private static final Logger logger = LoggerFactory.getLogger(StrategyController.class);

    @Value("${system.orderPath:}")
    private String basePath;
    private final ProcessBuilder pb = new ProcessBuilder();
    @GetMapping(path = "start/", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public RestResponse start() {
        logger.info("Start strategy");
        pb.command("./start.sh");
        pb.directory(new File(basePath));
        try {
            Process process = pb.start();
            process.waitFor();
            int exit = process.exitValue();
            if(exit!=0){
                return new RestResponse(true,"执行启动成功!");
            }else{
                return new RestResponse(true,"执行启动失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RestResponse(false,"执行启动失败!");
    }
    @GetMapping(path = "stop/", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public RestResponse stop() {
        logger.info("Stop strategy");
        pb.command("./stop.sh");
        pb.directory(new File(basePath));
        try {
            Process process = pb.start();
            process.waitFor();
            int exit = process.exitValue();
            if(exit!=0){
                return new RestResponse(true,"执行停止成功!");
            }else{
                return new RestResponse(true,"执行停止失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RestResponse(false,"执行停止失败!");
    }
}
