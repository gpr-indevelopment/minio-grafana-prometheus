package com.example.miniodemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class Tester {

    @Autowired
    private MinioService minioService;

    @Test
    public void createRandomBucket() throws Exception{
        String bucketName = UUID.randomUUID().toString();
        minioService.createBucket(bucketName);
        System.out.println(String.format("Bucket created with name %s!", bucketName));
    }

    @Test
    public void enableVersioning() throws Exception {
        minioService.enableVersioning("ad5f8399-31b8-4eb6-b367-d2ff075f728f");
    }
}
