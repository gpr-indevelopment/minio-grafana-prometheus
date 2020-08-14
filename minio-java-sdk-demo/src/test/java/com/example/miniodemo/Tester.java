package com.example.miniodemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.UUID;

@SpringBootTest
public class Tester {

    @Autowired
    private MinioService minioService;

    @Test
    public void createRandomBucket() throws Exception{
        String bucketName = UUID.randomUUID().toString();
        minioService.createBucket(bucketName);
        System.out.println(String.format("Bucket created with name %s", bucketName));
    }

    @Test
    public void enableVersioning() throws Exception {
        minioService.enableVersioning("feb63a9c-67d7-46c9-9a12-29fe384334e3");
    }

    @Test
    public void saveMultipleTestObjectsToBucket() throws Exception {
        File tempFile = File.createTempFile("tempFile", ".txt");
        tempFile.deleteOnExit();
        int numberOfSaves = 3;
        for (int i = 0; i < numberOfSaves; i++) {
            minioService.saveFileToBucket(tempFile, "feb63a9c-67d7-46c9-9a12-29fe384334e3");
        }
    }
}
