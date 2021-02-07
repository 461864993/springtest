package com.example.demo.controller;

import com.example.demo.entity.Param;
import com.example.demo.service.CommetService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/commet")
public class CommetControler {
    @Autowired
    CommetService commetService;
    private static final Logger log = LoggerFactory.getLogger(CollectControler.class);

    @PostMapping("getcommet")
    public JSONObject GetCommet(@RequestBody @Valid Param param, BindingResult bindingResult){
        log.info("Receive POST request，URL:/commet/getcommet，Param:" + param.toString());
        return commetService.GetCommet(param,bindingResult);
    }

    @PostMapping("addcommet")
    public JSONObject AddCommet(@RequestBody @Valid Param param, BindingResult bindingResult){
        log.info("Receive POST request，URL:/commet/addcommet，Param:" + param.toString());
        return commetService.AddCommet(param,bindingResult);
    }
}
