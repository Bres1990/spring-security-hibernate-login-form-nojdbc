package com.bres.siodme.web.controller;

import com.bres.siodme.web.model.User;
import com.bres.siodme.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.SQLException;


/**
 * Created by Adam on 2016-07-04.
 */

@Controller
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String logout) throws SQLException {

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin() {
        return "admin";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
    public String transfer(Model model, Principal principal) {
        User transferData = userRepository.findByUsername(principal.getName());
        model.addAttribute(transferData);
        return "data";
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String confirmData(@Valid @ModelAttribute("transferData") User transferData,
                              BindingResult result,
                              Model model) {
        model.addAttribute("firstName", transferData.getFirstName());
        model.addAttribute("lastName", transferData.getLastName());
        model.addAttribute("address", transferData.getAddress());
        model.addAttribute("accountNo", transferData.getAccountNo());

        return "data";
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public String confirmTransfer() {
        return "transfer";
    }
}
