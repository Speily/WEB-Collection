package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.*;
import com.kaishengit.crm.mapper.ChanceMapper;
import com.kaishengit.crm.mapper.ChanceRecodMapper;
import com.kaishengit.crm.service.ChanceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ChanceServiceImpl implements ChanceService {

    @Autowired
    private ChanceMapper chanceMapper;
    @Autowired
    private ChanceRecodMapper recodMapper;

    @Value("#{'${chance.State}'.split(',')}")
    private List<String> changeStateList ;

    @Override
    public List<String> findChanceStateList() {
        return changeStateList;
    }

    @Override
    public PageInfo<Chance> chanceList(Map<String, Object> queryParam, User user) {

        Integer pageNo = (Integer) queryParam.get("pageNo");
        String keyword = (String) queryParam.get("keyword");
        //keyword不为空时
        if (StringUtils.isNotBlank(keyword)) {
            keyword = "%" + keyword + "%";
        }
        PageHelper.startPage(pageNo, 8);

        List<Chance> changeList = chanceMapper.selectByParam(user, keyword);
        PageInfo<Chance> pageInfo = new PageInfo<>(changeList);
        return pageInfo;
    }

    @Override
    public void addNewChance(Chance chance) {
        chanceMapper.insert(chance);
    }

    /**
     * 查询chance,根据id
     * @param id
     * @return
     */
    @Override
    public Chance findChanceById(Integer id) {
        ChanceExample chanceExample = new ChanceExample();
        chanceExample.createCriteria().andIdEqualTo(id);
        return  chanceMapper.selectByPrimaryKey(id);
    }


    /**
     * 根据机会id获取对应跟踪记录
     * @param id 机会id
     * @return
     */
    @Override
    public List<ChanceRecod> findRecod(Integer id) {

        ChanceRecodExample chanceRecodExample = new ChanceRecodExample();
        chanceRecodExample.createCriteria().andChanceIdEqualTo(id);

        return recodMapper.selectByExampleWithBLOBs(chanceRecodExample);
    }

    /**
     * 修改机会跟进状态
     * @param chance
     * @param state 跟进状态
     */
    @Override
    public void updateChange(Chance chance, String state) {

        ChanceExample example = new ChanceExample();
        example.createCriteria().andCurrentStateEqualTo("将当前进度修改为："+state);

        chanceMapper.updateByExampleSelective(chance,example);
    }

    /**
     * 新建跟进记录
     */
    @Override
    public void addNewRecored(Chance chance,String state) {
        ChanceRecod recod = new ChanceRecod();
        if(chance != null){
            recod.setChanceId(chance.getId());
        }
        recod.setCreateTime(new Date());
        recod.setText("修改跟进状态为：" + state);
        recodMapper.insertSelective(recod);
    }

    /**
     * 删除跟进记录
     * @param id
     */
    @Override
    public void delRecord(Integer id) {
        ChanceRecodExample chanceRecodExample = new ChanceRecodExample();
        chanceRecodExample.createCriteria().andChanceIdEqualTo(id);
        recodMapper.deleteByExample(chanceRecodExample);
    }

    /**
     * 删除机会
     * @param id
     */
    @Override
    public void delChance(Integer id) {
        chanceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询cust跟进记录
     * @param custId 客户id
     * @return
     */
    @Override
    public List<ChanceRecod> findRecodByCustId(Integer custId) {

        ChanceRecodExample chanceRecodExample = new ChanceRecodExample();
        chanceRecodExample.createCriteria().andCustIdEqualTo(custId);
        List<ChanceRecod> recodList = recodMapper.selectByExampleWithBLOBs(chanceRecodExample);
        return recodList;
    }

    /**
     * 客户页新增跟踪
     * @param chanceRecod
     */
    @Override
    public void newRecoredOfCust(ChanceRecod chanceRecod) {
        recodMapper.insertSelective(chanceRecod);
    }
}
