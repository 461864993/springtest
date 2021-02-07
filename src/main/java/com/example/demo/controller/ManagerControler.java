package com.example.demo.controller;

import com.example.demo.entity.Manager.SetUserstatus;
import com.example.demo.service.UserService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/manage")
public class ManagerControler {
    @Autowired
    UserService userService;
    @Value("${manager.id}")
    String manager_id;
    private static final Logger log = LoggerFactory.getLogger(ManagerControler.class);

    @PostMapping("setuserstatus")
    public JSONObject SetUserStatus(@RequestBody @Valid SetUserstatus setUserstatus, BindingResult bindingResult){
        log.info("Receive POST request，URL:/testBoot/register,Param：" + setUserstatus.toString());
        return userService.SetUserStatus(setUserstatus,bindingResult,manager_id);
    }
}
