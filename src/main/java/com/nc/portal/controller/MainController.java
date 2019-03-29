package com.nc.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//todo artem
//перенести портал на порт 8081
//MainController переименовать в StartPageController; + переименовать метод, html file, css file;
//@RequestMapping в MainController перенести над методом, @GetMapping - убрать

@Controller
@RequestMapping(value = "/")
public class MainController {

    @GetMapping
    public String getMain() {
        return "main";
    }

}
