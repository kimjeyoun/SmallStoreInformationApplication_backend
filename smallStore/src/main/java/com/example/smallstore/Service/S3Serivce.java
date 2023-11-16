package com.example.smallstore.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Serivce {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    // 파일 저장하기
    public boolean uploadImage(MultipartFile fileName) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(fileName.getSize());
            metadata.setContentType(fileName.getContentType());

            // 파일이 s3에 저장된 링크 가져오기 (s3 지금 연결안해놓음)
            //amazonS3.putObject(bucket, originalFilename, s3UploadRequest.getFileName().getInputStream(), metadata);

            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // 파일 보여주기
    public String downloadImage(String originalFilename) {
        String imgUrl = amazonS3.getUrl(bucket, originalFilename).toString();

        return imgUrl;
    }

    // 파일 삭제
    public void deleteImage(String originalFilename)  {
        amazonS3.deleteObject(bucket, originalFilename);
    }
}
