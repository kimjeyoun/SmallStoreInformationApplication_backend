package com.example.smallstore.Dto.Item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class ItemShowRequest {
    @ApiModelProperty(example = "1", notes = "가게 pk(자동으로 올라가는 숫자)")
    private Long shopNum;
}
