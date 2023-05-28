package com.example.ECommerce.Transformer;

import com.example.ECommerce.DTO.RequestDto.ItemRequestDto;
import com.example.ECommerce.DTO.ResponceDto.ItemResponseDto;
import com.example.ECommerce.Models.Item;

public class ItemTransformer {

        public static Item ItemRequestDtoToItem(ItemRequestDto itemRequestDto){
            return Item.builder()
                    .requiredQuantity(itemRequestDto.getRequiredQuantity())
                    .build();
        }

        public static ItemResponseDto ItemToItemResponseDto(Item item){

            return ItemResponseDto.builder()
                    .priceOfOneItem(item.getProduct().getPrice())
                    .productName(item.getProduct().getName())
                    .quantity(item.getRequiredQuantity())
                    .totalPrice(item.getRequiredQuantity()*item.getProduct().getPrice())
                    .build();
        }
}
