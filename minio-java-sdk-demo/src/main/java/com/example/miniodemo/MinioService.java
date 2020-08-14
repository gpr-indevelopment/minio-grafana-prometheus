package com.example.miniodemo;

import io.minio.EnableVersioningArgs;
import io.minio.MakeBucketArgs;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class MinioService {

    @Autowired
    private MinioClientConfig minioClientConfig;

    public void createBucket(String bucketName) throws Exception {
        MakeBucketArgs args = MakeBucketArgs.builder().bucket(bucketName).build();
        minioClientConfig.getMinioClient().makeBucket(args);
    }

    public void enableVersioning(String bucketName) throws Exception {
        EnableVersioningArgs versioningArgs = EnableVersioningArgs.builder().bucket(bucketName).build();
        minioClientConfig.getMinioClient().enableVersioning(versioningArgs);
    }

    public void saveFileToBucket(File file, String bucketName) throws Exception {
        InputStream stream = new FileInputStream(file);
        PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName).object(file.getName()).stream(stream, stream.available(), -1).build();
        minioClientConfig.getMinioClient().putObject(args);
    }
}
