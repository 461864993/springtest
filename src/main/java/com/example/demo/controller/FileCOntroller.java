package com.example.demo.controller;

import com.example.demo.entity.Param;
import com.example.demo.service.CollectService;
import com.example.demo.service.FileService;
import com.example.demo.tool.Tool;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@EnableAutoConfiguration
@RequestMapping("/picdeal")
public class FileCOntroller {
    @Value("${filepath}")
    private String filePath;
    @Value("${server}")
    private String server;
    @Autowired
    FileService fileService;
    @Autowired
    Tool tool;
    @Autowired
    CollectService collectionService;
    private static final Logger log = LoggerFactory.getLogger(FileCOntroller.class);
    /**
     * 上传图片
     * @param file 图片文件
     * @return 上传状态或图片名称
     */
    @PostMapping("/uppic")
    public JSONObject uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("user_id") String user_id,
                              @RequestParam("filepath") String file_path) {
        log.info("Receive POST request，URL:/uppic");
        return fileService.uploadFile(file,user_id,file_path);
    }
    /**
     * 根据图片名称返回本地图片
     * @param name 图片名称
     * @return 返回本地图片
     */
    @GetMapping(value = "/pic", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPicture(@RequestParam("name") String name) {
        log.info("Receive POST request，URL：/uppic,Picture：" + name);
        return fileService.getPic(name);
    }

    @PostMapping("undopic")
    public JSONObject UndoPic(@RequestBody @Valid Param param, BindingResult bindingResult){
        log.info("Receive POST request，URL：/undopic,Param:" + param);
        return collectionService.UndoPic(param,bindingResult,filePath);
    }

}
