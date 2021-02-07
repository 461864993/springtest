package com.example.demo.service;

import com.example.demo.controller.FileCOntroller;
import com.example.demo.tool.Tool;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileService {
    @Value("${filepath}")
    private String filePath;
    @Value("${server}")
    private String server;
    @Autowired
    Tool tool;
    private static final Logger log = LoggerFactory.getLogger(FileService.class);
    public JSONObject uploadFile(MultipartFile file,String user_id,String file_path){
        if(file_path.equals("")){
            file_path = user_id + "_" + Tool.MD5(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),16);
        }
        log.info(filePath);
        log.info("Receive POST request，URL：/uppic,Picture：" + filePath + file_path + "/" + file.getOriginalFilename());
        JSONObject data = new JSONObject(),result;
        String return_code = null;
        if (file.isEmpty()) {
            return_code = "400001";//失败，请选择图片
        }else {
            log.info(filePath + file_path);
            File path = new File(filePath + file_path);
            if(!path.exists()){
                path.mkdir();
            }
            log.info(path.getPath());
            String fileName = file.getOriginalFilename();
            File dest = new File(path + "/" + fileName);
            try {
                file.transferTo(dest);
                log.info("Picture：" + file_path+ "/" + file.getOriginalFilename() + "Upload Successfully...");
                return_code = "000000";//成功
                data.put("picPath", server + "/picdeal/pic?name=" + file_path + "/" + fileName);
                data.put("filePath", file_path);
            } catch (IOException e) {
                log.info("Picture：" + file_path + "/" + file.getOriginalFilename() + "Upload Faile，Reason："+ e.getMessage());
                return_code = "400009";//失败
            }
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }

    public byte[] getPic(String name) {
        //        return fileService.GetPic(name);
        log.info(filePath + name);
        File file = new File(filePath + name);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            inputStream.close();
            return bytes;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
