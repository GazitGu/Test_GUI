package com.em.gui.controller;

import com.em.gui.ScreenConfiguration;
import com.em.gui.dialog.DialogController;
import com.em.gui.dialog.FXMLDialog;
import com.em.gui.property.UserProperty;
import com.em.gui.restful.RestManagement;
import com.em.model.RestResponse;
import com.em.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guchong on 5/23/2018.
 */
public class UserCreateController implements DialogController {
    private ScreenConfiguration screens;
    private FXMLDialog dialog;
    @Autowired
    private ControllerManager controllerManager;

    @Override
    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    public UserCreateController(ScreenConfiguration screens) {
        this.screens = screens;

    }

    @FXML
    TextField userName;
    @FXML
    PasswordField userPwd;
    @FXML
    TextField description;
    @FXML
    TextField mac;
    @FXML
    TableView userInfo;
    @FXML
    TableColumn description0;
    @FXML
    TableColumn userName0;
    @FXML
    TableColumn<UserProperty,Integer> userRole;
    @FXML
    TableColumn<UserProperty,String> macs;
    @FXML
    RadioButton dealer;
    @FXML
    RadioButton risk;
    @FXML
    Label message;

    private ObservableList<UserProperty> usersObservableList = FXCollections.observableArrayList();

    private boolean isUpdate = false;

    public void start(){
        userInfo.getItems().clear();
        clearData();
        List<User> users = controllerManager.getAllUser();
        List<UserProperty> userPropertyList = new ArrayList<>();
        UserProperty up;
        for(User u : users){
            up = new UserProperty();
            up.setUsername(u.getUsername());
            up.setRole(u.getRole());
            up.setDescription(u.getDescription());
            up.setMacs(u.getMacs());
            userPropertyList.add(up);
        }
        usersObservableList.addAll(userPropertyList);
        userName0.setCellValueFactory(new PropertyValueFactory("username"));
        userRole.setCellValueFactory(new PropertyValueFactory("role"));
        userRole.setCellFactory(new Callback<TableColumn<UserProperty, Integer>, TableCell<UserProperty, Integer>>() {
            @Override
            public TableCell<UserProperty, Integer> call(TableColumn<UserProperty, Integer> param) {
                TableCell<UserProperty, Integer> tableCell = new TableCell<UserProperty, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        if (!empty) {
                            if(item==null){
                                return;
                            }
                            StringBuilder sb = new StringBuilder();
                            if(item==1){
                                sb.append("管理员 ");
                            }
                            if(item==2){
                                sb.append("风控员 ");
                            }
                            if(item==3){
                                sb.append("交易员 ");
                            }
                            setText(sb.toString());
                        }else{
                            setText(null);
                        }
                    }
                };
                return tableCell;
            }
        });
        description0.setCellValueFactory(new PropertyValueFactory("description"));
        macs.setCellValueFactory(new PropertyValueFactory("macs"));
        userInfo.setItems(usersObservableList);
        dealer.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    risk.setSelected(false);
                }
            }
        });
        risk.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    dealer.setSelected(false);
                }
            }
        });
        userInfo.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2)
            {
                isUpdate = true;
                UserProperty userProperty = (UserProperty) userInfo.getSelectionModel().getSelectedItem();
                if(userProperty!=null){
                    userName.setText(userProperty.getUsername());
                    userName.setDisable(true);
                    userPwd.setDisable(true);
                    mac.setText(userProperty.getMacs());
                    description.setText(userProperty.getDescription());
                    description.setDisable(true);
                    int root = userProperty.getRole();
                    if(root==2){
                        risk.setSelected(true);
                    }else{
                        risk.setSelected(false);
                    }
                    if(root==3){
                        dealer.setSelected(true);
                    }else{
                        dealer.setSelected(false);
                    }
                }
            }
        });

    }

    public void clearData(){
        userName.setText("");
        userName.setDisable(false);
        userPwd.setText("");
        userPwd.setDisable(false);
        description.setText("");
        description.setDisable(false);
        mac.setText("");
        this.dealer.setSelected(false);
        this.risk.setSelected(false);
        isUpdate = false;
    }

    @FXML
    public void addUser(){
        String userNameStr =  userName.getText().trim();
        String userPwdStr = userPwd.getText().trim();
        String desc = description.getText().trim();
        String macsStr = mac.getText().trim();
        if(!isUpdate){

            if(userNameStr.isEmpty()){
                message.setText("用户名不能为空!");
                return;
            }
            if(userPwdStr.isEmpty()){
                message.setText("用户密码不能为空!");
                return;
            }
            int root = 0;
            if(dealer.isSelected()){
                root = 3;
                if(macsStr.isEmpty()){
                    message.setText("交易员MAC白名单不能为空!");
                    return;
                }
            }
            if(risk.isSelected()){
                root = 2;

            }
            if(root==0){
                message.setText("设置该用户的角色!");
                return;
            }
            message.setText("");
            RestResponse restResponse = controllerManager.newUser(userNameStr,userPwdStr,root,desc,macsStr);
            if(restResponse.isSuccess()){
                //新增用户成功
                message.setText("用户创建成功！");
                UserProperty user = new UserProperty();
                user.setUsername(userNameStr);
                user.setMacs(macsStr);
                user.setDescription(desc);
                user.setRole(root);
                usersObservableList.add(user);
            }else{
                message.setText(restResponse.getResult());
            }
            clearData();
        }else{
            message.setText("");
            RestResponse restResponse = controllerManager.updateUser(userNameStr,macsStr);
            if(restResponse.isSuccess()){
                //新增用户成功
                message.setText("用户更新成功！");
                for(UserProperty userProperty:usersObservableList){
                    if(userProperty.getUsername().equalsIgnoreCase(userNameStr)){
                        userProperty.setMacs(macsStr);
                    }
                }
            }else{
                message.setText(restResponse.getResult());
            }
            clearData();
        }
    }

    @FXML
    public void cleanMessage(){
        clearData();
        message.setText("");
        //dialog.close();
    }
}
