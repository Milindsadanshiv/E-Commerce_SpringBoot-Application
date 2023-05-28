package com.example.ECommerce.Service;

import com.example.ECommerce.DTO.RequestDto.CheckoutCartRequestDto;
import com.example.ECommerce.DTO.RequestDto.ItemRequestDto;
import com.example.ECommerce.DTO.ResponceDto.CartResponseDto;
import com.example.ECommerce.DTO.ResponceDto.ItemResponseDto;
import com.example.ECommerce.DTO.ResponceDto.OrderResponseDto;
import com.example.ECommerce.Exceptions.InvalidCardException;
import com.example.ECommerce.Exceptions.InvalidCustomerException;
import com.example.ECommerce.Models.*;
import com.example.ECommerce.Repository.CardRepository;
import com.example.ECommerce.Repository.CartRepository;
import com.example.ECommerce.Repository.CustomerRepository;
import com.example.ECommerce.Repository.OrderedRepository;
import com.example.ECommerce.Transformer.ItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired OrderService orderService;
    @Autowired
    OrderedRepository orderedRepository;

    @Autowired
    CardRepository cardRespository;

    public CartResponseDto saveCart(Integer customerId, Item item){

        Customer customer = customerRepository.findById(customerId).get();
        Cart cart = customer.getCart();

        int newTotal = cart.getCartTotal() + item.getRequiredQuantity()*item.getProduct().getPrice();
        cart.setCartTotal(newTotal);
        cart.getItems().add(item);
        cart.setNumberOfItems(cart.getItems().size());
        item.setCart(cart);
        Cart savedCart = cartRepository.save(cart);

        CartResponseDto cartResponseDto = CartResponseDto.builder()
                .cartTotal(savedCart.getCartTotal())
                .customerName(customer.getName())
                .numberOfItems(savedCart.getNumberOfItems())
                .build();

        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        for(Item itemEntity: savedCart.getItems()){
            ItemResponseDto itemResponseDto = ItemTransformer.ItemToItemResponseDto(itemEntity);
            itemResponseDtoList.add(itemResponseDto);
        }

        cartResponseDto.setItems(itemResponseDtoList);
        return cartResponseDto;
    }

    public OrderResponseDto checkOutCart(CheckoutCartRequestDto checkoutCartRequestDto) throws Exception, InvalidCustomerException, InvalidCardException {

        Customer customer;
        try{
            customer = customerRepository.findById(checkoutCartRequestDto.getCustomerId()).get();
        }
        catch (Exception e){
            throw new InvalidCustomerException("Customer id is invalid!!!");
        }

        Card card = cardRespository.findByCardNo(checkoutCartRequestDto.getCardNo());
        if(card==null || card.getCvv()!=checkoutCartRequestDto.getCvv() || card.getCustomer()!=customer){
            throw new InvalidCardException("Your card is not valid!!");
        }

        Cart cart = customer.getCart();
        if(cart.getNumberOfItems()==0){
            throw new Exception("Cart is empty!!");
        }

        try{
            Ordered order = orderService.placeOrder(customer,card);  // throw exception if product goes out of stock
            customer.getOrderList().add(order);
            resetCart(cart);
            Ordered savedOrder = orderedRepository.save(order);

            // prepare response dto
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setOrderDate(savedOrder.getOrderDate());
            orderResponseDto.setCardUsed(savedOrder.getCardUsed());
            orderResponseDto.setCustomerName(customer.getName());
            orderResponseDto.setOrderNo(savedOrder.getOrderNo());
            orderResponseDto.setTotalValue(savedOrder.getTotalValue());

            List<ItemResponseDto> items = new ArrayList<>();
            for(Item itemEntity: savedOrder.getItems()){
                ItemResponseDto itemResponseDto = ItemTransformer.ItemToItemResponseDto(itemEntity);
                items.add(itemResponseDto);
            }
            orderResponseDto.setItems(items);
            return orderResponseDto;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void resetCart(Cart cart){

        cart.setCartTotal(0);
        for(Item item: cart.getItems()){
            item.setCart(null);
        }
        cart.setNumberOfItems(0);
        cart.getItems().clear();

    }
    public String removeFromCart(int cartId)
    {
        cartRepository.deleteById(cartId);
        return "Items has been successfully removed from your cart";
    }

    public List<ItemResponseDto> allItemsList(int cartId)
    {
        List<ItemResponseDto> itemList =new ArrayList<>();

        List<Item>items=cartRepository.findById(cartId).get().getItems();
        for (Item item:items)
        {
            ItemResponseDto itemResponseDto=new ItemResponseDto();
            itemResponseDto.setProductName(item.getProduct().getName());
            itemResponseDto.setPriceOfOneItem(item.getProduct().getPrice());
            itemResponseDto.setTotalPrice(item.getCart().getCartTotal());
            itemResponseDto.setQuantity(item.getRequiredQuantity());
            itemList.add(itemResponseDto);
        }

       return itemList;

    }
}
