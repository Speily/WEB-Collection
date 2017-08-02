package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.entity.UserDept;
import com.kaishengit.crm.mapper.UserDeptMapper;
import com.kaishengit.crm.mapper.UserMapper;
import com.kaishengit.crm.service.UserService;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.exception.SessionException;
import com.kaishengit.weixin.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by SPL on 2017/7/18 0018.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDeptMapper userDeptMapper;
    @Autowired
    private WeiXinUtil weiXinUtil;

    @Value("${password.salt}")
    private String passwordSalt;

    /*
     * 添加员工
     */
    @Override
    @Transactional
    public void add(User user, Integer[] deptIds) {
        user.setCreateTime(new Date());
        user.setPassword(DigestUtils.md5Hex(user.getPassword() + passwordSalt));
        user.setTel(user.getTel());
        userMapper.insert(user);

        //添加员工和部门关系
        for (Integer deptId : deptIds) {
            UserDept userDept = new UserDept();
            userDept.setDeptId(deptId);
            userDept.setUserId(user.getId());
            userDeptMapper.insert(userDept);
        }
        System.out.println(user.getTel());
        //同步到微信通讯录
        weiXinUtil.createUser(user.getId(),user.getUserName(),deptIds,user.getTel());
    }


    /*
     * 删除员工
     */
    @Override
    @Transactional
    public void dellById(Integer userId) {

        //删除员工和部门关系
        userDeptMapper.delByUserId(userId);

        //删除员工user
        userMapper.delById(userId);

        //从微信通讯录删除
        weiXinUtil.deleteUserById(userId.toString());

    }

    @Override
    public Long countByDeptId(Integer deptId) {
        if (new Integer(1000).equals(deptId)) {
            deptId = null;
        }
        return userMapper.countByDeptId(deptId);
    }

    @Override
    public List<User> findByDeptId(Integer deptId) {
        if (new Integer(1000).equals(deptId)) {
            deptId = null;
        }
        return userMapper.finByDeptId(deptId);
    }

    @Override
    public Long count() {
        return userMapper.count();
    }

    /**
     * 登录验证
     * @param tel
     * @param password
     * @return
     */
    @Override
    public User findByTelLoadDept(String tel, String password) throws ServiceException{

        if(StringUtils.isEmpty(tel)){
            throw new ServiceException("参数异常");
        }

        User user = userMapper.findByTelLoadDept(DigestUtils.md5Hex(tel+passwordSalt));

        if(user == null){
            throw new ServiceException("账号或密码错误");
        }else{
            if(password.equals(user.getPassword())){
                return user;
            }
        }

        throw new SessionException("账号或密码错误");

    }

    /**
     * 修改密码
     * @param user
     * @param oldPassword
     * @param newPassword
     * @throws ServiceException
     */
    @Override
    public void update(User user, String oldPassword, String newPassword) throws ServiceException {
        if(user.getPassword().equals(oldPassword)){
            user.setUpdateTime(new Date());
            user.setPassword(DigestUtils.md5Hex(newPassword+passwordSalt));
            userMapper.update(user);
        }else{
            throw new ServiceException("原密码错误");
        }
    }

    @Override
    public List<User> findAllUser() {
        return userMapper.findAll();
    }

    /**
     * 通过id查询user
     * @param userId
     * @return
     */
    @Override
    public User findByUserId(Integer userId) {
        return userMapper.findById(userId);
    }


}
