package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Chance;
import com.kaishengit.crm.entity.ChanceRecod;
import com.kaishengit.crm.entity.User;

import java.util.List;
import java.util.Map;

public interface ChanceService {

    List<String> findChanceStateList();

    PageInfo<Chance> chanceList(Map<String, Object> queryParam, User user);

    void addNewChance(Chance chance);

    Chance findChanceById(Integer id);

    List<ChanceRecod> findRecod(Integer id);

    void updateChange(Chance chance, String state);

    void addNewRecored(Chance chance,String state);

    void delRecord(Integer id);

    void delChance(Integer id);

    List<ChanceRecod> findRecodByCustId(Integer custId);

    void newRecoredOfCust(ChanceRecod chanceRecod);
}
