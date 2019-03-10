package com.nc.portal.controller;

import com.nc.portal.model.OrdersDTO;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping(value = "/operator")
public class OperatorController {

    @Autowired
    OrdersService ordersService;
    @InitBinder//конвертер для пустой строки в дату
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        // true passed to CustomDateEditor constructor means convert empty String to null
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//пустая дата
        binder.registerCustomEditor(String.class,new StringTrimmerEditor(true));//пустая строка
    }
    @GetMapping
    public String getPage(Model model) {
        if (UserDTO.getStaticRole().equals("OPERATOR")) {
            OrdersDTO[] ordersDTO= ordersService.getAllOrders();
            model.addAttribute("orders", ordersDTO);
            model.addAttribute("order", new OrdersDTO());
            return "operator";
        }
        model.addAttribute("userDTO", new UserDTO());
        return "redirect:/auth";

    }
    @PostMapping
    public String updateOrder(@ModelAttribute OrdersDTO order ,Model model) {
        if (UserDTO.getStaticRole().equals("OPERATOR")) {
            ordersService.updateOrders(order);
            OrdersDTO[] ordersDTO= ordersService.getAllOrders();
            model.addAttribute("orders", ordersDTO);
            model.addAttribute("order", new OrdersDTO());
            return "operator";
        }
        model.addAttribute("userDTO", new UserDTO());
        return "redirect:/auth";

    }
}
