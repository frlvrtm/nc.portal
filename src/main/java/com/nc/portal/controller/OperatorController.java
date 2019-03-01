package com.nc.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OperatorController {
    @GetMapping
    @RequestMapping(value = "/operator")
    public String getPage() {
        return "operator";
    }
}
