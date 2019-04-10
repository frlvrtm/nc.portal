package com.nc.portal.model;

public class RoleThreadLocal {

    private static final ThreadLocal authThreadLocal = new ThreadLocal();

    public static <T> void setRole(T data) {
        authThreadLocal.set(data);
    }

    public static <T> T getRole() {
        return (T) authThreadLocal.get();
    }

    public static void remove() {
        authThreadLocal.remove();
    }
}
