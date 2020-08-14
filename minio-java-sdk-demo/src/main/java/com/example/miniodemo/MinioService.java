package com.example.miniodemo;

import io.minio.MakeBucketArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MinioService {

    @Autowired
    private MinioClientConfig minioClientConfig;

    public String createBucket() {
        try {
            String bucketName = UUID.randomUUID().toString();
            MakeBucketArgs args = MakeBucketArgs.builder().bucket(bucketName).build();
            minioClientConfig.getMinioClient().makeBucket(args);
            return bucketName;
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong when creating a bucket on MinIO.");
        }
    }
}
