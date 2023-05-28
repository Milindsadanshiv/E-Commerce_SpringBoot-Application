package com.example.ECommerce.Transformer;

import com.example.ECommerce.DTO.RequestDto.SellerRequestDto;
import com.example.ECommerce.DTO.ResponceDto.SellerResponseDto;
import com.example.ECommerce.Models.Seller;

public class SellerTransformer {
    public static Seller SellerRequestDtoToSeller(SellerRequestDto sellerRequestDto){

        return Seller.builder()
                .name(sellerRequestDto.getName())
                .age(sellerRequestDto.getAge())
                .emailId(sellerRequestDto.getEmailId())
                .mobNo(sellerRequestDto.getMobNo())
                .build();
    }

    public static SellerResponseDto SellerToSellerResponseDto(Seller seller){

        return SellerResponseDto.builder()
                .name(seller.getName())
                .age(seller.getAge())
                .build();
    }
}
