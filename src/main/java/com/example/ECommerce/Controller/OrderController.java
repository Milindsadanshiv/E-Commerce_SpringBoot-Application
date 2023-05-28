package com.example.ECommerce.Controller;
import com.example.ECommerce.DTO.RequestDto.OrderRequestDto;
import com.example.ECommerce.DTO.ResponceDto.OrderResponseDto;
import com.example.ECommerce.Exceptions.InvalidCardException;
import com.example.ECommerce.Exceptions.InvalidCustomerException;
import com.example.ECommerce.Exceptions.InvalidProductException;
import com.example.ECommerce.Repository.OrderedRepository;
import com.example.ECommerce.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    // API to order and item individually
    @Autowired
    OrderService orderService;
    @Autowired
    private OrderedRepository orderedRepository;

    @PostMapping("/place")
    public OrderResponseDto placeDirectOrder(@RequestBody OrderRequestDto orderRequestDto) throws Exception, InvalidCustomerException, InvalidProductException, InvalidCardException {

        return orderService.placeOrder(orderRequestDto);
    }

    // get all the orders for a customer

    // get recent 5 orders

    // delete an order from the order list

    // select the order and also tell the customer name with the highest total value.
}