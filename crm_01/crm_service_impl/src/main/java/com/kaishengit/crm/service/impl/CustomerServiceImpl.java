package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.CustomerExample;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerMapper customerMapper;

    @Value("#{'${cust.trade}'.split(',')}")
    private List<String> tradeList;
    @Value("#{'${cust.source}'.split(',')}")
    private List<String> sourceList;

    /**
     * 新增客户
     * @param user
     * @param cust
     */
    @Override
    public void newMyCust(User user, Customer cust) {
        Integer userId = user.getId();
        cust.setUserId(userId);
        cust.setCreateTime(new Date());
        customerMapper.insert(cust);
    }

    @Override
    public PageInfo<Customer> findMyCustList(Map<String, Object> queryParam,User user) {

        Integer pageNo = (Integer) queryParam.get("pageNo");
        String keyword =(String) queryParam.get("keyword");
        if(StringUtils.isNotBlank(keyword)){
            keyword =  "%" + keyword + "%" ;
        }
        PageHelper.startPage(pageNo,8);

        List<Customer> customerList = customerMapper.selectByParam(user,keyword);

        PageInfo<Customer> pageInfo = new PageInfo<>(customerList);

        return pageInfo;
    }

    @Override
    public List<String> findTradeList() {
        return tradeList;
    }


    @Override
    public List<String> findSourceList() {
        return sourceList;
    }

    /**
     * 根据id查询客户
     * @param id
     * @return
     */
    @Override
    public Customer findById(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Customer customer) {
        customer.setUpdateTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    @Override
    public void del(Integer id) {
        // TODU 删除客户相关项
        customerMapper.deleteByPrimaryKey(id);
    }

    /**
     * 放入公海
     * @param customer
     */
    @Override
    public void toPublic(Customer customer) {

        customer.setMark("来自于——"+customer.getCustName());
        customer.setLastContactTime(new Date());
        customer.setUserId(0);

        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 转交他人
     * @param customer
     * @param userId
     */
    @Override
    public void turnToSomeone(Customer customer, Integer userId,User user) {
        customer.setMark("来自于——"+ user.getUserName());
        customer.setUserId(userId);
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 客户信息导出excet
     * @param user
     * @param outputStream
     */
    @Override
    public void exportExcel(User user, OutputStream outputStream) {

        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andUserIdEqualTo(user.getId());
        List<Customer> customerList = customerMapper.selectByExample(customerExample);

        //承自Control层

        //3.创建工作表
        Workbook workbook = new HSSFWorkbook();
        //4.创建sheet页
        Sheet sheet = workbook.createSheet(user.getUserName()+"的客户资料");
        //5.创建数据
        Row row = sheet.createRow(0);//行
        Cell cell = row.createCell(0);//单元格
        cell.setCellValue("客户名称");//值

        row.createCell(1).setCellValue("职位");
        row.createCell(2).setCellValue("级别");
        row.createCell(3).setCellValue("地址");
        row.createCell(4).setCellValue("联系电话");

        for(int i = 0;i<customerList.size();i++){
            Customer customer = customerList.get(i);
            Row dataRow = sheet.createRow(i+1);
            dataRow.createCell(0).setCellValue(customer.getCustName());
            dataRow.createCell(1).setCellValue(customer.getJob());
            dataRow.createCell(2).setCellValue(customer.getLevel());
            dataRow.createCell(3).setCellValue(customer.getAddress());
            dataRow.createCell(4).setCellValue(customer.getTel());
        }
        //6。写入磁盘

        try {
            workbook.write(outputStream);
        } catch (Exception ex) {
            throw new ServiceException("导出Excel异常",ex);
        }

    }

}
