package com.em.controller;

import com.em.model.Order;
import com.em.model.Product;
import com.em.model.RestResponse;
import com.em.model.User;
import com.em.util.LogReader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by guchong on 5/26/2018.
 */
@RestController
@RequestMapping(path = "/order/")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Value("${system.orderPath:}")
    private String orderPath;

    private final ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

    private AtomicInteger maxOrderId = new AtomicInteger(1000);
    private final Map<Integer,Order> orders = new ConcurrentHashMap<>(1000);
    private final List<Order> notMatchedOrder = new CopyOnWriteArrayList<>();
    private final ThreadLocal<StringBuilder> stringBuilderThreadLocal = new ThreadLocal<StringBuilder>(){
        @Override
        protected StringBuilder initialValue() {
            return new StringBuilder();
        }
    };
    @PostConstruct
    public void start(){
        //read file to recover all orders.
        logger.info("Enter order controller constructor.");
        readOrder();
        String today = new LocalDate().toString("yyyy-MM-dd");
        File file = new File(orderPath+File.separator+"log"+File.separator+today+".log");
        logger.info("log file {}",orderPath+File.separator+"log"+File.separator+today+".log");
        final LogReader logReader = new LogReader(file);
        ses.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Iterator<Order> iterator = notMatchedOrder.iterator();
                while(iterator.hasNext()){
                    Order order = iterator.next();
                    Order orderCopy = orders.get(order.getOrderId());
                    if(orderCopy!=null){
                        orderCopy.setOrdStatus(order.getOrdStatus());
                        if(order.getCumQty()>0){
                            orderCopy.setCumQty(order.getCumQty());
                            orderCopy.setAvgPx(order.getAvgPx());
                            if(orderCopy.getQuantity()==order.getCumQty()){
                                orderCopy.setOrdStatus("FILLED");
                            }
                            if(orderCopy.getCumQty()>0 && orderCopy.getQuantity()>order.getCumQty()){
                                orderCopy.setOrdStatus("PARTIALLY_FILLED");
                            }
                        }
                        iterator.remove();
                    }
                }
                List<Order> orderList = logReader.call();
                for(Order order : orderList){
                    logger.info("Received from log: {}",order);
                    if(orders.containsKey(order.getOrderId())){
                        Order orderCopy = orders.get(order.getOrderId());
                        orderCopy.setOrdStatus(order.getOrdStatus());
                        if(order.getCumQty()>0){
                            orderCopy.setCumQty(order.getCumQty());
                            orderCopy.setAvgPx(order.getAvgPx());
                            if(orderCopy.getQuantity()==order.getCumQty()){
                                orderCopy.setOrdStatus("FILLED");
                            }
                            if(orderCopy.getCumQty()>0 && orderCopy.getQuantity()>order.getCumQty()){
                                orderCopy.setOrdStatus("PARTIALLY_FILLED");
                            }
                        }

                    }else{
                        notMatchedOrder.add(order);
                    }
                }
            }
        },3,3, TimeUnit.SECONDS);
    }

    private void readOrder() {
        File file = new File(orderPath+File.separator+"cmd.txt");
        try {
            List<String> lines = FileUtils.readLines(file,"UTF-8");
            for(String line : lines){
                if(line.startsWith("send_order(")){
                    int startIndex = line.indexOf("send_order");
                    int endIndex = line.indexOf(",");
                    maxOrderId.set(Integer.parseInt(line.substring(startIndex+11,endIndex)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "newOrder", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public RestResponse newOrder(@RequestBody Order order) {
        order.setOrderId(maxOrderId.incrementAndGet());
        List<String> orderStr = new ArrayList<>();
        logger.info("Received order {}",order);
        StringBuilder sb = stringBuilderThreadLocal.get();
        sb.setLength(0);
        sb.append("send_order(").append(order.getOrderId()).append(",\"").append(order.getSymbol()).append("\",\"")
                .append(order.getSide()).append("\",").append(order.getQuantity()).append(",").append(order.getPrice()).append(")");
        orderStr.add(sb.toString());
        try {
            FileUtils.writeLines(new File(orderPath+ File.separator+"cmd.txt"),orderStr,true);
        } catch (Exception e) {
            e.printStackTrace();
            return new RestResponse(false,"新建订单失败!");
        }
        order.setOrdStatus("NEW");
        orders.put(order.getOrderId(),order);
        return new RestResponse(true,"");
    }

    @GetMapping(path = "getAllOrders/", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Collection<Order> getAllOrders() {
        return orders.values();
    }

    @GetMapping(path = "cancelOrder/{orderId}/", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public RestResponse cancelOrder(@PathVariable int orderId) {
        Order order = orders.get(orderId);
        if(order!=null){
            List<String> orderStr = new ArrayList<>();
            StringBuilder sb = stringBuilderThreadLocal.get();
            sb.setLength(0);
            sb.append("cancel_order(").append(order.getOrderId()).append(",\"").append(order.getSymbol()).append("\")");
            orderStr.add(sb.toString());
            try {
                FileUtils.writeLines(new File(orderPath+ File.separator+"cmd.txt"),orderStr,true);
            } catch (Exception e) {
                e.printStackTrace();
                return new RestResponse(false,"撤消订单失败!");
            }
            return new RestResponse(true,"");
        }else{
            logger.warn("Order {} not exists",orderId);
            return new RestResponse(false,"订单不存在");
        }

    }
}
