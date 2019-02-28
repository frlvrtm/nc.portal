package com.nc.portal.controller;

import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/reg")
public class RegController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public String getReg(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "registration";
    }

    @PostMapping
    public String submit(@ModelAttribute UserDTO userDTO, Model model) {
        accountService.createUser(userDTO);
        System.out.println("UserDTO created: " + userDTO.getUsername());
        model.addAttribute("userDTO", userDTO);
        return "auth";
    }

}
