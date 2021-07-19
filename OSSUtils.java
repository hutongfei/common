package com.my.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

@Component
public class OSSUtils {

    private OSSUtils() {

    }

    //定义日志
    private static final Logger log = LoggerFactory.getLogger(OSSUtils.class);
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    //"<yourAccessKeyId>"
    private static String accessKeyId = "XXXXXXXXXXXXXXXXXXX";
    //"<yourAccessKeySecret>"
    private static String accessKeySecret = "XXXXXXXXXXXXXXX";
    //OSS 储存空间 bucket名字 "<yourBucketName>"
    private static String bucketName = "XXXXXXXXXXXXXXX";


    private volatile static OSS ossClient;

    public String upload(String name,InputStream is) {

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 创建PutObjectRequest对象。
// 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, name, is);

// 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
// ObjectMetadata metadata = new ObjectMetadata();
// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
// metadata.setObjectAcl(CannedAccessControlList.Private);
// putObjectRequest.setMetadata(metadata);

// 上传字符串。
        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
        System.out.println(putObjectResult.getETag());
        System.out.println(putObjectResult.getVersionId());

// 关闭OSSClient。
        ossClient.shutdown();

        return "https://hutf-bucket.oss-cn-beijing.aliyuncs.com/" + name;
    }

}
