package com.example.demo.controller;

import com.example.demo.entity.Param;
import com.example.demo.entity.User.*;
import com.example.demo.service.*;
import com.example.demo.tool.Tool;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/testBoot")
public class UserControler {
    @Autowired
    Tool tool;
    @Autowired
    UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserControler.class);

    @PostMapping("getuser")
    public User GetUser1(@RequestParam(value = "id", required = true) String id){
        User getuser = userService.getuserbyid(id);
        return getuser;
    }

    @PostMapping("login")
    public JSONObject Login(@RequestBody @Valid Param param, BindingResult bindingResult){
        log.info("Receive POST request，URL：/testBoot/login,Param：" + param.toString());
        return userService.Login(param,bindingResult);
    }

    @RequestMapping("getUser/{id}")
    public User GetUser(@PathVariable String id){
        return userService.getuserbyid(id);
    }

    @PostMapping("register")
    public JSONObject RegisterUser(@RequestBody @Valid Param param, BindingResult bindingResult){
        log.info("Receive POST request，URL：/testBoot/register,Param：" + param.toString());
        return userService.RegisterUser(param,bindingResult);
    }

    @PostMapping("updateuserpwd")
    public JSONObject UpdatePwd(@RequestBody @Valid Param param, BindingResult bindingResult){
        log.info("Receive POST request，URL：/testBoot/updateuserpwd,Param：" + param.toString());
        return userService.UpdatePwd(param,bindingResult);
    }

    @PostMapping("wxlogin")
    public JSONObject WXLogin(@RequestBody @Valid Param param, BindingResult bindingResult){
        log.info("Receive POST request，URL：/testBoot/wxlogin,Param：" + param.toString());
        return userService.WXLogin(param,bindingResult);
    }

//    @PostMapping("alipaylogin")
//    public JSONObject AlipayLogin(@RequestBody @Valid Param param,BindingResult bindingResult){
//        log.info("Receive POST request，URL：/testBoot/alipaylogin,Param：" + param.toString());
//        return userService.AlipayLogin(param,bindingResult);
//    }

    @PostMapping("wxchecktoken")
    public JSONObject WXCheckToken(@RequestBody @Valid Param param,BindingResult bindingResult){
            log.info("Receive POST request，URL：/testBoot/wxchecktoken,Param：" + param.toString());
        return userService.CheckAccessToken(param,bindingResult);
    }

    @PostMapping("updateuser")
    public JSONObject UpdateUser(@RequestBody @Valid Param param,BindingResult bindingResult){
        log.info("Receive POST request，URL：/testBoot/updateuser,Param：" + param.toString());
        return userService.UpdateUser(param,bindingResult);
    }

    @PostMapping("mytest")
    public String mytest(){
        return "Successfully";
    }
}
