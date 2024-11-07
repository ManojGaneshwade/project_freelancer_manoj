package com.demo.Freelancing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.Freelancing.Entity.TechExpert;

public interface TechRepository extends JpaRepository<TechExpert, Long>{

}
