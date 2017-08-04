package com.kaishengit.controller;

import com.kaishengit.pojo.Account;
import com.kaishengit.service.AccountService;
import com.kaishengit.util.Condition;
import com.kaishengit.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(name = "p",defaultValue = "1") Integer pageNum,
                       HttpServletRequest request){
        Condition[] conditionList = Condition.builderConditionList(request);
        Page<Account> page = accountService.findAllByConditionAndPage(pageNum,conditionList);
        model.addAttribute("page",page);
        return "account/list";
    }


}
