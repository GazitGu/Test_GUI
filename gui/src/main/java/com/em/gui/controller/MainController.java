package com.em.gui.controller;

import com.em.gui.ScreenConfiguration;
import com.em.gui.dialog.DialogController;
import com.em.gui.dialog.FXMLDialog;
import com.em.gui.restful.RestManagement;
import com.em.model.RestResponse;
import com.em.model.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by guchong on 5/23/2018.
 */
public class MainController implements DialogController {
    private FXMLDialog dialog;
    private ScreenConfiguration screens;
    @Autowired
    private ControllerManager controllerManager;
    private User user;

    @Override
    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    public MainController(){}
    public MainController(ScreenConfiguration screens,User user){
        this.screens = screens;
        this.user = user;
    }

    @FXML
    MenuBar menuBar;
    public void start(){
        //判断用户组级别以及该用户的权限
        if(user!=null){
                int role = user.getRole();
                if(role == 1){       //admin
                        menuBar.getMenus().addAll(getMenu1(),getMenu2(),getMenu3());
                }else if(role == 2){      //风控
                        menuBar.getMenus().addAll(getMenu3(),getMenu4());
                }
                else if(role == 3){//trader
                    menuBar.getMenus().addAll(getMenu5(),getMenu7());
                }
        }
        menuBar.getMenus().addAll(getMenu6(),getMenuToExit());
    }

    //用户设置
    private Menu getMenu1(){
        Menu menu1 = new Menu();
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setText("创建用户");
        menuItem1.setOnAction(event -> {
            createUser();
        });
        menu1.setId("user");
        menu1.setText("用户");
        menu1.getItems().addAll(menuItem1);
        return menu1;
    }

    //产品
    private Menu getMenu2(){
        Menu menu2 = new Menu();
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setText("产品管理");
        menuItem1.setOnAction(event -> {
            newProduct();
        });
        menu2.setId("product");
        menu2.setText("产品");
        menu2.getItems().addAll(menuItem1);
        return menu2;
    }

    //风控
    private Menu getMenu3(){
        Menu menu3 = new Menu();
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setText("全局风控");
        menuItem1.setOnAction(event -> {
            showRisk();
        });
        menu3.setId("risk");
        menu3.setText("风控");
        menu3.getItems().addAll(menuItem1);
        return menu3;
    }

    //交易帐号设置
    private Menu getMenu4(){
        Menu menu4 = new Menu();
        MenuItem menuItem4_1 = new MenuItem();
        menuItem4_1.setText("交易帐号设置");
        menuItem4_1.setOnAction(event -> {
            setAccountPwd();
        });
        menu4.setId("account");
        menu4.setText("帐号");
        menu4.getItems().addAll(menuItem4_1);
        return menu4;
    }

    //订单
    private Menu getMenu5(){
        Menu menu5 = new Menu();
        MenuItem menuItem5 = new MenuItem();
        menuItem5.setText("新建订单");
        menuItem5.setOnAction(event -> {
            newOrder();
        });
        MenuItem menuItem6 = new MenuItem();
        menuItem6.setText("订单管理");
        menuItem6.setOnAction(event -> {
            orderManagement();
        });
        menu5.setId("order");
        menu5.setText("订单");
        menu5.getItems().addAll(menuItem5,menuItem6);
        return menu5;
    }

    //设置
    private Menu getMenu6(){
        Menu menu1 = new Menu();
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setText("修改密码");
        menuItem1.setOnAction(event -> {
            changePwd();
        });
        menu1.setId("setting");
        menu1.setText("设置");
        menu1.getItems().addAll(menuItem1);
        return menu1;
    }

    //策略
    private Menu getMenu7(){
        Menu menu1 = new Menu();
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setText("启动策略");
        menuItem1.setOnAction(event -> {
            startStrategy();
        });
        MenuItem menuItem2 = new MenuItem();
        menuItem2.setText("停止策略");
        menuItem2.setOnAction(event -> {
            stopStrategy();
        });
        menu1.setId("strategy");
        menu1.setText("策略");
        menu1.getItems().addAll(menuItem1,menuItem2);
        return menu1;
    }

    //退出
    private Menu getMenuToExit(){
        Menu menu6 = new Menu();
        MenuItem menuItem6 = new MenuItem();
        menuItem6.setText("退出");
        menuItem6.setOnAction(event -> {
            exit();
        });
        menu6.setId("exit");
        menu6.setText("退出");
        menu6.getItems().addAll(menuItem6);
        return menu6;
    }


    public void createUser(){ screens.userCreateDialog().show(); }

    public void newProduct(){ screens.productDialog().show(); }

    public void showRisk(){
        screens.globalRiskSettingDialog().show();
    }

    public void setAccountPwd(){ screens.tradingAccountSettingDialog().show(); }

    public void newOrder(){ screens.newOrderDialog().show(); }

    public void orderManagement(){
        screens.orderManagementDialog().show();
    }

    public void changePwd(){
        screens.changePwdDialog().show();
    }

    public void startStrategy(){
        RestResponse ret = controllerManager.startStrategy();
        Alert alert;
        if(ret.isSuccess()){
            alert = new Alert(Alert.AlertType.INFORMATION,"策略启动成功!",
                    new ButtonType("OK",ButtonBar.ButtonData.YES));
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION,ret.getResult(),
                    new ButtonType("OK",ButtonBar.ButtonData.YES));
        }
        alert.setTitle("提示");
        alert.setHeaderText("");
        alert.show();
    }

    public void stopStrategy(){
        RestResponse ret = controllerManager.stopStrategy();
        Alert alert;
        if(ret.isSuccess()){
            alert = new Alert(Alert.AlertType.INFORMATION,"策略停止成功!",
                    new ButtonType("OK",ButtonBar.ButtonData.YES));
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION,ret.getResult(),
                    new ButtonType("OK",ButtonBar.ButtonData.YES));
        }
        alert.setTitle("提示");
        alert.setHeaderText("");
        alert.show();
    }

    public void exit(){
        dialog.close();
        Platform.exit();
        System.exit(0);
    }
}
