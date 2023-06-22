//package com.powerchat.gpt.core.resource_upload;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//
//import java.nio.ByteBuffer;
//import java.util.UUID;
//
//@Service
//@Component
//public class ResourceUploader {
//
//    private static final String BUCKET_NAME = "";
//
//    private static final String AMAZON_BASE_URL = "";
//
//    private static final Region REGION = Region.SA_EAST_1;
//
//    @Value("${aws.accessKeyId}")
//    private String accessKeyId;
//
//    @Value("${aws.secretAccessKey}")
//    private String secretAccessKey;
//
//    public String uploadAssetAndGenerateURLAddress(ByteBuffer fileByteBuffer, ResourceType resourceType) {
//
//        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
//        final StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsCredentials);
//
//        S3Client s3Client = S3Client.builder()
//                .region(REGION)
//                .credentialsProvider(credentialsProvider)
//                .build();
//
//        String objectKey = UUID.randomUUID().toString() + '.' + resourceType.getExtension();
//
//        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                .bucket(BUCKET_NAME)
//                .key(objectKey)
//                .contentType(resourceType.getName())
//                .build();
//
//        s3Client.putObject(putObjectRequest, RequestBody.fromByteBuffer(fileByteBuffer));
//
//        return AMAZON_BASE_URL + objectKey;
//    }
//}
//
