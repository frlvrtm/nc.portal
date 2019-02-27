package com.nc.portal.model;

import lombok.Data;

/*
* test by Modest
* */
@Data
public class UserDTO {

    /**
     * Логин
     */
    private String username;
    /**
     * Пароль
     */
    private String password;
    private String role;
    public UserDTO() {
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
        role="";
    }

}
