package com.nc.portal.controller;

import com.nc.portal.model.DriverDTO;
import com.nc.portal.model.ListDriverDTO;
import com.nc.portal.model.UserDTO;
import com.nc.portal.service.AccountService;
import com.nc.portal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/admin")
@Controller
public class AdminController {

    @Autowired
    AccountService accountService;

    @Autowired
    AdminService adminService;

    /**
     * Метод для отображения страницы входа для админа
     *
     * @param model
     * @return
     */
    @GetMapping()
    public String logInForAdmin(Model model) {
        if (UserDTO.getStaticRole().equals("ADMIN")) {
            return "redirect:/admin/page";
        }
        model.addAttribute("userDTO", new UserDTO());
        return "authadmin";
    }

    /**
     * Форма для проверки введенных данных для админа
     *
     * @param userDTO
     * @param model
     * @return
     */
    @PostMapping
    public String submit(@ModelAttribute UserDTO userDTO, Model model) {
        accountService.getRole(userDTO);
        if (UserDTO.getStaticRole().equals("ADMIN")) {
            return "redirect:/admin/page";
        } else {
            String message = "";
            if (UserDTO.getStaticRole().equals("UNAUTORIZED")) {
                message = "Incorrect name or password";
            } else {
                message = "Incorrect role " + UserDTO.getStaticRole();
                accountService.logout();
                UserDTO.setStaticRole("UNAUTORIZED");
            }
            model.addAttribute("errorMessage", message);
            return "authadmin";
        }
    }

    /**
     * Страница админа
     *
     * @param model
     * @return
     */
    @GetMapping("/page")
    public String getPageAddUser(Model model) {
        if (UserDTO.getStaticRole().equals("ADMIN")) {
            model.addAttribute("drivers", adminService.getAllDrivers());
            model.addAttribute("userDTO", new UserDTO());
            model.addAttribute("cars", adminService.getFreeCars());
            return "admin";
        } else
            return "error/access-denied";
    }

    @PostMapping("/page")
    public String addUser(@ModelAttribute UserDTO userDTO, Model model) {
        if (UserDTO.getStaticRole().equals("ADMIN")) {
            int responseCode = adminService.createEmployee(userDTO);
            if (responseCode == 406) {
                model.addAttribute("errorMessage", "Name already taken");
            } else {
                model.addAttribute("infoMessage", "User added");
                model.addAttribute("userDTO", new UserDTO());
            }
            model.addAttribute("drivers", adminService.getAllDrivers());
            return "admin";
        } else
            return "error/access-denied";
    }

    @PostMapping("/page1")
    public String update(@ModelAttribute ListDriverDTO drivers, Model model) {
        if (UserDTO.getStaticRole().equals("ADMIN")) {
            //DriverDTO[] listDrivers = (DriverDTO[])drivers;
           // model.
            List<DriverDTO> list = drivers.getList();
            return "redirect:/admin/page";
        } else
            return "error/access-denied";
    }
}