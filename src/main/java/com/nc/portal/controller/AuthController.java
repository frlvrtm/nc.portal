package com.nc.portal.controller;

import com.nc.portal.model.Role;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

//todo nikita
//удалить старые комменты
//ctrl+alt+l - автоформатирование - юзать !!!
//auth.html - xmlns:th="http://www.thymeleaf.org" - удалить, div with ${errorMessage} - пока удалить, комменты про javascript - удалить
//com.nc.portal.controller.AuthController#getAuth - model из input пока удалить (ошибка будет падать),
//добавить в параметры HttpServletRequest request, HttpServletResponse response будем их использовать в запросах
//в com.nc.portal.service.AuthService создать метод getToken; принимать login, password, возвращать token
//тут com.nc.portal.service.AuthService#createHttpHeaders что то похожее на правду..
//создать класс com.netcracker.demo.models.AuthThreadLocalTO с private static final полем java.lang.ThreadLocal
//здесь com.nc.portal.controller.AuthController#getAuth сначала получать token, потом ставить токет в это поле..
//потом делать вызов getRole с request, response из input

@Controller
public class AuthController {

    @Autowired
    AuthService authService;

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String getAuthPage() {
        UserDTO.staticRole = Role.UNAUTHORIZED;
        return "auth";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String getAuth(@RequestParam("username") String username,
                          @RequestParam("password") String password, Model model,HttpServletRequest resp) {
        String token = authService.getRole(username,password);
        if (UserDTO.staticRole.equals(Role.UNAUTHORIZED)) {
            model.addAttribute("errorMessage", "incorrect name or password");
            return "auth";
        } else {
            resp.getSession().setAttribute("x-auth-token",token);
            return "redirect:/" + UserDTO.staticRole.getUrl();
        }
    }
}


