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

    static Role staticRole = Role.UNAUTHORIZED;

    public static Role getStaticRole() {
        return staticRole;
    }

    public static void setStaticRole(Role role){
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
