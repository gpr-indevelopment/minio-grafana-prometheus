package com.example.miniodemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
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
        minioService.enableVersioning("bf7b17c7-cb2c-4b3c-b94b-d2eee7c65b2e");
    }

    @Test
    public void saveMultipleTestObjectsToBucket() throws Exception {
        Map<String, String> metaData = new HashMap<>();
        metaData.put("Test-Key", "Value");
        metaData.put("Test-Key-Two", "Value-Two");
        File tempFile = File.createTempFile("tempFile", ".txt");
        tempFile.deleteOnExit();
        int numberOfSaves = 6;
        for (int i = 0; i < numberOfSaves; i++) {
            minioService.saveFileToBucket(tempFile, "bf7b17c7-cb2c-4b3c-b94b-d2eee7c65b2e", metaData);
        }
    }

    @Test
    public void listObjects() throws Exception {
        minioService.listObjects("bf7b17c7-cb2c-4b3c-b94b-d2eee7c65b2e", true, false);
    }

    @Test
    public void deleteObject() throws Exception {
        minioService.deleteObject("tempFile6122685719368647483.txt", "feb63a9c-67d7-46c9-9a12-29fe384334e3");
    }

    @Test
    public void statObject() throws Exception {
        minioService.getObjectStats("tempFile8899535745467921568.txt", "bf7b17c7-cb2c-4b3c-b94b-d2eee7c65b2e");
    }
}
