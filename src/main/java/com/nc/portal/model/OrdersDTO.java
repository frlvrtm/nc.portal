package com.nc.portal.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrdersDTO {
    private int idOrder;
    private String pointFrom;
    private String pointTo;
    private double cost;
    //private String weight;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String driver;
    private String customer;
    private double price;

    public OrdersDTO() {
    }

    public OrdersDTO(String customer, String pointFrom, String pointTo, String description) {
        this.customer = customer;
        this.pointFrom = pointFrom;
        this.pointTo = pointTo;
        this.description = description;
    }

    public static String[] orderCust = {"OPEN", "ASSIGNED", "INPROGRESS", "CLOSED", "RESOLVED"};

    public static String[] weightCat = {"less one kg", "1-3 kg", "3-10 kg", "10-20 kg"};

}
