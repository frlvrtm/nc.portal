package com.nc.portal.model;

import lombok.Data;

import java.sql.Date;

@Data
public class OrdersDTO {
    private int id_order;
    private String point_from;
    private String point_to;
    private double cost;
    private String weight;
    private String description;
    private Date start_time;
    private Date end_time;
    private String status;
    private String driver;
    private String customer;
    private double price;
    public enum orderStat {
        OPEN,
        ASSIGNED,
        INPROGRESS,
        CLOSED,
        RESOLVED
    }
    public static String[] weightCat ={"less one kg","1-3 kg","3-10 kg","10-20 kg"};

}
