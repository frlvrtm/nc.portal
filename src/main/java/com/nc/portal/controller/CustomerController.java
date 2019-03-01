package com.nc.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerController {
    @GetMapping
    @RequestMapping(value = "/customer")
    public String getPage( ) {
        return "customer";
    }
}
