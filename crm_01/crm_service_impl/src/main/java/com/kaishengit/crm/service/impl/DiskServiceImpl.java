package com.kaishengit.crm.service.impl;

import com.google.zxing.NotFoundException;
import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.entity.DiskExample;
import com.kaishengit.crm.mapper.DiskMapper;
import com.kaishengit.crm.service.DiskService;
import com.kaishengit.exception.ServiceException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DiskServiceImpl implements DiskService{

    @Autowired
    private DiskMapper diskMapper;

    @Value("${upload.path}")
    private String updatePath;

    /**
     * 根据pid查找文件夹|文件
     * @param pId
     * @return
     */
    @Override
    public List<Disk> findFileByPid(Integer pId) {

        if(pId == null){
            pId = 0;
        }

        DiskExample diskExample = new DiskExample();
        diskExample.createCriteria().andPIdEqualTo(pId);

        return diskMapper.selectByExample(diskExample);
    }

    /**
     * 文件上传
     * @param inputStream
     * @param name
     * @param fileSize
     * @param pId
     * @param userId
     */
    @Override
    @Transactional
    public void fileUpload(InputStream inputStream, String name, Long fileSize, Integer pId, Integer userId) {



        String saveName = UUID.randomUUID() + name.substring(name.lastIndexOf('.'));
        String size = FileUtils.byteCountToDisplaySize(fileSize);//文件大小byte转换成可读性单位
        //存入表
        Disk disk = new Disk();
        disk.setType("file");
        disk.setUpdateTime(new Date());
        disk.setSaveName(saveName);
        disk.setName(name);
        disk.setFileSize(size);
        disk.setpId(pId);
        disk.setUserId(userId);
        disk.setDownloadCount(1);
        diskMapper.insert(disk);

        //存入磁盘
        try {
            OutputStream outputStream = new FileOutputStream(new File(updatePath,saveName)); //输出流
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new ServiceException("上传文件异常",e);
        }



    }

    /**
     * 主键查找
     * @param pId
     * @return
     */
    @Override
    public Disk findFileById(Integer pId) {
        return diskMapper.selectByPrimaryKey(pId);
    }

    /**
     * 新建文件夹
     * @param disk
     */
    @Override
    public void NewFolder(Disk disk) {
        disk.setUpdateTime(new Date());
        disk.setType(Disk.DIR);
        diskMapper.insert(disk);
    }

    /**下载
     *
     * @param disk
     * @param outputStream
     */
    @Override
    @Transactional
    public void downloadFile(Disk disk, OutputStream outputStream) {
        //更新下载次数
        disk.setDownloadCount(disk.getDownloadCount()+1);
        diskMapper.updateByPrimaryKey(disk);

        //下载
        String saveName = disk.getSaveName();
        try {
        File file = new File(updatePath,saveName);
            if(file.exists()){

                    InputStream inputStream = new FileInputStream(file);
                    IOUtils.copy(inputStream,outputStream);

                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();

            }else{
                throw  new ServiceException("您下载文件找不到了");
            }
        }catch (IOException e) {
            throw new ServiceException("下载异常",e);
        }
    }

    /**
     * 删除文件
     */
    @Override
    @Transactional
    public void dellFileById(Integer id,Integer pId) {
        //判断文件类型
        Disk disk = diskMapper.selectByPrimaryKey(id);
        if(disk == null){
            throw new ServiceException("文件不见了");
        }
        String saveName = disk.getSaveName();

        if(disk.getType().equals("file")){
            //如果是文件，删除表中数据和磁盘数据
            diskMapper.deleteByPrimaryKey(id);
            File file = new File(updatePath,saveName);
            file.delete();
        }else{
            //如果不是文件，遍历调用其本身，递归算法
            List<Disk> diskList = findFileByPid(id);
            if(diskList.size() > 0){
                for(Disk disk1 : diskList){
                    dellFileById(disk1.getId(),disk1.getpId());
                    //删除对应pid的数据
                    DiskExample diskExample = new DiskExample();
                    diskExample.createCriteria().andPIdEqualTo(disk1.getpId());
                    diskMapper.deleteByExample(diskExample);
                    diskMapper.deleteByPrimaryKey(id);
                }
                //空文件夹
            }else{
                File file = new File(updatePath+saveName);
                file.delete();
            }

        }
    }

    /**
     * 重命名
     * @param id
     * @param newName
     */
    @Override
    public void reNameFileById(Integer id, String newName) {
        Disk disk = diskMapper.selectByPrimaryKey(id);
        DiskExample diskExample = new DiskExample();
        diskExample.createCriteria().andNameEqualTo(newName);
        diskMapper.updateByExampleSelective(disk,diskExample);
    }


}
