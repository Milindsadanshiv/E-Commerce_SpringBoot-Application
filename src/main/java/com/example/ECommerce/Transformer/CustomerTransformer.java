package com.example.ECommerce.Transformer;

import com.example.ECommerce.DTO.RequestDto.CustomerRequestDto;
import com.example.ECommerce.DTO.ResponceDto.CustomerResponseDto;
import com.example.ECommerce.Models.Customer;

public class CustomerTransformer {
    public static Customer CustomerRequestDtoToCustomer(CustomerRequestDto customerRequestDto){


        return Customer.builder()
                .age(customerRequestDto.getAge())
                .name(customerRequestDto.getName())
                .address(customerRequestDto.getAddress())
                .emailId(customerRequestDto.getEmailId())
                .mobNo(customerRequestDto.getMobNo())
                .build();
    }

    public static CustomerResponseDto CustomerToCustomerResponseDto(Customer customer){

        return CustomerResponseDto.builder()
                .name(customer.getName())
                .message("Welcome" + customer.getName()+ " to Amazon !!!")
                .build();
    }
    public static CustomerResponseDto customer_to_dto(Customer customer)
    {
        return CustomerResponseDto.builder().name(customer.getName()).build();
    }
}
