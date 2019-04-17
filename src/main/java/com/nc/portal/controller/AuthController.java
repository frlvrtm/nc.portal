package com.nc.portal.controller;

import com.nc.portal.model.Role;
import com.nc.portal.model.RoleThreadLocal;
import com.nc.portal.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    @Autowired
    AuthService authService;

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String getAuthPage() {
        return "auth/auth";
    }


    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String getAuth(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          Model model,
                          HttpServletRequest request,
                          HttpServletResponse response) {

        //System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getId());
        authService.getRole(request, response, username, password);

        //Role role = CookieUtil.getRole(request);
        Role role = RoleThreadLocal.getRole();
        if (role == Role.UNAUTHORIZED) {
            model.addAttribute("errorMessage", "incorrect name or password");
            return "auth/auth";
        } else {
            return "redirect:/" + role.getUrl();
        }
    }
}


