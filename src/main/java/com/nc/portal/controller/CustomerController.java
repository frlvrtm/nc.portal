package com.nc.portal.controller;

import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerController {
    @GetMapping
    @RequestMapping(value = "/customer")
    public String getPage() {
        if (UserDTO.staticRole.equals(Role.CUSTOMER))
            return "customer";
        else
            return "error/access-denied";
    }
}
