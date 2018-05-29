package com.em.gui;

import com.em.gui.controller.*;
import com.em.gui.dialog.FXMLDialog;
import com.em.gui.util.Utils;
import com.em.model.User;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.annotation.*;

/**
 * Created by guchong on 5/23/2018.
 */
@Configuration
@PropertySource({"classpath:application.properties"})
@Lazy
public class ScreenConfiguration {
    private Stage primaryStage;
    private double xOffset = 100;
    private double yOffset = 10;
    private double logWinPosX = 0;
    private double logWinPosY = 0;
    public void setLogWinPosX(double logWinPosX) {
        this.logWinPosX = logWinPosX;
    }

    public void setLogWinPosY(double logWinPosY) {
        this.logWinPosY = logWinPosY;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showScreen(Parent screen) {
        primaryStage.setScene(new Scene(screen, 1000, 500));
        primaryStage.show();
    }

    @Bean
    public FXMLDialog managerLoginDialog() {
        LoginController managerLoginController = managerLoginController();
        FXMLDialog loginDialog = new FXMLDialog(managerLoginController, getClass().getResource("Login.fxml"), primaryStage, StageStyle.UNDECORATED);;
        loginDialog.getIcons().add(new Image(getClass().getResourceAsStream("/em.png")));
        Parent root = loginDialog.getRoot();
        AnchorPane loginPane =  (AnchorPane)root.lookup("#AnchorPane");
        loginPane.setOnMousePressed((MouseEvent event) -> {
            event.consume();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        loginPane.setOnMouseDragEntered((ae) -> {
            root.setCursor(Cursor.OPEN_HAND);
            //borderPane.setStyle("-fx-border-color:red;-fx-border-width:2;-fx-border-style:solid;");
            ae.consume();
        });
        loginPane.setOnMouseDragged((MouseEvent event) -> {
            event.consume();
            logWinPosX = event.getScreenX() - xOffset;
            loginDialog.setX(logWinPosX);
            if (event.getScreenY() - yOffset < 0) {
                logWinPosY = 0;
                loginDialog.setY(0);
            } else {
                logWinPosY = event.getScreenY() - yOffset;
                loginDialog.setY(logWinPosY);
            }
            root.setCursor(Cursor.CLOSED_HAND);
        });
        logWinPosX = loginDialog.getX();
        logWinPosY = loginDialog.getY();
        managerLoginController.init();
        return loginDialog;
    }

    @Bean
    public LoginController managerLoginController() {
        LoginController managerLoginController = new LoginController(this);
        return managerLoginController;
    }

    @Bean
    public FXMLDialog userCreateDialog() {
        UserCreateController userEstablishController  = userCreateController();
        FXMLDialog optionParamDialog = new FXMLDialog(userEstablishController, getClass().getResource("UserCreate.fxml"),primaryStage, StageStyle.DECORATED);
        Utils.resetChildWindowPosY(optionParamDialog);
        userEstablishController.start();
        optionParamDialog.setTitle("用户创建");
        optionParamDialog.getIcons().add(new Image(getClass().getResourceAsStream("/em.png")));
        return optionParamDialog;
    }

    @Bean
    public UserCreateController userCreateController() {
        UserCreateController userEstablishController =  new UserCreateController(this);
        return userEstablishController;
    }

    @Bean
    public FXMLDialog changePwdDialog() {
        ChangePwdController changePwdController  = changePwdController();
        FXMLDialog changePwdDialog = new FXMLDialog(changePwdController, getClass().getResource("ChangePwd.fxml"),primaryStage, StageStyle.DECORATED);
        Utils.resetChildWindowPosY(changePwdDialog);
        changePwdController.start();
        changePwdDialog.setTitle("密码修改");
        changePwdDialog.getIcons().add(new Image(getClass().getResourceAsStream("/em.png")));
        return changePwdDialog;
    }

    @Bean
    public ChangePwdController changePwdController() {
        ChangePwdController changePwdController =  new ChangePwdController(this);
        return changePwdController;
    }

    @Bean
    public FXMLDialog globalRiskSettingDialog() {
        GlobalRiskSettingController globalRiskSettingController  = globalRiskSettingController();
        FXMLDialog globalRiskSettingDialog = new FXMLDialog(globalRiskSettingController, getClass().getResource("GlobalRiskSetting.fxml"),primaryStage, StageStyle.DECORATED);
        Utils.resetChildWindowPosY(globalRiskSettingDialog);
        globalRiskSettingController.start();
        globalRiskSettingDialog.setTitle("全局风控");
        globalRiskSettingDialog.getIcons().add(new Image(getClass().getResourceAsStream("/em.png")));
        return globalRiskSettingDialog;
    }

    @Bean
    public GlobalRiskSettingController globalRiskSettingController() {
        GlobalRiskSettingController globalRiskSettingController =  new GlobalRiskSettingController(this);
        return globalRiskSettingController;
    }

    @Bean
    public FXMLDialog productDialog() {
        ProductController productController  = productController();
        FXMLDialog productDialog = new FXMLDialog(productController, getClass().getResource("Product.fxml"),primaryStage, StageStyle.DECORATED);
        Utils.resetChildWindowPosY(productDialog);
        productController.start();
        productDialog.setTitle("产品");
        productDialog.getIcons().add(new Image(getClass().getResourceAsStream("/em.png")));
        return productDialog;
    }

    @Bean
    public ProductController productController() {
        ProductController productController =  new ProductController(this);
        return productController;
    }

    @Bean
    public FXMLDialog tradingAccountSettingDialog() {
        TradingAccountSettingController tradingAccountSettingController  = tradingAccountSettingController();
        FXMLDialog tradingAccountSettingDialog = new FXMLDialog(tradingAccountSettingController, getClass().getResource("TradingAccountSetting.fxml"),primaryStage, StageStyle.DECORATED);
        Utils.resetChildWindowPosY(tradingAccountSettingDialog);
        tradingAccountSettingController.start();
        tradingAccountSettingDialog.setTitle("交易密码设置");
        tradingAccountSettingDialog.getIcons().add(new Image(getClass().getResourceAsStream("/em.png")));
        return tradingAccountSettingDialog;
    }

    @Bean
    public TradingAccountSettingController tradingAccountSettingController() {
        TradingAccountSettingController tradingAccountSettingController =  new TradingAccountSettingController(this);
        return tradingAccountSettingController;
    }

    //new order panel
    @Bean
    public FXMLDialog newOrderDialog() {
        NewOrderController newOrderController  = newOrderController();
        FXMLDialog newOrderDialog = new FXMLDialog(newOrderController, getClass().getResource("NewOrder.fxml"),primaryStage, StageStyle.DECORATED);
        Utils.resetChildWindowPosY(newOrderDialog);
        newOrderController.start();
        newOrderDialog.setTitle("下单");
        newOrderDialog.getIcons().add(new Image(getClass().getResourceAsStream("/em.png")));
        return newOrderDialog;
    }

    @Bean
    public NewOrderController newOrderController() {
        NewOrderController newOrderController =  new NewOrderController(this);
        return newOrderController;
    }
    //order management panel
    @Bean
    public FXMLDialog orderManagementDialog() {
        OrderManagementController orderManagementController  = orderManagementController();
        FXMLDialog orderManagementDialog = new FXMLDialog(orderManagementController, getClass().getResource("OrderManagement.fxml"),primaryStage, StageStyle.DECORATED);
        Utils.resetChildWindowPosY(orderManagementDialog);
        orderManagementController.start();
        orderManagementDialog.setTitle("订单管理");
        orderManagementDialog.getIcons().add(new Image(getClass().getResourceAsStream("/em.png")));
        return orderManagementDialog;
    }

    @Bean
    public OrderManagementController orderManagementController() {
        OrderManagementController orderManagementController =  new OrderManagementController(this);
        return orderManagementController;
    }

    public FXMLDialog  mainScreen(User user, String host) {
        MainController managerMainController = managerMainController(user);
        FXMLDialog mainDialog =  new FXMLDialog(managerMainController, getClass().getResource("Main.fxml"), primaryStage, StageStyle.DECORATED);
        Parent root = mainDialog.getRoot();
        MenuBar menuBar = (MenuBar)root.lookup("#menuBar");
        mainDialog.setTitle("Light交易系统 "+user.getUsername()+"@"+host);
        mainDialog.getIcons().add(new Image(getClass().getResourceAsStream("/em.png")));

        mainDialog.setAlwaysOnTop(true);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        mainDialog.setX(0); //x > 100?x:100);
        mainDialog.setY(1);
        mainDialog.setWidth(primaryScreenBounds.getWidth());
        mainDialog.setHeight(65);
        mainDialog.setMaxHeight(65);
        menuBar.setOnMousePressed((MouseEvent event) -> {
            event.consume();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        menuBar.setOnMouseDragEntered((ae) -> {
            root.setCursor(Cursor.OPEN_HAND);
            //borderPane.setStyle("-fx-border-color:red;-fx-border-width:2;-fx-border-style:solid;");
            ae.consume();
        });
        menuBar.setOnMouseDragged((MouseEvent event) -> {
            event.consume();
            mainDialog.setX(event.getScreenX() - xOffset);
            if (event.getScreenY() - yOffset < 0) {
                mainDialog.setY(0);
            } else {
                mainDialog.setY(event.getScreenY() - yOffset);
            }
            root.setCursor(Cursor.CLOSED_HAND);
        });
        managerMainController.start();
        return mainDialog;
    }
    @Bean
    public MainController managerMainController(User user) {
        return new MainController(this,user);
    }
}
