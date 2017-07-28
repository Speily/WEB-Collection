package com.kaishengit.crm.controller;


import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.google.zxing.WriterException;
import com.kaishengit.crm.entity.ChanceRecod;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.exception.NotFoundException;
import com.kaishengit.crm.exception.UnableException;
import com.kaishengit.crm.service.ChanceService;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.TaskService;
import com.kaishengit.crm.service.UserService;
import com.kaishengit.util.EncodUtils;
import com.kaishengit.util.QRCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;


/**
 * 客户控制器
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChanceService chanceService;

    @Autowired
    private TaskService taskService;

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

        //使用Map集合向Service层传值
        Map<String, Object> queryParam = Maps.newHashMap();
        queryParam.put("keyword", keyword);
        queryParam.put("pageNo", pageNo);

        PageInfo<Customer> pageInfo = customerService.findMyCustList(queryParam, user);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("keyword", keyword);

        model.addAttribute("formWhere", "my");

        return "customer/my_home";
    }

    /**
     * 新增客户页
     *
     * @param model
     * @return String
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
     * 新增个人客户
     *
     * @param cust               客户
     * @param session
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/my/new")
    public String newMyCust(Customer cust, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("currentUser");
        customerService.newMyCust(user, cust);
        redirectAttributes.addFlashAttribute("message", "新增成功");

        return "redirect:/customer/my";
    }

    /**
     * 查看当前客户详情
     *
     * @param id    客户id
     * @param model
     * @return
     */
    @GetMapping("/my/{id:\\d+}")
    public String myCustInfo(@PathVariable Integer id, Model model,HttpSession session) {

        User user = (User) session.getAttribute("currentUser");
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        //获取员工集合，客户详情页转交他人时使用
        List<User> userList = userService.findAllUser();
        model.addAttribute("userList", userList);

        //查询跟进记录
        List<ChanceRecod> recordList = chanceService.findRecodByCustId(customer.getId());
        model.addAttribute("recordList",recordList);
        //查找该客户的待办事项
        List<Task> taskList = taskService.selectUndoneTask(customer.getId(),user.getId());
        model.addAttribute("taskList",taskList);

        //flag用于前端识别响应来源（个人|公海）
        model.addAttribute("formWhere", "my");

        return "customer/info";
    }


    /**
     * 查看公海客户详情
     *
     * @param id    客户id
     * @param model
     * @return
     */
    @GetMapping("/public/{id:\\d+}")
    public String pubCustInfo(@PathVariable Integer id, Model model) {

        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);

        //获取员工集合，客户详情页转交他人时使用
        List<User> userList = userService.findAllUser();
        model.addAttribute("userList", userList);

        //flag用于前端识别响应来源（个人|公海）
        model.addAttribute("formWhere", "public");

        return "customer/info";
    }

    /**
     * 修改客户信息
     *
     * @param id      客户id
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/my/{id:\\d+}/edit")
    public String updateMyCust(@PathVariable Integer id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        Customer customer = customerService.findById(id);
        //传值前端，复写
        model.addAttribute("customer", customer);
        model.addAttribute("sourceList", customerService.findSourceList());
        model.addAttribute("tradeList", customerService.findTradeList());

        if (customer == null) {
            throw new NotFoundException();
        }
        if (customer.getUserId() == 0) {
            return "customer/my_edit";
        } else {

            //判断当前用户与客户所属用户是否一致
            if (!customer.getUserId().equals(user.getId())) {
                throw new UnableException();
            }

            return "customer/my_edit";
        }
    }

    /**
     * 修改客户信息
     *
     * @param customer
     * @param session
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/my/{id:\\d+}/edit")
    public String updateMyCust(Customer customer, HttpSession session, RedirectAttributes redirectAttributes) {

        if (customer == null) {
            throw new NotFoundException();
        }
        User user = (User) session.getAttribute("currentUser");
        //判断客户来源，公海|个人
        if (customer.getUserId() == 0) {
            customerService.update(customer);
            redirectAttributes.addFlashAttribute("success", "修改成功");
            return "redirect:/customer/public";
        } else {
            if (!user.getId().equals(customer.getUserId())) {
                throw new UnableException();
            }
            customerService.update(customer);
            redirectAttributes.addFlashAttribute("success", "修改成功");
            return "redirect:/customer/my/" + customer.getId();
        }
    }

    /**
     * 删除客户
     *
     * @param id                 客户id
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/my/{id:\\d+}/del")
    public String delCust(@PathVariable Integer id,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("currentUser");
        System.out.println(user.getId());
        System.out.println(id);
        Customer customer = customerService.findById(id);
        if (customer == null) {
            throw new NotFoundException();
        }
        //判断客户来源，公海|个人
        if (customer.getUserId() == 0) {
            customerService.del(id);
            redirectAttributes.addFlashAttribute("message", "删除成功");
            return "redirect:/customer/public";
        } else {
            if (!customer.getUserId().equals(user.getId())) {
                throw new UnableException();
            }
            customerService.del(id);
            redirectAttributes.addFlashAttribute("message", "删除成功");
            return "redirect:/customer/my";
        }

    }

    /**
     * 放入公海
     *
     * @param id                 客户id
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/my/{id:\\d+}/topublic")
    public String toPublic(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("currentUser");
        Customer customer = customerService.findById(id);
        if (customer == null) {
            throw new NotFoundException();
        }
        //判断当前用户与客户是否对应一致
        if (!customer.getUserId().equals(user.getId())) {
            throw new UnableException();
        }

        customerService.toPublic(customer);
        redirectAttributes.addFlashAttribute("message", "已放入公海");

        return "redirect:/customer/my";
    }

    /**
     * 转交他人
     *
     * @param custId             客户id
     * @param userId             当前用户id
     * @param model
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/my/{custId:\\d+}/turnto/{userId:\\d+}")
    public String turnToSomeone(@PathVariable Integer custId,
                                @PathVariable Integer userId,
                                Model model,
                                HttpSession session, RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("currentUser");
        Customer customer = customerService.findById(custId);
        if (customer == null) {
            throw new NotFoundException();
        }
        //判断客户来源，公海|个人
        if (customer.getUserId() == 0) {

            customerService.turnToSomeone(customer, userId, user);

            redirectAttributes.addFlashAttribute("message", "转交成功");
            return "redirect:/customer/public";
        } else {
            //判断当前用户与客户是否对应一致
            if (!customer.getUserId().equals(user.getId())) {
                throw new UnableException();
            }
            customerService.turnToSomeone(customer, userId, user);

            redirectAttributes.addFlashAttribute("message", "转交成功");
            return "redirect:/customer/my";
        }


    }

    /**
     * 导出Excel
     */
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("currentUser");
        //1.设置浏览器输出内容MIME
        response.setContentType("application/vnd.ms-excel");
        //2.设置文件下载对话框文件名
        response.addHeader("Content-Disposition", " attachment;filename=\"customer.xls\"");
        customerService.exportExcel(user, response.getOutputStream());
    }


    /**
     * 查询公海账户
     */
    @GetMapping("/public")
    public String publicCustomer(Model model,
                                 @RequestParam(required = false, defaultValue = "") String keyword,
                                 @RequestParam(required = false, value = "p", defaultValue = "1") Integer pageNo,
                                 HttpSession session) {

        keyword = EncodUtils.toUTF8(keyword);

        User user = new User();
        user.setId(0);

        Map<String, Object> maps = Maps.newHashMap();
        maps.put("keyword", keyword);
        maps.put("pageNo", pageNo);
        PageInfo<Customer> pageInfo = customerService.findMyCustList(maps, user);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("keyword", keyword);

        model.addAttribute("formWhere", "public");

        return "customer/my_home";
    }


    /**
     * 新增公海账户
     *
     * @return
     */
    @GetMapping("/public/new")
    public String newPubCust(Model model) {
        model.addAttribute("tradeList", customerService.findTradeList());
        model.addAttribute("sourceList", customerService.findSourceList());
        return "customer/new_mycust";
    }

    @PostMapping("/public/new")
    public String newPubCust(Customer cust, HttpSession session, RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("currentUser");
        customerService.newPubCust(user, cust);
        redirectAttributes.addFlashAttribute("message", "新增成功");
        return "redirect:/customer/public";
    }


    /**
     * 显示客户二维码图片
     */
    @GetMapping("/my/qrcode/{id:\\d+}")
    public void showCustomerQRCode(@PathVariable Integer id,HttpServletResponse response) {
        Customer customer = customerService.findById(id);

        response.setContentType("image/png");

        //vcard电话名片格式
        StringBuffer str = new StringBuffer();
        str.append("BEGIN:VCARD\r\n");
        str.append("VERSION:3.0\r\n");
        str.append("N:").append(customer.getCustName()).append("\r\n");
        str.append("TITLE:").append(customer.getJob()).append("\r\n");
        str.append("TEL:").append(customer.getTel()).append("\r\n");
        str.append("ADR:").append(customer.getAddress()).append("\r\n");
        str.append("END:VCARD\r\n");

        try {
            OutputStream outputStream = response.getOutputStream();
            QRCodeUtil.writeToStream(str.toString(), outputStream, 300, 300);
            outputStream.flush();
            outputStream.close();
        } catch (IOException |WriterException ex) {
            throw new RuntimeException("渲染二维码失败",ex);
        }
    }

    /**
     * 添加待办事项（客户）
     */
    @PostMapping("/my/{customerId:\\d+}/task/new")
    public String newTaskToCustomer(Task task) {
        taskService.newTaskSelected(task);
        return "redirect:/customer/my/"+task.getCustId();
    }

    /**
     * 添加跟进记录
     */
    @PostMapping("/my/new/record")
    public String newCustRecord(ChanceRecod chanceRecod,RedirectAttributes redirectAttributes){
        chanceService.newRecoredOfCust(chanceRecod);
        redirectAttributes.addFlashAttribute("message","添加成功");
        return "redirect:/customer/my/"+ chanceRecod.getCustId();
    }
}
