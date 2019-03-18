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
}
