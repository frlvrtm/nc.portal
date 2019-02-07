package com.nc.portal.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Date;

@Data
@Getter
@Setter
public class Order {

    private Long id;
    /**
     * Пункт загрузки
     */
    private Point point1;
    /**
     * Пункт выгрузки
     */
    private Point point2;
    /**
     * Время регистрации заказа
     */
    private Date date;

    public Order() {
    }

    public Order(Long id, Point point1, Point point2, Date date) {
        this.id = id;
        this.point1 = point1;
        this.point2 = point2;
        this.date = date;
    }

}
