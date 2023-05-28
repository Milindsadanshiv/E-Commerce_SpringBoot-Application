package com.example.ECommerce.Service;

import com.example.ECommerce.DTO.RequestDto.CustomerRequestDto;
import com.example.ECommerce.DTO.ResponceDto.CustomerResponseDto;
import com.example.ECommerce.DTO.ResponceDto.ReturnCustomer;
import com.example.ECommerce.Exceptions.InvalidCustomerException;
import com.example.ECommerce.Exceptions.MobileNoAlreadyPresentException;
import com.example.ECommerce.Models.Cart;
import com.example.ECommerce.Models.Customer;
import com.example.ECommerce.Repository.CustomerRepository;
import com.example.ECommerce.Transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;

import static com.example.ECommerce.Transformer.CustomerTransformer.customer_to_dto;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) throws MobileNoAlreadyPresentException {

        if(customerRepository.findByMobNo(customerRequestDto.getMobNo())!=null)
            throw new MobileNoAlreadyPresentException("Sorry! Customer already exists!");

        // request dto -> customer
        Customer customer = CustomerTransformer.CustomerRequestDtoToCustomer(customerRequestDto);
        Cart cart = Cart.builder()
                .cartTotal(0)
                .numberOfItems(0)
                .customer(customer)
                .build();
        customer.setCart(cart);



        Customer savedCustomer = customerRepository.save(customer);  // customer and cart

        // prepare response dto
        return CustomerTransformer.CustomerToCustomerResponseDto(savedCustomer);
    }

    public List<CustomerResponseDto> all_customers()
    {
        List<Customer> customers=customerRepository.findAll();
       List<CustomerResponseDto> customerTransformerList=new ArrayList<>();
        for (Customer customer:customers)
        {
            customerTransformerList.add(customer_to_dto(customer));
        }
        return customerTransformerList;
    }

    public String deleteCustomer(String email)
    {
       List<Customer> customers=customerRepository.findAll();
       for (Customer customer:customers)
       {
           if (customer.getEmailId().equals(email))
           {
               customerRepository.delete(customer);
           }
       }
        return "Customer has been removed";
    }

    public ReturnCustomer  getCustomerByMail(String email) throws Exception {

        ReturnCustomer returnCustomer = new ReturnCustomer();
        try {


            List<Customer> customers = customerRepository.findAll();
            for (Customer customer : customers) {
                if (customer.getEmailId().equals(email)) {
                    returnCustomer.setName(customer.getName());
                    returnCustomer.setAge(customer.getAge());
                    returnCustomer.setEmailId(customer.getEmailId());
                    returnCustomer.setMobNo(customer.getMobNo());
                }
            }
        }
        catch (Exception e)
        {
            throw new Exception("email not valid");
        }
        return returnCustomer;
    }
    public List<Customer> getCustomerAgeMoreThan25()
    {
        List<Customer> list=customerRepository.findAll();

        List<Customer> customers=new ArrayList<>();
        for (Customer customer:list)
        {
            Customer c=new Customer();
            if (customer.getAge()>25)
            {
                c.setName(customer.getName());
                c.setAge(customer.getAge());
                c.setEmailId(customer.getEmailId());
                customers.add(c);
            }
        }
        return customers;
    }
}
