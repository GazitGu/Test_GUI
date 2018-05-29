package com.em.gui.controller;

import com.em.gui.ScreenConfiguration;
import com.em.gui.dialog.DialogController;
import com.em.gui.dialog.FXMLDialog;
import com.em.model.Order;
import com.em.model.Product;
import com.em.model.RestResponse;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by guchong on 5/26/2018.
 */
public class NewOrderController implements DialogController {
    private ScreenConfiguration screens;
    private FXMLDialog dialog;
    @Autowired
    private ControllerManager controllerManager;

    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    public NewOrderController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    @FXML
    private TextField symbol;
    @FXML
    private TextField quantity;
    @FXML
    private TextField price;
    @FXML
    private ComboBox<String> orderType;
    @FXML
    private ComboBox<Product> product;
    @FXML
    private RadioButton buy;
    @FXML
    private RadioButton sell;
    @FXML
    private RadioButton open;
    @FXML
    private RadioButton close;
    @FXML
    private RadioButton closeToday;
    @FXML
    private Label message;

    public void start(){
        orderType.getItems().addAll("限价","市价");
        List<Product> products = controllerManager.getAllProducts();
        product.getItems().addAll(products);
        product.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product object) {
                return object.getProduct();
            }

            @Override
            public Product fromString(String string) {
                return null;
            }
        });
        product.getSelectionModel().selectFirst();
        buy.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    sell.setSelected(false);
                }
            }
        });
        sell.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    buy.setSelected(false);
                }
            }
        });
        open.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    close.setSelected(false);
                    closeToday.setSelected(false);
                }
            }
        });
        close.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    open.setSelected(false);
                    closeToday.setSelected(false);
                }
            }
        });
        closeToday.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    close.setSelected(false);
                    open.setSelected(false);
                }
            }
        });
        clear();
    }

    @Override
    public void reopenInit() {
        List<Product> products = controllerManager.getAllProducts();
        product.getItems().clear();
        product.getItems().addAll(products);
        product.getSelectionModel().selectFirst();
        clear();
    }

    public void clear(){
        symbol.setText("");
        quantity.setText("");
        price.setText("");
        buy.setSelected(true);
        open.setSelected(true);
        orderType.getSelectionModel().selectFirst();
    }

    @FXML
    public void submit(){
        message.setText("");
        Order order = new Order();
        order.setSymbol(symbol.getText().trim());
        order.setQuantity(Double.parseDouble(quantity.getText().trim()));
        order.setPrice(Double.parseDouble(price.getText().trim()));
        if(buy.isSelected()){
            order.setSide("B");
        }else{
            order.setSide("S");
        }
        order.setProduct(product.getSelectionModel().getSelectedItem().getProduct());
        if(open.isSelected()){
            order.setPositionEffect("OPEN");
        }else if(close.isSelected()){
            order.setPositionEffect("CLOSE");
        }else if(closeToday.isSelected()){
            order.setPositionEffect("CLOSE_TODAY");
        }
        RestResponse ret = controllerManager.newOrder(order);
        if(ret.isSuccess()){
            message.setText("订单发送成功!");
            clear();
        }else{
            message.setText(ret.getResult());
        }
    }

}
