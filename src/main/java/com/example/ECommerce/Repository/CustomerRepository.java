package com.example.ECommerce.Repository;

import com.example.ECommerce.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByMobNo(String mobNo);


}
