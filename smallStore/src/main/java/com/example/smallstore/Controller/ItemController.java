package com.example.smallstore.Controller;

import com.example.smallstore.Dto.Item.ItemDeleteRequest;
import com.example.smallstore.Dto.Item.ItemRegistRequest;
import com.example.smallstore.Dto.Item.ItemShowRequest;
import com.example.smallstore.Dto.Item.ItemUpdatetRequest;
import com.example.smallstore.Dto.ResponseDto;
import com.example.smallstore.Entity.Item;
import com.example.smallstore.Service.ItemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/item")
public class ItemController {
    private final ItemService itemService;

    // 상품 등록
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "상품 등록 성공")
    })
    @ApiOperation(value = "상품 등록 확인")
    @PostMapping("/registItem")
    public ResponseEntity<ResponseDto> registItem(@RequestBody List<ItemRegistRequest> itemRegistRequest) {
        return itemService.registItem(itemRegistRequest);
    }

    // 상품 보여주기
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "상품 보여주기"),
            @ApiResponse(code = 400, message = "상품이 없음.")
    })
    @ApiOperation(value = "상품 보여주기")
    @GetMapping("/showList")
    public ResponseEntity<List<Item>> showList(@RequestBody ItemShowRequest itemShowRequest) {
        return itemService.showList(itemShowRequest);
    }

    // 상품 수정
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "상품 수정 성공")
    })
    @ApiOperation(value = "상품 수정")
    @PutMapping("/updateItem")
    public ResponseEntity<Item> updateItem(@RequestBody ItemUpdatetRequest itemUpdatetRequest) {
        return itemService.updateItem(itemUpdatetRequest);
    }

    // 상품 삭제
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "상품 삭제 완료")
    })
    @ApiOperation(value = "상품 삭제")
    @DeleteMapping("/deleteItem")
    public ResponseEntity<ResponseDto> deleteItem(@RequestBody ItemDeleteRequest itemDeleteRequest) {
        return itemService.deleteItem(itemDeleteRequest);
    }
}
