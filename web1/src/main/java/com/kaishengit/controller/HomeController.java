package com.kaishengit.controller;

import com.kaishengit.entity.User;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home(){
        System.out.println("WEB-INF/views/home.jsp");
        return "home";
    }

    @RequestMapping("/form")
    public String form(){
        return "load";
    }

   /* @RequestMapping("/save")
    public String save(User user,String others){


        return "Redirect:/form";
    }*/

   //获取表单数据
    @PostMapping(value = "/save",produces = "text/html;charset=UTF-8")
    public String save(User user, String others, RedirectAttributes redirectAttributes){
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getAddress());
        System.out.println(others);
        redirectAttributes.addFlashAttribute("message","success");
        return "redirect:/form";
    }

    /**
     * 文件上传
     * @param multipartFile
     * @param doc
     * @param redirectAttributes
     * @return
     */
    @PostMapping(value = "/upload",produces = "text/html;charset=UTF-8")
    public String upload(MultipartFile multipartFile,String doc, RedirectAttributes redirectAttributes){
        System.out.println(doc);
        String name = multipartFile.getOriginalFilename();//获取文件名
        String newName = UUID.randomUUID().toString() + name.substring(name.lastIndexOf("."));

        File file = new File("d:/temp/upload");
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            InputStream input = multipartFile.getInputStream();
            OutputStream output = new FileOutputStream(new File(file,newName));

            IOUtils.copy(input,output);
            output.flush();
            output.close();
            input.close();
            redirectAttributes.addFlashAttribute("message","上传成功");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/form";
    }

    @GetMapping(value = "/download" ,produces = "txt/html;charset=UTF-8")
    public String download(String fileName, String downloadName, HttpServletResponse response){

        File newFile = new File("d:/temp/upload");
        File file = new File(newFile,fileName);

        if(file.exists()){
            try {
                InputStream input = new FileInputStream(file);
                OutputStream output = response.getOutputStream();

                IOUtils.copy(input, output);

                output.flush();
                output.close();
                input.close();
            }catch (IOException ex){
                ex.fillInStackTrace();
            }
        }
        return "redirect:load";
    }

}
