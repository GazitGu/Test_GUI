package com.em.gui.controller;

import com.em.gui.ScreenConfiguration;
import com.em.gui.dialog.DialogController;
import com.em.gui.dialog.FXMLDialog;
import com.em.gui.restful.RestManagement;
import com.em.model.RestResponse;
import com.em.model.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by guchong on 5/23/2018.
 */
public class LoginController implements DialogController {

    private ScreenConfiguration screens;
    private FXMLDialog dialog;
    private RestResponse loginResponse;
    @Autowired
    private ControllerManager controllerManager;

    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    public LoginController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    @FXML
    Label header;
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    Button login;

//    @FXML
//    private Label message;

    private ObservableList<String> userList;
    private ObservableList<String> strList;
    private String pwd = null; //密码
    //private String host;//网关

    public void init() {
        //初始化网络配置下拉框
        //host="127.0.0.1:8080";
    }

    @FXML
    public void login() {
        //header.setText("");
        header.setText("正在登录...");
        controllerManager.start();
        String userName = username.getText().trim();
        if (StringUtils.isEmpty(userName)) {
            header.setText("用户名输入错误！");
            header.setTextFill(Color.DARKRED);
            return;
        }
        try{
            User user = controllerManager.validateUser(userName, password.getText());
            System.out.println(user);
            if (user != null) {
                if (user.getRole() ==1 || user.getRole() ==2 || user.getRole() ==3) {
                    dialog.close();
                    //登录成功
                    screens.mainScreen(user,controllerManager.getServiceHost()).show();
                    System.out.println("登录成功...");
                }
            } else {
                header.setText("登录失败，请重新登录！");
                header.setTextFill(Color.DARKRED);
                login.setDisable(false);
            }
        }catch(Exception e){
            header.setText("登录失败，请检查后端服务是否可用！");
            header.setTextFill(Color.DARKRED);
            login.setDisable(false);

        }

    }

    @FXML
    public void exit() {
        dialog.close();
    }

}
