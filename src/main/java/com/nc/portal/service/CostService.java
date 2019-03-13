package com.nc.portal.service;

import org.springframework.stereotype.Service;

@Service
public class CostService implements GlobalConstants {

    private String URL_COST;

    public CostService() {
        this.URL_COST = URL + "cost";
    }

}
