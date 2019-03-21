package com.nc.portal.model;

import lombok.Data;

import java.sql.Date;

@Data
public class OrdersDTO {
    private int idOrder;
    private String pointFrom;
    private String pointTo;
    private double cost;
    private String weight;
    private String description;
    private Date startTime;
    private Date endTime;
    private String status;
    private String driver;
    private String customer;
    private double price;

    public static String[] orderCust = {"OPEN", "ASSIGNED", "INPROGRESS", "CLOSED", "RESOLVED"};

    public static String[] weightCat = {"less one kg", "1-3 kg", "3-10 kg", "10-20 kg"};

}
