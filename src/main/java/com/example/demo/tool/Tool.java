package com.example.demo.tool;

import com.example.demo.entity.Activation.Activation;
import com.example.demo.entity.Collect.Collect;
import com.example.demo.entity.Commet.Commet;
import com.example.demo.entity.Param;
import com.example.demo.entity.Partner;
import com.example.demo.entity.Resultjson;
import com.example.demo.service.PartnerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class Tool {

    private static final Logger log = LoggerFactory.getLogger(Tool.class);

    //MD5加密
    public static String MD5(String sourceStr, int md5type) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0){
                    i += 256;
                }
                if (i < 16){
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            if(md5type == 32){
                result = buf.toString();
                log.info("MD5(" + sourceStr + ",32) = " + result);
            }else if(md5type == 16){
                result = buf.toString().substring(8, 24);
                log.info("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));
            }
        } catch (NoSuchAlgorithmException e) {
            log.info(String.valueOf(e));
        }
        return result;
    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param salt    加密密码
     * @return
     */
    public byte[] encrypt(String content, String salt) {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(salt.getBytes());
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @param salt    解密密钥
     * @return
     */
    public byte[] decrypt(byte[] content, String salt) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(salt.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //客户端鉴权
    public boolean PartnerCheck(StringRedisTemplate stringRedisTemplate,PartnerService partnerService, String partner_id, String time, String token,String param){
        log.info("Read redis");
        Partner partner = new Partner(partner_id,
                stringRedisTemplate.
                        opsForValue().get("partner_" + partner_id) +
                        "");//读取redis中的partner信息
        log.info("redis Read complete");
        if(partner.getPartner_secret().equals("null")){
            log.info("redis Not Found，Read mysql");
            try{
                partner = partnerService.getpartner_info(partner.getPartner_id());//读取库里的partner信息
                log.info(partner.toString());
                log.info("mysql Read complete");
                if(partner != null) {
                    log.info("Write redis");
                    partner.setPartner_secret(partnerService.getpartner_info(partner.getPartner_id()).getPartner_secret());
                    stringRedisTemplate.opsForValue().set("partner_" + partner_id,partner.getPartner_secret());//将库里的partner信息写到redis里
                    log.info("partner_" + partner_id + ":" + partner.getPartner_secret());
                }else {
                    return false;
                }
            }catch (NullPointerException e){
                return false;
            }
        }
        log.info("Authenticate Complete,Return");
        log.info(partner.getPartner_secret() + time + param);
        return token.equals(MD5(partner.getPartner_secret() + time + param,32)) && TimeCheck(time);
    }

    //时间验证
    public boolean TimeCheck(String time){
        Boolean flag = false;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date data = format.parse(time);
            Date currentTime = new Date();
            int timemistake = (int) ((currentTime.getTime() - data.getTime()) / 1000);
            log.info(String.valueOf(timemistake));
            if(timemistake <= 60 && timemistake >= -60){
                flag = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }


    public List<Activation> CreatActivityCode(String secret,int num){
        List<Activation> aclist = new ArrayList<Activation>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        for (int i = 1; i <= num; i ++){
            Activation activation = new Activation();
            activation.setActivation_code(MD5(secret + df.format(new Date()),32));
            activation.setActivation_status(0);
            aclist.add(activation);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return aclist;
    }

    public JSONObject JsonHandle(JSONObject jsonObject){
        for(Object key:jsonObject.keySet()){
            if(jsonObject.get(key) == null){
                jsonObject.remove(key);
            }
        }
        return jsonObject;
    }

    //组装返回json
    public JSONObject SetReturnJson(String return_code,String return_msg, JSONObject reault_data){
        Resultjson resultjson = new Resultjson(return_code,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if(!return_msg.equals("")){
            resultjson.setMsg(return_msg);
        }
        JSONObject result = JSONObject.fromObject(resultjson);//将标准返回字段塞到返回对象里
        result.put("data",reault_data);
        return result;
    }

    //json解析
    public static Map<String, Object> parseJSON2Map(JSONObject json) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 最外层解析
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            // 如果内层还是数组的话，继续解析
            if (v instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                @SuppressWarnings("unchecked")
                Iterator<JSONObject> it = ((JSONArray) v).iterator();
                while (it.hasNext()) {
                    JSONObject json2 = it.next();
                    list.add(parseJSON2Map(json2));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    //参数非空检查
    public boolean CheckParam(Param param,BindingResult bindingResult, Class beanclass){
        if(bindingResult.hasErrors()){
            log.info(param.toString() + "CheckParam Result:" + bindingResult.getAllErrors().get(0).getDefaultMessage());
            return false;
        }else{
            if(beanclass == null){
                return true;
            }
            Map<String, Object> map = new org.apache.commons.beanutils.BeanMap(JSONObject.toBean(param.getParam(),beanclass));
            if(!map.containsValue(null) && !map.containsValue("")){
                log.info(map.values().toString());
                return true;
            }else{
                log.info(map.values().toString());
                return false;
            }
        }
    }

    //解析HTML，返回照片列表
    public String[] dealHtml(String html){
        Document document = Jsoup.parse(html);
        Elements element = document.getElementsByTag("img");
        String pics[] = new String[element.size()];
        Iterator<Element> imgs = document.select("img").iterator();
        int i = 0;
        while (imgs.hasNext()) {
            Element img = imgs.next();
            pics[i] = img.attr("src").split("=")[1].split("/")[1];
            i ++;
        }
        return pics;
    }

    //查询服务端图片列表
    public String[] getfilename(String path){
        // get file list where the path has
        File file = new File(path);
        // get the folder list
        File[] array = file.listFiles();
        log.info(path);
        String filename[] = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                filename[i] = array[i].getName();
//                // only take file name
//                System.out.println("^^^^^" + array[i].getName());
//                // take file path and name
//                System.out.println("#####" + array[i]);
//                // take file path and name
//                System.out.println("*****" + array[i].getPath());
            } else if (array[i].isDirectory()) {
                getfilename(array[i].getPath());
            }
        }
        return filename;
    }

    //匹配服务端和HTML中的图片列表，HTML不存在的图片删除
//    @Async("taskExecutor")
    public int Checkpiclist(String html, String path){
        String[] pics = dealHtml(html);
        String[] files = getfilename(path);
        int flag = 0;
        if(pics.length > 0 && files.length > 0){
            Arrays.sort(pics);
            for(int i = 0; i < files.length; i ++){
                log.info(files[i]);
                if(Arrays.binarySearch(pics,files[i]) < 0){
                    log.info(files[i] + "File Not Found，Delete....");
                    deletefile(path + "/" + files[i]);
                    log.info("Delete File：" + path + "/" + files[i]);
                }
            }
            flag = 1;
        }else{
            delDir(path);
            flag = -1;
        }
        return flag;
    }

    //删除服务端图片
    public void deletefile(String filename){
        File file=new File(filename);
        if(file.exists()&&file.isFile()){
            if(file.delete()){
                log.info(filename);
            }
        }
    }

    //删除服务端图片文件夹
    public void delDir(String path){
        log.info(path);
        File dir=new File(path);
        if(dir.exists()){
            File[] tmp=dir.listFiles();
            log.info( path+ "");
            for(int i=0;i<tmp.length;i++){
                if(tmp[i].isDirectory()){
                    delDir(path+"/"+tmp[i].getName());
                    log.info("Delete Folder：" + path+"/"+tmp[i].getName());
                }
                 else{
                    if(tmp[i].delete()){
                        log.info(tmp[i].getName() + "Delete File Compplete");
                    }
                }
            }
            dir.delete();
        }
    }

    //解析url，加上前缀http://
    public String dealUrl(String url){
        if(url.substring(0,8).equalsIgnoreCase("https://")){
            url = url;
        }else if(url.substring(0,7).equalsIgnoreCase("http://")){
            url = url;
        }else{
            url = "http://" + url;
        }
        return  url;
    }

    //处理评论时间，如果是今日发表，将日期截除，反之将时间截除
    public List<Commet> dealCommet(List<Commet> commetlist){
        for(int i = 0; i < commetlist.size(); i ++){
            if(commetlist.get(i).getCommet_time().split(" ")[0].equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))){
                commetlist.get(i).setCommet_time(commetlist.get(i).getCommet_time().split(" ")[1].substring(0,5));
            }else{
                commetlist.get(i).setCommet_time(commetlist.get(i).getCommet_time().split(" ")[0]);
            }
        }
        return commetlist;
    }

    //处理分享时间，如果是今日发表，将日期截除，反之将时间截除
    public List<Collect> dealCollect(List<Collect> collectlist){
        for(int i = 0; i < collectlist.size(); i ++){
            if(collectlist.get(i).getCollect_time().split(" ")[0].equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))){
                collectlist.get(i).setCollect_time(collectlist.get(i).getCollect_time().split(" ")[1].substring(0,5));
            }else{
                collectlist.get(i).setCollect_time(collectlist.get(i).getCollect_time().split(" ")[0]);
            }
        }
        return collectlist;
    }
}
