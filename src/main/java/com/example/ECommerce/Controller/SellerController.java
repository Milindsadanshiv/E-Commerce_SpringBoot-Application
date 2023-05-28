package com.example.ECommerce.Controller;
import com.example.ECommerce.DTO.RequestDto.SellerRequestDto;
import com.example.ECommerce.DTO.ResponceDto.SellerResponseDto;
import com.example.ECommerce.Exceptions.EmailAlreadyPresentException;
import com.example.ECommerce.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @PostMapping("/add")
    public ResponseEntity addSeller(@RequestBody SellerRequestDto sellerRequestDto){
        try{
            SellerResponseDto sellerResponseDto = sellerService.addSeller(sellerRequestDto);
            return new ResponseEntity(sellerResponseDto, HttpStatus.CREATED);
        }
        catch (EmailAlreadyPresentException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    //  GET a seller by email

    // get by id

    // get all seller

    // update seller info based on email id

    // delete a seller based on email

    //delete by id

    // get all sellers of a particular age
}