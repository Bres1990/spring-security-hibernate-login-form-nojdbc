package com.bres.siodme.web.controller;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;


/**
 * Created by Adam on 2016-07-04.
 */

@Controller
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LoginController {

//    @RequestMapping(value = "/registration", method = RequestMethod.GET)
//    public String registration(Model model) {
//        model.addAttribute("userForm", new User());
//
//        return "registration";
//    }
//
//    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    public String registration(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult) {
//        //userValidator.validate(userForm, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
//
//        userService.save(userForm);
//        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
//
//        return "redirect:/welcome";
//    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) throws SQLException {
//        Connection con = null;
//        con = DriverManager.getConnection("jdbc:hsqldb:file:C:/Users/Adam/IdeaProjects/Sagiton_6/src/main/resources/db"
//                , "root", "bres");

//        userRepository.save(new User("ADMINISTRATOR", "administration"));
//        userRepository.save(new User("username", "password"));

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin() {
        return "admin";
    }
}
