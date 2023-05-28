package com.example.ECommerce.Service;

import com.example.ECommerce.DTO.RequestDto.ItemRequestDto;
import com.example.ECommerce.Enum.ProductStatus;
import com.example.ECommerce.Exceptions.InvalidCustomerException;
import com.example.ECommerce.Exceptions.InvalidProductException;
import com.example.ECommerce.Models.Customer;
import com.example.ECommerce.Models.Item;
import com.example.ECommerce.Models.Product;
import com.example.ECommerce.Repository.CustomerRepository;
import com.example.ECommerce.Repository.ItemRepository;
import com.example.ECommerce.Repository.ProductRepository;
import com.example.ECommerce.Transformer.ItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    public Item addItem(ItemRequestDto itemRequestDto) throws Exception, InvalidCustomerException, InvalidProductException {

        Customer customer;
        try{
            customer = customerRepository.findById(itemRequestDto.getCustomerId()).get();
        }
        catch (Exception e){
            throw new InvalidCustomerException("Customer Id is invalid !!");
        }

        Product product;
        try{
            product = productRepository.findById(itemRequestDto.getProductId()).get();
        }
        catch(Exception e){
            throw new InvalidProductException("Product doesn't exist");
        }

        if(itemRequestDto.getRequiredQuantity()>product.getQuantity() || product.getProductStatus()!= ProductStatus.AVAILABLE){
            throw new Exception("Product out of Stock");
        }

        Item item = ItemTransformer.ItemRequestDtoToItem(itemRequestDto);
        // item.setCart(customer.getCart());
        item.setProduct(product);

        product.getItemList().add(item);
        return itemRepository.save(item);
    }
}
