package com.kaishengit.crm.controller;


import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.exception.NotFoundException;
import com.kaishengit.crm.exception.UnableException;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.util.EncodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 我的客户首页
     *
     * @param model
     * @param keyword 搜索关键字
     * @param pageNo  当前页码
     * @return
     */
    @GetMapping("/my")
    public String custView(Model model,
                           @RequestParam(required = false, defaultValue = "") String keyword,
                           @RequestParam(required = false, defaultValue = "1", value = "p") Integer pageNo,
                           HttpSession httpSession) {
        keyword = EncodUtils.toUTF8(keyword);
        User user = (User) httpSession.getAttribute("currentUser");

        Map<String, Object> queryParam = Maps.newHashMap();
        queryParam.put("keyword", keyword);
        queryParam.put("pageNo", pageNo);

        PageInfo<Customer> pageInfo = customerService.findMyCustList(queryParam, user);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("keyword", keyword);

        return "customer/my_home";
    }

    /**
     * 新增客户页码
     */
    @GetMapping("/my/new")
    public String newMyCust(Model model) {
        List<String> tradeList = customerService.findTradeList();
        List<String> sourceList = customerService.findSourceList();
        model.addAttribute("tradeList", tradeList);
        model.addAttribute("sourceList", sourceList);
        return "customer/new_mycust";
    }


    /**
     * 新增客户
     */
    @PostMapping("/my/new")
    public String newMyCust(Customer cust, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("currentUser");
        customerService.newMyCust(user, cust);
        redirectAttributes.addFlashAttribute("message", "新增成功");
        return "redirect:/customer/my";
    }

    /**
     * 查看客户详情
     */
    @GetMapping("/my/{id:\\d+}")
    public String custInfo(@PathVariable Integer id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "customer/info";
    }

    /**
     * 修改客户信息
     */
    @GetMapping("/my/{id:\\d+}/edit")
    public String updateMyCust(@PathVariable Integer id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        Customer customer = customerService.findById(id);
        if (customer == null) {
            throw new NotFoundException();
        }
        //判断当前用户与客户是否对应一致
        if (!customer.getUserId().equals(user.getId())) {
            throw new UnableException();
        }
        //传值前端，复写
        model.addAttribute("customer",customer);
        model.addAttribute("sourceList",customerService.findSourceList());
        model.addAttribute("tradeList",customerService.findTradeList());
        return "customer/my_edit";
    }
    @PostMapping("/my/{id:\\d+}/edit")
    public String updateMyCust(Customer customer, HttpSession session, RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("currentUser");

        if (!user.getId().equals(customer.getUserId())) {
            throw new UnableException();
        }

        customerService.update(customer);
        redirectAttributes.addFlashAttribute("success", "修改成功");

        return "redirect:/customer/my/" + customer.getId();
    }
    /**
     * 删除客户
     */
    @GetMapping("/my/{id:\\d+}/del")
    public String delCust(@PathVariable Integer id ,
                          HttpSession session,
                          RedirectAttributes redirectAttributes){
        User user = (User) session.getAttribute("currentUser");
        System.out.println(user.getId());
        System.out.println(id);
        Customer customer = customerService.findById(id);
        if (customer == null) {
            throw new NotFoundException();
        }
        if(!customer.getUserId().equals(user.getId())) {
            throw new UnableException();
        }
        customerService.del(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/customer/my";
    }
}
