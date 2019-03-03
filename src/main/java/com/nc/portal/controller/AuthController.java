package com.nc.portal.controller;

import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public String getAuth(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "auth";
    }

    @PostMapping
    public String submit(@ModelAttribute UserDTO userDTO,Model model) {
        accountService.getRole(userDTO);
        String role = userDTO.getRole();
        /*
         * 1.Нужно вывести окошко с ошибкой!
         * 2.Возможно сделать флаг или вынести UserDTO и проверять роль
         */
        if (role == null) {
            model.addAttribute("errorMessage", "incorrect name or password");
            return "auth";
        }
        switch (role) {
            case "ADMIN":
                return "redirect:/admin";
            case "OPERATOR":
                return "redirect:/operator";
            case "DRIVER":
                return "redirect:/driver";
            case "CUSTOMER":
                return "redirect:/customer";
            default:
                return "auth";
        }
    }



}


