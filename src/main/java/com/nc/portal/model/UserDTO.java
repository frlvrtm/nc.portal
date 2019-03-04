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

    /**
     * Роль,статическая, чтобы проверять какой пользователь авторизован последний
     */
    private static String role = "UNAUTHORIZED";

    public static String getRole() {
        return role;
    }

    public static void setRole(String staticRole) {
        role = staticRole;
    }

    public UserDTO() {
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
        // role="";
    }

}
