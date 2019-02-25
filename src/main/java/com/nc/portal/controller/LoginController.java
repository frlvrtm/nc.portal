package com.nc.portal.controller;

import com.nc.portal.model.Account;
import com.nc.portal.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/auth")
public class LoginController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public String FormAuthentication(Model model) {
        model.addAttribute("account", new Account());
        return "auth";
    }

    @PostMapping
    public void submit(@ModelAttribute Account account) {
        accountService.AuthRequest(account);
        System.out.println("Hello, " + account.getUsername());
    }

}


