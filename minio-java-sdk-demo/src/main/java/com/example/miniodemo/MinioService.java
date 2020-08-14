package com.example.miniodemo;

import io.minio.*;
import io.minio.messages.Item;
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

    public void listObjects(String bucketName) throws Exception{
        ListObjectsArgs args = ListObjectsArgs.builder().bucket(bucketName).includeVersions(true).build();
        Iterable<Result<Item>> results = minioClientConfig.getMinioClient().listObjects(args);
        for (Result<Item> result : results) {
            Item item = result.get();
            System.out.println(item.lastModified() + "\t" + item.size() + "\t" + item.objectName() + "\t" + item.versionId() + "\t" + item.isDeleteMarker());
        }
    }

    public void deleteObject(String objectName, String bucketName) throws Exception {
        RemoveObjectArgs args = RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build();
        minioClientConfig.getMinioClient().removeObject(args);
    }
}
