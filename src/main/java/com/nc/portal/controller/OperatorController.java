package com.nc.portal.controller;

import com.nc.portal.model.*;
import com.nc.portal.service.CustomerService;
import com.nc.portal.service.OrdersService;
import com.nc.portal.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;

@Controller
public class OperatorController {

    @Autowired
    OrdersService ordersService;
    @Autowired
    CustomerService customerService;

    /* @InitBinder//конвертер для пустой строки в дату
     public void initBinder(WebDataBinder binder) {
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         dateFormat.setLenient(false);
         // true passed to CustomDateEditor constructor means convert empty String to null
         binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//пустая дата
         binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));//пустая строка
     }*/
    public String decodeName(String dec) {
        byte[] base64Token = dec.split(" ")[1].getBytes(Charset.forName("US-ASCII"));
        byte[] decoded = Base64.getDecoder().decode(base64Token);
        String token = new String(decoded);
        return token.split(":")[0];
    }

    @RequestMapping(value = "/operator", method = RequestMethod.GET)
    public String getPage() {
        return "operator/index";
    }

    @RequestMapping(value = "/operator/orders", method = RequestMethod.GET)
    public String getPage(HttpServletRequest request, Model model) {
        Role role = CookieUtil.getRole(request);
        if (role == Role.OPERATOR) {
            List<OrdersDTO> ordersDTO = ordersService.getAllOrders(request);
            model.addAttribute("orders", ordersDTO);
            model.addAttribute("order", new OrdersDTO());
            return "operator/operator";
        } else
            return "error/access-denied";
    }

    @RequestMapping(value = "/operator/assigned", method = RequestMethod.POST)
    public String assignedDriver(@RequestParam("driver") String driver,
                                 @RequestParam("idOrder") String orderId,
                                 HttpServletRequest request,
                                 Model model) {
        Role role = CookieUtil.getRole(request);
        if (role == Role.OPERATOR) {
            //----------------------------------------------------------------------
            int code = ordersService.updateStatus(request, driver, orderId, OrderStatus.ASSIGNED);
            //if code = ACCEPTED то прошло успешно, иначе какая-то ошибка
            //----------------------------------------------------------------------
            if (code == 201) {
                List<OrdersDTO> ordersDTO = ordersService.getAllOrders(request);
                model.addAttribute("orders", ordersDTO);
                model.addAttribute("order", new OrdersDTO());
                model.addAttribute("Message", "ACCEPTED! Order status has been changed to assigned.");
            } else {
                model.addAttribute("errorMessage", "Error! Data not saved.");
            }
            return "operator/operator";
        } else
            return "error/access-denied";
    }

    @RequestMapping(value = "/operator/close", method = RequestMethod.POST)
    public String closeOrder(@RequestParam("idOrder") String orderId,
                             HttpServletRequest request,
                             Model model) {
        Role role = CookieUtil.getRole(request);
        if (role == Role.OPERATOR) {
//----------------------------------------------------------------------
            int code = ordersService.updateStatus(request, null, orderId, OrderStatus.CLOSED);
            //if code = ACCEPTED то прошло успешно, иначе какая-то ошибка
//----------------------------------------------------------------------
            if (code == 201) {
                List<OrdersDTO> ordersDTO = ordersService.getAllOrders(request);
                model.addAttribute("orders", ordersDTO);
                model.addAttribute("order", new OrdersDTO());
                model.addAttribute("Message", "ACCEPTED! Order status has been changed to closed.");
            } else {
                model.addAttribute("errorMessage", "Error! Data not saved.");
            }
            return "operator/operator";
        } else
            return "error/access-denied";
    }

    @RequestMapping(value = "/operator/profile", method = RequestMethod.GET)
    public String getProfile(Model model) {
        String username = decodeName(AuthThreadLocal.getAuth());
        UserDTO userDTO = customerService.getUserByName(username, "OPERATOR");
        model.addAttribute("user", userDTO);
        return "operator/profile";
    }

    @RequestMapping(value = "/operator/profile", method = RequestMethod.POST)
    public String setProfile(@RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("phone") String phone,
                             Model model) {
        String username = decodeName(AuthThreadLocal.getAuth());
        int code = customerService.updateUser(username, firstName, lastName, phone, "OPERATOR");
        if (code == 201) {
            model.addAttribute("Message", "Data saved successfully!");
        } else {
            model.addAttribute("errorMessage", "Error! Data not saved.");
        }
        return "redirect:/operator/profile";
    }
}
