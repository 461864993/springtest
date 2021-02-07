package com.example.demo.controller;

import com.example.demo.entity.City;
import com.example.demo.entity.Resultjson;
import com.example.demo.service.CityService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testwork")
public class CityControler {
    @Autowired
    CityService cityService;
    private Resultjson rj = new Resultjson("","");
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger log = LoggerFactory.getLogger(CityControler.class);


    @GetMapping("APItest")
    public List<City> GetCity(){
        log.info("Receive GET request，URL:/testwork/APItest");
        log.info("Data processing...");
        List<City> cityList = cityService.getcitynumber();
        log.info("Result：" + cityList.toString());
        return cityList;
    }

    @PostMapping("NumberUpdate")
    public Resultjson update (@RequestParam("id") int id,@RequestParam("num") int num){
        log.info("Receive GET request，URL：/testwork/NumberUpdate");
        log.info("Data：id:" + id + ",num:" + num);
        log.info("Data processing...");
        if(cityService.updatecitynumber(id,num)){
            rj = new Resultjson("000000",df.format(new Date()));
        }else{
            rj = new Resultjson("999999",df.format(new Date()));
        }
        log.info("Result：" + rj.toString());
        return rj;
    }
}
