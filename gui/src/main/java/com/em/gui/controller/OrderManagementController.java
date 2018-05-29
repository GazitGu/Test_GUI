package com.em.gui.controller;

import com.em.gui.ScreenConfiguration;
import com.em.gui.dialog.DialogController;
import com.em.gui.dialog.FXMLDialog;
import com.em.gui.property.OrderProperty;
import com.em.gui.property.UserProperty;
import com.em.model.Product;
import com.em.model.ProductTree;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by guchong on 5/26/2018.
 */
public class OrderManagementController implements DialogController {
    private ScreenConfiguration screens;
    private FXMLDialog dialog;
    @Autowired
    private ControllerManager controllerManager;
    @FXML
    private TreeView<ProductTree> productTreeView;
    @FXML
    private TableView<OrderProperty> orderManagementTable;

    @FXML
    private TableColumn<OrderProperty,Integer> orderId;
    @FXML
    private TableColumn<OrderProperty,String> symbol;
    @FXML
    private TableColumn<OrderProperty,String> ordStatus;
    @FXML
    private TableColumn<OrderProperty,String> side;
    @FXML
    private TableColumn<OrderProperty,Double> quantity;
    @FXML
    private TableColumn<OrderProperty,Double> price;
    @FXML
    private TableColumn<OrderProperty,Double> cumQty;
    @FXML
    private TableColumn<OrderProperty,Double> avgPx;
    @FXML
    private TableColumn<OrderProperty,String> product;
    @FXML
    private TableColumn<OrderProperty,String> positionEffect;

    private List<Product> products;

    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    public OrderManagementController(ScreenConfiguration screens) {
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
            if(selectedItem.getValue().getProdcutId()>0){
                switchProductInfo(selectedItem.getValue().getProduct());
            }
//            logger.info("Selected Node {} with id {} : ",selectedItem.getValue().getProduct(),selectedItem.getValue().getProdcutId());
        });
        orderId.setCellValueFactory(new PropertyValueFactory("orderId"));
        symbol.setCellValueFactory(new PropertyValueFactory("symbol"));
        ordStatus.setCellValueFactory(new PropertyValueFactory("ordStatus"));
        ordStatus.setCellFactory(new Callback<TableColumn<OrderProperty, String>, TableCell<OrderProperty, String>>() {
            @Override
            public TableCell<OrderProperty, String> call(TableColumn<OrderProperty, String> param) {
                TableCell<OrderProperty, String> tableCell = new TableCell<OrderProperty, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (!empty) {
                            if(item==null){
                                return;
                            }
                            switch(item){
                                case "NEW":setText("新建");break;
                                case "FILLED":setText("全部成交");break;
                                case "PARTIALLY_FILLED":setText("部分成交");break;
                                case "CANCELED":setText("已撤单");break;
                                default:setText(item);break;
                            }
                        }else{
                            setText(null);
                        }
                    }
                };
                tableCell.setAlignment(Pos.CENTER);
                return tableCell;
            }
        });
        side.setCellValueFactory(new PropertyValueFactory("side"));
        side.setCellFactory(new Callback<TableColumn<OrderProperty, String>, TableCell<OrderProperty, String>>() {
            @Override
            public TableCell<OrderProperty, String> call(TableColumn<OrderProperty, String> param) {
                TableCell<OrderProperty, String> tableCell = new TableCell<OrderProperty, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (!empty) {
                            if(item==null){
                                return;
                            }
                            if("b".equalsIgnoreCase(item)){
                                setText("买");
                            }else{
                                setText("卖");
                            }
                        }else{
                            setText(null);
                        }
                    }
                };
                tableCell.setAlignment(Pos.CENTER);
                return tableCell;
            }
        });
        quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        price.setCellValueFactory(new PropertyValueFactory("price"));
        cumQty.setCellValueFactory(new PropertyValueFactory("cumQty"));
        avgPx.setCellValueFactory(new PropertyValueFactory("avgPx"));
        product.setCellValueFactory(new PropertyValueFactory("product"));
        positionEffect.setCellValueFactory(new PropertyValueFactory("positionEffect"));
        positionEffect.setCellFactory(new Callback<TableColumn<OrderProperty, String>, TableCell<OrderProperty, String>>() {
            @Override
            public TableCell<OrderProperty, String> call(TableColumn<OrderProperty, String> param) {
                TableCell<OrderProperty, String> tableCell = new TableCell<OrderProperty, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (!empty) {
                            if(item==null){
                                return;
                            }
                            switch (item){
                                case "OPEN":setText("开仓");break;
                                case "CLOSE":setText("平仓");break;
                                case "CLOSE_TODAY":setText("开今");break;
                                default:setText(item);break;
                            }
                        }else{
                            setText(null);
                        }
                    }
                };
                tableCell.setAlignment(Pos.CENTER);
                return tableCell;
            }
        });
        orderManagementTable.setItems(controllerManager.getOrderPropertyObservableList());
        addRightClickEvent();
    }

    private void addRightClickEvent(){
        orderManagementTable.setRowFactory(new Callback<TableView<OrderProperty>, TableRow<OrderProperty>>() {
            @Override
            public TableRow<OrderProperty> call(TableView<OrderProperty> tableView) {
                final TableRow<OrderProperty> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem removeMenuItem = new MenuItem("撤单");
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int orderId = row.getItem().getOrderId();
                        controllerManager.cancelOrder(orderId);
                    }
                });
                contextMenu.getItems().addAll(removeMenuItem);
                // Set context menu on row, but use a binding to make it only show for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu)null)
                                .otherwise(contextMenu)
                );
                return row ;
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
            }
        }
        productTreeView.setRoot(root);
        root.setExpanded(true);
    }

    private void switchProductInfo(String prod) {

    }
}
