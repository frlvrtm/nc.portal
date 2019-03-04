package com.nc.portal.model;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String role;

    static String staticRole = "UNAUTHORIZED";

    public static String getStaticRole() {
        return staticRole;
    }

    public static void setStaticRole(String role){
        staticRole = role;
    }
}
