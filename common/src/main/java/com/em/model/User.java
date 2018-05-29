package com.em.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by guchong on 5/22/2018.
 */
@Entity
@Table(name = "User")
public class User implements Serializable {
    @Id
    @Column(length = 30)
    private String username;
    @Column(length = 100)
    private String description;
    @Column(length = 100)
    private String password;
    @Column(length = 100)
    private String macs;
    @Column(length = 10)
    private int role; //1 admin 2 risk controller 3 trader

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMacs() {
        return macs;
    }

    public void setMacs(String macs) {
        this.macs = macs;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username != null ? username.equals(user.username) : user.username == null;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", macs='" + macs + '\'' +
                ", role=" + role +
                '}';
    }
}
