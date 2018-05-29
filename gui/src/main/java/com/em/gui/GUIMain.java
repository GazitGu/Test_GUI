package com.em.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.*;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * Created by guchong on 5/23/2018.
 */
@ComponentScan(basePackages = {"com.em.gui"}, excludeFilters = {})
public class GUIMain extends Application{
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        String path = GUIMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            String decodedPath = URLDecoder.decode(path, "UTF-8");
            System.out.println("base path:"+decodedPath);
            InputStream is = new FileInputStream(decodedPath.replaceAll("gui.jar","application.properties"));
            Properties properties = new Properties();
            properties.load(is);
            System.getProperties().putAll(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        primaryStage = stage;
        ApplicationContext context =
                new AnnotationConfigApplicationContext(GUIMain.class);
        ScreenConfiguration screens = context.getBean(ScreenConfiguration.class);
        screens.setPrimaryStage(stage);
        screens.managerLoginDialog().show();
    }
}
