package com.example.ECommerce.DTO.ResponceDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public  class ReturnCustomer {
   private String name;
    private int age;
    private String emailId;
    private String mobNo;
}
