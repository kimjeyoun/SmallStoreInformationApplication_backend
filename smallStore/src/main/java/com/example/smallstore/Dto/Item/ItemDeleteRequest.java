package com.example.smallstore.Dto.Item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class ItemDeleteRequest {
    @ApiModelProperty(example = "1")
    private Long num;
}
