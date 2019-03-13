package com.nc.portal.controller;

import com.nc.portal.model.DriverDTO;
import com.nc.portal.model.ListDriverDTO;
import com.nc.portal.model.Role;
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
        if (UserDTO.getStaticRole().equals(Role.ADMIN)) {
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
        if (UserDTO.getStaticRole().equals(Role.ADMIN)) {
            return "redirect:/admin/page";
        } else {
            String message = "";
            if (UserDTO.getStaticRole().equals(Role.UNAUTHORIZED)) {
                message = "Incorrect name or password";
            } else {
                message = "Incorrect role " + UserDTO.getStaticRole().toString();
                accountService.logout();
                UserDTO.setStaticRole(Role.UNAUTHORIZED);
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
        if (UserDTO.getStaticRole().equals(Role.ADMIN)) {
            List<DriverDTO> list = adminService.getAllDrivers();
            model.addAttribute("drivers", new ListDriverDTO(list));
            model.addAttribute("userDTO", new UserDTO());
            model.addAttribute("cars", adminService.getFreeCars());
/*            ListDriverDTO list = new ListDriverDTO();
            for (int i = 1; i <= 2; i++) {
                list.add(new DriverDTO());
            }
            model.addAttribute("form", list);*/
            return "admin";
        } else
            return "error/access-denied";
    }

    @PostMapping("/page")
    public String addUser(@ModelAttribute UserDTO userDTO, Model model) {
        if (UserDTO.getStaticRole().equals(Role.ADMIN)) {
            int responseCode = adminService.createEmployee(userDTO);
            if (responseCode == 406) {
                model.addAttribute("errorMessage", "Name already taken");
            } else {
                model.addAttribute("infoMessage", "User added");
                model.addAttribute("userDTO", new UserDTO());
            }
            List<DriverDTO> list = adminService.getAllDrivers();
            model.addAttribute("drivers", new ListDriverDTO(list));
            return "admin";
        } else
            return "error/access-denied";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("drivers") ListDriverDTO drivers, Model model) {
        if (UserDTO.getStaticRole().equals(Role.ADMIN)) {
            int code = adminService.updateUsers(drivers);
            return "redirect:/admin/page";
        } else
            return "error/access-denied";
    }
}