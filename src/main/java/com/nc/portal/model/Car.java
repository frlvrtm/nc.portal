package com.nc.portal.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Car {

    private Long id;
    /**
     * Марка автомобиля
     */
    private String brand;
    /**
     * Модель автомобиля
     */
    private String model;
    /**
     * Номерной знак
     */
    private String number;
    /**
     * Цвет автомобиля
     */
    private String color;

    public Car() {
    }

    public Car(Long id, String brand, String model, String number, String color) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.number = number;
        this.color = color;
    }

}
