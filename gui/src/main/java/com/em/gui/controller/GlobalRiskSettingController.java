package com.em.gui.controller;

import com.em.gui.ScreenConfiguration;
import com.em.gui.dialog.DialogController;
import com.em.gui.dialog.FXMLDialog;
import com.em.model.RestResponse;
import com.em.model.Risk;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by guchong on 5/24/2018.
 */
public class GlobalRiskSettingController implements DialogController{

    private ScreenConfiguration screens;
    private FXMLDialog dialog;
    @Autowired
    private ControllerManager controllerManager;

    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    public GlobalRiskSettingController(ScreenConfiguration screens) {
        this.screens = screens;
    }
    @FXML
    private TextField flowControl;

    @FXML
    private TextField totalNotional;

    @FXML
    private TextField rejectRate;

    @FXML
    private TextField totalOrders;

    @FXML
    private TextField cancelRate;

    @FXML
    private TextField tradeRate;

    @FXML
    private TextField threshold;

    @FXML
    private TextField var1;

    @FXML
    private TextField var2;

    @FXML
    private TextField var3;

    @FXML
    private TextField var4;

    @FXML
    private TextField var5;

    @FXML
    private Label message;

    @FXML
    private void submit(){
        message.setText("");
        Risk risk = new Risk();
        risk.setFlowControl(Integer.parseInt(flowControl.getText().trim()));
        risk.setTotalNotional(Integer.parseInt(totalNotional.getText().trim()));
        risk.setOrdersTotal(Integer.parseInt(totalOrders.getText().trim()));
        risk.setTradeRate(Double.parseDouble(tradeRate.getText().trim()));
        risk.setCancelRate(Double.parseDouble(cancelRate.getText().trim()));
        risk.setRejectRate(Double.parseDouble(rejectRate.getText().trim()));
        risk.setThreshold(Integer.parseInt(threshold.getText().trim()));
        risk.setVar1(var1.getText().trim());
        risk.setVar2(var2.getText().trim());
        risk.setVar3(var3.getText().trim());
        risk.setVar4(var4.getText().trim());
        risk.setVar5(var5.getText().trim());

        RestResponse ret = controllerManager.upsetRisk(risk);
        if(ret.isSuccess()){
            message.setText("设置成功!");
        }else{
            message.setText(ret.getResult());
        }
    }

    @FXML
    private void exit(){
        dialog.close();
    }
}
