package com.nc.portal.model;

import lombok.Data;

@Data
public class PriceDto {
    private String pointFrom;
    private String pointTo;

    public PriceDto(String pointFrom, String pointTo) {
        this.pointFrom = pointFrom;
        this.pointTo = pointTo;
    }
}
