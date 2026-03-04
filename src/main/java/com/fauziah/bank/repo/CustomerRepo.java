package com.fauziah.bank.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fauziah.bank.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, String>{

    Optional<Customer> findByNumberId(String numberId);
    Optional<Customer> deleteByNumberId(String numberId);

}
