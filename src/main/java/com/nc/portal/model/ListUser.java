package com.nc.portal.model;

import lombok.Data;

import java.util.List;

@Data
public class ListUser {
    private List<UserDTO> list;

    public ListUser(List<UserDTO> list) {
        this.list = list;
    }
}
