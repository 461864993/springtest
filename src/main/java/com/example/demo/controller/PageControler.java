package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageControler {

    @RequestMapping("/editor")
    public String editor(){
        return "editor";
    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }
    @RequestMapping("/browser")
    public String browser(){
//        map.addAttribute("url", url);
        return "browser";
    }

}
