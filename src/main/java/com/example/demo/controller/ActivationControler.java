package com.example.demo.controller;

import com.example.demo.entity.Activation.GetActivation;
import com.example.demo.entity.Resultjson;
import com.example.demo.service.ActivationService;
import com.example.demo.tool.Tool;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/activationCtrl")
public class ActivationControler {
    @Autowired
    Tool tool;
    @Autowired
    ActivationService activationService;
    private static final Logger log = LoggerFactory.getLogger(ActivationControler.class);

    @PostMapping("codeadd")
    public Resultjson CodeAdd(@RequestParam String secret,@RequestParam int num){
        String return_code;
        if(activationService.activationadd(tool.CreatActivityCode(secret,num))){
            return_code = "000000";
        }else{
            return_code = "999999";
        }
        return new Resultjson(return_code,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

//    @PostMapping("getcode")
//    public JSONObject GetCode(@RequestBody @Valid GetActivation getActivation, BindingResult bindingResult){
//        log.info("Receive POST request，URL:/activationCtrl/getcode，Param:" + getActivation.toString());
//        return activationService.getcode(getActivation,bindingResult);
//    }
}
