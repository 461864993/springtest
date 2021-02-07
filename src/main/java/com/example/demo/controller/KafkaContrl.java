package com.example.demo.controller;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/testKafka")
public class KafkaContrl {
    private static final Logger log = LoggerFactory.getLogger(KafkaContrl.class);
//    @GetMapping("test")
//    public String testpush(@RequestParam("num") int num,@RequestParam("topic_nam") String topic_name){
//        log.info("接收到Kafka消息，编号为：" + num);
//        Properties props = new Properties();
//        //设置接入点，请通过控制台获取对应Topic的接入点
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.87:9092,192.168.0.86:9092,192.168.0.85:9092");
//
//        //Kafka消息的序列化方式
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//        //请求的最长等待时间
//        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 30 * 1000);
//
//        //构造Producer对象，注意，该对象是线程安全的，一般来说，一个进程内一个Producer对象即可；
//        //如果想提高性能，可以多构造几个对象，但不要太多，最好不要超过5个
//        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
//
//        //构造一个Kafka消息
//        String topic = topic_name; //消息所属的Topic，请在控制台申请之后，填写在这里
//        String value = "this is the message's value" + num; //消息的内容
//        log.info("Kafka消息内容：" + value);
//        try {
////            for (int i =0; i < 100; i++) {
//                //发送消息，并获得一个Future对象
//                ProducerRecord<String, String> kafkaMessage =  new ProducerRecord<String, String>(topic, value + ": ");
//                Future<RecordMetadata> metadataFuture = producer.send(kafkaMessage);
//                //同步获得Future对象的结果
//                RecordMetadata recordMetadata = metadataFuture.get();
////                System.out.println("Produce ok:" + recordMetadata.toString());
//                log.info("Produce ok:" + recordMetadata.toString());
////            }
//        } catch (Exception e) {
//            //要考虑重试
//            //参考常见报错: https://help.aliyun.com/document_detail/68168.html?spm=a2c4g.11186623.6.567.2OMgCB
//            System.out.println("error occurred");
//            e.printStackTrace();
//            log.info(e.toString());
//        }
//        return value;
//    }
}
