package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list(@RequestParam(required = false,defaultValue = "1",name = "p") Integer pageNo ,
                       @RequestParam(required = false) String userName,
                       @RequestParam(required = false) String address,
                       @RequestParam(required = false) Integer min,
                       @RequestParam(required = false) Integer max,
                       Model model){

        userName = StringUtils.toUTF8(userName);
        address = StringUtils.toUTF8(address);

        PageInfo<User> pageList = userService.findByParam(pageNo,userName,address,min,max);

        model.addAttribute("page",pageList);
        model.addAttribute("success","state");
        model.addAttribute("userName",userName);
        model.addAttribute("address",address);
        model.addAttribute("min",min);
        model.addAttribute("max",max);
        System.out.println("/list");
        return "user/list";
    }

    @GetMapping("/add")
    public String add(){
        return "user/add";
    }

    @PostMapping("/add")
    public String add(User user, RedirectAttributes redirectAttributes){
        userService.add(user);
        redirectAttributes.addFlashAttribute("message","新增成功");
        return "redirect:/user/list";
    }

    @GetMapping("/{id:\\d+}/del")
    public String del(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        userService.delById(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/user/list";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String edit(@PathVariable Integer id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user",user);
        return "user/edit";
    }

    @GetMapping("/edit")
    public String edit(User user,RedirectAttributes redirectAttributes){
        userService.update(user);
        redirectAttributes.addFlashAttribute("message","更新成功");
        return "/user/list";
    }


}
