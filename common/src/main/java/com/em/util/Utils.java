package com.em.util;

import com.em.model.Product;
import com.em.model.Risk;
import com.em.model.StrategyConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by guchong on 5/25/2018.
 */
public class Utils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Risk getDefaultRisk(){
        Risk risk = new Risk();
        risk.setFlowControl(300);
        risk.setThreshold(800);
        risk.setTotalNotional(100_000_000);
        risk.setOrdersTotal(40_000);
        risk.setTradeRate(0.2);
        risk.setCancelRate(0.4);
        risk.setRejectRate(0.3);
        return risk;
    }

    public static void flush(String file, Risk risk, List<Product> products){

        File f = new File(file);
        if(!f.exists()){
            File parent = f.getParentFile();
            if(!parent.exists()){
                parent.mkdirs();
            }
        }
        try {
            StrategyConfiguration strategyConfiguration = new StrategyConfiguration(risk,products);
            FileUtils.write(f,mapper.writeValueAsString(strategyConfiguration),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
