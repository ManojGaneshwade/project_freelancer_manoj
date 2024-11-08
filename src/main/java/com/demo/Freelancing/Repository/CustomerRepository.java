package com.demo.Freelancing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.Freelancing.Entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
