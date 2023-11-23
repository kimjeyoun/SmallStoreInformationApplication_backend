package com.example.smallstore.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kotlinx.serialization.descriptors.PrimitiveKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseDto<T> {
    @Schema(description = "상태 코드", example = "200")
    private int status;

    @Schema(description = "상태 메세지", example = "성공하였습니다.")
    private String message;

    @Schema(description = "성공할 시 보낼 데이터")
    private T data;

    public static<T> ResponseDto<T> failRes(final int statusCode, final String responseMessage) {
        return res(statusCode, responseMessage, null);
    }

    public static<T> ResponseDto<T> successRes(final int statusCode, final String responseMessage) {
        return res(statusCode, responseMessage, null);
    }

    public static<T> ResponseDto<T> res(final int statusCode, final String responseMessage, final T t) {
        return ResponseDto.<T>builder()
                .data(t)
                .status(statusCode)
                .message(responseMessage)
                .build();
    }
}
