//package com.travelcompass.api.global.s3;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.travelcompass.api.global.config.AmazonConfig;
//import com.travelcompass.api.global.entity.Uuid;
//import com.travelcompass.api.global.repository.UuidRepository;
//import java.io.IOException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class AmazonS3Manager {
//
//    private final AmazonS3 amazonS3;
//
//    private final AmazonConfig amazonConfig;
//
//    public String uploadFile(String keyName, MultipartFile file) {
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(file.getSize());
//
//        try {
//            amazonS3.putObject(
//                    new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(),
//                            metadata));
//        } catch (IOException e) {
//            log.error("Failed to upload file to S3 : {}", (Object) e.getStackTrace());
//        }
//
//        return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
//    }
//
//    public String generateLocationKeyName(Uuid uuid) {
//        return amazonConfig.getLocationPath() + '/' + uuid.getUuid();
//    }
//
//}
