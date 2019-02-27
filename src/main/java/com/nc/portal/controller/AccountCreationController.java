package com.nc.portal.controller;

import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/reg")
public class AccountCreationController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public String FormRegister(Model model) {
        model.addAttribute("account", new UserDTO());
        return "registration";
    }

    @PostMapping
    public void button_Reg(@ModelAttribute UserDTO userDTO) {
       // accountService.getRole(userDTO);
        System.out.println("UserDTO created: " + userDTO.getUsername());
    }

}
