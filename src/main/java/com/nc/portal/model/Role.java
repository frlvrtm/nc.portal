package com.nc.portal.model;

import lombok.Data;
import lombok.Getter;

public enum Role {
    UNAUTHORIZED("auth"),
    ADMIN("admin"),
    DRIVER("driver"),
    OPERATOR("operator"),
    CUSTOMER("customer");

    @Getter
    String url;

    Role(String url) {
        this.url = url;
    }

    Role() {
    }
}
