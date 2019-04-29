package com.nc.portal.controller;

import com.nc.portal.controller.validators.ListUserValidator;
import com.nc.portal.model.*;
import com.nc.portal.service.AdminService;
import com.nc.portal.service.AuthService;
import com.nc.portal.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    AuthService authService;

    @Autowired
    AdminService adminService;

    @Autowired
    ListUserValidator listUserValidator;

    //Словарь для добавления аттрибутов в модель
    private Map<String, String> dictionary = new HashMap<>();

    @InitBinder("form")
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
        if (request.getMethod().equals("POST")) {
            //проверка на дубликаты
            binder.setValidator(listUserValidator);
            //из "" сдеть null
            binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        }
    }

    /**
     * Метод для отображения страницы входа для админа
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String logInForAdmin(HttpServletRequest request) {
        Role role = CookieUtil.getRole(request);
        if (role == Role.ADMIN) {
            return "redirect:/admin/page";
        }
        return "authadmin";
    }

    /**
     * Форма для проверки введенных данных для админа (доделать идею)
     */
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String getAuth(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam("username") String username,
                          @RequestParam("password") String password,
                          Model model) {
        authService.getRole(request, response, username, password);
        Role role = CookieUtil.getRole(request);
        if (role == Role.ADMIN) {
            return "redirect:/admin/page";
        } else {
            String message = "";
            if (role == Role.UNAUTHORIZED) {
                message = "Incorrect name or password";
            } else {
                message = "Incorrect role " + UserDTO.staticRole.toString();
                //authService.logout();
                //UserDTO.staticRole = Role.UNAUTHORIZED;
            }
            model.addAttribute("errorMessage", message);
            return "authadmin";
        }
    }

    /*
     * Страница админа
     */
    @RequestMapping(value = "/admin/page", method = RequestMethod.GET)
    public String getPageAdd(HttpServletRequest request,
                             Model model) {
//        Role role = CookieUtil.getRole(request);
//        if (role == Role.ADMIN) {
//            //все сотрудники
//            model.addAttribute("form", new ListUser(adminService.getAllEmployees(request)));
//            //форма для заполнения нового юзера
//            model.addAttribute("userDTO", new UserDTO());
//            //форма для заполнения новой машины
//            model.addAttribute("carDTO", new CarDTO());
//            //Список всех машин без водителей
//            model.addAttribute("carsFree", adminService.getFreeCars(request));
//            //Список всех машин
//            model.addAttribute("carsAll", adminService.getAllCars(request));
//            //alert
//            for (String key : dictionary.keySet()) {
//                model.addAttribute(key, dictionary.get(key));
//            }
//            //очистить словарь
//            dictionary = new HashMap<>();
//            return "admin/admin";
//        } else
//            return "error/access-denied";
        return "admin/admin";
    }

    @RequestMapping(value = "/admin/showdrivers", method = RequestMethod.GET)
    public String getDriv(HttpServletRequest request, Model model) {
        Role role = CookieUtil.getRole(request);
        if (role == Role.ADMIN) {
            model.addAttribute("form", new ListUser(adminService.getAllEmployees(request)));
            return "admin/showDrivers";
        } else
            return "error/access-denied";
    }

    @RequestMapping(value = "/admin/showoperators", method = RequestMethod.GET)
    public String getOper(HttpServletRequest request, Model model) {
        Role role = CookieUtil.getRole(request);
        if (role == Role.ADMIN) {
            model.addAttribute("form", new ListUser(adminService.getAllEmployees(request)));
            return "admin/showOperators";
        } else
            return "error/access-denied";
    }

    @RequestMapping(value = "/admin/showcars", method = RequestMethod.GET)
    public String getCar(HttpServletRequest request, Model model) {
        Role role = CookieUtil.getRole(request);
        if (role == Role.ADMIN) {
            model.addAttribute("form", new ListUser(adminService.getAllEmployees(request)));
            model.addAttribute("carsFree", adminService.getFreeCars(request));
            model.addAttribute("carsAll", adminService.getAllCars(request));
            return "admin/showCars";
        } else
            return "error/access-denied";
    }

    @RequestMapping(value = "/admin/createuser", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request,
                          @ModelAttribute UserDTO userDTO, Model model) {
        Role role = CookieUtil.getRole(request);
        if (role == Role.ADMIN) {
            int code = adminService.createEmployee(request, userDTO);
            switch (code) {
                case 201:
                    model.addAttribute("Message", "Data savedUser added");
                    break;
                case 406:
                    model.addAttribute("errorMessage", "Name already taken");
                    break;
                case -1:
                    model.addAttribute("errorMessage", "Unexpected error");
                    break;
            }
            return "redirect:/admin/createuser";
        } else
            return "error/access-denied";
    }

    /*
    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public String update(HttpServletRequest request,
                         @ModelAttribute("form") @Valid ListUser listUser,
                         BindingResult bindingResult,
                         Model model) {
        Role role = CookieUtil.getRole(request);
        if (role == Role.ADMIN) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("errorMessage", "1 car selected for 2 drivers");
                return "redirect:/admin/update";
            }
            List<UserDTO> list = listUser.getList();
            int code = adminService.updateUsers(request, list);
            switch (code) {
                case 0:
                    model.addAttribute("Message", "No changes, no need to update");
                    break;
                case 201:
                    model.addAttribute("Message", "Updated");
                    break;
                case 400:
                    model.addAttribute("errorMessage", "Bad request");
                    break;
                case -1:
                    model.addAttribute("errorMessage", "Unexpected error");
                    break;
            }
            return "redirect:/admin/update";
        } else
            return "error/access-denied";
    }

    @RequestMapping(value = "/admin/delete", method = RequestMethod.POST)
    public String delete(HttpServletRequest request,
                         @ModelAttribute("form") ListUser listUser,
                         @RequestParam String username,
                         Model model) {
        Role role = CookieUtil.getRole(request);
        if (role == Role.ADMIN) {
            adminService.deleteEmployee(request, username);
            model.addAttribute("Message", username + " deleted");
            return "redirect:/admin/delete";
        } else
            return "error/access-denied";
    }
    */

    @RequestMapping(value = "/admin/createcar", method = RequestMethod.POST)
    public String addCar(HttpServletRequest request,
                         @ModelAttribute() CarDTO carDTO, Model model) {
        Role role = CookieUtil.getRole(request);
        if (role == Role.ADMIN) {
            int code = adminService.addCar(request, carDTO);
            switch (code) {
                case 201:
                    model.addAttribute("Message", "Data savedUser added");
                    break;
                case 406:
                    model.addAttribute("errorMessage", "Number already taken");
                    break;
                case -1:
                    model.addAttribute("errorMessage", "Unexpected error");
                    break;
            }
            return "redirect:/admin/createcar";
        } else
            return "error/access-denied";
    }

    @RequestMapping(value = "/admin/createuser", method = RequestMethod.GET)
    public String createUser(@ModelAttribute() UserDTO userDTO, Model model) {
        model.addAttribute("userDTO", userDTO);
        return "admin/createUser";
    }

    @RequestMapping(value = "/admin/createcar", method = RequestMethod.GET)
    public String createCar(@ModelAttribute() CarDTO carDTO, Model model) {
        model.addAttribute("carDTO", carDTO);
        return "admin/createCar";
    }


}