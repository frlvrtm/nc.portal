package com.nc.portal.controller;

import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    AccountService accountService;

    @GetMapping
    @RequestMapping(value = "/auth")
    public String getAuth(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "auth";
    }

    @PostMapping
    @RequestMapping(value = "/page")
    public String submit(@ModelAttribute UserDTO userDTO) {
        accountService.getRole(userDTO);
        String role = userDTO.getRole();

        System.out.println(role);
        /*
         * 1.Нужно вывести окошко с ошибкой!
         * 2.Возможно сделать флаг или вынести UserDTO и проверять роль
         */
        if (role == null)
            return "auth";

        switch (role) {
            case "ADMIN":
                return "admin";
            case "OPERATOR":
                return "operator";
            case "DRIVER":
                return "driver";
            case "CUSTOMER":
                return "customer";
            default:
                return "auth";
        }
    }



}


