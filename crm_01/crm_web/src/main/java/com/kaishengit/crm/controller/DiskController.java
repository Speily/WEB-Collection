package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.exception.NotFoundException;
import com.kaishengit.crm.service.DiskService;
import com.kaishengit.result.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 公司网盘控制器
 */
@Controller
@RequestMapping("/disk")
public class DiskController {

    @Autowired
    private DiskService diskService;

    /**
     * 公司网盘主页目录
     * @return
     */
    @GetMapping("/home")
    public String diskHome(@RequestParam(required = false,defaultValue = "0",name = "id") Integer pId, Model model){
        //id,pId——根目录用作pId,子目录就是主键id
        //pid=0，查询公司网盘文件主列表
        List<Disk> diskList = diskService.findFileByPid(pId);
        model.addAttribute("diskList",diskList);

        //如果pId不是0，则查找的是对应主键的数据，此时Pid代表主键
        if(pId != 0){
            Disk disk = diskService.findFileById(pId);
            model.addAttribute("disk",disk);
        }

        return "disk/disk_home";
    }

    /**
     * ajax新建文件夹
     * @param disk
     * @return
     */
    @PostMapping("/new/folder")
    @ResponseBody
    public AjaxResult NewFolder(Disk disk) {
        diskService.NewFolder(disk);
        List<Disk> diskList = diskService.findFileByPid(disk.getpId());
        return AjaxResult.success(diskList);
    }
    /**
     * ajax文件上传
     */
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult fileUpload(@RequestParam MultipartFile file, Integer pId, Integer userId) throws IOException {
        String name = file.getOriginalFilename();//获取文件名
        Long fileSize = file.getSize();//获取文件大小
        InputStream inputStream = null;//获取输入流

        inputStream = file.getInputStream();

        diskService.fileUpload(inputStream,name,fileSize,pId,userId);

        List<Disk> diskList = diskService.findFileByPid(pId);

        return AjaxResult.success(diskList);
    }

    /**
     * 下载
     */
    @GetMapping("/download")
    public void download(@RequestParam(name = "id") Integer id, HttpServletResponse response) throws IOException {
        Disk disk = diskService.findFileById(id);
        if(disk == null) {
            throw new NotFoundException();
        }
        String name = disk.getName();
        //设置中文格式
        name = new String(name.getBytes("UTF-8"),"ISO8859-1");
        //设置MIME类型，
        response.setContentType("application/octet-stream");
        //设置响应头，弹出对话框文件名
        response.setHeader("Content-Disposition","attachment; filename=\""+name+"\"");
        //响应输出流
        OutputStream outputStream = response.getOutputStream();

        diskService.downloadFile(disk,outputStream);
    }

    /**
     * 文件删除
     */
    @GetMapping("/del")
    @ResponseBody
    public AjaxResult delFile(@RequestParam(name = "id") Integer id,@RequestParam(name = "pId") Integer pId){
        diskService.dellFileById(id,pId);
        List<Disk> diskList = diskService.findFileByPid(pId);
        return AjaxResult.success(diskList);
    }

    /**
     * 文件重命名
     */
    @GetMapping("/rename")
    @ResponseBody
    public AjaxResult reNameFile(@RequestParam(name = "id") Integer id,@RequestParam(name = "_") Integer pId,String newName){
        diskService.reNameFileById(id,newName);
        List<Disk> diskList = diskService.findFileByPid(pId);
        return AjaxResult.success(diskList);
    }
}
