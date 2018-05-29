package com.em.service;

import com.em.model.RiskSetting;

/**
 * Created by guchong on 5/22/2018.
 */
public interface RiskSettingService {
    RiskSetting save(RiskSetting riskSetting);

    RiskSetting findByRiskId(int riskId);
}
