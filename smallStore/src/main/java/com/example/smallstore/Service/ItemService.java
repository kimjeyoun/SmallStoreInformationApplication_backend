package com.example.smallstore.Service;

import com.example.smallstore.Dto.Item.*;
import com.example.smallstore.Dto.ResponseDto;
import com.example.smallstore.Entity.Item;
import com.example.smallstore.Entity.Shop;
import com.example.smallstore.Repository.ItemRepository;
import com.example.smallstore.Repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ShopRepository shopRepository;

    // 상품 등록
    public ResponseEntity registItem(List<ItemRegistRequest> itemRegistRequest) {
        Shop shop = shopRepository.findById(itemRegistRequest.get(0).getShopNum()).orElseThrow();
        List<Item> itemList = itemRegistRequest.stream()
                .map(data -> new Item(shop,data))
                .collect(Collectors.toList());
        itemRepository.saveAll(itemList);

        return ResponseEntity.ok(ResponseDto.successRes(200, "상품 등록 성공"));
    }

    // 상품 보여주기
    public ResponseEntity showList(ItemShowRequest itemShowRequest) {
        List<Item> items = itemRepository.findAllByShopShopNum(itemShowRequest.getShopNum());
        List<ItemResponse> itemList = items.stream()
                .map(data -> new ItemResponse(data))
                .collect(Collectors.toList());

        if(itemList.isEmpty()){
            return ResponseEntity.badRequest().body(ResponseDto.failRes(400, "상품이 없습니다."));
        }

        return ResponseEntity.ok(ResponseDto.res(200, "상품 보여주기", itemList));
    }

    // 마이페이지 수정
    public ResponseEntity updateItem(ItemUpdatetRequest itemUpdatetRequest) {
        Item item = itemRepository.findById(itemUpdatetRequest.getNum()).orElseThrow();
        item.updateItems(itemUpdatetRequest);
        itemRepository.save(item);
        return ResponseEntity.ok(ResponseDto.res(200, "마이페이지 수정 성공", item));
    }

    // 상품 삭제
    public ResponseEntity deleteItem(ItemDeleteRequest itemDeleteRequest) {
        itemRepository.deleteByNum(itemDeleteRequest.getNum());
        return ResponseEntity.ok(ResponseDto.successRes(200, "상품 삭제 완료"));
    }
}
