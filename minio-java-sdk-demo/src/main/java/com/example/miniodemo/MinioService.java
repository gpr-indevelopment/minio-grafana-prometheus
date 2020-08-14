package com.example.miniodemo;

import io.minio.EnableVersioningArgs;
import io.minio.MakeBucketArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
