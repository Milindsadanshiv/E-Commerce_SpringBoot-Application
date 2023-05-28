package com.example.ECommerce.Controller;


import com.example.ECommerce.DTO.RequestDto.CustomerRequestDto;
import com.example.ECommerce.DTO.ResponceDto.CustomerResponseDto;
import com.example.ECommerce.DTO.ResponceDto.ReturnCustomer;
import com.example.ECommerce.Exceptions.MobileNoAlreadyPresentException;
import com.example.ECommerce.Models.Customer;
import com.example.ECommerce.Service.CustomerService;
import com.example.ECommerce.Transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/add")
    public CustomerResponseDto addCustomer(@RequestBody CustomerRequestDto customerRequestDto) throws MobileNoAlreadyPresentException {

        return customerService.addCustomer(customerRequestDto);
    }

    // view all customers

    @GetMapping("/all_customers")
    public List<CustomerResponseDto> all_customers()
    {
        return customerService.all_customers();
    }
    // get a customer by email/mob
    @GetMapping("/getByEmail")
     public ReturnCustomer getCustomerByMail(@RequestParam String email) throws Exception {
         return customerService. getCustomerByMail(email);
     }
    // get all customers whose age is greater than 25
    @GetMapping("/getCustomerAgeAbove25")
    public List<Customer> getCustomerAgeMoreThan25()
    {
        return customerService.getCustomerAgeMoreThan25();
    }
    // get all customers who use VISA card

    // update a customer info by email

    // delete a customer by email/mob
    @DeleteMapping("/delete")
    public String deleteCustomer(@RequestParam String email)
    {
        return customerService.deleteCustomer(email);
    }
}
