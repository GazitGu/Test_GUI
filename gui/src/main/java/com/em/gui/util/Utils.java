package com.em.gui.util;

import javafx.stage.Stage;

/**
 * Created by guchong on 5/23/2018.
 */
public class Utils {
    public static final int MAIN_WINDOW_HEIGHT = 65;
    public static final int CHILD_WINDOW_POS_Y = 50;
    public static void resetChildWindowPosY(Stage stage) {
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal.intValue() > CHILD_WINDOW_POS_Y) {
                stage.setY(CHILD_WINDOW_POS_Y);
            }
        });
    }
}
