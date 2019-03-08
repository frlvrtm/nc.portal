package com.nc.portal.model;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String role;

    //для оператора и водителя
    private String firstName;
    private String lastName;
    private String phone;
    //для водителя
    private String carNumber;
    private String realPoint;

    static String staticRole = "UNAUTHORIZED";

    public static String getStaticRole() {
        return staticRole;
    }

    public static void setStaticRole(String role){
        staticRole = role;
    }

    static String basicAuth = "";

    public static String getBasicAuth() {
        return basicAuth;
    }

    public static void setBasicAuth(String basic){
        basicAuth = basic;
    }
}
