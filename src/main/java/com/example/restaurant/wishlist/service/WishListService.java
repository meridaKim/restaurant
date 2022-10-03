package com.example.restaurant.wishlist.service;

import com.example.restaurant.naver.NaverClient;
import com.example.restaurant.naver.dto.SearchLocalReq;
import com.example.restaurant.wishlist.dto.WishListDto;
import com.example.restaurant.wishlist.entity.WishListEntity;
import com.example.restaurant.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListService extends WishListEntity {

    private final NaverClient naverClient;
    private final WishListRepository wishListRepository;

    public WishListDto search(String query){
        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);

        var searchLocalRes = naverClient.searchLocal(searchLocalReq);

        if(searchLocalRes.getTotal() >0){
            var localItem = searchLocalRes.getItems().stream().findFirst().get();
            var result = new WishListDto();
            result.setTitle(localItem.getTitle());
            result.setCategory(localItem.getCategory());
            result.setAddress(localItem.getAddress());
            result.setRoadAddress(localItem.getRoadAddress());

            return result;
        }
        return new WishListDto();
    }

    public WishListDto add(WishListDto wishListDto) {
        var entity = dtoToEntity(wishListDto);
        var saveEntity =wishListRepository.save(entity);
        return entityToDto(saveEntity);
    }
    private WishListEntity dtoToEntity(WishListDto wishListDto){
        var entity = new WishListEntity();
        entity.setTitle(wishListDto.getTitle());
        entity.setIndex(wishListDto.getIndex());
        entity.setAddress(wishListDto.getAddress());
        entity.setRoadAddress(wishListDto.getRoadAddress());
        entity.setLocalDateTime(wishListDto.getLocalDateTime());
        return entity;
    }
    private WishListDto entityToDto(WishListEntity wishListEntity) {
        var dto = new WishListDto();
        dto.setIndex(wishListEntity.getIndex());
        dto.setTitle(wishListEntity.getTitle());
        dto.setAddress(wishListEntity.getAddress());
        dto.setRoadAddress(wishListEntity.getRoadAddress());
        dto.setLocalDateTime(wishListEntity.getLocalDateTime());
        return dto;
    }



    public List<WishListDto> findAll() {
        return (List<WishListDto>) wishListRepository.listAll()
                .stream()
                .map(it->entityToDto((WishListEntity) it))
                .collect(Collectors.toList());
    }


}
