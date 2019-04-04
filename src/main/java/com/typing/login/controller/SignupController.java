package com.typing.login.controller;

import com.typing.login.domain.model.GroupOrder;
import com.typing.login.domain.model.SignupForm;
import com.typing.login.domain.model.User;
import com.typing.login.domain.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {
    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String getSignUp(@ModelAttribute SignupForm form, Model model) {
        return "login/signup";
    }

    @PostMapping("/signup")
    public String postSignUp(@ModelAttribute @Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return getSignUp(form, model);
        }
        System.out.println("[" + form + "]");

        User user = new User();
        user.setUserId(form.getUserId());
        user.setPassword(form.getPassword());
        user.setUserName(form.getUserName());
        user.setRole("ROLE_GENERAL");
        boolean result = userService.insert(user);

        if (result == true) {
            System.out.println("[insert success]");
        } else {
            System.out.println("[insert failure]");
        }
        return "redirect:/login";
    }

    @ExceptionHandler(DataAccessException.class)
    public String dataAccessExceptionHandler(DataAccessException e, Model model) {

        model.addAttribute("error", "内部サーバーエラー(DB) : ExceptionHandler");
        model.addAttribute("message", "SignupControllerでDataAccessExceptionが発生しました");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e, Model model) {

        model.addAttribute("error", "内部サーバーエラー(DB) : ExceptionHandler");
        model.addAttribute("message", "SignupControllerでExceptionが発生しました");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return "error";
    }
}
