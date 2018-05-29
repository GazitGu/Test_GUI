package com.em.gui.controller;

import com.em.gui.ScreenConfiguration;
import com.em.gui.dialog.DialogController;
import com.em.gui.dialog.FXMLDialog;
import com.em.model.RestResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by guchong on 5/24/2018.
 */
public class ChangePwdController implements DialogController {

    private ScreenConfiguration screens;
    private FXMLDialog dialog;
    @Autowired
    private ControllerManager controllerManager;

    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    public ChangePwdController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    @FXML
    private PasswordField oldPwd;

    @FXML
    private PasswordField newPwd1;

    @FXML
    private PasswordField newPwd2;

    @FXML
    private Label message;

    @FXML
    public void exit() {
        dialog.close();
    }

    @FXML
    public void submit() {
        message.setText("");
        if(StringUtils.isNotEmpty(oldPwd.getText().trim())){
            if(!newPwd1.getText().trim().equals(newPwd2.getText().trim())){
                message.setText("新密码不一致!");
                return;
            }else{
                RestResponse result = controllerManager.updatePwd(oldPwd.getText().trim(),newPwd1.getText().trim());
                if(result.isSuccess()){
                    message.setText("密码修改成功!");
                }else{
                    message.setText(result.getResult());
                }
            }
        }else{
            message.setText("旧密码为空!");
            return;
        }

    }
}
