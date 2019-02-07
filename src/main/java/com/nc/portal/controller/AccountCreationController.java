package com.nc.portal.controller;

import com.nc.portal.model.Account;
import com.nc.portal.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/auth")
public class AccountCreationController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public String FormRegister(Model model) {
        model.addAttribute("account", new Account());
        return "auth";
    }

    @PostMapping
    public String button_Reg(@ModelAttribute Account account) {
        accountService.AccCreationRequest(account);
        System.out.println("Account created: " + account.getUsername());
        return "result";
    }

}