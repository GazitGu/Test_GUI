package com.em.gui.controller;

import com.em.gui.ScreenConfiguration;
import com.em.gui.dialog.DialogController;
import com.em.gui.dialog.FXMLDialog;
import com.em.model.Product;
import com.em.model.ProductTree;
import com.em.model.RestResponse;
import com.em.model.TradingAccount;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;

import java.util.List;

/**
 * Created by guchong on 5/24/2018.
 */
public class TradingAccountSettingController implements DialogController {
    private static final Logger logger = LoggerFactory.getLogger(TradingAccountSettingController.class);
    private ScreenConfiguration screens;
    private FXMLDialog dialog;
    @Autowired
    private ControllerManager controllerManager;
    @FXML
    private TreeView<ProductTree> productTreeView;

    private List<Product> products;

    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    public TradingAccountSettingController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    @FXML
    private TextField productName;

    @FXML
    private TextField tradingAccount;

    @FXML
    private PasswordField password;

    @FXML
    private Label message;

    public void start(){
        reopenInit();
        productTreeView.setCellFactory(new Callback<TreeView<ProductTree>, TreeCell<ProductTree>>() {
            @Override public TreeCell<ProductTree> call(TreeView<ProductTree> treeView) {
                return new TreeCell<ProductTree>() {
                    @Override protected void updateItem(final ProductTree pair, boolean empty) {
                        super.updateItem(pair, empty);

                        if (!empty && pair != null) {
                            setText(pair.getProduct());
                        } else {
                            setText(null);
                            setGraphic(null);
                        }

                    }
                };
            }
        });
        productTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            TreeItem<ProductTree> selectedItem = (TreeItem<ProductTree>) newValue;
            if(selectedItem.getValue().getProdcutId()>0){
                switchProductInfo(selectedItem.getValue().getProduct());
            }
            logger.info("Selected Node {} with id {} : ",selectedItem.getValue().getProduct(),selectedItem.getValue().getProdcutId());
        });
    }

    @Override
    public void reopenInit() {
        products = controllerManager.getAllProducts();
        ProductTree productTree = new ProductTree(controllerManager.getLoginUser().getUsername(),0);
        TreeItem<ProductTree> root = new TreeItem<>(productTree);
        if(products!=null){
            for(Product product : products){
                productTree = new ProductTree(product.getProduct(),1);
                root.getChildren().add(new TreeItem<>(productTree));
            }
        }
        productTreeView.setRoot(root);
        root.setExpanded(true);
    }

    private void switchProductInfo(String prod) {
        message.setText("");
        for(Product product: products){
            if(product.getProduct().equalsIgnoreCase(prod)){
                productName.setText(prod);
                tradingAccount.setText(product.getTradingAccount());
                break;
            }
        }
    }

    @FXML
    public void submit(){
        RestResponse restResponse = controllerManager.updateTradingAccount(productName.getText().trim(),password.getText().trim());
        if(restResponse.isSuccess()){
            message.setText("交易密码设置成功!");
        }else{
            message.setText(restResponse.getResult());
        }
    }
}
