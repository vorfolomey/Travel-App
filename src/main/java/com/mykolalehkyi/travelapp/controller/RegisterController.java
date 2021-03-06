package com.mykolalehkyi.travelapp.controller;

import com.mykolalehkyi.travelapp.dao.UserDetailsDao;
import com.mykolalehkyi.travelapp.model.User;
import com.mykolalehkyi.travelapp.service.SecurityService;
import com.mykolalehkyi.travelapp.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class RegisterController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserDetailsDao userDetailsDao;

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String register(HttpServletRequest request, HttpServletResponse response) {
        User user= new User();
        user.setFirstname(request.getParameter("firstname"));
        user.setLastname(request.getParameter("lastname"));
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setPasswordConfirm(request.getParameter("passwordConfirm"));
        user.setEmail(request.getParameter("email"));

        if(user.getFirstname().isEmpty() || user.getLastname().isEmpty() || user.getUsername().isEmpty() ||
                user.getPassword().isEmpty() || user.getPasswordConfirm().isEmpty() || user.getEmail().isEmpty()|| (!user.getPasswordConfirm().equals(user.getPassword())) )
        {
            System.out.println("something need to fulfill");
            //TODO return that something need to fulfill
        }
        else {
            if (userDetailsDao.findUserByUsername(user.getUsername()) != null) {
                throw new RuntimeException("User already exists");
            }
            userDetailsDao.save(user);
            securityService.autoLogin(user.getUsername(), user.getPasswordConfirm());
            return "redirect:/";
        }

        return "registration";
    }
}
