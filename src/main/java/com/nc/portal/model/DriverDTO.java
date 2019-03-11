package com.nc.portal.model;

import lombok.Data;

import java.util.List;

@Data
public class DriverDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String carNumber;
    private String realPoint;


    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof DriverDTO)) return false;

        DriverDTO driver = (DriverDTO) other;

        //костыль
        boolean b = true;
        if (this.carNumber == null)
            if (driver.carNumber == null)
                b = false;
            else return false;

        //
        //\\

        if (!this.username.equals(driver.getUsername()))
            return false;
        if (!this.firstName.equals(driver.getFirstName()))
            return false;
        if (!this.lastName.equals(driver.getLastName()))
            return false;
        if (!this.phone.equals(driver.getPhone()))
            return false;
        if (b)
            if (!this.carNumber.equals(driver.getCarNumber()))
                return false;
        return true;
    }

}
