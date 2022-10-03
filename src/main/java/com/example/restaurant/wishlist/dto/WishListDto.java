package com.example.restaurant.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WishListDto {
    private Integer index;
    private String title; //가게명
    private String category; // 카테고리
    private String address; //주소
    private String roadAddress; //도로명
    private LocalDateTime localDateTime; //날짜
}
