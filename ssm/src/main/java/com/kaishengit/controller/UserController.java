package com.kaishengit.controller;

import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.ws.ResponseWrapper;
import java.util.List;

/**
 * Created by SPL on 2017/7/14 0014.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list(Model model){

        List<User> userList = userService.findAll();
        for(User user:userList){
            System.out.println(user.getAddress());
        }
        model.addAttribute("userList",userList);

        return "user/list";
    }

}
