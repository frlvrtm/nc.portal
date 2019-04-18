package com.nc.portal.controller;

import com.nc.portal.model.OrderStatus;
import com.nc.portal.model.OrdersDTO;
import com.nc.portal.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Controller
public class OperatorController {

    @Autowired
    OrdersService ordersService;

   /* @InitBinder//конвертер для пустой строки в дату
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        // true passed to CustomDateEditor constructor means convert empty String to null
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//пустая дата
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));//пустая строка
    }*/

    @RequestMapping(value = "/operator", method = RequestMethod.GET)
    public String getPage(Model model) {
        OrdersDTO[] ordersDTO = ordersService.getAllOrders();
        model.addAttribute("orders", ordersDTO);
        model.addAttribute("order", new OrdersDTO());
        return "operator/operator";
    }

    @RequestMapping(value = "/operator/assigned", method = RequestMethod.POST)
    public String assignedDriver(@RequestParam("driver") String driver,
                                 @RequestParam("orderId") String orderId,
                                 HttpServletRequest request,
                                 Model model) {
//----------------------------------------------------------------------
        int code = ordersService.updateStatus(request, driver, orderId, OrderStatus.ASSIGNED);
        //if code = ACCEPTED то прошло успешно, иначе какая-то ошибка
//----------------------------------------------------------------------
        OrdersDTO[] ordersDTO = ordersService.getAllOrders();
        model.addAttribute("orders", ordersDTO);
        model.addAttribute("order", new OrdersDTO());
        return "operator/operator";
    }

    @RequestMapping(value = "/operator/close", method = RequestMethod.POST)
    public String closeOrder(@RequestParam("orderId") String orderId,
                             HttpServletRequest request,
                             Model model) {
//----------------------------------------------------------------------
        int code = ordersService.updateStatus(request, null, orderId, OrderStatus.CLOSE);
        //if code = ACCEPTED то прошло успешно, иначе какая-то ошибка
//----------------------------------------------------------------------
        OrdersDTO[] ordersDTO = ordersService.getAllOrders();
        model.addAttribute("orders", ordersDTO);
        model.addAttribute("order", new OrdersDTO());
        return "operator/operator";
    }
}
