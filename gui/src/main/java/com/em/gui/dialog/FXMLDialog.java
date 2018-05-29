package com.em.gui.dialog;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;

/**
 * Created by guchong on 5/23/2018.
 */
public class FXMLDialog extends Stage {
    private Parent root;
    private final DialogController controller;
    private final URL fxml;

    public Parent getRoot() {
        return root;
    }

    public FXMLDialog(DialogController controller, URL fxml, Window owner) {
        this(controller, fxml, owner,  StageStyle.DECORATED);
    }

    public FXMLDialog(final DialogController controller, URL fxml, Window owner, StageStyle style) {
        super(style);
        this.controller = controller;
        this.fxml = fxml;
        initOwner(owner);
        initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader(fxml);
        try {
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> aClass) {
                    return controller;
                }
            });
            controller.setDialog(this);
            root = (Parent) loader.load();
            setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void lookupNode(String node) {
        if(root != null) {
            root.lookup(node);
        }
    }

    public void reload(){
        FXMLLoader loader = new FXMLLoader(fxml);
        try {
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> aClass) {
                    return controller;
                }
            });
            controller.setDialog(this);
            root = (Parent) loader.load();
            setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DialogController getController() {
        return controller;
    }

}
