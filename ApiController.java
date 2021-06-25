package com.my.controller;

import cn.hutool.core.io.IoUtil;
import com.my.bean.Users;
import com.my.utils.OSSUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.net.www.http.HttpClient;

import java.io.*;

/**
 * @author hutf
 * @createTime 2021年06月15日 22:50:00
 */
@RestController
@CrossOrigin
public class ApiController {

    @Autowired
    private OSSUtils ossUtils;

    @PostMapping("/submitJson")
    public Users submitJson(@RequestBody Users users) {
        System.out.println(users.toString());
        return users;
    }

    @PostMapping("/submitForm")
    public String submitForm(@RequestParam String userName,@RequestParam String password) {
        Users users = new Users();
        users.setPassword(password);
        users.setUserName(userName);
        System.out.println(users.toString());


        return users.toString();
    }


    @GetMapping("/img")
    @ResponseBody
    public String imgApi() throws IOException {

        String imgPath = "src/main/resources/imgPath";
        File file = new File(imgPath);
        if (!file.isDirectory()) {
            boolean newFile = file.createNewFile();
            System.out.println(newFile);
        }

        String img = System.currentTimeMillis() + ".jpg";
        String newUrl = imgPath + "/" + img;

        String downUrl = "https://img12.360buyimg.com/n1/s150x150_jfs/t1/164028/13/19810/141210/607d328aE3b9319a1/b3a14384c998ebd2.jpg";

        downImg(downUrl,newUrl);


        File newFile = new File(newUrl);
        FileInputStream newFileIs = new FileInputStream(newFile);
        String upload = ossUtils.upload(img, newFileIs);

        if (newFile.exists()) {
            newFile.delete();
        }

        return "" + upload;


    }

    private void downImg(String downUrl,String newUrl) throws IOException {
        // 创建一个java代码的浏览器

        CloseableHttpClient aDefault = HttpClients.createDefault();

        HttpGet get = new HttpGet(downUrl);

        // 执行请求
        CloseableHttpResponse response = aDefault.execute(get);

        //HttpStatus http状态代码的常量
        if(HttpStatus.SC_OK  != response.getStatusLine().getStatusCode()){
            throw new RuntimeException("服务器响应错误");
        }

        // 获取服务器response的数据
        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();

        File file = new File(newUrl);
        FileOutputStream fos = new FileOutputStream(file);

        IoUtil.copy(content, fos);

        fos.flush();
        fos.close();
        content.close();
        // 释放链接
        aDefault.close();
    }


}
