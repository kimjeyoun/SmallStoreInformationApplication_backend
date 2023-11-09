package com.example.smallstore.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.smallstore.Dto.Shop.S3.S3DownloadRequest;
import com.example.smallstore.Dto.Shop.S3.S3UploadRequest;
import com.example.smallstore.Entity.User;
import com.example.smallstore.JWT.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Serivce {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    // 파일 저장하기
    public ResponseEntity<String> uploadImage(S3UploadRequest s3UploadRequest, HttpServletRequest request) throws IOException {
        String originalFilename = s3UploadRequest.getFileName().getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(s3UploadRequest.getFileName().getSize());
        metadata.setContentType(s3UploadRequest.getFileName().getContentType());

        // 파일을 저장해주는 메소드
        amazonS3.putObject(bucket, originalFilename, s3UploadRequest.getFileName().getInputStream(), metadata);
        String imgUrl = amazonS3.getUrl(bucket, originalFilename).toString();

        return ResponseEntity.ok("이미지 저장에 성공하였습니다.");
    }

    // 파일 보여주기
    public ResponseEntity<String> downloadImage(S3DownloadRequest s3DownloadRequest) {
        UrlResource urlResource = new UrlResource(amazonS3.getUrl(bucket, s3DownloadRequest.getFileName()));

        return ResponseEntity.ok(urlResource.toString());
    }

    // 파일 삭제
    public void deleteImage(String originalFilename)  {
        amazonS3.deleteObject(bucket, originalFilename);
    }
}
