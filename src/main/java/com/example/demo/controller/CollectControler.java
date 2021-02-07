package com.example.demo.controller;
import com.example.demo.entity.Param;
import com.example.demo.service.CollectService;
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
@RequestMapping("/collection")
public class CollectControler {
    @Autowired
    CollectService collectionService;
    private static final Logger log = LoggerFactory.getLogger(CollectControler.class);

    @PostMapping("getallcollection")
    public JSONObject GetAllCollection(@RequestBody @Valid Param param, BindingResult bindingResult){
        log.info("Receive POST request，URL:/collection/getallcollection，Param:" + param.toString());
        return collectionService.GetAllColection(param,bindingResult);
    }

    @PostMapping("addcollect")
    public JSONObject AddCollect(@RequestBody @Valid Param param,BindingResult bindingResult){
        log.info("Receive POST request，URL:/collection/addcollect，Param:" + param.toString());
        return collectionService.AddCollect(param,bindingResult);
    }

    @PostMapping("updatecollect")
    public JSONObject UpdateCollect(@RequestBody @Valid Param param,BindingResult bindingResult){
        log.info("Receive POST request，URL:/collection/updatecollect，Param:" + param.toString());
        return collectionService.UpdateCollect(param,bindingResult);
    }

    @PostMapping("geturl")
    public JSONObject getURL(@RequestBody @Valid Param param, BindingResult bindingResult){
        log.info("Receive POST request，URL:/collection/geturl，Param:" + param.toString());
        return collectionService.getfromURL(param,bindingResult);
    }

    @PostMapping("getmycollect")
    public JSONObject getMyCollect(@RequestBody @Valid Param param, BindingResult bindingResult){
        log.info("Receive POST request，URL:/collection/getmycollect，Param:" + param.toString());
        return collectionService.GetMyColection(param,bindingResult);
    }

    @PostMapping("delcollect")
    public JSONObject DelCollect(@RequestBody @Valid Param param, BindingResult bindingResult){
        log.info("Receive POST request，URL:/collection/delcollect，Param:" + param.toString());
        return collectionService.DelCollect(param,bindingResult);
    }

    @PostMapping("searchcollect")
    public JSONObject SearchCollect(@RequestBody @Valid Param param, BindingResult bindingResult){
        log.info("Receive POST request，URL:/collection/searchcollect，Param:" + param.toString());
        return collectionService.SearchCollect(param,bindingResult);
    }

    @PostMapping("getmycollectcount")
    public JSONObject GetMyCollectNum(@RequestBody @Valid Param param, BindingResult bindingResult){
        log.info("Receive POST request，URL：/collection/getmycollectnum,Param：" + param.toString());
        return collectionService.GetMyCollectNum(param,bindingResult);
    }
}
