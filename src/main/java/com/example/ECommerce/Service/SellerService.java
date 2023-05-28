package com.example.ECommerce.Service;

import com.example.ECommerce.DTO.RequestDto.SellerRequestDto;
import com.example.ECommerce.DTO.ResponceDto.SellerResponseDto;
import com.example.ECommerce.Exceptions.EmailAlreadyPresentException;
import com.example.ECommerce.Models.Seller;
import com.example.ECommerce.Repository.SellerRepository;
import com.example.ECommerce.Transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {
    @Autowired
    SellerRepository sellerRepository;

    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) throws EmailAlreadyPresentException {

//        Seller seller = new Seller();
//        seller.setName(sellerRequestDto.getName());
//        seller.setEmailId(sellerRequestDto.getEmailId());
//        seller.setMobNo(sellerRequestDto.getMobNo());
//        seller.setAge(sellerRequestDto.getAge());

        if (sellerRepository.findByEmailId(sellerRequestDto.getEmailId()) != null)
            throw new EmailAlreadyPresentException("Email Id already registered");

        Seller seller = SellerTransformer.SellerRequestDtoToSeller(sellerRequestDto);
        Seller savedSeller = sellerRepository.save(seller);

        // prepare response Dto
        SellerResponseDto sellerResponseDto = SellerTransformer.SellerToSellerResponseDto(savedSeller);
        return sellerResponseDto;
    }
}
