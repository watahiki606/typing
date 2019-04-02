package com.typing.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import com.typing.login.domain.model.SignupForm;
import com.typing.login.domain.model.User;
import com.typing.login.domain.service.UserService;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    // ホーム画面のGET用
    @GetMapping("/home")
    public String getHome(Model model) {

        // th:include="[path] :: [th:fragment's value]"
        model.addAttribute("contents", "login/home :: home_contents");

        return "login/homeLayout";
    }

    // ユーザー一覧画面のGET用
    @GetMapping("/userList")
    public String getUserList(Model model) {

        model.addAttribute("contents", "login/userList :: userList_contents");
        List<User> userList = userService.selectMany();
        model.addAttribute("userList", userList);
        int count = userService.count();
        model.addAttribute("userListCount", count);

        return "login/homeLayout";
    }

    // ユーザー詳細画面のGET用(動的URL)
    @GetMapping("/userDetail/{id:.+}") // {variable:regexp}
    public String getUserDetail(@ModelAttribute SignupForm form, Model model, @PathVariable("id") String userId) {

        System.out.println("userId = " + userId);

        model.addAttribute("contents", "login/userDetail :: userDetail_contents");

        if (userId != null && userId.length() > 0) {
            User user = userService.selectOne(userId);
            form.setUserId(user.getUserId());
            form.setUserName(user.getUserName());
            model.addAttribute("signupForm", form);
        }

        return "login/homeLayout";
    };

    // ユーザー更新用
    @PostMapping(value = "/userDetail", params = "update")
    public String postUserDetailUpdate(@ModelAttribute SignupForm form, Model model) {

        System.out.println("update button is pushed");

        User user = new User();
        user.setUserId(form.getUserId());
        user.setPassword(form.getPassword());
        user.setUserName(form.getUserName());

        boolean result = userService.updateOne(user);

        if (result = true) {
            model.addAttribute("result", "更新しました");
        } else {
            model.addAttribute("result", "更新できませんでした");
        }

        return getUserList(model);
    }

    // ユーザー削除用
    @PostMapping(value = "/userDetail", params = "delete")
    public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model) {

        System.out.println("delete button is pushed");

        boolean result = userService.deleteOne(form.getUserId());

        if (result == true) {
            model.addAttribute("result", "削除しました");
        } else {
            model.addAttribute("result", "削除できませんでした");
        }

        return getUserList(model);

    }

    @PostMapping("/logout")
    public String postLogout() {

        return "redirect:/login";
    }

}