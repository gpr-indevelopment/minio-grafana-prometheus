package com.example.miniodemo;

import io.minio.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

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

    public void saveFileToBucket(File file, String bucketName, Map<String, String> metaData) throws Exception {
        InputStream stream = new FileInputStream(file);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(file.getName())
                .userMetadata(metaData)
                .stream(stream, stream.available(), -1)
                .build();
        minioClientConfig.getMinioClient().putObject(args);
    }

    public void listObjects(String bucketName, boolean includeMetaData, boolean includeVersion) throws Exception{
        ListObjectsArgs args = ListObjectsArgs.builder().bucket(bucketName).includeUserMetadata(includeMetaData).includeVersions(includeVersion).build();
        Iterable<Result<Item>> results = minioClientConfig.getMinioClient().listObjects(args);
        for (Result<Item> result : results) {
            Item item = result.get();
            System.out.println(item.lastModified() + "\t" + item.size() + "\t" + item.objectName() + "\t" + item.versionId() + "\t" + item.isDeleteMarker());
            if(Objects.nonNull(item.userMetadata())) {
                item.userMetadata().forEach((key, value) -> System.out.println("\t Key=" + key + " Value=" + value));
            }
        }
    }

    public void deleteObject(String objectName, String bucketName) throws Exception {
        RemoveObjectArgs args = RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build();
        minioClientConfig.getMinioClient().removeObject(args);
    }

    public void getObjectStats(String objectName, String bucketName) throws Exception{
        StatObjectArgs args = StatObjectArgs.builder().bucket(bucketName).object(objectName).build();
        ObjectStat stat = minioClientConfig.getMinioClient().statObject(args);
    }

    public void setBucketLifecycle(String bucketName, String xmlConfig) throws Exception {
        SetBucketLifeCycleArgs args = SetBucketLifeCycleArgs.builder().bucket(bucketName).config(xmlConfig).build();
        minioClientConfig.getMinioClient().setBucketLifeCycle(args);
    }

    public void getObjectFromSQL(String sqlExpression) throws Exception {
        SelectObjectContentArgs args = SelectObjectContentArgs.builder().sqlExpression(sqlExpression).build();
        minioClientConfig.getMinioClient().selectObjectContent(args);
    }
}
