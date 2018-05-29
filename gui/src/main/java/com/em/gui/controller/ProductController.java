package com.em.gui.controller;

import com.em.gui.ScreenConfiguration;
import com.em.gui.dialog.DialogController;
import com.em.gui.dialog.FXMLDialog;
import com.em.model.Product;
import com.em.model.ProductTree;
import com.em.model.RestResponse;
import com.em.model.User;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.controlsfx.control.CheckComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by guchong on 5/25/2018.
 */
public class ProductController implements DialogController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private ScreenConfiguration screens;
    private final Set<String> existsItems = new HashSet<>();
    private FXMLDialog dialog;
    @FXML
    private TreeView<ProductTree> productTreeView;

    private List<Product> products;
    @Autowired
    private ControllerManager controllerManager;

    @FXML
    private TextField productName;

    @FXML
    private TextField tradingAccount;

    @FXML
    private CheckComboBox<String> trader;

    @FXML
    private CheckComboBox<String> riskController;

    @FXML
    private TextArea description;
    @FXML
    private Label message;
    @FXML
    private Label header;

    private boolean newProduct = true;


    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    public ProductController(ScreenConfiguration screens) {
        this.screens = screens;
    }

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
            if(selectedItem.getValue().getProdcutId()==0){
                newProduct = true;
                newProduct();
            }else{
                newProduct = false;
                switchProductInfo(selectedItem.getValue().getProduct());
            }
            logger.info("Selected Node {} with id {} : ",selectedItem.getValue().getProduct(),selectedItem.getValue().getProdcutId());
        });
        trader.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                logger.info("selected ---- {}",trader.getCheckModel().getCheckedItems().size());
                if(trader.getCheckModel().getCheckedItems().size()>5){
                    logger.info("{}",trader.getCheckModel().getCheckedItems());
                    trader.getCheckModel().clearCheck(trader.getCheckModel().getCheckedItems().get(6));
                }
            }
        });
        riskController.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                if(riskController.getCheckModel().getCheckedItems().size()>5){
                    riskController.getCheckModel().clearCheck(riskController.getCheckModel().getCheckedItems().get(6));
                }
            }
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
                existsItems.add(product.getProduct());
            }
        }
        productTreeView.setRoot(root);
        root.setExpanded(true);
        List<User> users = controllerManager.getAllUser();
        trader.getItems().clear();
        riskController.getItems().clear();
        if(users!=null){
            for(User user : users){
                logger.info("User:{}",user);
                if(user.getRole()==3){
                    trader.getItems().addAll(user.getUsername());
                }else if(user.getRole()==2){
                    riskController.getItems().addAll(user.getUsername());
                }
            }
        }

    }

    private void switchProductInfo(String prod) {
        message.setText("");
        header.setText("修改产品参数");
        for(Product product: products){
            if(product.getProduct().equalsIgnoreCase(prod)){
                productName.setText(prod);
                productName.setEditable(false);
                tradingAccount.setText(product.getTradingAccount());
                trader.getCheckModel().clearChecks();
                for(String str : product.getUsers().split(",")){
                    trader.getCheckModel().check(str);
                }
                riskController.getCheckModel().clearChecks();
                for(String str : product.getRiskControllers().split(",")){
                    riskController.getCheckModel().check(str);
                }
                description.setText(product.getDescription());
                break;
            }
        }
    }

    private void newProduct(){
        header.setText("开立产品");
        message.setText("");
        productName.setText("");
        productName.setDisable(false);
        tradingAccount.setText("");
        trader.getCheckModel().clearChecks();
        riskController.getCheckModel().clearChecks();
        description.setText("");
    }

    @FXML
    public void submit(){
        Product product = new Product();
        product.setProduct(productName.getText().trim());
        product.setTradingAccount(tradingAccount.getText().trim());
        StringBuilder sb = new StringBuilder();
        int i=1;
        int j = trader.getCheckModel().getCheckedItems().size();
        for(String trader : trader.getCheckModel().getCheckedItems()){
            sb.append(trader).append(",");
            if(i<j){
                sb.append(",");
            }
            i++;
        }
        product.setUsers(sb.toString());
        j = riskController.getCheckModel().getCheckedItems().size();
        i=0;
        sb.setLength(0);
        for(String riskCont : riskController.getCheckModel().getCheckedItems()){
            sb.append(riskCont).append(",");
            if(i<j){
                sb.append(",");
            }
            i++;
        }
        product.setRiskControllers(sb.toString());
        product.setDescription(description.getText().trim());
        RestResponse restResponse = controllerManager.upsetProduct(product);
        if(restResponse.isSuccess()){
            if(newProduct){
                message.setText("开立产品成功!");
            }else{
                message.setText("更新产品成功!");
            }
            newProduct();
            if(!existsItems.contains(product.getProduct())){
                ProductTree productTree = new ProductTree(product.getProduct(),1);
                productTreeView.getRoot().getChildren().add(new TreeItem<>(productTree));
                existsItems.add(product.getProduct());
            }

        }else{
            message.setText(restResponse.getResult());
        }
    }

}
