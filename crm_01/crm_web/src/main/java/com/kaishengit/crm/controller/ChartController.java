package com.kaishengit.crm.controller;

import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.result.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chart")
public class ChartController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/public")
    public String staticDateCharts() {
        return "echarts/pub_echart";
    }

    @GetMapping("/my")
    public String customerLevelTotal() {
        return "echarts/myEchart";
    }

    @GetMapping("/customer/bar.json")
    @ResponseBody
    public AjaxResult loadBarData() {
        List<Map<String,Object>> levelDataMap = customerService.findCustomerLevelCount();
        return AjaxResult.success(levelDataMap);
    }

}
