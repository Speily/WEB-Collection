package com.kaishengit.crm.mapper;

import com.kaishengit.crm.entity.UserDept;
import org.apache.ibatis.annotations.Param;

/**
 * Created by SPL on 2017/7/18 0018.
 */
public interface UserDeptMapper {
    void insert(UserDept userDept);

    void delByUserId(@Param("userId") Integer userId);
}
