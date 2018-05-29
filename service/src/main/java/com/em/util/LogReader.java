package com.em.util;

/**
 * Created by guchong on 5/28/2018.
 */

import com.em.model.Order;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class LogReader{

    public static void main(String[] args){
        String str = "send_order(10,100)";
        int startIndex = str.indexOf("send_order");
        int endIndex = str.indexOf(",");
        System.out.println(str.substring(startIndex+11,endIndex));
    }

    private static final Logger logger = LoggerFactory.getLogger(LogReader.class);
    private File logFile = null;
    private long lastTimeFileSize = 0; // 上次文件大小
    public LogReader(File logFile) {
        this.logFile = logFile;
        lastTimeFileSize = logFile.length();
    }

    /**
     * 实时输出日志信息
     */
    public List<Order> call() {
        List<Order> orders = new ArrayList<>();
        try {
            long len = logFile.length();
            if (len < lastTimeFileSize) {
                logger.info("Log file was reset. Restarting logging from start of file.");
                lastTimeFileSize = len;
            } else if (len > lastTimeFileSize) {
                RandomAccessFile randomFile = new RandomAccessFile(logFile, "r");
                randomFile.seek(lastTimeFileSize);
                String tmp = null;
                List<String> value = new ArrayList<>();
                while ((tmp = randomFile.readLine()) != null) {
                    if(tmp.contains("<--")){
                        logger.info("--- {} ---",tmp);
                        value.clear();
                        String[] arr1 = tmp.split("<--");
                        String[] arr2 = arr1[1].split("\\[");
                        for(String param : arr2){
                            logger.info("--- {} ---",param);
                            String v = param.replaceAll("\\]","").trim();
                            if(StringUtils.isNotEmpty(v)){
                                value.add(v);
                            }
                        }
                        Order order = new Order();
                        order.setOrderId(Integer.parseInt(value.get(0)));
                        order.setCumQty(order.getCumQty()+Integer.parseInt(value.get(2)));
                        order.setAvgPx(Double.parseDouble(value.get(3)));
                        orders.add(order);
                    }else if(tmp.contains("X<-")){
                        value.clear();
                        String[] arr1 = tmp.split("X<-");
                        String[] arr2 = arr1[1].split("\\[");
                        for(String param : arr2){
                            String v = param.replaceAll("\\]","").trim();
                            if(StringUtils.isNotEmpty(v)){
                                value.add(v);
                            }
                        }
                        Order order = new Order();
                        order.setOrderId(Integer.parseInt(value.get(0)));
                        order.setOrdStatus("CANCELED");
                        orders.add(order);
                    }
                }
                lastTimeFileSize = randomFile.length();
                randomFile.close();
            }
        } catch (IOException e) {
            logger.error("", e);
        }
        return orders;

    }

}