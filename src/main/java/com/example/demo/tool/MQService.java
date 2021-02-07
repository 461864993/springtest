package com.example.demo.tool;

import com.example.demo.entity.Activation.GetActivation;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.ActivationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@EnableRabbit
//@Service
public class MQService {
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//    @Autowired
//    UserMapper userMapper;
//    @Autowired
//    ActivationService activationService;
//    private static final Logger log = LoggerFactory.getLogger(MQService.class);
//
//
//    public boolean MQSend(String key,Object object){
//        rabbitTemplate.convertAndSend("exchange.direct", key, object);
//        log.info("MQ消息：" + object.toString() + "进入消息队列...");
//        return true;
//    }
//
//    @RabbitListener(queues = {"GetActivation"})
//    public void ReceiveMQ(GetActivation getActivation){
//        log.info("接收到MQ消息：" + getActivation.toString() + ",开始处理...");
//        activationService.UpdateActivation(getActivation);
////        userMapper.insertuser(user);
//    }
}
