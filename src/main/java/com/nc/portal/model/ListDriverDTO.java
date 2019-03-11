package com.nc.portal.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListDriverDTO {

    private List<DriverDTO> list;

    public ListDriverDTO() {
        this.list = new ArrayList<>();
    }

    public ListDriverDTO(List<DriverDTO> list) {
        this.list = list;
    }

    public void add(DriverDTO driverDTO) {
        this.list.add(driverDTO);
    }
}
