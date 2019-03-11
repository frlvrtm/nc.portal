package com.nc.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class MainController {

    @GetMapping
    public String getMain() {
        return "main";
    }

    @GetMapping(value = "/cost_page")
    public String getCost_Page() {
        return "cost_page";
    }

}
