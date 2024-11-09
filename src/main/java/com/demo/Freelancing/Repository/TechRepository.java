package com.demo.Freelancing.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.Freelancing.Entity.TechExpert;

public interface TechRepository extends JpaRepository<TechExpert, Long>{
	
	Optional<TechExpert> findByMobile(Long mobile);
	
	boolean existsByMobile(Long mobile);

}
