package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Disk;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface DiskService {
    /**
     * 通过pId查找
     */
    List<Disk> findFileByPid(Integer pId);

    void fileUpload(InputStream inputStream, String name, Long fileSize, Integer pId, Integer userId);

    /**
     * 通过主键查找
     */
    Disk findFileById(Integer pId);

    void NewFolder(Disk disk);

    void downloadFile(Disk disk, OutputStream outputStream);

    void dellFileById(Integer id,Integer pId);

    void reNameFileById(Integer id,String newName);
}
