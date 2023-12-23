package com.driver.repository;

import com.driver.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.driver.model.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

    @Query(value = "select * from customer;",nativeQuery = true)
    List<Customer> findCustomers();
}
