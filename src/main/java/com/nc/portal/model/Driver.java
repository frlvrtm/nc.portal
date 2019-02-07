package com.nc.portal.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Driver {

    private Long id;
    /**
     * Имя
     */
    private String firstName;
    /**
     * Фамилия
     */
    private String secondName;
    /**
     * Отчество
     */
    private String middleName;

    public Driver() {
    }

    public Driver(Long id, String firstName, String secondName, String middleName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
    }

}
