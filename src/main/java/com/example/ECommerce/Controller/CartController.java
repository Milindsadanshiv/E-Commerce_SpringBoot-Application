package com.example.ECommerce.Controller;

import com.example.ECommerce.DTO.RequestDto.CheckoutCartRequestDto;
import com.example.ECommerce.DTO.RequestDto.ItemRequestDto;
import com.example.ECommerce.DTO.ResponceDto.CartResponseDto;
import com.example.ECommerce.DTO.ResponceDto.ItemResponseDto;
import com.example.ECommerce.DTO.ResponceDto.OrderResponseDto;
import com.example.ECommerce.Exceptions.InvalidCardException;
import com.example.ECommerce.Exceptions.InvalidCustomerException;
import com.example.ECommerce.Exceptions.InvalidProductException;
import com.example.ECommerce.Models.Item;
import com.example.ECommerce.Service.CartService;
import com.example.ECommerce.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    ItemService itemService;

    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public ResponseEntity addToCart(@RequestBody ItemRequestDto itemRequestDto) throws Exception {

        try{
            Item savedItem = itemService.addItem(itemRequestDto);
            CartResponseDto cartResponseDto = cartService.saveCart(itemRequestDto.getCustomerId(),savedItem);
            return new ResponseEntity(cartResponseDto, HttpStatus.ACCEPTED);
        }
        catch (Exception | InvalidCustomerException | InvalidProductException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkout")
    public OrderResponseDto checkOutCart(@RequestBody CheckoutCartRequestDto checkoutCartRequestDto) throws Exception, InvalidCustomerException, InvalidCardException {

        return cartService.checkOutCart(checkoutCartRequestDto);
    }


    // remove from cart
     @DeleteMapping("/remove")
     public String removeFromCart(@RequestParam int cartId)
     {
         return cartService.removeFromCart(cartId);
     }
    // view all items in cart

    @GetMapping("itemList")
    List<ItemResponseDto> allItemsList(@RequestParam int cartId)
    {
        return cartService.allItemsList(cartId);
    }

    // email sending

    // my email - kunaljindal995@gmail.com
}
