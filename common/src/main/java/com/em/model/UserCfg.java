package com.em.model;

/**
 * Created by guchong on 5/25/2018.
 */
public class UserCfg {
    private String username;
    private String macs;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMacs() {
        return macs;
    }

    public void setMacs(String macs) {
        this.macs = macs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCfg userCfg = (UserCfg) o;

        return username != null ? username.equals(userCfg.username) : userCfg.username == null;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
