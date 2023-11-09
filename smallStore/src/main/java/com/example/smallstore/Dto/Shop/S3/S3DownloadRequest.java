package com.example.smallstore.Dto.Shop.S3;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class S3DownloadRequest {
    @ApiModelProperty(value = "파일 타입", example = "itemImg/shopPicture/shopLogo")
    private String fileType;

    @ApiModelProperty(value = "이미지 파일")
    private String fileName;
}

