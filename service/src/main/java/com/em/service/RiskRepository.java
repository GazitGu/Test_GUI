package com.em.service;

import com.em.model.RiskSetting;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by guchong on 5/22/2018.
 */
public interface RiskRepository extends CrudRepository<RiskSetting, Integer> {
    List<RiskSetting> findAll();

    RiskSetting save(RiskSetting riskSetting);

    RiskSetting findByRiskId(int riskId);

    void delete(RiskSetting riskSetting);
}
