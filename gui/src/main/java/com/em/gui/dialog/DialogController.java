package com.em.gui.dialog;

/**
 * Created by guchong on 5/23/2018.
 */
public interface DialogController {

    void setDialog(FXMLDialog dialog);

    /**
     * Singleton windows need use it do the initialization
     */
    default void reopenInit() {
        //do nothing here, will override
    }

    /**
     * start method to do some initializations
     */
    default void start() {
        //do nothing here, need override
    }

    default void open(StringBuilder stringBuilder,String... param){

    }
}
