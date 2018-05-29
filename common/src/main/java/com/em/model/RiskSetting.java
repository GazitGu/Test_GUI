package com.em.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by guchong on 5/22/2018.
 */
@Entity
@Table(name = "RiskSetting")
public class RiskSetting {
    @Id
    @Column(length = 10)
    private int riskId;
    @Column(length = 1000)
    private String content;

    public int getRiskId() {
        return riskId;
    }

    public void setRiskId(int riskId) {
        this.riskId = riskId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RiskSetting that = (RiskSetting) o;

        return riskId == that.riskId;
    }

    @Override
    public int hashCode() {
        return riskId;
    }

    @Override
    public String toString() {
        return "RiskSetting{" +
                "riskId=" + riskId +
                ", content='" + content + '\'' +
                '}';
    }
}
