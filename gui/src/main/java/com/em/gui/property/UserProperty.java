package com.em.gui.property;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by guchong on 5/23/2018.
 */
public class UserProperty {
    private StringProperty username = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private IntegerProperty role = new SimpleIntegerProperty();
    private StringProperty macs = new SimpleStringProperty();

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getRole() {
        return role.get();
    }

    public IntegerProperty roleProperty() {
        return role;
    }

    public void setRole(int role) {
        this.role.set(role);
    }

    public String getMacs() {
        return macs.get();
    }

    public StringProperty macsProperty() {
        return macs;
    }

    public void setMacs(String macs) {
        this.macs.set(macs);
    }
}
