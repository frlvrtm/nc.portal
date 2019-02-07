package com.nc.portal.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/*
* test by Modest
* */
@Data
@Getter
@Setter
public class Account {

    /**
     * Логин
     */
    private String username;
    /**
     * Пароль
     */
    private String password;

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
