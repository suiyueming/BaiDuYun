package com.suiyue;

import com.baidubce.Protocol;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BucketSummary;
import com.baidubce.services.bos.model.ListBucketsResponse;
import com.baidubce.services.bos.model.PutObjectResponse;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wcsn on 16-5-24.
 */
public class Sample {
    public static void main(String[] args) {
        String ACCESS_KEY_ID = "7965068fae754ae0ba3e14eb18153e1d";// 用户的Access Key ID
        String SECRET_ACCESS_KEY = "8c66ed40ef1c487dbd86fc6b64ecb85c";// 用户的Secret Access Key
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
        BosClient client = new BosClient(config);
//        try {
//            PutObject(-2,client, "suiyue", "file");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        for (int i=0; i<10000; i++) {
            try {
                PutObject(i,client, "suiyue", "file_" + i);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public static void listBuckets (BosClient client) {
        // 获取用户的Bucket列表
        ListBucketsResponse lbs = client.listBuckets();

        // 遍历Bucket
        for (BucketSummary bucket : lbs.getBuckets()) {
            System.out.println(bucket.getName());
        }
    }
    public static void PutObject(int count, BosClient client, String bucketName, String objectKey) throws FileNotFoundException {
        // 获取指定文件
        File file = new File("result.txt");
        // 以文件形式上传Object
        PutObjectResponse putObjectFromFileResponse = client.putObject(bucketName, objectKey, file);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        String timeString = dateFormat.format(now);
        String content = count + " " + timeString + " " + putObjectFromFileResponse.getETag() + "\n";
        appendFile("result_baidu.txt", content);
    }

    public static void appendFile(String fileName, String content) {
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
