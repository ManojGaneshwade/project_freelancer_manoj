package com.demo.Freelancing.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.Freelancing.Entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	Optional<Customer> findByMobile(Long mobile);
	
	boolean existsByMobile(Long mobile);

}
