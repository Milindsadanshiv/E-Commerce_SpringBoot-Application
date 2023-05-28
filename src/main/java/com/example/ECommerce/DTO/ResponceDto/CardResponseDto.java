package com.example.ECommerce.DTO.ResponceDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class CardResponseDto {

    String cardNo;

    String customerName;
}
